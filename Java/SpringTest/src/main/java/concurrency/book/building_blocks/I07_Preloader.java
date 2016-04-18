package concurrency.book.building_blocks;

import java.util.concurrent.*;

/**
 * Preloader
 *
 * Using FutureTask to preload data that is needed later
 *
 * Preloader creates a FutureTask that describes the task of loading product
 * information from a database and a thread in which the computation will be
 * performed. It provides a start method to start the thread, since it is
 * inadvisable to start a thread from a constructor or static initializer. When
 * the program later needs the ProductInfo, it can call get, which returns the
 * loaded data if it is ready, or waits for the load to complete if not.
 *
 * Tasks described by Callable can throw checked and unchecked exceptions, and
 * any code can throw an Error. Whatever the task code may throw, it is wrapped
 * in an ExecutionException and rethrown from Future.get. This complicates code
 * that calls get, not only because it must deal with the possibility of
 * ExecutionException (and the unchecked CancellationException), but also
 * because the cause of the ExecutionException is returned as a Throwable, which
 * is inconvenient to deal with.
 */

public class I07_Preloader {
	ProductInfo loadProductInfo() throws DataLoadException {
		return null;
	}

	/*
	 * A cancellable asynchronous computation. This class provides a base
	 * implementation of Future, with methods to start and cancel a computation,
	 * query to see if the computation is complete, and retrieve the result of
	 * the computation. The result can only be retrieved when the computation
	 * has completed; the get methods will block if the computation has not yet
	 * completed. Once the computation has completed, the computation cannot be
	 * restarted or cancelled (unless the computation is invoked using
	 * runAndReset).
	 * 
	 * A FutureTask can be used to wrap a Callable or Runnable object. Because
	 * FutureTask implements Runnable, a FutureTask can be submitted to an
	 * Executor for execution.
	 */
	private final FutureTask<ProductInfo> future = new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
		public ProductInfo call() throws DataLoadException {
			return loadProductInfo();
		}
	});
	private final Thread thread = new Thread(future);

	public void start() {
		thread.start();
	}

	public ProductInfo get() throws DataLoadException, InterruptedException {
		try {
			return future.get();
		} catch (ExecutionException e) {
			Throwable cause = e.getCause();
			if (cause instanceof DataLoadException)
				throw (DataLoadException) cause;
			else
				throw LaunderThrowable.launderThrowable(cause);
		}
	}

	interface ProductInfo {
	}
}

class DataLoadException extends Exception {
}