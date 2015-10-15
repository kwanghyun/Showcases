package concurrency.showcases.pubsub;

import java.util.Random;

public class Producer extends Thread {
	private Buffer buffer;

	public Producer(Buffer buffer) {
		this.buffer = buffer;
	}

	public void run() {
		Random r = new Random();
		while (true) {
			int num = r.nextInt();
			buffer.add(num);
			System.out.println("Produced " + num);
		}
	}
}
