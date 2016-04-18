package concurrency.book.performance;

import java.util.concurrent.*;

/**
 * WorkerThread
 * <p/>
 * Serialized access to a task queue
 *
 * Imagine an application where N threads execute doWork in Listing 11.1,
 * fetching tasks from a shared work queue and processing them; assume that
 * tasks do not depend on the results or side effects of other tasks. Ignoring
 * for a moment how the tasks get onto the queue, how well will this application
 * scale as we add processors? At first glance, it may appear that the
 * application is completely parallelizable: tasks do not wait for each other,
 * and the more processors available, the more tasks can be processed
 * concurrently. However, there is a serial component as well—fetching the task
 * from the work queue. The work queue is shared by all the worker threads, and
 * it will require some amount of synchronization to maintain its integrity in
 * the face of concurrent access. If locking is used to guard the state of the
 * queue, then while one thread is dequeing a task, other threads that need to
 * dequeue their next task must wait—and this is where task processing is
 * serialized.
 * 
 * The processing time of a single task includes not only the time to execute
 * the task Runnable, but also the time to dequeue the task from the shared work
 * queue. If the work queue is a LinkedBlockingQueue, the dequeue operation may
 * block less than with a synchronized LinkedList because LinkedBlockingQueue
 * uses a more scalable algorithm, but accessing any shared data structure
 * fundamentally introduces an element of serialization into a program.
 * 
 * This example also ignores another common source of serialization: result
 * handling. All useful computations produce some sort of result or side
 * effect—if not, they can be eliminated as dead code. Since Runnable provides
 * for no explicit result handling, these tasks must have some sort of side
 * effect, say writing their results to a log file or putting them in a data
 * structure. Log files and result containers are usually shared by multiple
 * worker threads and therefore are also a source of serialization. If instead
 * each thread maintains its own data structure for results that are merged
 * after all the tasks are performed, then the final merge is a source of
 * serialization.
 */

public class I01_WorkerThread extends Thread {
	private final BlockingQueue<Runnable> queue;

	public I01_WorkerThread(BlockingQueue<Runnable> queue) {
		this.queue = queue;
	}

	public void run() {
		while (true) {
			try {
				Runnable task = queue.take();
				task.run();
			} catch (InterruptedException e) {
				break; /* Allow thread to exit */
			}
		}
	}
}