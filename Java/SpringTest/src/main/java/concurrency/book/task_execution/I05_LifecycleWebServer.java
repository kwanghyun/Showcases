package concurrency.book.task_execution;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;
import java.util.logging.*;

/**
 * LifecycleWebServer
 * <p/>
 * Web server with shutdown support
 *
 * t can be shut down in two ways: programmatically by calling stop, and through
 * a client request by sending the web server a specially formatted HTTP
 * request.
 */
public class I05_LifecycleWebServer {
	private final ExecutorService exec = Executors.newCachedThreadPool();

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
					log("task submission rejected", e);
			}
		}
	}

	public void stop() {
		exec.shutdown();
	}

	private void log(String msg, Exception e) {
		Logger.getAnonymousLogger().log(Level.WARNING, msg, e);
	}

	void handleRequest(Socket connection) {
		Request req = readRequest(connection);
		if (isShutdownRequest(req))
			stop();
		else
			dispatchRequest(req);
	}

	interface Request {
	}

	private Request readRequest(Socket s) {
		return null;
	}

	private void dispatchRequest(Request r) {
	}

	private boolean isShutdownRequest(Request r) {
		return false;
	}
}