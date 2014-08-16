package concurrency.book;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import concurrency.annotations.Book;
import concurrency.annotations.ThreadSafe;

@Book(name = "Java Concurrency in Practice", chapter = "Chapter 8")
public class ThreadPool {

	/*8.1. Task that Deadlocks in a Single-threaded Executor. Don¡¯t Do this.*/
	
	// Listing 8.1 illustrates thread starvation deadlock. Render-PageTask
	// submits two additional tasks to the Executor to fetch the page header and
	// footer, renders the page body, waits for the results of the header and
	// footer tasks, and then combines the header, body, and footer into the
	// finished page. With a single-threaded executor, ThreadDeadlock will
	// always deadlock.
	
	// Whenever you submit to an Executor tasks that are not independent, be
	// aware of the possibility of thread starvation deadlock, and document any
	// pool sizing or configuration constraints in the code or configuration
	// file where the Executor is configured.
	public class ThreadDeadlock {
	    ExecutorService exec = Executors.newSingleThreadExecutor();
	    
	    public class RenderPageTask implements Callable<String> {
	        public String call() throws Exception {
	            Future<?> header, footer;
	            header = exec.submit(new LoadFileTask("header.html"));
	            footer = exec.submit(new LoadFileTask("footer.html"));
	            String page = renderBody();
	            // Will deadlock -- task waiting for result of subtask
	            return header.get() + page + footer.get();
	        }
	    }

		public String renderBody() {return null;} //Mockup
	}
	
	//Mockup
	class LoadFileTask implements Callable<Object>{
		public LoadFileTask(String string) {}
		public Object call() throws Exception {return null;}
	}
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 8.2. Sizing Thread Pools
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */
	/* 
	 * N-CPU = number of CPUs
	 * U-CPU = Traget CPU Utilization, 0 <= U-CPU <=1
	 * W/C = Ratio of wait time to compute time.
	 * 
	 * The Optimal pool size for keeping the processors at the desired utilzation is :
	 * 
	 * N threads = N-CPU * U-CPU * ( 1 + W/C)
	 * */
	
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 8.3. Configuring ThreadPoolExecutor
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/*8.3.3. Saturation Policies*/
	
	// The default policy, abort, causes execute to throw the unchecked
	// Rejected-ExecutionException; the caller can catch this exception and
	// implement its own overflow handling as it sees fit. The discard policy
	// silently discards the newly submitted task if it cannot be queued for
	// execution; the discard-oldest policy discards the task that would
	// otherwise be executed next and tries to resubmit the new task.
	private void saturationPolicy() {
		
		int N_THREADS = 1; int CAPACITY = 10; //Mock up
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(N_THREADS,
				N_THREADS, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(CAPACITY));
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
	}
	
	
	/*8.4. Using a Semaphore to Throttle Task Submission.*/
	
	// BoundedExecutor in Listing 8.4. In such an approach, use an unbounded
	// queue and set the bound on the semaphore to be equal to the pool size
	// plus the number of queued tasks you want to allow, since the semaphore is
	// bounding the number of tasks both currently executing and awaiting
	// execution.
	@ThreadSafe
	public class BoundedExecutor {
	    private final Executor exec;
	    private final Semaphore semaphore;

	    public BoundedExecutor(Executor exec, int bound) {
	        this.exec = exec;
	        this.semaphore = new Semaphore(bound);
	    }

	    public void submitTask(final Runnable command)
	            throws InterruptedException {
			// Acquires a permit from this semaphore, blocking until one is
			// available, or the thread is interrupted.
			// Acquires a permit, if one is available and returns immediately,
			// reducing the number of available permits by one.
	        semaphore.acquire();
	        try {
	            exec.execute(new Runnable() {
	                public void run() {
	                    try {
	                        command.run();
	                    } finally {
							// Releases a permit, increasing the number of
							// available permits by one. If any threads are
							// trying to acquire a permit, then one is selected
							// and given the permit that was just released. That
							// thread is (re)enabled for thread scheduling
							// purposes.
	                        semaphore.release();
	                    }
	                }
	            });
	        } catch (RejectedExecutionException e) {
				// Exception thrown by an Executor when a task cannot be
				// accepted for execution.
	            semaphore.release();
	        }
	    }
	}
	
	
	
	
	/*8.6. Custom Thread Factory.*/
	public class MyThreadFactory implements ThreadFactory {
	    private final String poolName;

	    public MyThreadFactory(String poolName) {
	        this.poolName = poolName;
	    }

	    public Thread newThread(Runnable runnable) {
	        return new MyAppThread(runnable, poolName);
	    }
	}
	
	/*8.7. Custom Thread Base Class.*/
	
	// Listing 8.7, which lets you provide a thread name, sets a custom
	// UncaughtException-Handler that writes a message to a Logger, maintains
	// statistics on how many threads have been created and destroyed, and
	// optionally writes a debug message to the log when a thread is created or
	// terminates.
	public static class MyAppThread extends Thread {
	    public static final String DEFAULT_NAME = "MyAppThread";
	    private static volatile boolean debugLifecycle = false;
	    private static final AtomicInteger created = new AtomicInteger();
	    private static final AtomicInteger alive = new AtomicInteger();
	    private static final Logger log = Logger.getAnonymousLogger();

		public MyAppThread(Runnable r) {
			this(r, DEFAULT_NAME);
		}

		public MyAppThread(Runnable runnable, String name) {
			super(runnable, name + "-" + created.incrementAndGet());
			setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
				public void uncaughtException(Thread t, Throwable e) {
					log.log(Level.SEVERE, "UNCAUGHT in thread " + t.getName(),
							e);
				}
			});
		}

		public void run() {
			// Copy debug flag to ensure consistent value throughout.
			boolean debug = debugLifecycle;
			if (debug)
				log.log(Level.FINE, "Created " + getName());
			try {
				alive.incrementAndGet();
				super.run();
			} finally {
				alive.decrementAndGet();
				if (debug)
					log.log(Level.FINE, "Exiting " + getName());
			}
		}

	    public static int getThreadsCreated() { return created.get(); }
	    public static int getThreadsAlive() { return alive.get(); }
	    public static boolean getDebug() { return debugLifecycle; }
	    public static void setDebug(boolean b) { debugLifecycle = b; }
	}

	
	/*8.8. Modifying an Executor Created with the Standard Factories.*/
	public static void setPoolSize() {
		ExecutorService exec = Executors.newCachedThreadPool();
		if (exec instanceof ThreadPoolExecutor)
			((ThreadPoolExecutor) exec).setCorePoolSize(10);
		else
			throw new AssertionError("Oops, bad assumption");
	}
	
	
	/*8.9. Thread Pool Extended with Logging and Timing.*/
	
	// Listing 8.9 shows a custom thread pool that uses before-Execute,
	// afterExecute, and terminated to add logging and statistics gathering. To
	// measure a task¡¯s runtime, beforeExecute must record the start time and
	// store it somewhere afterExecute can find it. Because execution hooks are
	// called in the thread that executes the task, a value placed in a
	// ThreadLocal by beforeExecute can be retrieved by afterExecute.
	// TimingThreadPool uses a pair of AtomicLongs to keep track of the total
	// number of tasks processed and the total processing time, and uses the
	// terminated hook to print a log message showing the average task time.
	public class TimingThreadPool extends ThreadPoolExecutor {
		
		//Mockup
		public TimingThreadPool(int corePoolSize, int maximumPoolSize,
				long keepAliveTime, TimeUnit unit,
				BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
				RejectedExecutionHandler handler) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
					threadFactory, handler);
		}

		// This class provides thread-local variables. These variables differ
		// from their normal counterparts in that each thread that accesses one
		// (via its get or set method) has its own, independently initialized
		// copy of the variable. ThreadLocal instances are typically private
		// static fields in classes that wish to associate state with a thread
		// (e.g., a user ID or Transaction ID).
		private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
		private final Logger log = Logger.getLogger("TimingThreadPool");
		private final AtomicLong numTasks = new AtomicLong();
		private final AtomicLong totalTime = new AtomicLong();

		protected void beforeExecute(Thread t, Runnable r) {
			super.beforeExecute(t, r);
			log.fine(String.format("Thread %s: start %s", t, r));
			
			// Sets the current thread's copy of this thread-local variable to
			// the specified value.
			startTime.set(System.nanoTime());
		}

		protected void afterExecute(Runnable r, Throwable t) {
			try {
				long endTime = System.nanoTime();
				
				// Returns the value in the current thread's copy of this
				// thread-local variable.
				long taskTime = endTime - startTime.get();
				numTasks.incrementAndGet();
				totalTime.addAndGet(taskTime);
				log.fine(String.format("Thread %s: end %s, time=%dns", t, r,
						taskTime));
			} finally {
				super.afterExecute(r, t);
			}
		}

		protected void terminated() {
			try {
				log.info(String.format("Terminated: avg time=%dns",
						totalTime.get() / numTasks.get()));
			} finally {
				super.terminated();
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("[AvailableProcessors] : " + Runtime.getRuntime().availableProcessors());
	}


}
