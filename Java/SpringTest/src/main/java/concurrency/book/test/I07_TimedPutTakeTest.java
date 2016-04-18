package concurrency.book.test;

import java.util.concurrent.*;

/**
 * TimedI04_PutTakeTest
 * <p/>
 * Testing with a barrier-based timer
 *
 * The modified test method using the barrier-based timer is shown in Listing
 * 12.12.
 * 
 * We can learn several things from running TimedPutTakeTest. One is the
 * throughput of the producer-consumer hand-off operation for various
 * combinations of parameters; another is how the bounded buffer scales with
 * different numbers of threads; a third is how we might select the bound size.
 * Answering these questions requires running the test for various combinations
 * of parameters, so we'll need amain test driver, shown in Listing 12.13.
 */
public class I07_TimedPutTakeTest extends I04_PutTakeTest {
	private I06_BarrierTimer timer = new I06_BarrierTimer();

	public I07_TimedPutTakeTest(int cap, int pairs, int trials) {
		super(cap, pairs, trials);
		barrier = new CyclicBarrier(nPairs * 2 + 1, timer);
	}

	public void test() {
		try {
			timer.clear();
			for (int i = 0; i < nPairs; i++) {
				pool.execute(new I04_PutTakeTest.Producer());
				pool.execute(new I04_PutTakeTest.Consumer());
			}
			barrier.await();
			barrier.await();
			long nsPerItem = timer.getTime() / (nPairs * (long) nTrials);
			System.out.print("Throughput: " + nsPerItem + " ns/item");
			assertEquals(putSum.get(), takeSum.get());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws Exception {
		int tpt = 100000; // trials per thread
		for (int cap = 1; cap <= 1000; cap *= 10) {
			System.out.println("Capacity: " + cap);
			for (int pairs = 1; pairs <= 128; pairs *= 2) {
				I07_TimedPutTakeTest t = new I07_TimedPutTakeTest(cap, pairs, tpt);
				System.out.print("Pairs: " + pairs + "\t");
				t.test();
				System.out.print("\t");
				Thread.sleep(1000);
				t.test();
				System.out.println();
				Thread.sleep(1000);
			}
		}
		I04_PutTakeTest.pool.shutdown();
	}
}