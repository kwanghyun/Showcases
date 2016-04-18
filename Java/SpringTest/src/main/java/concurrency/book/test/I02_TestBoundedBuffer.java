package concurrency.book.test;

import junit.framework.TestCase;

/**
 * TestBoundedBuffer
 * <p/>
 * Basic unit tests for BoundedBuffer
 *
 * If a method is supposed to block under certain conditions, then a test for
 * that behavior should succeed only if the thread does not proceed. Testing
 * that a method blocks is similar to testing that a method throws an exception;
 * if the method returns normally, the test has failed.
 * 
 * Testing that a method blocks introduces an additional complication: once the
 * method successfully blocks, you have to convince it somehow to unblock. The
 * obvious way to do this is via interruption—start a blocking activity in a
 * separate thread, wait until the thread blocks, interrupt it, and then assert
 * that the blocking operation completed. Of course, this requires your blocking
 * methods to respond to interruption by returning early or throwing
 * InterruptedException.
 * 
 * The “wait until the thread blocks” part is easier said than done; in
 * practice, you have to make an arbitrary decision about how long the few
 * instructions being executed could possibly take, and wait longer than that.
 * You should be prepared to increase this value if you are wrong (in which case
 * you will see spurious test failures).
 * 
 * Listing 12.3 shows an approach to testing blocking operations. It creates a
 * “taker” thread that attempts to take an element from an empty buffer. If take
 * succeeds, it registers failure. The test runner thread starts the taker
 * thread, waits a long time, and then interrupts it. If the taker thread has
 * correctly blocked in the take operation, it will throw InterruptedException,
 * and the catch block for this exception treats this as success and lets the
 * thread exit. The main test runner thread then attempts to join with the taker
 * thread and verifies that the join returned successfully by calling
 * Thread.isAlive; if the taker thread responded to the interrupt, the join
 * should complete quickly.
 * 
 * The timed join ensures that the test completes even if take gets stuck in
 * some unexpected way. This test method tests several properties of take—not
 * only that it blocks but that, when interrupted, it throws
 * InterruptedException. This is one of the few cases in which it is appropriate
 * to subclass Thread explicitly instead of using a Runnable in a pool: in order
 * to test proper termination with join. The same approach can be used to test
 * that the taker thread unblocks after an element is placed in the queue by the
 * main thread.
 * 
 * It is tempting to use Thread.getState to verify that the thread is actually
 * blocked on a condition wait, but this approach is not reliable. There is
 * nothing that requires a blocked thread ever to enter the WAITING or
 * TIMED_WAITING states, since the JVM can choose to implement blocking by
 * spin-waiting instead. Similarly, because spurious wakeups from Object.wait or
 * Condition.await are permitted (see Chapter 14), a thread in the WAITING or
 * TIMED_WAITING state may temporarily transition to RUNNABLE even if the
 * condition for which it is waiting is not yet true. Even ignoring these
 * implementation options, it may take some time for the target thread to settle
 * into a blocking state. The result of Thread.getState should not be used for
 * concurrency control, and is of limited usefulness for testing—its primary
 * utility is as a source of debugging information.
 * 
 * 
 */
public class I02_TestBoundedBuffer extends TestCase {
	private static final long LOCKUP_DETECT_TIMEOUT = 1000;
	private static final int CAPACITY = 10000;
	private static final int THRESHOLD = 10000;

	public void testIsEmptyWhenConstructed() {
		I01_SemaphoreBoundedBuffer<Integer> bb = new I01_SemaphoreBoundedBuffer<Integer>(10);
		assertTrue(bb.isEmpty());
		assertFalse(bb.isFull());
	}

	public void testIsFullAfterPuts() throws InterruptedException {
		I01_SemaphoreBoundedBuffer<Integer> bb = new I01_SemaphoreBoundedBuffer<Integer>(10);
		for (int i = 0; i < 10; i++)
			bb.put(i);
		assertTrue(bb.isFull());
		assertFalse(bb.isEmpty());
	}

	public void testTakeBlocksWhenEmpty() {
		final I01_SemaphoreBoundedBuffer<Integer> bb = new I01_SemaphoreBoundedBuffer<Integer>(10);
		Thread taker = new Thread() {
			public void run() {
				try {
					int unused = bb.take();
					fail(); // if we get here, it's an error
					/*
					 * fail() : The class Exception and its subclasses are a
					 * form of Throwable that indicates conditions that a
					 * reasonable application might want to catch.
					 */
				} catch (InterruptedException success) {
				}
			}
		};
		try {
			taker.start();
			Thread.sleep(LOCKUP_DETECT_TIMEOUT);
			taker.interrupt();

			/*
			 * join() : Waits at most millis milliseconds for this thread to
			 * die. A timeout of 0 means to wait forever.
			 * 
			 * This implementation uses a loop of this.wait calls conditioned on
			 * this.isAlive. As a thread terminates the this.notifyAll method is
			 * invoked. It is recommended that applications not use wait,
			 * notify, or notifyAll on Thread instances.
			 */
			taker.join(LOCKUP_DETECT_TIMEOUT);
			assertFalse(taker.isAlive());
		} catch (Exception unexpected) {
			fail();
		}
	}

	class Big {
		double[] data = new double[100000];
	}

	/*
	 * The tests so far have been concerned with a class's adherence to its
	 * specification—that it does what it is supposed to do. A secondary aspect
	 * to test is that it does not do things it is not supposed to do, such as
	 * leak resources. Any object that holds or manages other objects should not
	 * continue to maintain references to those objects longer than necessary.
	 * Such storage leaks prevent garbage collectors from reclaiming memory (or
	 * threads, file handles, sockets, database connections, or other limited
	 * resources) and can lead to resource exhaustion and application failure.
	 * 
	 * Resource management issues are especially important for classes like
	 * BoundedBuffer—the entire reason for bounding a buffer is to prevent
	 * application failure due to resource exhaustion when producers get too far
	 * ahead of consumers. Bounding causes overly productive producers to block
	 * rather than continue to create work that will consume more and more
	 * memory or other resources.
	 * 
	 * Undesirable memory retention can be easily tested with heap-inspection
	 * tools that measure application memory usage; a variety of commercial and
	 * open-source heap-profiling tools can do this. The testLeak method in
	 * contains place-holders for a heap-inspection tool to snapshot the heap,
	 * which forces a garbage collection[5] and then records information about
	 * the heap size and memory usage.
	 * 
	 * [5] Technically, it is impossible to force a garbage collection;
	 * System.gc only suggests to the JVM that this might be a good time to
	 * perform a garbage collection. HotSpot can be instructed to ignore
	 * System.gc calls with -XX:+DisableExplicitGC.
	 * 
	 * The testLeak method inserts several large objects into a bounded buffer
	 * and then removes them; memory usage at heap snapshot #2 should be
	 * approximately the same as at heap snapshot #1. On the other hand, if
	 * doExtract forgot to null out the reference to the returned element
	 * (items[i]=null), the reported memory usage at the two snapshots would
	 * definitely not be the same. (This is one of the few times where explicit
	 * nulling is necessary; most of the time, it is either not helpful or
	 * actually harmful [EJ Item 5].)
	 */
	public void testLeak() throws InterruptedException {
		I01_SemaphoreBoundedBuffer<Big> bb = new I01_SemaphoreBoundedBuffer<Big>(CAPACITY);
		int heapSize1 = snapshotHeap();
		for (int i = 0; i < CAPACITY; i++)
			bb.put(new Big());
		for (int i = 0; i < CAPACITY; i++)
			bb.take();
		int heapSize2 = snapshotHeap();
		assertTrue(Math.abs(heapSize1 - heapSize2) < THRESHOLD);
	}

	private int snapshotHeap() {
		/* Snapshot heap and return heap size */
		return 0;
	}

}