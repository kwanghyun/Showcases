package concurrency.interview;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Facebook interview question Implement a non block run which takes as many
 * runnables as given but only runs a specified number of threads. You can also
 * update maxSize in between which will change number of threads executing
 * http://tutorials.jenkov.com/java-concurrency/thread-pools.html
 */
public class ThreadPool {

	private BlockingQueue<Runnable> taskQueue = null;
	private List<PoolThread> threads = new ArrayList<PoolThread>();
	private boolean isStopped = false;

	public ThreadPool(int noOfThreads, int maxNoOfTasks) {
		taskQueue = new LinkedBlockingQueue<>(maxNoOfTasks);

		for (int i = 0; i < noOfThreads; i++) {
			threads.add(new PoolThread(taskQueue));
		}
		for (PoolThread thread : threads) {
			thread.start();
		}
	}

	public synchronized void execute(Runnable task) throws Exception {
		if (this.isStopped)
			throw new IllegalStateException("ThreadPool is stopped");

		this.taskQueue.offer(task);
	}

	public synchronized void stop() {
		this.isStopped = true;
		for (PoolThread thread : threads) {
			thread.doStop();
		}
	}
}

class PoolThread extends Thread {

	private BlockingQueue<Runnable> taskQueue = null;
	private boolean isStopped = false;

	public PoolThread(BlockingQueue<Runnable> queue) {
		taskQueue = queue;
	}

	public void run() {
		while (!isStopped()) {
			try {
				Runnable runnable = (Runnable) taskQueue.poll();
				runnable.run();
			} catch (Exception e) {
				// log or otherwise report exception,
				// but keep pool thread alive.
			}
		}
	}

	public synchronized void doStop() {
		isStopped = true;
		this.interrupt(); // break pool thread out of dequeue() call.
	}

	public synchronized boolean isStopped() {
		return isStopped;
	}
}
