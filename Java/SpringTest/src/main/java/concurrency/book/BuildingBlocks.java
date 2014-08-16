package concurrency.book;

import java.io.File;
import java.io.FileFilter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import concurrency.annotations.Book;
import concurrency.annotations.GuardedBy;
import concurrency.showcases.BlockingQueue;

@Book(name = "Java Concurrency in Practice", chapter = "Chapter 5")
public class BuildingBlocks {

	/*5.1. Synchronized Collections*/
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 5.1.1. Problems with Synchronized Collections
	 * 
	 * The synchronized collections are thread-safe, but you may sometimes need
	 * to use additional client-side locking to guard compound actions. Common
	 * compound actions on collections include iteration (repeatedly fetch
	 * elements until the collection is exhausted), navigation (find the next
	 * element after this one according to some order), and conditional
	 * operations such as put-if-absent (check if a Map has a mapping for key K,
	 * and if not, add the mapping (K,V)).
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/*5.1. Compound Actions on a Vector that may Produce Confusing Results.*/
	public static Object getLast(Vector list) {
	    int lastIndex = list.size() - 1;
	    return list.get(lastIndex);
	}

	public static void deleteLast(Vector list) {
	    int lastIndex = list.size() - 1;
	    list.remove(lastIndex);
	}
	
	/*5.2. Compound Actions on Vector Using Client-side Locking.*/
	public static Object getLastSafely(Vector list) {
	    synchronized (list) {
	        int lastIndex = list.size() - 1;
	        return list.get(lastIndex);
	    }
	}

	public static void deleteLastSafely(Vector list) {
	    synchronized (list) {
	        int lastIndex = list.size() - 1;
	        list.remove(lastIndex);
	    }
	}
	
	public void iterationExample(){
		Vector vector = new Vector();
		
		/* 5.3. Iteration that may Throw ArrayIndexOutOfBoundsException. */
		for (int i = 0; i < vector.size(); i++)
			doSomething(vector.get(i));
		
		/*5.4. Iteration with Client-side Locking.*/
		synchronized (vector) {
		    for (int i = 0; i < vector.size(); i++)
		        doSomething(vector.get(i));
		}
		
		
		/*5.5. Iterating a List with an Iterator.*/
		List<Widget> widgetList = Collections.synchronizedList(new ArrayList<Widget>());
		// ...
		// May throw ConcurrentModificationException
		for (Widget w : widgetList)
			doSomething(w);
		/*
		 * Listing 5.5 illustrates iterating a collection with the for-each loop
		 * syntax. Internally, javac generates code that uses an Iterator,
		 * repeatedly calling hasNext and next to iterate the List. Just as with
		 * iterating the Vector, the way to prevent
		 * ConcurrentModificationException is to hold the collection lock for
		 * the duration of the iteration.
		 */

	}
	private class Widget{} private void doSomething(Object object) {}
	
	
	
	/*5.6. Iteration Hidden within String Concatenation. Don’t Do this.*/
	public class HiddenIterator {
	    @GuardedBy("this")
	    private final Set<Integer> set = new HashSet<Integer>();

	    public synchronized void add(Integer i) { set.add(i); }
	    public synchronized void remove(Integer i) { set.remove(i); }

	    public void addTenThings() {
	        Random r = new Random();
	        for (int i = 0; i < 10; i++)
	            add(r.nextInt());
	        System.out.println("DEBUG: added ten elements to " + set); //Hidden Iterators
	   }
		/*
		 * While locking can prevent iterators from throwing
		 * ConcurrentModificationException, you have to remember to use locking
		 * everywhere a shared collection might be iterated. This is trickier
		 * than it sounds, as iterators are sometimes hidden, as in
		 * HiddenIterator in Listing 5.6. There is no explicit iteration in
		 * HiddenIterator, but the code in bold entails iteration just the same.
		 * The string concatenation gets turned by the compiler into a call to
		 * StringBuilder.append(Object), which in turn invokes the collection’s
		 * toString method—and the implementation of toString in the standard
		 * collections iterates the collection and calls toString on each
		 * element to produce a nicely formatted representation of the
		 * collection’s contents.
		 * 
		 * The addTenThings method could throw ConcurrentModificationException,
		 * because the collection is being iterated by toString in the process
		 * of preparing the debugging message. Of course, the real problem is
		 * that HiddenIterator is not thread-safe; the HiddenIterator lock
		 * should be acquired before using set in the println call, but
		 * debugging and logging code commonly neglect to do this.
		 * 
		 * The real lesson here is that the greater the distance between the
		 * state and the synchronization that guards it, the more likely that
		 * someone will forget to use proper synchronization when accessing that
		 * state. If HiddenIterator wrapped the HashSet with a synchronizedSet,
		 * encapsulating the synchronization, this sort of error would not
		 * occur.
		 * 
		 * Just as encapsulating an object’s state makes it easier to preserve
		 * its invariants, encapsulating its synchronization makes it easier to
		 * enforce its synchronization policy.
		 */
	}
	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 5.3. BlockingQueues and the Producer-consumer Pattern.
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */
	/*5.8. Producer and Consumer Tasks in a Desktop Search Application.*/
	public class FileCrawler implements Runnable {
	    private final BlockingQueue<File> fileQueue = null;
	    private final FileFilter fileFilter = null;
	    private final File root = null;
	    
	    public FileCrawler(LinkedBlockingQueue<File> queue, FileFilter filter, File root2) {}

		//	    ...
	    public void run() {
	        try {
	            crawl(root);
	        } catch (InterruptedException e) {
				/*
				 * Restore the interrupt. Sometimes you cannot throw
				 * InterruptedException, for instance when your code is part of
				 * a Runnable. In these situations, you must catch
				 * InterruptedException and restore the interrupted status by
				 * calling interrupt on the current thread, so that code higher
				 * up the call stack can see that an interrupt was issued
				 */
	            Thread.currentThread().interrupt();
	        }
	    }

	    private void crawl(File root) throws InterruptedException {
	        File[] entries = root.listFiles(fileFilter);
	        if (entries != null) {
	            for (File entry : entries)
	                if (entry.isDirectory())
	                    crawl(entry);
	                else if (!alreadyIndexed(entry))
	                    fileQueue.put(entry);
	        }
	    }
		private boolean alreadyIndexed(File entry) {return false;}
	}

	
	public class Indexer implements Runnable {
	    private final BlockingQueue<File> queue;

	    public Indexer(BlockingQueue<File> queue) {
	        this.queue = queue;
	    }

		public void run() {
	        try {
	            while (true)
	                indexFile(queue.take());
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    }

		private void indexFile(File take) {}
	}

	/*5.9. Starting the Desktop Search.*/
	static int BOUND =10; static int N_CONSUMERS = 10;
	
//	class DesktopSearch{
//		public void startIndexing(File[] roots) {
//		    BlockingQueue<File> queue = new LinkedBlockingQueue<File>(BOUND);
//		    FileFilter filter = new FileFilter() {
//		        public boolean accept(File file) { return true; }
//		    };
//	
//		    for (File root : roots)
//		        new Thread(new FileCrawler(queue, filter, root)).start();
//	
//		    for (int i = 0; i < N_CONSUMERS; i++)
//		        new Thread(new Indexer(queue)).start();
//		}
//	}
	
	public static class Drop {
		// Message sent from producer to consumer.
		private String message;
		// True if consumer should wait for producer to send message,
		// false if producer should wait for consumer to retrieve message.
		private boolean empty = true;

		public synchronized String take() {
			// Wait until message is available.
			while (empty) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			// Toggle status.
			empty = true;
			// Notify producer that status has changed.
			notifyAll();
			return message;
		}

		public synchronized void put(String message) {
			// Wait until message has been retrieved.
			while (!empty) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			// Toggle status.
			empty = false;
			// Store message.
			this.message = message;
			// Notify consumer that status has changed.
			notifyAll();
		}
	}
	
	static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("%s: %s%n", threadName, message);
	}

	public static class Producer implements Runnable {
		private Drop drop;

		public Producer(Drop drop) {
			this.drop = drop;
		}

		public void run() {
			String importantInfo[] = { "Mares eat oats", "Does eat oats",
					"Little lambs eat ivy", "A kid will eat ivy too" };
			Random random = new Random();

			for (int i = 0; i < importantInfo.length; i++) {
				threadMessage("Message Produced");
				drop.put(importantInfo[i]);
				try {
//					Thread.sleep(random.nextInt(5000));
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
			}
			drop.put("DONE");
		}
	}

	public static class Consumer implements Runnable {
		private Drop drop;

		public Consumer(Drop drop) {
			this.drop = drop;
		}

		public void run() {
			Random random = new Random();
			for (String message = drop.take(); !message.equals("DONE"); message = drop.take()) {
				System.out.format("MESSAGE RECEIVED: %s%n", message);
				try {
//					Thread.sleep(random.nextInt(5000));
					Thread.sleep(2500);
				} catch (InterruptedException e) {
				}
			}
		}
	}
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 *  5.5.1. Latches 
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */
	/* Using CountDownLatch for Starting and Stopping Threads in Timing Tests.*/
	public class TestHarness {
		public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
			final CountDownLatch startGate = new CountDownLatch(1);
			final CountDownLatch endGate = new CountDownLatch(nThreads);

			for (int i = 0; i < nThreads; i++) {
				Thread t = new Thread() {
					public void run() {
						try {
							// Causes the current thread to wait until the latch
							// has counted down to zero, unless the thread is
							// interrupted. 
							startGate.await();
							try {
								task.run();
							} finally {
								// Decrements the count of the latch, releasing
								// all waiting threads if the count reaches
								// zero.
								endGate.countDown();
							}
						} catch (InterruptedException ignored) {
						}
					}
				};
				t.start();
			}

			long start = System.nanoTime();
			
			// The first thing each worker thread does is wait
			// on the starting gate; this ensures that none of
			// them starts working until they all are ready to
			// start.
			startGate.countDown();
			
			// The last thing each does is count down on the
			// ending gate; this allows the master thread to
			// wait efficiently until the last of the worker
			// threads has finished, so it can calculate the
			// elapsed time.
			endGate.await();
			
			long end = System.nanoTime();
			return end - start;
			
			// Why did we bother with the latches in TestHarness instead of just
			// starting the threads immediately after they are created?
			// Presumably, we wanted to measure how long it takes to run a task
			// n times concurrently. If we simply created and started the
			// threads, the threads started earlier would have a “head start” on
			// the later threads, and the degree of contention would vary over
			// time as the number of active threads increased or decreased.
			// Using a starting gate allows the master thread to release all the
			// worker threads at once, and the ending gate allows the master
			// thread to wait for the last thread to finish rather than waiting
			// sequentially for each thread to finish.
		}
	}

	public class BoundedHashSet<T> {
	    private final Set<T> set;
	    
		// A counting semaphore. Conceptually, a semaphore maintains a set of
		// permits. Each acquire blocks if necessary until a permit is
		// available, and then takes it. Each release adds a permit, potentially
		// releasing a blocking acquirer. However, no actual permit objects are
		// used; the Semaphore just keeps a count of the number available and
		// acts accordingly.
		// Semaphores are often used to restrict the number of threads than can
		// access some (physical or logical) resource
	    private final Semaphore sem;

	    public BoundedHashSet(int bound) {
	        this.set = Collections.synchronizedSet(new HashSet<T>());
	        sem = new Semaphore(bound);
	    }

	    public boolean add(T o) throws InterruptedException {

	    	// Acquires a permit from this semaphore, blocking until one is
			// available, or the thread is interrupted.
	    	sem.acquire();
	        boolean wasAdded = false;
	        try {
	            wasAdded = set.add(o);
	            return wasAdded;
	        }
	        finally {
	            if (!wasAdded)
					// Releases a permit, returning it to the semaphore.
					//
					// Releases a permit, increasing the number of available
					// permits by one. If any threads are trying to acquire a
					// permit, then one is selected and given the permit that
					// was just released. That thread is (re)enabled for thread
					// scheduling purposes.
	                sem.release();
	        }
	    }

	    public boolean remove(Object o) {
	        boolean wasRemoved = set.remove(o);
	        if (wasRemoved)
	            sem.release();
	        return wasRemoved;
	    }
	}

	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 *  5.6. Building an Efficient, Scalable Result Cache
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/*
	 * 5.16. Initial Cache Attempt Using HashMap and Synchronization.
	 * */
	public interface Computable<A, V> {
	    V compute(A arg) throws InterruptedException;
	}

	public class ExpensiveFunction
	        implements Computable<String, BigInteger> {
	    public BigInteger compute(String arg) {
	        // after deep thought...
	        return new BigInteger(arg);
	    }
	}

	public class Memoizer1<A, V> implements Computable<A, V> {
	    @GuardedBy("this")
	    private final Map<A, V> cache = new HashMap<A, V>();
	    private final Computable<A, V> c;

	    public Memoizer1(Computable<A, V> c) {
	        this.c = c;
	    }

	    public synchronized V compute(A arg) throws InterruptedException {
	        V result = cache.get(arg);
	        if (result == null) {
	            result = c.compute(arg);
	            cache.put(arg, result);
	        }
	        return result;
	    }
	}
	// HashMap is not thread-safe, so to ensure that two threads do not access
	// the HashMap at the same time, Memoizer1 takes the conservative approach
	// of synchronizing the entire compute method. This ensures thread safety
	// but has an obvious scalability problem: only one thread at a time can
	// execute compute at all. 
	
	// Poor Concurrency of Memoizer1.
	//  A ->  [Compute f(1)]
	//  B ------------------->[Compute f(2)]
	//  C ------------------------------------>[return cahed f(1)]
	
	/* 
	 * 5.17. Replacing HashMap  with ConcurrentHashMap.
	 * */
	public class Memoizer2<A, V> implements Computable<A, V> {
	    private final Map<A, V> cache = new ConcurrentHashMap<A, V>();
	    private final Computable<A, V> c;

	    public Memoizer2(Computable<A, V> c) { this.c = c; }

	    public V compute(A arg) throws InterruptedException {
	        V result = cache.get(arg);
	        if (result == null) {
	            result = c.compute(arg);
	            cache.put(arg, result);
	        }
	        return result;
	    }
	}
	
	// Since ConcurrentHashMap is thread-safe, there is no need to synchronize
	// when accessing the backing Map, thus eliminating the serialization
	// induced by synchronizing compute in Memoizer1.
	
	// Memoizer2 certainly has better concurrent behavior than Memoizer1:
	// multiple threads can actually use it concurrently. But it still has some
	// defects as a cache—there is a window of vulnerability in which two
	// threads calling compute at the same time could end up computing the same
	// value. In the case of memoization, this is merely inefficient—the purpose
	// of a cache is to prevent the same data from being calculated multiple
	// times.
	
	// 5.3. Two Threads Computing the Same Value When Using Memoizer2.
	//
	//  A -> [f(1) not in cache] -> [    Compute f(1)    ] -> [add f(1) to cache]
	//  B ------------------------  >[f(1) not in cache]->[    Compute f(1)    ] -> [add f(1) to cache]  
	
	
	 /*
	  * 5.18. Memoizing Wrapper Using FutureTask.
	  * */
	public class Memoizer3<A, V> implements Computable<A, V> {
	    private final Map<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	    private final Computable<A, V> c;

	    public Memoizer3(Computable<A, V> c) { this.c = c; }

	    public V compute(final A arg) throws InterruptedException {
			// Memoizer3 first checks to see if the appropriate calculation has
			// been started (as opposed to finished, as in Memoizer2). If not,
			// it creates a FutureTask, registers it in the Map, and starts the
			// computation; otherwise it waits for the result of the existing
			// computation.
	        Future<V> f = cache.get(arg);
	        if (f == null) { 
	            Callable<V> eval = new Callable<V>() {
	                public V call() throws InterruptedException {
	                    return c.compute(arg);
	                }
	            };
	            FutureTask<V> ft = new FutureTask<V>(eval);
	            f = ft;
	            cache.put(arg, ft);
	            ft.run(); // call to c.compute happens here
	        }
	        try {
				// The result might be available immediately or might be in the
				// process of being computed—but this is transparent to the
				// caller of Future.get.
	            return f.get();
	        } catch (ExecutionException e) {
	            throw launderThrowable(e.getCause());
	        }
	    }
	}
	// Memoizer3 is vulnerable to this problem because a compound action
	// (put-if-absent) is performed on the backing map that cannot be made
	// atomic using locking. Memoizer in Listing 5.19 takes advantage of the
	// atomic putIfAbsent method of ConcurrentMap, closing the window of
	// vulnerability in Memoizer3.
	
	
	/*5.19. Final Implementation of Memoizer.*/
	public class Memoizer<A, V> implements Computable<A, V> {
	    private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	    private final Computable<A, V> c;

	    public Memoizer(Computable<A, V> c) { this.c = c; }

		public V compute(final A arg) throws InterruptedException {
			while (true) {
				//Checks to see if the appropriate calculation has been started
				Future<V> f = cache.get(arg);
				if (f == null) {
					Callable<V> eval = new Callable<V>() {
						public V call() throws InterruptedException {
							return c.compute(arg);
						}
					};
					FutureTask<V> ft = new FutureTask<V>(eval);
					f = cache.putIfAbsent(arg, ft);
					if (f == null) {
						f = ft;
						ft.run();
					}
				}
				try {
					return f.get();
				} catch (CancellationException e) {
					// Caching a Future instead of a value creates the
					// possibility of cache pollution: if a computation is
					// cancelled or fails, future attempts to compute the result
					// will also indicate cancellation or failure. To avoid
					// this, Memoizer removes the Future from the cache if it
					// detects that the computation was cancelled
					cache.remove(arg, f);
				} catch (ExecutionException e) {
					throw launderThrowable(e.getCause());
				}
			}
		}
	}

	
	/** If the Throwable is an Error, throw it; if it is a
	 *  RuntimeException return it, otherwise throw IllegalStateException
	 */
	public static RuntimeException launderThrowable(Throwable t) {
	    if (t instanceof RuntimeException)
	        return (RuntimeException) t;
	    else if (t instanceof Error)
	        throw (Error) t;
	    else
	        throw new IllegalStateException("Not unchecked", t);
	}


	public static void main(String[] args) {
		
		//Producer & Consumer TEST
        Drop drop = new Drop();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
	}

}
