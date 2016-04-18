package concurrency.book.task_execution;

import java.util.concurrent.*;

/**
 * ThreadPerTaskExecutor
 * 
 * Executor that starts a new thread for each task
 *
 * Up to a certain point, more threads can improve throughput, but beyond that
 * point creating more threads just slows down your application, and creating
 * one thread too many can cause your entire application to crash horribly. The
 * way to stay out of danger is to place some bound on how many threads your
 * application creates, and to test your application thoroughly to ensure that,
 * even when this bound is reached, it does not run out of resources.
 *
 * The problem with the thread-per-task approach is that nothing places any
 * limit on the number of threads created except the rate at which remote users
 * can throw HTTP requests at it. Like other concurrency hazards, unbounded
 * thread creation may appear to work just fine during prototyping and
 * development, with problems surfacing only when the application is deployed
 * and under heavy load. So a malicious user, or enough ordinary users, can make
 * your web server crash if the traffic load ever reaches a certain threshold.
 * For a server application that is supposed to provide high availability and
 * graceful degradation under load, this is a serious failing.
 */
public class I02_ThreadPerTaskExecutor implements Executor {
	public void execute(Runnable r) {
		new Thread(r).start();
	};
}