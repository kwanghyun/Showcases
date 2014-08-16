package concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrencyBestPractices {

	/* * * * * * * * * * * * * * * * Note! * * * * * * * * * * * * * * * * * *
	 * 1. Synchronization is required for reliable communication between threads
	 * 	as well as for mutual exclusion. 
	 * 2. Synchronization has no effect unless both read and write operations are synchronized. 
	 * 3. Confine mutable data to a single thread.
	 * 4. To avoid liveness and safety failures, never cede control to the client within 
	 * 	a synchronized method or block.
	 * 5. As a rule, you should do as little work as possible inside synchronized regions.
	 * 6. Prefer executors and tasks to threads.
	 * 7. Prefer concurrency utilities to wait and notify.
	 * 8. Always use the wait loop idiom to invoke the wait method; never invoke it outside of a loop.
	 * 9. There is seldom, if ever, a reason to use wait and notify in new code.
	 * 10. Under most circumstances, normal initialization is preferable to lazy initialization.
	 * 11. If you need to use lazy initialization for performance on a static field, use 
	 * 	the lazy initialization holder class idiom.
	 * 12. If you need to use lazy initialization for performance on an instance field, 
	 * 	use the double-check idiom.
	 * 13. Thread priorities are among the least portable features of the Java platform.
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

	/* 1. Hoisting & Liveness Failure */

	// Broken! - How long would you expect this program to run?
	public class StopThread {
		private boolean stopRequested;

		public void hoisting() throws InterruptedException {
			Thread backgroundThread = new Thread(new Runnable() {
				public void run() {
					int i = 0;
					while (!stopRequested)
						i++;
				}
			});
			backgroundThread.start();
			TimeUnit.SECONDS.sleep(1);
			stopRequested = true;
		}
	}

	// the program never terminates: the background thread loops forever!

	/*
	 * The problem is that in the absence of synchronization, there is no
	 * guarantee as to when, if ever, the background thread will see the change
	 * in the value of stop- Requested that was made by the main thread. In the
	 * absence of synchronization, it¡¯s quite acceptable for the virtual machine
	 * to transform this code:
	 */	
	public void sample() {
		// Helpers START
		boolean stopRequested = false;
		int i = 0;
		// Helpers END

		while (!stopRequested)
			i++;

		// into this code:

		if (!stopRequested)
			while (true)
				i++;
	}

	// This optimization is known as hoisting, and it is precisely what the HotSpot server
	// VM does. The result is a liveness failure: the program fails to make progress.

	
	/* 2. Safety Failure */

	// Broken - requires synchronization!
	private static volatile int nextSerialNumber = 0;

	public static int generateSerialNumber() {
		return nextSerialNumber++;
	}

	/*
	 * The problem is that the increment operator (++) is not atomic. It
	 * performs two operations on the nextSerialNumber field: first it reads the
	 * value, then it writes back a new value, equal to the old value plus one.
	 * If a second thread reads the field between the time a thread reads the
	 * old value and writes back a new one, the second thread will see the same
	 * value as the first and return the same serial number. This is a safety
	 * failure: the program computes the wrong results.
	 */
	
	
	/*3. Effectively Immutable & Safe Publication*/
	
	/*
	 * It is acceptable for one thread to modify a data object for a while and
	 * then to share it with other threads, synchronizing only the act of
	 * sharing the object reference. Other threads can then read the object
	 * without further synchronization, so long as it isn¡¯t modified again. Such
	 * objects are said to be effectively immutable [Goetz06, 3.5.4].
	 * Transferring such an object reference from one thread to others is called
	 * safe publication
	 */
	
	
	/*4. Alien*/
	
	/*
	 * inside a synchronized region, do not invoke a method that is designed to
	 * be overridden, or one provided by a client in the form of a function
	 * object (Item 21). From the perspective of the class with the synchronized
	 * region, such methods are alien. The class has no knowledge of what the
	 * method does and has no control over it. Depending on what an alien method
	 * does, calling it from a synchronized region can cause exceptions,
	 * deadlocks, or data corruption.
	 */
	
	// Broken - invokes alien method from synchronized block!
	public class ObservableSet<E> extends ForwardingSet<E> {
		/*
		 * It allows clients to subscribe to notifications when elements are
		 * added to the set. This is the Observer pattern
		 */
		public ObservableSet(Set<E> set) {
			super(set);
		}

		private final List<SetObserver<E>> observers = new ArrayList<SetObserver<E>>();

		public void addObserver(SetObserver<E> observer) {
			synchronized (observers) {
				observers.add(observer);
			}
		}

		public boolean removeObserver(SetObserver<E> observer) {
			synchronized (observers) {
				return observers.remove(observer);
			}
		}

		private void notifyElementAdded(E element) {
			synchronized (observers) {
				for (SetObserver<E> observer : observers)
					observer.notify(this, element);
			}
		}
		
		private void openCallNotifyElementAdded(E element) {
			List<SetObserver<E>> snapshot = null;
			synchronized (observers) {
				snapshot = new ArrayList<SetObserver<E>>(observers);
			}
			for (SetObserver<E> observer : snapshot)
				observer.notify(this, element);
		}

		@Override
		public boolean add(E element) {
			boolean added = super.add(element);
			if (added)
				notifyElementAdded(element);
			return added;
		}

		@Override
		public boolean addAll(Collection<? extends E> c) {
			boolean result = false;
			for (E element : c)
				result |= add(element); // calls notifyElementAdded
			return result;
		}
	}

	public interface SetObserver<E> {
		// Invoked when an element is added to the observable set
		void notify(ObservableSet<E> set, E element);
	}
	
	/*
	 * ObservableSet appears to work. For example, the following program prints
	 * the numbers from 0 through 99:
	 */
	static void alienCallTest(){
		ObservableSet<Integer> set = new ConcurrencyBestPractices().new ObservableSet<Integer>(
				new HashSet<Integer>());
		set.addObserver(new SetObserver<Integer>() {
			public void notify(ObservableSet<Integer> s, Integer e) {
				System.out.println(e);
			}
		});
		for (int i = 0; i < 100; i++)
			set.add(i);
	}
	
	/*
	 * You might expect the program to print the numbers 0 through 23, after
	 * which the observer would unsubscribe and the program complete its work
	 * silently. What actually happens is that it prints the numbers 0 through
	 * 23, and then throws a ConcurrentModificationException. The problem is
	 * that notifyElementAdded is in the process of iterating over the observers
	 * list when it invokes the observer¡¯s added method. The added method calls
	 * the observable set¡¯s removeObserver method, which in turn calls
	 * observers.remove. Now we are in trouble. We are trying to remove an
	 * element from a list in the midst of iterating over it, which is illegal.
	 * The iteration in the notifyElementAdded method is in a synchronized block
	 * to prevent concurrent modification, but it doesn¡¯t prevent the iterating
	 * thread itself from calling back into the observable set and modifying its
	 * observers list.
	 */
	static void AlienCallConcurrentModificationTest(){
		ObservableSet<Integer> set = new ConcurrencyBestPractices().new ObservableSet<Integer>(
				new HashSet<Integer>());
		set.addObserver(new SetObserver<Integer>() {
			public void notify(ObservableSet<Integer> s, Integer e) {
				System.out.println(e);
				if (e == 23)
					s.removeObserver(this);
			}
		});
		for (int i = 0; i < 100; i++)
			set.add(i);
	}
	
	/*
	 * This time we don¡¯t get an exception; we get a deadlock. The background
	 * thread calls s.removeObserver, which attempts to lock observers, but it
	 * can¡¯t acquire the lock, because the main thread already has the lock. All
	 * the while, the main thread is waiting for the background thread to finish
	 * removing the observer, which explains the deadlock.
	 */
	static void AlienCallDeadLockTest(){
		ObservableSet<Integer> set = new ConcurrencyBestPractices().new ObservableSet<Integer>(
				new HashSet<Integer>());
		set.addObserver(new SetObserver<Integer>() {
			public void notify(final ObservableSet<Integer> s, Integer e) {
				System.out.println(e);
				if (e == 23) {
					ExecutorService executor = Executors.newSingleThreadExecutor();
					final SetObserver<Integer> observer = this;
					try {
						executor.submit(new Runnable() {
							public void run() {
								s.removeObserver(observer);
							}
						}).get();
					} catch (ExecutionException ex) {
						throw new AssertionError(ex.getCause());
					} catch (InterruptedException ex) {
						throw new AssertionError(ex);
					} finally {
						executor.shutdown();
					}
				}
			}
		});
		for (int i = 0; i < 100; i++)
			set.add(i);
	}
	
	
	/*5. Open Call*/
	
	/*
	 * Luckily, it is usually not too hard to fix this sort of problem by moving
	 * alien method invocations out of synchronized blocks. For the
	 * notifyElementAdded method, this involves taking a ¡°snapshot¡± of the
	 * observers list that can then be safely traversed without a lock. With
	 * this change, both of the previous examples run without exception or
	 * deadlock:
	 * 
	 * i.e : openCallNotifyElementAdded(), 150 line
	 */
	
	/*
	 * In fact, there¡¯s a better way to move the alien method invocations out of
	 * the synchronized block.
	 */
	// Thread-safe observable set with CopyOnWriteArrayList
	class ThreadSafeObservableSet<E> {
		private final List<SetObserver<E>> observers = new CopyOnWriteArrayList<SetObserver<E>>();

		public void addObserver(SetObserver<E> observer) {
			observers.add(observer);
		}

		public boolean removeObserver(SetObserver<E> observer) {
			return observers.remove(observer);
		}

		private void notifyElementAdded(E element) {
			for (SetObserver<E> observer : observers){
//				observer.notify(this, element);
			}
		}
	}

	/*
	 * An alien method invoked outside of a synchronized region is known as an
	 * open call [Lea00 2.4.1.3]. Besides preventing failures, open calls can
	 * greatly increase concurrency. An alien method might run for an
	 * arbitrarily long period. If the alien method were invoked from a
	 * synchronized region, other threads would be denied access to the
	 * protected resource unnecessarily.
	 */
	
	
	
	/*6. For Preventing Denial-of-service attack, use Pirvate Lock Object*/
	
	/*
	 * To prevent this denial-of-service attack, you can use a private lock
	 * object instead of using synchronized methods (which imply a publicly
	 * accessible lock):
	 */
	class PrivateLockObject {
		// Private lock object idiom - thwarts denial-of-service attack
		private final Object lock = new Object();

		public void foo() {
			synchronized (lock) {
				// ...
			}
		}
	}
	
	/*
	 * Because the private lock object is inaccessible to clients of the class,
	 * it is impossible for them to interfere with the object¡¯s synchronization.
	 * 
	 * Note that the lock field is declared final. This prevents you from
	 * inadvertently changing its contents, which could result in catastrophic
	 * unsynchronized access to the containing object
	 */
	 
	
	
	/* 7. Lazy Initialization */
	
	/*
	 * In the presence of multiple threads, lazy initialization is tricky. If
	 * two or more threads share a lazily initialized field, it is critical that
	 * some form of synchronization be employed, or severe bugs can result
	 */
	class LazyInitialization {
		// Normal initialization of an instance field
		private FieldType field = computeFieldValue();
		/*
		 * If you use lazy initialization to break an initialization
		 * circularity, use a synchronized accessor, as it is the simplest,
		 * clearest alternative:
		 */
		// Lazy initialization of instance field - synchronized accessor
		private FieldType lazyField;

		synchronized FieldType getField() {
			if (lazyField == null)
				lazyField = computeFieldValue();
			return lazyField;
		}
	}

	/*
	 * Both of these idioms (normal initialization and lazy initialization
	 * with a synchronized accessor) are unchanged when applied to static
	 * fields, except that you add the static modifier to the field and
	 * accessor declarations. If you need to use lazy initialization for
	 * performance on a static field, use the lazy initialization holder
	 * class idiom. This idiom (also known as the initializeon- demand
	 * holder class idiom) exploits the guarantee that a class will not be
	 * initialized until it is used
	 */

	/*
	 * When the getField method is invoked for the first time, it reads Field-
	 * Holder.field for the first time, causing the FieldHolder class to get
	 * initialized. The beauty of this idiom is that the getField method is not
	 * synchronized and performs only a field access, so lazy initialization
	 * adds practically nothing to the cost of access. A modern VM will
	 * synchronize field access only to initialize the class. Once the class is
	 * initialized, the VM will patch the code so that subsequent access to the
	 * field does not involve any testing or synchronization.
	 */

	// Lazy initialization holder class idiom for static fields
	private static class FieldHolder {
		static final FieldType field = computeFieldValue();
	}

	static FieldType getField() {
		return FieldHolder.field;
	}
	
	
	/*
	 * If you need to use lazy initialization for performance on an instance
	 * field, use the double-check idiom. This idiom avoids the cost of locking
	 * when accessing the field after it has been initialized. The
	 * idea behind the idiom is to check the value of the field twice (hence the
	 * name double-check): once without locking, and then, if the field appears
	 * to be uninitialized, a second time with locking. Only if the second check
	 * indicates that the field is uninitialized does the call initialize the
	 * field. Because there is no locking if the field is already initialized,
	 * it is critical that the field be declared volatile
	 */
	// Double-check idiom for lazy initialization of instance fields
	private volatile FieldType field;

	FieldType getField2() {
		FieldType result = field;
		if (result == null) { // First check (no locking)
			synchronized (this) {
				result = field;
				if (result == null) // Second check (with locking)
					field = result = computeFieldValue();
			}
		}
		return result;
	}
	
	/*
	 * Prior to release 1.5, the double-check idiom did not work reliably
	 * because the semantics of the volatile modifier were not strong enough to
	 * support it. The memory model introduced in release 1.5 fixed this
	 * problem. Today, the double-check idiom is the technique of choice for
	 * lazily initializing an instance field. While you can apply the
	 * double-check idiom to static fields as well, there is no reason to do so:
	 * the lazy initialization holder class idiom is a better choice.
	 */
	
	
	/*
	 * you should initialize most fields normally, not lazily. If you must
	 * initialize a field lazily in order to achieve your performance goals, or
	 * to break a harmful initialization circularity, then use the appropriate
	 * lazy initialization technique. For instance fields, it is the
	 * double-check idiom; for static fields, the lazy initialization holder
	 * class idiom.
	 */
	
	
	
	/* 8. Threads should not run if they aren¡¯t doing useful work. */	
	
	
	/*
	 * Threads should not busy-wait, repeatedly checking a shared object waiting
	 * for something to happen. Besides making the program vulnerable to the
	 * vagaries of the scheduler, busy-waiting greatly increases the load on the
	 * processor, reducing the amount of useful work that others can accomplish.
	 */
	
	// Awful CountDownLatch implementation - busy-waits incessantly!
	public class SlowCountDownLatch {
		private int count;

		public SlowCountDownLatch(int count) {
			if (count < 0)
				throw new IllegalArgumentException(count + " < 0");
			this.count = count;
		}

		public void await() {
			while (true) {
				synchronized (this) {
					if (count == 0)
						return;
				}
			}
		}

		public synchronized void countDown() {
			if (count != 0)
				count--;
		}
	}
	
	/*
	 * On my machine, SlowCountDownLatch is about 2,000 times slower than
	 * CountDownLatch when 1,000 threads wait on a latch. While this example may
	 * seem a bit far-fetched, it¡¯s not uncommon to see systems with one or more
	 * threads that are unnecessarily runnable. The results may not be as
	 * dramatic as Slow- CountDownLatch, but performance and portability are
	 * likely to suffer.
	 */
	
	
	
	
	//Helpers
	private static FieldType computeFieldValue() {return null;}
	class FieldType{}
	
	public static void main(String[] args) {
//		AlienCallDeadLockTest();
	}
	
	
	// Reusable forwarding class
	public class ForwardingSet<E> implements Set<E> {
		private final Set<E> s;

		public ForwardingSet(Set<E> s) {
			this.s = s;
		}

		public void clear() {
			s.clear();
		}

		public boolean contains(Object o) {
			return s.contains(o);
		}

		public boolean isEmpty() {
			return s.isEmpty();
		}

		public int size() {
			return s.size();
		}

		public Iterator<E> iterator() {
			return s.iterator();
		}

		public boolean add(E e) {
			return s.add(e);
		}

		public boolean remove(Object o) {
			return s.remove(o);
		}

		public boolean containsAll(Collection<?> c) {
			return s.containsAll(c);
		}

		public boolean addAll(Collection<? extends E> c) {
			return s.addAll(c);
		}

		public boolean removeAll(Collection<?> c) {
			return s.removeAll(c);
		}

		public boolean retainAll(Collection<?> c) {
			return s.retainAll(c);
		}

		public Object[] toArray() {
			return s.toArray();
		}

		public <T> T[] toArray(T[] a) {
			return s.toArray(a);
		}

		@Override
		public boolean equals(Object o) {
			return s.equals(o);
		}

		@Override
		public int hashCode() {
			return s.hashCode();
		}

		@Override
		public String toString() {
			return s.toString();
		}
	}



}
