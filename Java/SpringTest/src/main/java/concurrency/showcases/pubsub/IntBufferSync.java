package concurrency.showcases.pubsub;

public class IntBufferSync implements Buffer {
	private int index;
	private int[] buffer = new int[8];

	public synchronized void add(int num) {
		while (true) {
			if (index < buffer.length) {
				buffer[index++] = num;
				return;
			}
		}
	}

	public synchronized int remove() {
		while (true) {
			if (index > 0) {
				return buffer[--index];
			}
		}
	}
}
