package concurrency.book.shutdown;

import java.math.BigInteger;
import java.util.concurrent.*;

/**
 * PrimeProducer
 * <p/>
 * Using interruption for cancellation
 *
 * If a thread is interrupted when it is not blocked, its interrupted status is
 * set, and it is up to the activity being cancelled to poll the interrupted
 * status to detect interruption. In this way interruption is “sticky”—if it
 * doesn't trigger an InterruptedException, evidence of interruption persists
 * until someone deliberately clears the interrupted status.
 * 
 * Calling interrupt does not necessarily stop the target thread from doing what
 * it is doing; it merely delivers the message that interruption has been
 * requested.
 * 
 * A good way to think about interruption is that it does not actually interrupt
 * a running thread; it just requests that the thread interrupt itself at the
 * next convenient opportunity. (These opportunities are called cancellation
 * points.) Some methods, such as wait, sleep, and join, take such requests
 * seriously, throwing an exception when they receive an interrupt request or
 * encounter an already set interrupt status upon entry. Well behaved methods
 * may totally ignore such requests so long as they leave the interruption
 * request in place so that calling code can do something with it. Poorly
 * behaved methods swallow the interrupt request, thus denying code further up
 * the call stack the opportunity to act on it.
 * 
 * Interruption is usually the most sensible way to implement cancellation.
 */
public class I03_PrimeProducer extends Thread {
	private final BlockingQueue<BigInteger> queue;

	I03_PrimeProducer(BlockingQueue<BigInteger> queue) {
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