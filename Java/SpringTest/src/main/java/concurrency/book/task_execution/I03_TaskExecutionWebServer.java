package concurrency.book.task_execution;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * TaskExecutionWebServer
 * <p/>
 * Web server using a thread pool
 *
 * Executor may be a simple interface, but it forms the basis for a flexible and
 * powerful framework for asynchronous task execution that supports a wide
 * variety of task execution policies. It provides a standard means of
 * decoupling task submission from task execution, describing tasks with
 * Runnable. The Executor implementations also provide lifecycle support and
 * hooks for adding statistics gathering, application management, and
 * monitoring.
 *
 * Executor is based on the producer-consumer pattern, where activities that
 * submit tasks are the producers (producing units of work to be done) and the
 * threads that execute tasks are the consumers (consuming those units of work).
 * Using an Executor is usually the easiest path to implementing a
 * producer-consumer design in your application.
 */
public class I03_TaskExecutionWebServer {
	private static final int NTHREADS = 100;
	private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

	public static void main(String[] args) throws IOException {
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

	private static void handleRequest(Socket connection) {
		// request-handling logic here
	}
}