package concurrency.book.building_blocks;

import java.util.concurrent.*;

/**
 * TestHarness
 * 
 * Using CountDownLatch for starting and stopping threads in timing tests
 *
 * TestHarness in Listing 5.11 illustrates two common uses for latches.
 * TestHarness creates a number of threads that run a given task concurrently.
 * It uses two latches, a “starting gate” and an “ending gate”. The starting
 * gate is initialized with a count of one; the ending gate is initialized with
 * a count equal to the number of worker threads. The first thing each worker
 * thread does is wait on the starting gate; this ensures that none of them
 * starts working until they all are ready to start. The last thing each does is
 * count down on the ending gate; this allows the master thread to wait
 * efficiently until the last of the worker threads has finished, so it can
 * calculate the elapsed time.
 *
 * Why did we bother with the latches in TestHarness instead of just starting
 * the threads immediately after they are created? Presumably, we wanted to
 * measure how long it takes to run a task n times concurrently. If we simply
 * created and started the threads, the threads started earlier would have a
 * “head start” on the later threads, and the degree of contention would vary
 * over time as the number of active threads increased or decreased. Using a
 * starting gate allows the master thread to release all the worker threads at
 * once, and the ending gate allows the master thread to wait for the last
 * thread to finish rather than waiting sequentially for each thread to finish.
 */
public class I06_TestHarness {
	public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThreads);

		for (int i = 0; i < nThreads; i++) {
			Thread t = new Thread() {
				public void run() {
					try {
						/*
						 * Causes the current thread to wait until the latch has
						 * counted down to zero, unless the thread is
						 * interrupted.
						 */
						startGate.await();
						try {
							task.run();
						} finally {
							endGate.countDown();
						}
					} catch (InterruptedException ignored) {
					}
				}
			};
			t.start();
		}

		long start = System.nanoTime();
		startGate.countDown();
		endGate.await();
		long end = System.nanoTime();
		return end - start;
	}

	public static void main(String[] args) throws InterruptedException {
		I06_TestHarness ob = new I06_TestHarness();
		System.out.println(ob.timeTasks(5, () -> System.out.println("Task excuting..........")));
	}
}