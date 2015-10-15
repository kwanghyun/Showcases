package concurrency.future;

import java.util.concurrent.Callable;

public class MyCallable implements Callable<Long> {
	public Long call() throws Exception {

		System.out.println("[MyCallable] :: START => "
				+ Thread.currentThread().getName());
		long sum = 0;
		for (long i = 0; i <= 100; i++) {
			sum += i;
		}
		return sum;
	}
}
