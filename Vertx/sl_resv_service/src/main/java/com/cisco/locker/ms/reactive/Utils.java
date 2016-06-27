package com.cisco.locker.ms.reactive;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.HttpAsyncClient;

import rx.Notification;
import rx.Observable;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscription;
import rx.apache.http.ObservableHttp;
import rx.apache.http.ObservableHttpResponse;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

public class Utils {
	public static <T> void subscribePrint(Observable<T> observable, String name) {
		observable.subscribe((v) -> System.out.println(name + " : " + v), (e) -> {
			System.err.println("Error from " + name + ":");
			System.err.println(e.getMessage());
		} , () -> System.out.println(name + " ended!"));
	}

	public static <T> Subscription subscribePrintAndReturn(Observable<T> observable, String name) {
		return observable.subscribe((v) -> System.out.println(name + " : " + v), (e) -> {
			System.err.println("Error from " + name + ":");
			System.err.println(e.getMessage());
		} , () -> System.out.println(name + " ended!"));
	}

	/**
	 * Subscribes to an observable, printing all its emissions. Blocks until the
	 * observable calls onCompleted or onError.
	 */
	public static <T> void blockingSubscribePrint(Observable<T> observable, String name) {
		CountDownLatch latch = new CountDownLatch(1);
		subscribePrint(observable.doAfterTerminate(() -> latch.countDown()), name);
		try {
			latch.await();
		} catch (InterruptedException e) {
		}
	}

	public static <T> Action1<Notification<? super T>> debug(String description) {
		return debug(description, "");
	}

	public static <T> Action1<Notification<? super T>> debug(String description, String offset) {
		// Creates a new AtomicReference with the given initial value.
		AtomicReference<String> nextOffset = new AtomicReference<String>(">");

		return (Notification<? super T> notification) -> {
			// getKind()
			// Retrieves the kind of this notification: OnNext, OnError, or
			// OnCompleted
			switch (notification.getKind()) {
			case OnNext:

				System.out.println(Thread.currentThread().getName() + "|" + description + ": " + offset
						+ nextOffset.get() + notification.getValue());
				break;
			case OnError:
				System.err.println(Thread.currentThread().getName() + "|" + description + ": " + offset
						+ nextOffset.get() + " X " + notification.getThrowable());
				break;
			case OnCompleted:
				System.out.println(
						Thread.currentThread().getName() + "|" + description + ": " + offset + nextOffset.get() + "|");
				break;
			default:
				break;
			}
			
			// getAndUpdate()
			// Atomically updates the current value with the results of applying
			// the given function, returning the previous value. The function
			// should be side-effect-free, since it may be re-applied when
			// attempted updates fail due to contention among threads.
			nextOffset.getAndUpdate(p -> "-" + p);
		};
	}
	
	public static Observable<Path> listFolder(Path dir, String glob) {
		/*
		 * create() : 
		 * Returns an Observable that will execute the specified function when a
		 * Subscriber subscribes to it.
		 */
		return Observable.<Path> create(subscriber -> {
			try {
				DirectoryStream<Path> stream = Files.newDirectoryStream(dir, glob);

				/*
				 * add() : 
				 * Adds a Subscription to this Subscriber's list of
				 * subscriptions if this list is not marked as unsubscribed. 
				 */				
				subscriber.add(Subscriptions.create(() -> {
					try {
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}));
				
				/*
				 * Converts an Iterable sequence into an Observable that emits
				 * the items in the sequence.
				 */
				Observable.<Path> from(stream).subscribe(subscriber);
				
			} catch (DirectoryIteratorException ex) {
				subscriber.onError(ex);
			} catch (IOException ioe) {
				subscriber.onError(ioe);
			}
		});
	}
	
	public static <T> Observable<T> sorted(Comparator<? super T> comparator, T... data) {
		List<T> listData = Arrays.asList(data);
		listData.sort(comparator);
		
		return Observable.from(listData);
	}
	
	public static Observable<Long> interval(Long... gaps) {
		return interval(Arrays.asList(gaps));
	}
	
	public static Observable<Long> interval(List<Long> gaps) {
		return interval(gaps, TimeUnit.MILLISECONDS);
	}
	
	public static Observable<Long> interval(List<Long> gaps, TimeUnit unit) {
		return interval(gaps, unit, Schedulers.computation());
	}
	
	public static Observable<Long> interval(List<Long> gaps, TimeUnit unit, Scheduler scheduler) {
		if (gaps == null || gaps.isEmpty()) {
			throw new IllegalArgumentException("Provide one or more interval gaps!");
		}
		
		return Observable.<Long>create(subscriber -> {
			int size = gaps.size();

			/*
			 * Retrieves or creates a new Scheduler.Worker that represents
			 * serial execution of actions.
			 */
			Worker worker = scheduler.createWorker();
			subscriber.add(worker);
			
			final Action0 action = new Action0() {

				long current = 0;
				@Override
				public void call() {
					subscriber.onNext(current++);

					long currentGap = gaps.get((int) current % size);
					/*
					 * current Gap : (delayTime) time to wait before executing the action;
					 * non-positive values indicate an undelayed schedule
					 */
					worker.schedule(this, currentGap, unit);
				}
			};

			/*
			 * Schedules an Action for execution at some point in the future.
			 * 
			 * Note to implementors: non-positive delayTime should be regarded
			 * as undelayed schedule, i.e., as if the
			 * schedule(rx.functions.Action0) was called.
			 */
			worker.schedule(action, gaps.get(0), unit);
		});
	}
	
	public static Observable<ObservableHttpResponse> request(String url) {
		Func0<CloseableHttpAsyncClient> resourceFactory = () -> {
			CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
			client.start();
			
			System.out.println(Thread.currentThread().getName() + " : Created and started the client.");
			return client;
		};
		
		Func1<HttpAsyncClient, Observable<ObservableHttpResponse>> observableFactory = (client) -> {
			System.out.println(Thread.currentThread().getName() + " : About to create Observable.");
			return ObservableHttp.createGet(url, client).toObservable();
		};

		Action1<CloseableHttpAsyncClient> disposeAction = (client) -> {
			try {
				System.out.println(Thread.currentThread().getName() + " : Closing the client.");
				client.close();
			} catch (IOException e) {
			}
		};

		return Observable.using(
				resourceFactory,
				observableFactory,
				disposeAction);
	}

}
