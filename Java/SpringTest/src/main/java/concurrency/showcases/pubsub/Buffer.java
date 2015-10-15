package concurrency.showcases.pubsub;

public interface Buffer {

	public void add(int num);
	public int remove();
}
