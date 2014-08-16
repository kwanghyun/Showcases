package concurrency.book;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import concurrency.annotations.Book;
import concurrency.annotations.GuardedBy;
import concurrency.annotations.ThreadSafe;
import concurrency.showcases.BlockingQueue;

@Book(name = "Java Concurrency in Practice", chapter = "Chapter 7")
public class TaskCancellation {
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 7.1. Task Cancellation
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/*7.1. Using a Volatile Field to Hold Cancellation State.*/
	@ThreadSafe
	public static class PrimeGenerator implements Runnable {
		@GuardedBy("this")
		private final List<BigInteger> primes = new ArrayList<BigInteger>();
		private volatile boolean cancelled;

		public void run() {
			BigInteger p = BigInteger.ONE;
			while (!cancelled) {
				p = p.nextProbablePrime();
				synchronized (this) {
					primes.add(p);
				}
			}
		}

		public void cancel() {
			cancelled = true;
		}

		public synchronized List<BigInteger> get() {
			return new ArrayList<BigInteger>(primes);
		}
	}

	static List<BigInteger> goForPrimes(int millisec) throws InterruptedException {
		PrimeGenerator generator = new PrimeGenerator();
		new Thread(generator).start();
		try {
			Thread.sleep(millisec);
		} finally {
			// he cancel method is called from a finally block to ensure that
			// the prime generator is cancelled even if the the call to sleep is
			// interrupted. If cancel were not called, the prime-seeking thread
			// would run forever, consuming CPU cycles and preventing the JVM
			// from exiting.
			generator.cancel();
		}
		return generator.get();
	}
	
	
	/*
	 * 7.3. Unreliable Cancellation that can Leave Producers Stuck in a Blocking
	 * Operation. Don’t Do this.
	 */
	
	// BrokenPrimeProducer in Listing 7.3 illustrates this problem. The producer
	// thread generates primes and places them on a blocking queue. If the
	// producer gets ahead of the consumer, the queue will fill up and put will
	// block. What happens if the consumer tries to cancel the producer task
	// while it is blocked in put? It can call cancel which will set the
	// cancelled flag—but the producer will never check the flag because it will
	// never emerge from the blocking put (because the consumer has stopped
	// retrieving primes from the queue).
	class BrokenPrimeProducer extends Thread {
		private final BlockingQueue<BigInteger> queue;
		private volatile boolean cancelled = false;

		BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
			this.queue = queue;
		}

		public void run() {
			try {
				BigInteger p = BigInteger.ONE;
				while (!cancelled)
					queue.put(p = p.nextProbablePrime());
			} catch (InterruptedException consumed) {
			}
		}

		public void cancel() {
			cancelled = true;
		}
	}

	void consumePrimes() throws InterruptedException {
		BlockingQueue<BigInteger> primes = new BlockingQueue<BigInteger>(1);// ...;
		BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
		producer.start();
		try {
			while (needMorePrimes())
				consume(primes.take());
		} finally {
			producer.cancel();
		}
	}
	
	private void consume(BigInteger take) {}
	private boolean needMorePrimes() {return false;}
	
	
	/*7.5. Using Interruption for Cancellation.*/
	
	// Calling interrupt does not necessarily stop the target thread from doing
	// what it is doing; it merely delivers the message that interruption has
	// been requested.
	// Interruption is usually the most sensible way to implement cancellation.
	
	// Because each thread has its own interruption policy, you should not
	// interrupt a thread unless you know what interruption means to that
	// thread.
	class PrimeProducer extends Thread {
		private final BlockingQueue<BigInteger> queue;

		PrimeProducer(BlockingQueue<BigInteger> queue) {
			this.queue = queue;
		}

		public void run() {
			try {
				BigInteger p = BigInteger.ONE;
				while (!Thread.currentThread().isInterrupted())
					queue.put(p = p.nextProbablePrime());
			} catch (InterruptedException consumed) {
				/* Allow thread to exit */
			}
		}

		public void cancel() {
			interrupt();
		}
	}
	
	
	/*7.7. Noncancelable Task that Restores Interruption Before Exit.*/
	
	// Only code that implements a thread’s interruption policy may swallow an
	// interruption request. General-purpose task and library code should never
	// swallow interruption requests.

	public Task getNextTask(BlockingQueue<Task> queue) {
		boolean interrupted = false;
		try {
			while (true) {
				try {
					return queue.take();
				} catch (InterruptedException e) {
					interrupted = true;
					// fall through and retry
				}
			}
		} finally {
			if (interrupted)
				Thread.currentThread().interrupt();
		}
	}
	
	//Mock-ups
	class Task{} 

	
	/*7.8. Scheduling an Interrupt on a Borrowed Thread. Don’t Do this.*/
	
	// This is an appealingly simple approach, but it violates the rules: you
	// should know a thread’s interruption policy before interrupting it. Since
	// timedRun can be called from an arbitrary thread, it cannot know the
	// calling thread’s interruption policy. If the task completes before the
	// timeout, the cancellation task that interrupts the thread in which
	// timedRun was called could go off after timedRun has returned to its
	// caller. We don’t know what code will be running when that happens, but
	// the result won’t be good.
	private static final ScheduledExecutorService cancelExec = null;// ...;

	public static void timedRun(Runnable r, long timeout, TimeUnit unit) {
		final Thread taskThread = Thread.currentThread();
		cancelExec.schedule(new Runnable() {
			public void run() {
				taskThread.interrupt();
			}
		}, timeout, unit);
		r.run();
	}
	
	
	/*7.9. Interrupting a Task in a Dedicated Thread.*/
	
	// The thread created to run the task can have its own execution policy, and
	// even if the task doesn’t respond to the interrupt, the timed run method
	// can still return to its caller. After starting the task thread, timedRun
	// executes a timed join with the newly created thread. After join returns,
	// it checks if an exception was thrown from the task and if so, rethrows it
	// in the thread calling timedRun. The saved Throwable is shared between the
	// two threads, and so is declared volatile to safely publish it from the
	// task thread to the timedRun thread.
	public static void timedRun2(final Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
		class RethrowableTask implements Runnable {
			private volatile Throwable t;

			public void run() {
				try {
					r.run();
				} catch (Throwable t) {
					this.t = t;
				}
			}

			void rethrow() {
				if (t != null)
					throw launderThrowable(t);
			}
		}

		RethrowableTask task = new RethrowableTask();
		final Thread taskThread = new Thread(task);
		taskThread.start();
		cancelExec.schedule(new Runnable() {
			public void run() {
				taskThread.interrupt();
			}
		}, timeout, unit);
		// This version addresses the problems in the previous examples, but
		// because it relies on a timed join, it shares a deficiency with join:
		// we don’t know if control was returned because the thread exited
		// normally or because the join timed out.
		
		// This is a flaw in the Thread API, because whether or not the join
		// completes successfully has memory visibility consequences in the Java
		// Memory Model, but join does not return a status indicating whether it
		// was successful.
		taskThread.join(unit.toMillis(timeout));
		task.rethrow();
	}
	
	
	/*7.9. Interrupting a Task in a Dedicated Thread.*/
	public static void timedRun3(Runnable r, long timeout, TimeUnit unit)
			throws InterruptedException {
		
		ScheduledExecutorService taskExec = null;// ...;
		Future<?> task = taskExec.submit(r);
		try {
			task.get(timeout, unit);
		} catch (TimeoutException e) {
		} catch (ExecutionException e) {
			// exception thrown in task; rethrow
			throw launderThrowable(e.getCause());
		} finally {
			// Harmless if task already completed
			
			// mayInterruptIfRunning : true if the thread executing this task
			// should be interrupted; otherwise, in-progress tasks are allowed
			// to complete
			task.cancel(true); 
			// You should not interrupt a pool thread directly when attempting
			// to cancel a task, because you won’t know what task is running
			// when the interrupt request is delivered—do this only through the
			// task’s Future. This is yet another reason to code tasks to treat
			// interruption as a cancellation request: then they can be
			// cancelled through their Futures.
		}
	}

	// When Future.get throws InterruptedException or TimeoutException and you
	// know that the result is no longer needed by the program, cancel the task
	// with Future.cancel.

	
	/*7.11. Encapsulating Nonstandard Cancellation in a Thread by Overriding Interrupt.*/
	public class ReaderThread extends Thread {
		private final Socket socket;
		private final InputStream in;

		public ReaderThread(Socket socket) throws IOException {
			this.socket = socket;
			this.in = socket.getInputStream();
		}

		public void interrupt() {
			try {
				socket.close();
			} catch (IOException ignored) {
			} finally {
				super.interrupt();
			}
		}

		public void run() {
			try {
				byte[] buf = new byte[200];
				while (true) {
					int count = in.read(buf);
					if (count < 0)
						break;
					else if (count > 0)
						processBuffer(buf, count);
				}
			} catch (IOException e) { /* Allow thread to exit */
			}
		}

		private void processBuffer(byte[] buf, int count) {
		}
	}
	
	
	/*7.11. Encapsulating Nonstandard Cancellation in a Thread by Overriding Interrupt.*/
	
	// CancellableTask in Listing 7.12 defines a CancellableTask interface that
	// extends Callable and adds a cancel method and a newTask factory method
	// for constructing a RunnableFuture. 
	
	public interface CancellableTask<T> extends Callable<T> {
	    void cancel();
	    RunnableFuture<T> newTask();
	}

	// CancellingExecutor extendsThreadPoolExecutor, and overrides newTaskFor to
	// let a CancellableTask create its own Future.
	@ThreadSafe
	public class CancellingExecutor extends ThreadPoolExecutor {
		
		public CancellingExecutor(int corePoolSize, int maximumPoolSize,
				long keepAliveTime, TimeUnit unit,
				java.util.concurrent.BlockingQueue<Runnable> workQueue,
				ThreadFactory threadFactory, RejectedExecutionHandler handler) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
					threadFactory, handler);
			// TODO Auto-generated constructor stub
		}

		// ...
		protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
			if (callable instanceof CancellableTask)
				return ((CancellableTask<T>) callable).newTask();
			else
				return super.newTaskFor(callable);
		}
	}

	// SocketUsingTask implements CancellableTask and defines Future.cancel to
	// close the socket as well as call super.cancel. If a SocketUsingTask is
	// cancelled through its Future, the socket is closed and the executing
	// thread is interrupted. This increases the task’s responsiveness to
	// cancellation: not only can it safely call interruptible blocking methods
	// while remaining responsive to cancellation, but it can also call blocking
	// socket I/O methods.
	public abstract class SocketUsingTask<T> implements CancellableTask<T> {
		@GuardedBy("this")
		private Socket socket;

		protected synchronized void setSocket(Socket s) {
			socket = s;
		}

		public synchronized void cancel() {
			try {
				if (socket != null)
					socket.close();
			} catch (IOException ignored) {
			}
		}

		public RunnableFuture<T> newTask() {
			return new FutureTask<T>(this) {
				public boolean cancel(boolean mayInterruptIfRunning) {
					try {
						SocketUsingTask.this.cancel();
					} finally {
						return super.cancel(mayInterruptIfRunning);
					}
				}
			};
		}
	}

	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 7.2. Stopping a Thread-based Service
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/*7.13. Producer-Consumer Logging Service with No Shutdown Support.*/
	
	// This is a multiple-producer, single-consumer design: any activity calling
	// log is acting as a producer, and the background logger thread is the
	// consumer. If the logger thread falls behind, the BlockingQueue eventually
	// blocks the producers until the logger thread catches up.
	public class LogWriter {
	    private final LinkedBlockingQueue<String> queue;
	    private final LoggerThread logger;

	    int CAPACITY = 100; //Mockup
	    
	    public LogWriter(Writer writer) {
	        this.queue = new LinkedBlockingQueue<String>(CAPACITY);
	        this.logger = new LoggerThread(writer);
	    }

	    public void start() { logger.start(); }

	    public void log(String msg) throws InterruptedException {
	        queue.put(msg);
	    }

	    private class LoggerThread extends Thread { 
	        private final PrintWriter writer = null; //Mockup
	        public LoggerThread(Writer writer2) {} //Mockup
			public void run() {
	            try {
	                while (true)
	                   writer.println(queue.take());
	            } catch(InterruptedException ignored) {
	            } finally {
	                writer.close();
	            }
	        }
	    }
	    
		// Cancelling a producer & consumer activity requires cancelling both the
		// producers and the consumers. Interrupting the logger thread deals with
		// the consumer, but because the producers in this case are not dedicated
		// threads, cancelling them is harder.
	    
	    
	    /*Listing 7.14. Unreliable Way to Add Shutdown Support to the Logging Service.*/
	    
		// Another approach to shutting down LogWriter would be to set a
		// “shutdown requested” flag to prevent further messages from being
		// submitted, as shown in Listing 7.14. The consumer could then drain
		// the queue upon being notified that shutdown has been requested,
		// writing out any pending messages and unblocking any producers blocked
		// in log. However, this approach has race conditions that make it
		// unreliable. The implementation of log is a check-then-act sequence:
		// producers could observe that the service has not yet been shut down
		// but still queue messages after the shutdown, again with the risk that
		// the producer might get blocked in log and never become unblocked.
	    
		public void log2(String msg) throws InterruptedException {
		    boolean shutdownRequested = false; //Mockup
		    
			if (!shutdownRequested)
		        queue.put(msg);
		    else
		        throw new IllegalStateException("logger is shut down");
		}
		
		// The way to provide reliable shutdown for LogWriter is to fix the race
		// condition, which means making the submission of a new log message
		// atomic. But we don’t want to hold a lock while trying to enqueue the
		// message, since put could block.
	}
	
	public class LogService {
		private final BlockingQueue<String> queue = null;
		private final LoggerThread loggerThread = null;
		private final PrintWriter writer = null;

		@GuardedBy("this")
		private boolean isShutdown;
		@GuardedBy("this")
		private int reservations;

		public void start() {
			loggerThread.start();
		}

		public void stop() {
			synchronized (this) {
				isShutdown = true;
			}
			loggerThread.interrupt();
		}

		public void log(String msg) throws InterruptedException {
			synchronized (this) {
				if (isShutdown)
					throw new IllegalStateException("");
				++reservations;
			}
			queue.put(msg);
		}

		private class LoggerThread extends Thread {
			public void run() {
				try {
					while (true) {
						try {
							synchronized (LogService.this) {
								if (isShutdown && reservations == 0)
									break;
							}
							String msg = queue.take();
							synchronized (LogService.this) {
								--reservations;
							}
							writer.println(msg);
						} catch (InterruptedException e) { /* retry */
						}
					}
				} finally {
					writer.close();
				}
			}
		}
	}


	/* 7.16. Logging Service that Uses an ExecutorService. */
	
	public class LogService2 {
	    private final ExecutorService exec = Executors.newSingleThreadExecutor();
	    private final PrintWriter writer = null;
//	    ...
	    public void start() { }

	    public void stop() throws InterruptedException {
	        try {
	            exec.shutdown();
	            exec.awaitTermination(5, TimeUnit.SECONDS);
	        } finally {
	            writer.close();
	        }
	    }
	    public void log(String msg) {
	        try {
	            exec.execute(new WriteTask(msg));
	        } catch (RejectedExecutionException ignored) { }
	    }
	}
	
	class WriteTask extends Task implements Runnable {public WriteTask(String msg) {}public void run() {}}
	
	
	
	/*7.2.3. Poison Pills*/
	
	// Another way to convince a producer-consumer service to shut down is with
	// a poison pill: a recognizable object placed on the queue that means “when
	// you get this, stop.” With a FIFO queue, poison pills ensure that
	// consumers finish the work on their queue before shutting down, since any
	// work submitted prior to submitting the poison pill will be retrieved
	// before the pill; producers should not submit any work after putting a
	// poison pill on the queue.
	
	public class IndexingService {
		private final File POISON = new File("");
		private final IndexerThread consumer = new IndexerThread();
		private final CrawlerThread producer = new CrawlerThread();
		private final BlockingQueue<File> queue = null;
		private final FileFilter fileFilter = null;
		private final File root = null;

		class CrawlerThread extends Thread {
			public void run() {
				try {
					crawl(root);
				} catch (InterruptedException e) { /* fall through */
				} finally {
					while (true) {
						try {
							queue.put(POISON);
							break;
						} catch (InterruptedException e1) { /* retry */
						}
					}
				}
			}
			private void crawl(File root) throws InterruptedException{}
		}

		class IndexerThread extends Thread {
			public void run() {
				try {
					while (true) {
						File file = queue.take();
						if (file == POISON)
							break;
						else
							indexFile(file);
					}
				} catch (InterruptedException consumed) {
				}
			}
			private void indexFile(File file) {}
		}

		public void start() {
			producer.start();
			consumer.start();
		}

		public void stop() {
			producer.interrupt();
		}

		public void awaitTermination() throws InterruptedException {
			consumer.join();
		}
	}	
	
	
	/*7.20. Using a Private Executor Whose Lifetime is Bounded by a Method Call.*/
	
	// If a method needs to process a batch of tasks and does not return until
	// all the tasks are finished, it can simplify service lifecycle management
	// by using a private Executor whose lifetime is bounded by that method.
	// (The invokeAll and invokeAny methods can often be useful in such
	// situations.)
	//
	// The checkMail method in Listing 7.20 checks for new mail in parallel on a
	// number of hosts. It creates a private executor and submits a task for
	// each host: it then shuts down the executor and waits for termination,
	// which occurs when all the mail-checking tasks have completed
	
	boolean checkMail(Set<String> hosts, long timeout, TimeUnit unit)
			throws InterruptedException {
		
		// Creates a thread pool that creates new threads as needed, but will
		// reuse previously constructed threads when they are available. These
		// pools will typically improve the performance of programs that execute
		// many short-lived asynchronous tasks
		ExecutorService exec = Executors.newCachedThreadPool();
		
		// The reason an AtomicBoolean is used instead of a volatile boolean is that
		// in order to access the hasNewMail flag from the inner Runnable, it would
		// have to be final, which would preclude modifying it.
		final AtomicBoolean hasNewMail = new AtomicBoolean(false);
		
		try {
			for (final String host : hosts)
				exec.execute(new Runnable() {
					public void run() {
						if (checkMail(host))
							hasNewMail.set(true);
					}
				});
		} finally {
			exec.shutdown();
			exec.awaitTermination(timeout, unit);
		}
		return hasNewMail.get();
	}
	protected boolean checkMail(String host) {return false;} //MockUp

	
	 /*7.21. ExecutorService that Keeps Track of Cancelled Tasks After Shutdown.*/
	
	// TrackingExecutor in Listing 7.21 shows a technique for determining which
	// tasks were in progress at shutdown time. By encapsulating an
	// ExecutorService and instrumenting execute (and similarly submit, not
	// shown) to remember which tasks were cancelled after shutdown,
	// TrackingExecutor can identify which tasks started but did not complete
	// normally. After the executor terminates, getCancelledTasks returns the
	// list of cancelled tasks. In order for this technique to work, the tasks
	// must preserve the thread’s interrupted status when they return, which
	// well behaved tasks will do anyway.
	public class TrackingExecutor extends AbstractExecutorService {
		private final ExecutorService exec = Executors.newCachedThreadPool();
		private final Set<Runnable> tasksCancelledAtShutdown = Collections
				.synchronizedSet(new HashSet<Runnable>());

		public TrackingExecutor(ExecutorService newCachedThreadPool) {}

		// ...
		public List<Runnable> getCancelledTasks() {
			if (!exec.isTerminated())
				throw new IllegalStateException("IllegalStateException");
			return new ArrayList<Runnable>(tasksCancelledAtShutdown);
		}

		public void execute(final Runnable runnable) {
			exec.execute(new Runnable() {
				public void run() {
					try {
						runnable.run();
					} finally {
						if (isShutdown()
								&& Thread.currentThread().isInterrupted())
							tasksCancelledAtShutdown.add(runnable);
					}
				}
			});
		}
	    // delegate other ExecutorService methods to exec
		public void shutdown() {}
		public List<Runnable> shutdownNow() {return null;}
		public boolean isShutdown() {return false;}
		public boolean isTerminated() {return false;}
		public boolean awaitTermination(long timeout, TimeUnit unit)throws InterruptedException {return false;}
	}

	// if a crawler must be shut down we might want to save its state so it can
	// be restarted later. CrawlTask provides a getPage method that identifies
	// what page it is working on. When the crawler is shut down, both the tasks
	// that did not start and those that were cancelled are scanned and their
	// URLs recorded, so that page-crawling tasks for those URLs can be added to
	// the queue when the crawler restarts.
	public abstract class WebCrawler {
		private volatile TrackingExecutor exec;
		@GuardedBy("this")
		private final Set<URL> urlsToCrawl = new HashSet<URL>();

		// ...
		public synchronized void start() {
			exec = new TrackingExecutor(Executors.newCachedThreadPool());
			for (URL url : urlsToCrawl)
				submitCrawlTask(url);
			urlsToCrawl.clear();
		}

		public synchronized void stop() throws InterruptedException {
			try {
				saveUncrawled(exec.shutdownNow());
				if (exec.awaitTermination(5, TimeUnit.SECONDS))
					saveUncrawled(exec.getCancelledTasks());
			} finally {
				exec = null;
			}
		}

		protected abstract List<URL> processPage(URL url);

		private void saveUncrawled(List<Runnable> uncrawled) {
			for (Runnable task : uncrawled)
				urlsToCrawl.add(((CrawlTask) task).getPage());
		}

		private void submitCrawlTask(URL u) {
			exec.execute(new CrawlTask(u));
		}

		private class CrawlTask implements Runnable {
			private final URL url = null;

			public CrawlTask(URL u) {
			}

			// ...
			public void run() {
				for (URL link : processPage(url)) {
					if (Thread.currentThread().isInterrupted())
						return;
					submitCrawlTask(link);
				}
			}

			public URL getPage() {
				return url;
			}
		}
	}
	
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 7.3. Handling Abnormal Thread Termination
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/*7.23. Typical Thread-pool Worker Thread Structure.*/
	
	// Listing 7.23 illustrates a way to structure a worker thread within a
	// thread pool. If a task throws an unchecked exception, it allows the
	// thread to die, but not before notifying the framework that the thread has
	// died. The framework may then replace the worker thread with a new thread,
	// or may choose not to because the thread pool is being shut down or there
	// are already enough worker threads to meet current demand.
	// ThreadPoolExecutor and Swing use this technique to ensure that a poorly
	// behaved task doesn’t prevent subsequent tasks from executing. If you are
	// writing a worker thread class that executes submitted tasks, or calling
	// untrusted external code (such as dynamically loaded plugins), use one of
	// these approaches to prevent a poorly written task or plugin from taking
	// down the thread that happens to call it.
	public void run() {
	    Throwable thrown = null;
	    try {
	        while (!isInterrupted())
	            runTask(getTaskFromWorkQueue());
	    } catch (Throwable e) {
	        thrown = e;
	    } finally {
	        threadExited(this, thrown);
	    }
	}
	
	//Mockups
	private void runTask(Object taskFromWorkQueue) {}
	private void threadExited(TaskCancellation taskCancellation,Throwable thrown) {}
	private Object getTaskFromWorkQueue() {return null;}
	private boolean isInterrupted() {return false;}

	
	/*7.25. UncaughtExceptionHandler that Logs the Exception.*/
	
	// When a thread exits due to an uncaught exception, the JVM reports this
	// event to an application-provided UncaughtExceptionHandler (see Listing
	// 7.24); if no handler exists, the default behavior is to print the stack
	// trace to System.err.
	
	// Before Java 5.0, the only way to control the UncaughtExceptionHandler was
	// by subclassing ThreadGroup. In Java 5.0 and later, you can set an
	// UncaughtExceptionHandler on a per-thread basis with
	// Thread.setUncaughtExceptionHandler, and can also set the default
	// UncaughtExceptionHandler with Thread.setDefaultUncaughtExceptionHandler.
	// However, only one of these handlers is called—first the JVM looks for a
	// per-thread handler, then for a ThreadGroup handler. The default handler
	// implementation in ThreadGroup delegates to its parent thread group, and
	// so on up the chain until one of the ThreadGroup handlers deals with the
	// uncaught exception or it bubbles up to the toplevel thread group. The
	// top-level thread group handler delegates to the default system handler
	// (if one exists; the default is none) and otherwise prints the stack trace
	// to the console.
	public class UEHLogger implements Thread.UncaughtExceptionHandler {
		public void uncaughtException(Thread t, Throwable e) {
			Logger logger = Logger.getAnonymousLogger();
			logger.log(Level.SEVERE,
					"Thread terminated with exception: " + t.getName(), e);
		}
	}
	
	// In long-running applications, always use uncaught exception handlers for
	// all threads that at least log the exception.
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 7.4. JVM Shutdown.
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/*7.26. Registering a Shutdown Hook to Stop the Logging Service.*/
	
	// Shutdown hooks can be used for service or application cleanup, such as
	// deleting temporary files or cleaning up resources that are not
	// automatically cleaned up by the OS. Listing 7.26 shows how LogService in
	// Listing 7.16 could register a shutdown hook from its start method to
	// ensure the log file is closed on exit
	public class LogService3 {
	    private final ExecutorService exec = Executors.newSingleThreadExecutor();
	    private final PrintWriter writer = null;
//	    ...
		public void start() {
		    Runtime.getRuntime().addShutdownHook(new Thread() {
		        public void run() {
		            try { LogService3.this.stop(); }
		            catch (InterruptedException ignored) {}
		        }
		    });
		}
	    public void stop() throws InterruptedException {
	        try {
	            exec.shutdown();
	            exec.awaitTermination(5, TimeUnit.SECONDS);
	        } finally {
	            writer.close();
	        }
	    }
	    public void log(String msg) {
	        try {
	            exec.execute(new WriteTask(msg));
	        } catch (RejectedExecutionException ignored) { }
	    }
	}
	
	public static RuntimeException launderThrowable(Throwable t) {
	    if (t instanceof RuntimeException)
	        return (RuntimeException) t;
	    else if (t instanceof Error)
	        throw (Error) t;
	    else
	        throw new IllegalStateException("Not unchecked", t);
	}

	
	public static void main(String[] args) {
		try {
			List<BigInteger> primes = goForPrimes(300);
			for(BigInteger prime : primes) System.out.println(prime);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
