package concurrency.semaphore;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Semaphore;

//The simplest way to think of a semaphore is to consider it an 
//abstraction that allows n units to be acquired, and offers acquire
//and release mechanisms. It safely allows us to ensure that only 
//n processes can access a certain resource at a given time
public class SemaphoreExample {

	//Limiting connections
	
//	This is a nice elegant solution to a problem of limited resources. 
//	The call to acquire() will block until permits are available. The beauty of the 
//	semaphore is that it hides all the complexity of managing access control,
//	counting permits and, of course, getting the thread-safety right.
	public class ConnectionLimiter {
		private final Semaphore semaphore;

		private ConnectionLimiter(int maxConcurrentRequests) {
			semaphore = new Semaphore(maxConcurrentRequests);
		}

		public URLConnection acquire(URL url) throws InterruptedException,
				IOException {
			semaphore.acquire();
			return url.openConnection();
		}

		public void release(URLConnection conn) {
			try {
				/*
				 * ... clean up here
				 */
			} finally {
				semaphore.release();
			}
		}
	}
}
