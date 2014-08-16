package concurrency.test;

import junit.framework.TestCase;


public class SingleBoundedBufferedTest extends TestCase {
	
    private static final long LOCKUP_DETECT_TIMEOUT = 1000;

	public void testIsEmptyWhenConstructed() {
        SingleBoundedBuffered<Integer> bb = new SingleBoundedBuffered<Integer>(10);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    public void testIsFullAfterPuts() throws InterruptedException {
        SingleBoundedBuffered<Integer> bb = new SingleBoundedBuffered<Integer>(10);
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
        final SingleBoundedBuffered<Integer> bb = new SingleBoundedBuffered<Integer>(10);
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
}