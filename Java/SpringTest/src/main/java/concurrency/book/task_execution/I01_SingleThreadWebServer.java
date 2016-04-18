package concurrency.book.task_execution;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * SingleThreadWebServer
 * <p/>
 * Sequential web server
 *
 * Processing a web request involves a mix of computation and I/O. The server
 * must perform socket I/O to read the request and write the response, which can
 * block due to network congestion or connectivity problems. It may also perform
 * file I/O or make database requests, which can also block. In a
 * single-threaded server, blocking not only delays completing the current
 * request, but prevents pending requests from being processed at all. If one
 * request blocks for an unusually long time, users might think the server is
 * unavailable because it appears unresponsive. At the same time, resource
 * utilization is poor, since the CPU sits idle while the single thread waits
 * for its I/O to complete.
 */

public class I01_SingleThreadWebServer {
	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(80);
		while (true) {
			Socket connection = socket.accept();
			handleRequest(connection);
		}
	}

	private static void handleRequest(Socket connection) {
		// request-handling logic here
	}
}