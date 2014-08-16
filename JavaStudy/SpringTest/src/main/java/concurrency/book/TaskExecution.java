package concurrency.book;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.derby.client.net.Request;
import org.postgresql.jdbc2.TimestampUtils;

import concurrency.annotations.Book;

@Book(name = "Java Concurrency in Practice", chapter = "Chapter 6")
public class TaskExecution {

	/*6.1. Sequential Web Server.*/
	class SingleThreadWebServer {
	    public void execute() throws IOException {
	        ServerSocket socket = new ServerSocket(80);
	        while (true) {
				// accept() : Listens for a connection to be made to this socket and
				// accepts it. The method blocks until a connection is made.
	            Socket connection = socket.accept();
	            handleRequest(connection);
	        }
	    }
	}

	/*6.2. Web Server that Starts a New Thread for Each Request.*/
	class ThreadPerTaskWebServer {
	    public void execute() throws IOException {
	        ServerSocket socket = new ServerSocket(80);
	        while (true) {
	            final  Socket connection = socket.accept();
	            Runnable task = new Runnable() {
	                    public void run() {
	                        handleRequest(connection);
	                    }
	                };
	            new Thread(task).start();
	        }
	    }
	}
	
	class TaskExecutionWebServer {
	    private final int NTHREADS = 100;
	    private final Executor exec = Executors.newFixedThreadPool(NTHREADS);

	    public  void execute() throws IOException {
	        ServerSocket socket = new ServerSocket(80);
	        while (true) {
	            final Socket connection = socket.accept();
	            Runnable task = new Runnable() {
	                public void run() {
	                    handleRequest(connection);
	                }
	            };
	            exec.execute(task);
	        }
	    }
	}
	
	public void handleRequest(Socket connection) {}

	// We can easily modify TaskExecutionWebServer to behave like
	// ThreadPer-TaskWebServer by substituting an Executor that creates a new
	// thread for each request.
	
	/*6.5. Executor  that Starts a New Thread for Each Task.*/
	public class ThreadPerTaskExecutor implements Executor {
	    public void execute(Runnable r) {
	        new Thread(r).start();
	    };
	}
	
	// It is also easy to write an Executor that would make
	// TaskExecutionWebServer behave like the single-threaded version, executing
	// each task synchronously before returning from execute.
	
	/* 6.6. Executor that Executes Tasks Synchronously in the Calling Thread. */
	public class WithinThreadExecutor implements Executor {
	    public void execute(Runnable r) {
	        r.run();
	    };
	}

	
	class LifecycleWebServer {
		private final ExecutorService exec = null; // ;

		public void start() throws IOException {
			ServerSocket socket = new ServerSocket(80);
			while (!exec.isShutdown()) {
				try {
					final Socket conn = socket.accept();
					exec.execute(new Runnable() {
						public void run() {
							handleRequest(conn);
						}
					});
				} catch (RejectedExecutionException e) {
					if (!exec.isShutdown())
						System.out.println("task submission rejected : " + e);
				}
			}
		}

		public void stop() {
			exec.shutdown();
		}

		void handleRequest(Socket connection) {
			Request req = readRequest(connection);
			if (isShutdownRequest(req))
				stop();
			else
				dispatchRequest(req);
		}

		private void dispatchRequest(Request req) {}
		private boolean isShutdownRequest(Request req) {return false;}
		public Request readRequest(Socket connection) {return null;}
	}


	/*6.9. Class Illustrating Confusing Timer Behavior.*/
	
	// Timer does have support for scheduling based on absolute, not relative
	// time, so that tasks can be sensitive to changes in the system clock;
	// ScheduledThreadPoolExecutor supports only relative time.
	
	// A Timer creates only a single thread for executing timer tasks. If a
	// timer task takes too long to run, the timing accuracy of other TimerTasks
	// can suffer.
	
	// Another problem with Timer is that it behaves poorly if a TimerTask
	// throws an unchecked exception. The Timer thread doesn¡¯t catch the
	// exception, so an unchecked exception thrown from a TimerTask terminates
	// the timer thread. Timer also doesn¡¯t resurrect the thread in this
	// situation; instead, it erroneously assumes the entire Timer was
	// cancelled. In this case, TimerTasks that are already scheduled but not
	// yet executed are never run, and new tasks cannot be scheduled. (This
	// problem, called ¡°thread leakage¡± is described in Section 7.3, along with
	// techniques for avoiding it.)
	
	public class OutOfTime {
	    public void execute() throws Exception {
	        Timer timer = new Timer();
//	        Parameters:
//	        	task : task to be scheduled.
//	        	delay : delay in milliseconds before task is to be executed
	        timer.schedule(new ThrowTask(), 1);
	        Thread.sleep(1000);
	        timer.schedule(new ThrowTask(), 1);
	        Thread.sleep(5000);
	    }

	    class ThrowTask extends TimerTask {
	        public void run() { throw new RuntimeException(); }
	    }
	}

	
	
	/* * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * 6.3. Finding Exploitable Parallelism
	 ** * * * * * * * * * * * * * * * * * * * * * * * * * * */
	
	/*6.10. Rendering Page Elements Sequentially.*/
	public class SingleThreadRenderer {
	    void renderPage(CharSequence source) {
	        renderText(source);
	        List<ImageData> imageData = new ArrayList<ImageData>();
	        for (ImageInfo imageInfo : scanForImageInfo(source))
	            imageData.add(imageInfo.downloadImage());
	        for (ImageData data : imageData)
	            renderImage(data);
	    }
	}

	/*6.13. Waiting for Image Download with Future.*/
	public class FutureRenderer {
		private final ExecutorService executor = null; // ...;

		void renderPage(CharSequence source) {
			final List<ImageInfo> imageInfos = scanForImageInfo(source);
			Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
				public List<ImageData> call() {
					List<ImageData> result = new ArrayList<ImageData>();
					for (ImageInfo imageInfo : imageInfos)
						result.add(imageInfo.downloadImage());
					return result;
				}
			};

			Future<List<ImageData>> future = executor.submit(task);
			renderText(source);

			try {
				List<ImageData> imageData = future.get();
				for (ImageData data : imageData)
					renderImage(data);
			} catch (InterruptedException e) {
				// Re-assert the thread's interrupted status
				Thread.currentThread().interrupt();
				// We don't need the result, so cancel the task too
				future.cancel(true);
			} catch (ExecutionException e) {
				throw launderThrowable(e.getCause());
			}
		}
	}
	// The real performance payoff of dividing a program¡¯s workload into tasks
	// comes when there are a large number of independent, homogeneous tasks
	// that can be processed concurrently.
	
	/*6.15. Using CompletionService  to Render Page Elements as they Become Available.*/
	
	// We can use a CompletionService to improve the performance of the page
	// renderer in two ways: shorter total runtime and improved responsiveness.
	// We can create a separate task for downloading each image and execute them
	// in a thread pool, turning the sequential download into a parallel one:
	// this reduces the amount of time to download all the images. And by
	// fetching results from the CompletionService and rendering each image as
	// soon as it is available, we can give the user a more dynamic and
	// responsive user interface.
	public class Renderer {
		private final ExecutorService executor;

		Renderer(ExecutorService executor) {
			this.executor = executor;
		}

		void renderPage(CharSequence source) {
			List<ImageInfo> info = scanForImageInfo(source);
			CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(executor);
			for (final ImageInfo imageInfo : info){
				completionService.submit(new Callable<ImageData>() {
					public ImageData call() {
						return imageInfo.downloadImage();
					}
				});
			}
			renderText(source);

			try {
				for (int t = 0, n = info.size(); t < n; t++) {
					// take() : Retrieves and removes the Future representing
					// the next completed task, waiting if none are yet present.
					Future<ImageData> f = completionService.take();
					ImageData imageData = f.get();
					renderImage(imageData);
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				throw launderThrowable(e.getCause());
			}
		}
	}

	
	public void renderText(CharSequence source) {}
	public void renderImage(ImageData data) {}
	public ArrayList<ImageInfo> scanForImageInfo(CharSequence source) {return new ArrayList<ImageInfo>();}
	class ImageData{}
	class ImageInfo{public ImageData downloadImage() {return null;}}
	
	
	/*Listing 6.16. Fetching an Advertisement with a Time Budget.*/
	
	// the user enters travel dates and requirements and the portal fetches and
	// displays bids from a number of airlines, hotels or car rental companies.
	// Depending on the company, fetching a bid might involve invoking a web
	// service, consulting a database, performing an EDI transaction, or some
	// other mechanism. Rather than have the response time for the page be
	// driven by the slowest response, it may be preferable to present only the
	// information available within a given time budget. For providers that do
	// not respond in time, the page could either omit them completely or
	// display a placeholder such as ¡°Did not hear from Air Java in time.¡±
	
	class adExample {
		private final ExecutorService exec = null; // ...;

		Page renderPageWithAd() throws InterruptedException {
			long endNanos = System.nanoTime() + TIME_BUDGET;
			Future<Ad> f = exec.submit(new FetchAdTask());
			// Render the page while waiting for the ad
			Page page = renderPageBody();
			Ad ad;
			try {
				// Only wait for the remaining time budget
				long timeLeft = endNanos - System.nanoTime();
				ad = f.get(timeLeft, TimeUnit.NANOSECONDS);
			} catch (ExecutionException e) {
				ad = DEFAULT_AD;
			} catch (TimeoutException e) {
				ad = DEFAULT_AD;
				f.cancel(true);
			}
			page.setAd(ad);
			return page;
		}
	}
	
	class FetchAdTask implements Callable{public Object call() throws Exception {return null;}} 
	class Page{public void setAd(Ad ad) {}}
	class Ad{}
	int TIME_BUDGET = 0;
	Ad DEFAULT_AD = new Ad();
	public Page renderPageBody() {return null;}
	
	
	/*6.17. Requesting Travel Quotes Under a Time Budget.*/
	
	// Listing 6.17 uses the timed version of invokeAll to submit multiple tasks
	// to an ExecutorService and retrieve the results. The invokeAll method
	// takes a collection of tasks and returns a collection of Futures. The two
	// collections have identical structures; invokeAll adds the Futures to the
	// returned collection in the order imposed by the task collection¡¯s
	// iterator, thus allowing the caller to associate a Future with the
	// Callable it represents. The timed version of invokeAll will return when
	// all the tasks have completed, the calling thread is interrupted, or the
	// timeout expires. Any tasks that are not complete when the timeout expires
	// are cancelled. On return from invokeAll, each task will have either
	// completed normally or been cancelled; the client code can call get or
	// isCancelled to find out which.
	private class QuoteTask implements Callable<TravelQuote> {
		private final TravelCompany company = null;
		private final TravelInfo travelInfo = null;
		public QuoteTask(TravelCompany company2, TravelInfo travelInfo2) {}
		public TravelQuote call() throws Exception {return company.solicitQuote(travelInfo);}
		public TravelQuote getFailureQuote(Throwable cause) {return null;}
		public TravelQuote getTimeoutQuote(CancellationException e) {return null;}
	}

	public List<TravelQuote> getRankedTravelQuotes(TravelInfo travelInfo,
			Set<TravelCompany> companies, Comparator<TravelQuote> sortOrder,
			long time, TimeUnit unit) throws InterruptedException {
		
		ExecutorService exec = null; // ...;

		List<QuoteTask> tasks = new ArrayList<QuoteTask>();
		for (TravelCompany company : companies)
			tasks.add(new QuoteTask(company, travelInfo));

		// Executes the given tasks, returning a list of Futures holding their
		// status and results when all complete or the timeout expires,
		// whichever happens first.
		List<Future<TravelQuote>> futures = exec.invokeAll(tasks, time, unit);

		List<TravelQuote> resultQuotes = new ArrayList<TravelQuote>(tasks.size());
		Iterator<QuoteTask> taskIter = tasks.iterator();
		for (Future<TravelQuote> f : futures) {
			QuoteTask task = taskIter.next();
			try {
				resultQuotes.add(f.get());
			} catch (ExecutionException e) {
				resultQuotes.add(task.getFailureQuote(e.getCause()));
			} catch (CancellationException e) {
				resultQuotes.add(task.getTimeoutQuote(e));
			}
		}

		Collections.sort(resultQuotes, sortOrder);
		return resultQuotes;
	}

	class TravelQuote{}
	class TravelInfo{}
	class TravelCompany{public TravelQuote solicitQuote(TravelInfo travelInfo) {return null;}}
	
	/** If the Throwable is an Error, throw it; if it is a
	 *  RuntimeException return it, otherwise throw IllegalStateException
	 */
	public static RuntimeException launderThrowable(Throwable t) {
	    if (t instanceof RuntimeException)
	        return (RuntimeException) t;
	    else if (t instanceof Error)
	        throw (Error) t;
	    else
	        throw new IllegalStateException("Not unchecked", t);
	}

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}




}
