package com.cisco.locker.ms.reactive;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import rx.Observable;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class Threads {
	CountDownLatch latch = new CountDownLatch(1);

	public void doOnEach() {
		Observable.range(5, 5).doOnEach(Utils.debug("Test", "")).subscribe();
	}

	public void defaultInterval() throws InterruptedException {
		Observable.interval(500L, TimeUnit.MILLISECONDS).take(5).doOnEach(Utils.debug("Default interval"))
				.doOnCompleted(() -> latch.countDown()).subscribe();

		latch.await();
	}

	public void schedule(Scheduler scheduler, int numberOfSubTasks, boolean onTheSameWorker) {
		List<Integer> list = new ArrayList<>(0);
		AtomicInteger current = new AtomicInteger(0);

		Random random = new Random();
		Worker worker = scheduler.createWorker();

		Action0 addWork = () -> {

			synchronized (list) {
				System.out.println("  @@ list => " + list);
				System.out.println("  [Item] Add : " + Thread.currentThread().getName() + " " + current.get());
				list.add(random.nextInt(current.get()));
				System.out.println("  [Item] End add : " + Thread.currentThread().getName() + " " + current.get());
			}

		};

		Action0 removeWork = () -> {

			synchronized (list) {
				if (!list.isEmpty()) {
					System.out.println("  [Item] Remove : " + Thread.currentThread().getName());
					list.remove(0);
					System.out.println("  [Item] End remove : " + Thread.currentThread().getName());

				}
			}

		};

		Action0 work = () -> {
			System.out.println(Thread.currentThread().getName());

			for (int i = 1; i <= numberOfSubTasks; i++) {
				current.set(i);

				System.out.println("[Work] Begin add!");
				if (onTheSameWorker) {
					worker.schedule(addWork);
				} else {
					scheduler.createWorker().schedule(addWork);
				}
				System.out.println("[Work] End add!");
			}

			while (!list.isEmpty()) {
				System.out.println("[Work] Begin remove!");

				if (onTheSameWorker) {
					worker.schedule(removeWork);
				} else {
					scheduler.createWorker().schedule(removeWork);
				}

				System.out.println("[Work] End remove!");
			}
		};

		worker.schedule(work);
	}

	public void runAllSheduler(Threads obj) throws InterruptedException {
		/*
		 * Creates and returns a Scheduler that executes work immediately on the
		 * current thread.—the main one and nothing is in parallel. This
		 * scheduler can be used to execute methods, such as interval() and
		 * timer(), in the foreground.
		 */
		System.out.println("Immediate");
		obj.schedule(Schedulers.immediate(), 5, false);
		System.out.println("----------------------------------");
		obj.schedule(Schedulers.immediate(), 5, true);

		/*
		 * Creates and returns a Scheduler that queues work on the current
		 * thread to be executed after the current work completes.
		 * 
		 * Schedulers.trampoline method enqueues sub-tasks on the current
		 * thread. The enqueued work is executed after the work currently in
		 * progress completes.
		 */
		System.out.println("Trampoline");
		obj.schedule(Schedulers.trampoline(), 2, false);
		System.out.println("----------------------------------");
		obj.schedule(Schedulers.trampoline(), 2, true);

		/*
		 * Creates and returns a Scheduler that creates a new Thread for each
		 * unit of work.
		 */
		System.out.println("New thread");
		obj.schedule(Schedulers.newThread(), 2, true);
		Thread.sleep(500L);
		System.out.println("----------------------------------");

		System.out.println("Spawn!");
		obj.schedule(Schedulers.newThread(), 2, false);
		Thread.sleep(500L);
		System.out.println("----------------------------------");

		/*
		 * Creates and returns a Scheduler intended for computational work.
		 * 
		 * This can be used for event-loops, processing callbacks and other
		 * computational work.
		 * 
		 * Do not perform IO-bound work on this scheduler. Use io() instead.
		 */
		System.out.println("Computation thread");
		obj.schedule(Schedulers.computation(), 5, true);
		Thread.sleep(500L);
		System.out.println("----------------------------------");

		System.out.println("Spawn!");
		obj.schedule(Schedulers.computation(), 5, false);
		Thread.sleep(500L);
		System.out.println("----------------------------------");

		/*
		 * Creates and returns a Scheduler intended for IO-bound work.
		 * 
		 * The implementation is backed by an Executor thread-pool that will
		 * grow as needed.
		 * 
		 * This can be used for asynchronously performing blocking IO.
		 */
		System.out.println("IO thread");
		obj.schedule(Schedulers.io(), 2, true);
		Thread.sleep(500L);
		System.out.println("----------------------------------");

		System.out.println("Spawn!");
		obj.schedule(Schedulers.io(), 2, false);
		Thread.sleep(500L);
		System.out.println("----------------------------------");

	}

	
	/*
	 * subscribeOn() : Asynchronously subscribes Observers to this Observable on
	 * the specified Scheduler.
	 */
	private void subscribeOn() throws InterruptedException {
		Observable<Integer> range = Observable
				.range(20, 4)
				.doOnEach(Utils.debug("Source"));
		
		/*
		 * Subscribes to an Observable and ignores onNext and onCompleted
		 * emissions.
		 */
		range.subscribe();

		System.out.println("DONE1!");
		System.out.println("---------------------------------------------");

		CountDownLatch latch = new CountDownLatch(1);
		
		/*
		 * This means that the caller thread doesn't block printing 'DONE2!' first
		 * or in between the the numbers, and all the Observable instance
		 * observable logic is executed on a computation thread. This way, you
		 * can use every scheduler you like to decide where to execute the work.
		 */
		Observable<Integer> range2 = Observable
		  .range(20, 4)
		  .doOnEach(Utils.debug("Source"))
		  .subscribeOn(Schedulers.computation())
		  .doAfterTerminate(() -> latch.countDown());
		
		range2.subscribe();
		
		System.out.println("DONE2!");
		latch.await();
	}

	/*
	 *  The call to it that is the closest to the beginning of the chain matters.
	 * Here we subscribe on the computation scheduler first, then on the IO
	 * scheduler, and then on the new thread scheduler, but our code will be
	 * executed on the computation scheduler because this is specified first in
	 * the chain.
	 * 
	 *  In conclusion, don't specify a scheduler in methods producing Observable
	 * instances; leave this choice to the callers of your methods.
	 * Alternatively, make your methods receive a Scheduler instance as a
	 * parameter; like the Observable.interval method, for example.
	 */
	private void multipleSubscribeOn() throws InterruptedException{
		CountDownLatch latch = new CountDownLatch(1);
		
		Observable<Integer> range = Observable
		  .range(20, 3)
		  .doOnEach(Utils.debug("Source"))
		  .subscribeOn(Schedulers.computation());
		
		Observable<Character> chars = range
		  .map(n -> n + 48)
		  .map(n -> Character.toChars(n))
		  .subscribeOn(Schedulers.io())
		  .map(c -> c[0])
		  .subscribeOn(Schedulers.newThread())
		  .doOnEach(Utils.debug("Chars ", "    "))
		  .doAfterTerminate(() -> latch.countDown());
		
		/*
		 * Subscribes to an Observable and ignores onNext and onCompleted
		 * emissions.
		 */
		chars.subscribe();
		
		latch.await();
	}
	
	/*
	 * We can achieve parallelism only by using the operators that we already
	 * know. Think about the flatMap() operator; it creates an Observable
	 * instance for each item emitted by the source. If we call the
	 * subscribeOn() operator with a Scheduler instance on these Observable
	 * instances, each one of them will be scheduled on a new Worker instance,
	 * and they'll work in parallel (if the host machine allows that).
	 * 
	 * We can see by the names of the threads that the Observable instances
	 * defined through the flatMap() operator are executed in parallel. And
	 * that's really the case—the four threads are using the four cores of my
	 * processor.
	 */	
	private void parallelism() throws InterruptedException{
		CountDownLatch latch = new CountDownLatch(1);
		
		Observable<Integer> range = Observable
				.range(20, 5)
				.flatMap(n -> Observable
				.range(n, 3)
				.subscribeOn(Schedulers.computation())
				.doOnEach(Utils.debug("Source")));
		
		
		Observable<Character> chars = range
				.observeOn(Schedulers.newThread())
				.map(n -> n + 48)
				.doOnEach(Utils.debug("+48 ", "    "))
				.observeOn(Schedulers.computation())
				.map(n -> Character.toChars(n))
				.map(c -> c[0])
				.doOnEach(Utils.debug("Chars ", "    "))
				.doAfterTerminate(() -> latch.countDown());
		
		chars.subscribe();
		
		System.out.println("Hey!");
		
		latch.await();
	}
	
	public static void main(String[] args) throws InterruptedException {
		Threads obj = new Threads();
		obj.test();
	}

	private void test() {
		
	}

}
