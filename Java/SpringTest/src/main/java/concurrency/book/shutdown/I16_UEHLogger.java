package concurrency.book.shutdown;

import java.util.logging.*;

/**
 * UEHLogger
 * <p/>
 * UncaughtExceptionHandler that logs the exception
 * 
 * The previous section offered a proactive approach to the problem of unchecked
 * exceptions. The Thread API also provides the UncaughtExceptionHandler
 * facility, which lets you detect when a thread dies due to an uncaught
 * exception. The two approaches are complementary: taken together, they provide
 * defense-indepth against thread leakage.
 * 
 * When a thread exits due to an uncaught exception, the JVM reports this event
 * to an application-provided UncaughtExceptionHandler (see Listing 7.24); if no
 * handler exists, the default behavior is to print the stack trace to
 * System.err.
 * 
 * To set an UncaughtExceptionHandler for pool threads, provide a ThreadFactory
 * to the ThreadPoolExecutor constructor. (As with all thread manipulation, only
 * the thread's owner should change its UncaughtExceptionHandler.) The standard
 * thread pools allow an uncaught task exception to terminate the pool thread,
 * but use a try-finally block to be notified when this happens so the thread
 * can be replaced. Without an uncaught exception handler or other failure
 * notification mechanism, tasks can appear to fail silently, which can be very
 * confusing. If you want to be notified when a task fails due to an exception
 * so that you can take some task-specific recovery action, either wrap the task
 * with a Runnable or Callable that catches the exception or override the
 * afterExecute hook in ThreadPoolExecutor.
 */
public class I16_UEHLogger implements Thread.UncaughtExceptionHandler {
	public void uncaughtException(Thread t, Throwable e) {
		Logger logger = Logger.getAnonymousLogger();
		logger.log(Level.SEVERE, "Thread terminated with exception: " + t.getName(), e);
	}
}