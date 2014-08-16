package concurrency.semaphore;

public class SemaphoreBasic {
	
	/*1.1 Simple Semaphore*/

	public static class Semaphore {
		private boolean signal = false;

		public synchronized void take() {
			this.signal = true;
			this.notify();
		}

		public synchronized void release() throws InterruptedException {
			while (!this.signal)
				wait();
			this.signal = false;
		}
	}
	
	
	/*1.2 Using Semaphores for Signaling*/
	public static class SendingThread implements Runnable {
		Semaphore semaphore = null;

		public SendingThread(Semaphore semaphore) {
			this.semaphore = semaphore;
		}

		public void run() {
			while (true) {
				// do something, then signal
				this.semaphore.take();

			}
		}
	}

	public static class RecevingThread implements Runnable {
		Semaphore semaphore = null;

		public RecevingThread(Semaphore semaphore) {
			this.semaphore = semaphore;
		}

		public void run() {
			while (true) {
				try {
					this.semaphore.release();
					// receive signal, then do something...
				} catch (InterruptedException e) {}
			}
		}
	}
	
	
	/*2. Counting Semaphore*/
	public class CountingSemaphore {
		private int signals = 0;

		public synchronized void take() {
			this.signals++;
			this.notify();
		}

		public synchronized void release() throws InterruptedException {
			while (this.signals == 0)
				wait();
			this.signals--;
		}
	}
	
	
	/*3. Bounded Semaphore*/
	public class BoundedSemaphore {
		private int signals = 0;
		private int bound = 0;

		public BoundedSemaphore(int upperBound) {
			this.bound = upperBound;
		}

		public synchronized void take() throws InterruptedException {
			while (this.signals == bound)
				wait();
			this.signals++;
			this.notify();
		}

		public synchronized void release() throws InterruptedException {
			while (this.signals == 0)
				wait();
			this.signals--;
			this.notify();
		}
	}
	
	
	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore();

		SendingThread sender = new SendingThread(semaphore);
		
		RecevingThread receiver = new RecevingThread(semaphore);


	}

}
