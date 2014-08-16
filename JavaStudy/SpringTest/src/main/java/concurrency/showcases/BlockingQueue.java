package concurrency.showcases;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
	private Queue<T> queue = new LinkedList<T>();
	private int capacity;

	public BlockingQueue(int capacity) {
		this.capacity = capacity;
	}

	public synchronized void put(T element) throws InterruptedException {
		while (queue.size() == capacity) {
			log("BlockingQueue Wating in Put");
			wait();
		}

		queue.add(element);
		notify();
	}

	public synchronized T take() throws InterruptedException {
		while (queue.isEmpty()) {
			log("BlockingQueue Wating in Take");
			wait();
		}

		T item = queue.remove();
		notify();
		return item;
	}
	
	public synchronized int getQueueCount(){
		System.out.print("[");
		for(T count : queue)
			System.out.print(count + ",");
		System.out.println("]");
		return queue.size();
	}
	
	public static class Producer implements Runnable{
		private BlockingQueue queue;
		public Producer(BlockingQueue workerQueue){
			queue = workerQueue;
		}
		
		public void run() {
			int count = 0; 
			while(true){
				try {
					queue.put(count);
					log("Producer Puted, Total size is " + queue.getQueueCount());
					Thread.sleep(1000);
					count++;
				} catch (InterruptedException e) {
					log("Producer Interupted");
				}
			}
		}
	}
	
	public static class Consumer implements Runnable{
		private BlockingQueue queue;
		public Consumer(BlockingQueue workerQueue){
			queue = workerQueue;
		}
		public void run() {
			while(true){
				try {
					log("Consumer Taked : "+queue.take());
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					log("Consumer Interupted");
				}
			}
		}
	}
	
	public static void log(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("[%s]: %s%n", threadName, message);
	}
	
	public static void main(String[] args) {
		BlockingQueue<Runnable> bq = new BlockingQueue<Runnable>(10);
		(new Thread(new Producer(bq))).start();
		(new Thread(new Consumer(bq))).start();
	}
}
