package concurrency.book.threadpool;

import java.util.concurrent.*;

/**
 * ThreadDeadlock
 * 
 * Task that deadlocks in a single-threaded Executor
 *
 * 8.1.1. Thread Starvation Deadlock
 * 
 * If tasks that depend on other tasks execute in a thread pool, they can
 * deadlock. In a single-threaded executor, a task that submits another task to
 * the same executor and waits for its result will always deadlock. The second
 * task sits on the work queue until the first task completes, but the first
 * will not complete because it is waiting for the result of the second task.
 * The same thing can happen in larger thread pools if all threads are executing
 * tasks that are blocked waiting for other tasks still on the work queue. This
 * is called thread starvation deadlock, and can occur whenever a pool task
 * initiates an unbounded blocking wait for some resource or condition that can
 * succeed only through the action of another pool task, such as waiting for the
 * return value or side effect of another task, unless you can guarantee that
 * the pool is large enough.
 * 
 * ThreadDeadlock in Listing 8.1 illustrates thread starvation deadlock.
 * Render-PageTask submits two additional tasks to the Executor to fetch the
 * page header and footer, renders the page body, waits for the results of the
 * header and footer tasks, and then combines the header, body, and footer into
 * the finished page. With a single-threaded executor, ThreadDeadlock will
 * always deadlock. Similarly, tasks coordinating amongst themselves with a
 * barrier could also cause thread starvation deadlock if the pool is not big
 * enough.
 */
public class I01_ThreadDeadlock {
	ExecutorService exec = Executors.newSingleThreadExecutor();

	public class LoadFileTask implements Callable<String> {
		private final String fileName;

		public LoadFileTask(String fileName) {
			this.fileName = fileName;
		}

		public String call() throws Exception {
			// Here's where we would actually read the file
			return "";
		}
	}

	public class RenderPageTask implements Callable<String> {
		public String call() throws Exception {
			Future<String> header, footer;
			header = exec.submit(new LoadFileTask("header.html"));
			footer = exec.submit(new LoadFileTask("footer.html"));
			String page = renderBody();
			// Will deadlock -- task waiting for result of subtask
			return header.get() + page + footer.get();
		}

		private String renderBody() {
			// Here's where we would actually render the page
			return "";
		}
	}
}
