package concurrency.showcases;

public class SimpleThreadExample {

	// Display a message, preceded by the name of the current thread
	static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("%s: %s%n", threadName, message);
	}

	private static class MessageLoop implements Runnable {
		public void run() {
			String messageArrs[] = { "Message-1", "Message-2", "Message-3",
					"Message-4", "Message-5", "Message-6", "Message-7",
					"Message-8", "Message-9", "Message-10" };
			try {
				for (int i = 0; i < messageArrs.length; i++) {
					// Pause for 4 seconds
					Thread.sleep(1000);
					// Print a message
					threadMessage(messageArrs[i]);
				}
			} catch (InterruptedException e) {
				threadMessage("I wasn't done!");
			}
		}
	}

	private static class WaitingTread implements Runnable{
		public void run(){
			while(true){
				try{
					threadMessage("Waiting");
					Thread.sleep(1000);
				}catch(InterruptedException e){
					threadMessage("I wasn't done!");
				}
			}
		}
	}
	
	public static void main(String args[]) throws InterruptedException {

//		// Delay, in milliseconds before we interrupt MessageLoop thread (default one minute).
//		long wait = 1000 * 5;
//
//		threadMessage("Starting MessageLoop thread");
//		long startTime = System.currentTimeMillis();
//		Thread t = new Thread(new MessageLoop());
//		t.start();
//
//		threadMessage("Waiting for MessageLoop thread to finish");
//		// loop until MessageLoop thread exits
//		while (t.isAlive()) {
//			threadMessage("Still waiting...");
//	
//			/* The join method allows one thread to wait for the completion of
//			 * another. If t is a Thread object whose thread is currently
//			 * executing, 
//			 * 
//			 * t.join();
//			 */
//			t.join(1000); // Wait maximum of 1 second for MessageLoop thread to finish.
//			if (((System.currentTimeMillis() - startTime) > wait) && t.isAlive()) {
//				threadMessage("Tired of waiting!");
//				t.interrupt();
//				// Waits for this thread to die.  wait indefinitely
//				t.join();
//			}
//		}
//		threadMessage("Finally!");
		
		threadMessage("Starting WaitingTread");
		Thread wt = new Thread(new WaitingTread());
		wt.start();
		for(int i = 0; i<10; i++){
			Thread.sleep(2000);
			wt.interrupt();
		}
		threadMessage("Finally!");
		wt.stop();
	}
}
