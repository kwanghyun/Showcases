package concurrency.basic;

import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 
 * Suppose we have two services which should wait for each other to complete the
 * execution. Moreover the service calling them should also wait for their
 * competition. So in our example the 2 services and the main thread calling
 * them will wait for each other to finish their execution.
 * 
 * Here, we saw that the main thread was waiting for the two services to reach
 * the barrier. Similarly, both the services were also waiting for each other to
 * reach the barrier. Hence we saw that we can synchronize the 3 threads and
 * make sure that they reach a barrier or in this case finish the execution,
 * then only others will continue.
 */
public class JavaCyclicBarrierExample {

	public static void main(String[] args) {

		// 3 threads are part of the barrier, ServiceOne, ServiceTwo and this
		// main thread calling them.
		final CyclicBarrier barrier = new CyclicBarrier(3);

		Thread serviceOneThread = new Thread(new ServiceOne(barrier));
		Thread serviceTwoThread = new Thread(new ServiceTwo(barrier));

		System.out.println("Starting both the services at" + new Date());

		serviceOneThread.start();
		serviceTwoThread.start();

		try {
			barrier.await();
		} catch (InterruptedException e) {
			System.out.println("Main Thread interrupted!");
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			System.out.println("Main Thread interrupted!");
			e.printStackTrace();
		}
		System.out.println("Ending both the services at" + new Date());
	}

}
