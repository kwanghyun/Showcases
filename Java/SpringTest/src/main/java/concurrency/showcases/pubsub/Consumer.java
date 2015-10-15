package concurrency.showcases.pubsub;

public class Consumer extends Thread {
	private Buffer buffer;

	public Consumer(Buffer buffer) {
		this.buffer = buffer;
	}

	public void run() {
		while (true) {
			int num = buffer.remove();
			System.out.println("Consumed " + num);
		}
	}
}