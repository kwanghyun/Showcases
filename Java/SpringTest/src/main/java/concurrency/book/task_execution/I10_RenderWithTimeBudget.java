package concurrency.book.task_execution;

import java.util.concurrent.*;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * RenderWithTimeBudget
 *
 * Fetching an advertisement with a time budget
 *
 * Sometimes, if an activity does not complete within a certain amount of time,
 * the result is no longer needed and the activity can be abandoned. For
 * example, a web application may fetch its advertisements from an external ad
 * server, but if the ad is not available within two seconds, it instead
 * displays a default advertisement so that ad unavailability does not undermine
 * the site's responsiveness requirements. Similarly, a portal site may fetch
 * data in parallel from multiple data sources, but may be willing to wait only
 * a certain amount of time for data to be available before rendering the page
 * without it.
 *
 * The primary challenge in executing tasks within a time budget is making sure
 * that you don't wait longer than the time budget to get an answer or find out
 * that one is not forthcoming. The timed version of Future.get supports this
 * requirement: it returns as soon as the result is ready, but throws
 * TimeoutException if the result is not ready within the timeout period.
 * 
 * A secondary problem when using timed tasks is to stop them when they run out
 * of time, so they do not waste computing resources by continuing to compute a
 * result that will not be used. This can be accomplished by having the task
 * strictly manage its own time budget and abort if it runs out of time, or by
 * cancelling the task if the timeout expires. Again, Future can help; if a
 * timed get completes with a TimeoutException, you can cancel the task through
 * the Future. If the task is written to be cancellable (see Chapter 7), it can
 * be terminated early so as not to consume excessive resources.
 * 
 * Listing 6.16 shows a typical application of a timed Future.get. It generates
 * a composite web page that contains the requested content plus an
 * advertisement fetched from an ad server. It submits the ad-fetching task to
 * an executor, computes the rest of the page content, and then waits for the ad
 * until its time budget runs out. If the get times out, it cancels the
 * ad-fetching task and uses a default advertisement instead.
 */
public class I10_RenderWithTimeBudget {
	private static final Ad DEFAULT_AD = new Ad();
	private static final long TIME_BUDGET = 1000;
	/*
	 * Creates a thread pool that creates new threads as needed, but will reuse
	 * previously constructed threads when they are available. These pools will
	 * typically improve the performance of programs that execute many
	 * short-lived asynchronous tasks. Calls to execute will reuse previously
	 * constructed threads if available. If no existing thread is available, a
	 * new thread will be created and added to the pool. Threads that have not
	 * been used for sixty seconds are terminated and removed from the cache.
	 * Thus, a pool that remains idle for long enough will not consume any
	 * resources.
	 */
	private static final ExecutorService exec = Executors.newCachedThreadPool();

	Page renderPageWithAd() throws InterruptedException {
		long endNanos = System.nanoTime() + TIME_BUDGET;
		Future<Ad> f = exec.submit(new FetchAdTask());
		// Render the page while waiting for the ad
		Page page = renderPageBody();
		Ad ad;
		try {
			// Only wait for the remaining time budget
			long timeLeft = endNanos - System.nanoTime();
			ad = f.get(timeLeft, NANOSECONDS);
		} catch (ExecutionException e) {
			ad = DEFAULT_AD;
		} catch (TimeoutException e) {
			ad = DEFAULT_AD;
			f.cancel(true);
		}
		page.setAd(ad);
		return page;
	}

	Page renderPageBody() {
		return new Page();
	}

	static class Ad {
	}

	static class Page {
		public void setAd(Ad ad) {
		}
	}

	static class FetchAdTask implements Callable<Ad> {
		public Ad call() {
			return new Ad();
		}
	}

}
