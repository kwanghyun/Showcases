package concurrency.showcases.pubsub;

public class IntBufferNG implements Buffer {
	private int index;
	private int[] buffer = new int[8];

	public void add(int num) {
		while (true) {
			if (index < buffer.length) {
				buffer[index++] = num;
				return;
			}
		}
	}

	public int remove() {
		while (true) {
			if (index > 0) {
				return buffer[--index];
			}
		}
	}
}
