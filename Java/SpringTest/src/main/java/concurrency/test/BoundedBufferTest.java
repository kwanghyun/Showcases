package concurrency.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import junit.framework.TestCase;


public class BoundedBufferTest extends TestCase {
	
    private static final long LOCKUP_DETECT_TIMEOUT = 1000;

	public void testIsEmptyWhenConstructed() {
        BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    public void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
        for (int i = 0; i < 10; i++)
            bb.put(i);
        assertTrue(bb.isFull());
        assertFalse(bb.isEmpty());
    }
    
	// It creates a ¡°taker¡± thread that attempts to take an element from an
	// empty buffer. If take succeeds, it registers failure. The test runner
	// thread starts the taker thread, waits a long time, and then interrupts
	// it. If the taker thread has correctly blocked in the take operation, it
	// will throw InterruptedException, and the catch block for this exception
	// treats this as success and lets the thread exit. The main test runner
	// thread then attempts to join with the taker thread and verifies that the
	// join returned successfully by calling Thread.isAlive; if the taker thread
	// responded to the interrupt, the join should complete quickly.
    public void testTakeBlocksWhenEmpty() {
        final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
        Thread taker = new Thread() {
            public void run() {
                try {
                    int unused = bb.take();
                    fail();  // if we get here, it's an error
                } catch (InterruptedException success) { }
            }};
        try {
            taker.start();
            Thread.sleep(LOCKUP_DETECT_TIMEOUT);
            taker.interrupt();
            taker.join(LOCKUP_DETECT_TIMEOUT);
            assertFalse(taker.isAlive());
        } catch (Exception unexpected) {
            fail();
        }
    }
   

	// The testLeak method inserts several large objects into a bounded buffer
	// and then removes them; memory usage at heap snapshot #2 should be
	// approximately the same as at heap snapshot #1. On the other hand, if
	// doExtract forgot to null out the reference to the returned element
	// (items[i]=null), the reported memory usage at the two snapshots would
	// definitely not be the same. (This is one of the few times where explicit
	// nulling is necessary; most of the time, it is either not helpful or
	// actually harmful
    void memoryleakTest() throws InterruptedException {
         int CAPACITY = 10000;
         int THRESHOLD = 10000;
         
		BoundedBuffer<Big> bb = new BoundedBuffer<Big>(CAPACITY);
         int heapSize1 = 10000;  /* snapshot heap */ ;
         for (int i = 0; i < CAPACITY; i++)
             bb.put(new Big());
         for (int i = 0; i < CAPACITY; i++)
             bb.take();
         int heapSize2 = 10000 /* snapshot heap */ ;
		assertTrue(Math.abs(heapSize1-heapSize2) < THRESHOLD);
    }
    //Helper
    class Big { double[] data = new double[100000]; }
    
    
    
	public void testPoolExpansion() throws InterruptedException {
		int MAX_SIZE = 100;
		
		ExecutorService exec = Executors.newFixedThreadPool(MAX_SIZE);
		ThreadFactory4Test threadFactory = new ThreadFactory4Test();

		for (int i = 0; i < 10 * MAX_SIZE; i++)
			exec.execute(new Runnable() {
				public void run() {
					try {
						Thread.sleep(Long.MAX_VALUE);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			});
//		for (int i = 0; i < 20 && threadFactory.getNumOfCreatedThread() < MAX_SIZE; i++)
//			Thread.sleep(100);
		System.out.println("COUNT : " + threadFactory.getNumOfCreatedThread());
		assertEquals(MAX_SIZE, threadFactory.getNumOfCreatedThread());
		exec.shutdownNow();
	}
    
}