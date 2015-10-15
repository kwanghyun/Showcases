package concurrency.showcases.pubsub;

public class IntBuffer implements Buffer{
	private int index;
	private int[] buffer = new int[8];

	public synchronized void add(int num) {
		while (index == buffer.length - 1) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		buffer[index++] = num;
		notifyAll();
	}

	public synchronized int remove() {
		while (index == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		int ret = buffer[--index];
		notifyAll();
		return ret;
	}
}
