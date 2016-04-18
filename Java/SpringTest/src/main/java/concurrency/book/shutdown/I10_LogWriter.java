package concurrency.book.shutdown;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.*;

/**
 * LogWriter
 * <p/>
 * Producer-consumer logging service with no shutdown support
 *
 * Most server applications use logging, which can be as simple as inserting
 * println statements into the code. Stream classes like PrintWriter are
 * thread-safe, so this simple approach would require no explicit
 * synchronization.[3] However, as we'll see in Section 11.6, inline logging can
 * have some performance costs in highvolume applications. Another alternative
 * is have the log call queue the log message for processing by another thread.
 * 
 * LogWriter in Listing 7.13 shows a simple logging service in which the logging
 * activity is moved to a separate logger thread. Instead of having the thread
 * that produces the message write it directly to the output stream, LogWriter
 * hands it off to the logger thread via a BlockingQueue and the logger thread
 * writes it out. This is a multiple-producer, single-consumer design: any
 * activity calling log is acting as a producer, and the background logger
 * thread is the consumer. If the logger thread falls behind, the BlockingQueue
 * eventually blocks the producers until the logger thread catches up.
 * 
 * For a service like LogWriter to be useful in production, we need a way to
 * terminate the logger thread so it does not prevent the JVM from shutting down
 * normally. Stopping the logger thread is easy enough, since it repeatedly
 * calls take, which is responsive to interruption; if the logger thread is
 * modified to exit on catching InterruptedException, then interrupting the
 * logger thread stops the service.
 * 
 * However, simply making the logger thread exit is not a very satisfying
 * shutdown mechanism. Such an abrupt shutdown discards log messages that might
 * be waiting to be written to the log, but, more importantly, threads blocked
 * in log because the queue is full will never become unblocked. Cancelling a
 * producerconsumer activity requires cancelling both the producers and the
 * consumers. Interrupting the logger thread deals with the consumer, but
 * because the producers in this case are not dedicated threads, cancelling them
 * is harder.
 */
public class I10_LogWriter {
	private final BlockingQueue<String> queue;
	private final LoggerThread logger;
	private static final int CAPACITY = 1000;

	public I10_LogWriter(Writer writer) {
		this.queue = new LinkedBlockingQueue<String>(CAPACITY);
		this.logger = new LoggerThread(writer);
	}

	public void start() {
		logger.start();
	}

	public void log(String msg) throws InterruptedException {
		queue.put(msg);
	}

	private class LoggerThread extends Thread {
		private final PrintWriter writer;

		public LoggerThread(Writer writer) {
			this.writer = new PrintWriter(writer, true); // autoflush
		}

		public void run() {
			try {
				while (true)
					writer.println(queue.take());
			} catch (InterruptedException ignored) {
			} finally {
				writer.close();
			}
		}
	}
}