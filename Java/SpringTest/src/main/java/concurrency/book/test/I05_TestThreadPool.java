package concurrency.book.test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

import junit.framework.TestCase;

/**
 * TestingThreadFactory
 * <p/>
 * Testing thread pool expansion
 * 
 * Callbacks to client-provided code can be helpful in constructing test cases;
 * callbacks are often made at known points in an object's lifecycle that are
 * good opportunities to assert invariants. For example, ThreadPoolExecutor
 * makes calls to the task Runnables and to the ThreadFactory.
 * 
 * Testing a thread pool involves testing a number of elements of execution
 * policy: that additional threads are created when they are supposed to, but
 * not when they are not supposed to; that idle threads get reaped when they are
 * supposed to, etc. Constructing a comprehensive test suite that covers all the
 * possibilities is a major effort, but many of them can be tested fairly simply
 * individually.
 * 
 * We can instrument thread creation by using a custom thread factory.
 * TestingThreadFactory in Listing 12.8 maintains a count of created threads;
 * test cases can then verify the number of threads created during a test run.
 * TestingThreadFactory could be extended to return a custom Thread that also
 * records when the thread terminates, so that test cases can verify that
 * threads are reaped in accordance with the execution policy.*
 * 
 * If the core pool size is smaller than the maximum size, the thread pool
 * should grow as demand for execution increases. Submitting long-running tasks
 * to the pool makes the number of executing tasks stay constant for long enough
 * to make a few assertions, such as testing that the pool is expanded as
 * expected.
 * 
 */
public class I05_TestThreadPool extends TestCase {

	private final TestingThreadFactory threadFactory = new TestingThreadFactory();

	public void testPoolExpansion() throws InterruptedException {
		int MAX_SIZE = 10;
		ExecutorService exec = Executors.newFixedThreadPool(MAX_SIZE);

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
		for (int i = 0; i < 20 && threadFactory.numCreated.get() < MAX_SIZE; i++)
			Thread.sleep(100);
		assertEquals(threadFactory.numCreated.get(), MAX_SIZE);
		exec.shutdownNow();
	}
}

class TestingThreadFactory implements ThreadFactory {
	public final AtomicInteger numCreated = new AtomicInteger();
	private final ThreadFactory factory = Executors.defaultThreadFactory();

	public Thread newThread(Runnable r) {
		numCreated.incrementAndGet();
		return factory.newThread(r);
	}
}