package com.cisco.locker.ms.reactive;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class Basic<T> {

	public void java6Example() {
		List<String> list = Arrays.asList("One", "Two", "Three", "Four", "Five");

		Observable<String> observable = Observable.from(list);
		observable.subscribe(new Action1<String>() {
			@Override
			public void call(String element) {
				System.out.println("v : " + element);
			}
		}, new Action1<Throwable>() {
			@Override
			public void call(Throwable t) {
				System.err.println("err : " + t); // (1)
			}
		}, new Action0() {
			@Override
			public void call() {
				System.out.println("We've finnished!"); // (2)
			}
		});
	}

	public void from() {
		List<String> list = Arrays.asList("blue", "red", "green", "yellow", "orange", "cyan", "purple");
		Observable<String> listObservable = Observable.from(list);
		listObservable.subscribe(System.out::println);
		System.out.println("----------------------------------------------");
		listObservable.subscribe(color -> System.out.print(color + "|"), System.out::println, System.out::println);
		System.out.println("----------------------------------------------");
		listObservable.subscribe(color -> System.out.print(color + "/"));
		System.out.println("");

	}

	public void directoryScan() {
		Path resources = Paths.get("src", "main", "resources");
		try (DirectoryStream<Path> dStream = Files.newDirectoryStream(resources)) {
			Observable<Path> dirObservable = Observable.from(dStream);
			dirObservable.subscribe(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void just() {
		Observable.just('R', 'x', 'J', 'a', 'v', 'a').subscribe(System.out::print, System.err::println,
				System.out::println);
	}

	public void etc() throws Exception {
		subscribePrintLong(Observable.interval(500L, TimeUnit.MILLISECONDS), "Interval Observable");
		subscribePrintLong(Observable.timer(1L, TimeUnit.SECONDS), "Timer Observable");

		subscribePrint(Observable.error(new Exception("Test Error!")), "Error Observable");
		subscribePrint(Observable.empty(), "Empty Observable");
		subscribePrint(Observable.never(), "Never Observable");
		subscribePrintInt(Observable.range(1, 3), "Range Observable");
		Thread.sleep(2000L);
	}

	public Observable<T> fromIterable(final Iterable<T> iterable) {
		return Observable.create(new OnSubscribe<T>() {
			@Override
			public void call(Subscriber<? super T> subscriber) {
				try {
					Iterator<T> iterator = iterable.iterator();
					while (iterator.hasNext()) {
						if (subscriber.isUnsubscribed()) {
							return;
						}
						subscriber.onNext(iterator.next());
					}
					if (!subscriber.isUnsubscribed()) {
						subscriber.onCompleted();
					}
				} catch (Exception e) {
					if (!subscriber.isUnsubscribed()) {
						subscriber.onError(e);
					}
				}
			}
		});
	}

	public void createTest() throws IOException {
		Basic<String> b = new Basic<>();
		Path path = Paths.get("src", "main", "resources", "log4j.properties"); // (1)
		List<String> data = Files.readAllLines(path);

		// rx.schedulers.Schedulers.computation()
		// Creates and returns a Scheduler intended for computational work.
		// This can be used for event-loops, processing callbacks and other
		// computational work.
		// Do not perform IO-bound work on this scheduler. Use io() instead.
		Observable<String> observable = b.fromIterable(data).subscribeOn(Schedulers.computation()); // (2)
		Subscription subscription = b.subscribePrintWithReturn(observable, "File");// (3)
		System.out.println("Before unsubscribe!");
		System.out.println("-------------------");
		subscription.unsubscribe(); // (4)
		System.out.println("-------------------");
		System.out.println("After unsubscribe!");
		subscription = b.subscribePrintWithReturn(observable, "File");
		// (4) The subscription is used to unsubscribe from the Observable
		// instance,
		// so the whole file won't be printed and there are markers showing when
		// the unsubscription is executed.
	}

	public void publish() {
		Basic<Long> b = new Basic<>();

		Observable<Long> interval = Observable.interval(200L, TimeUnit.MILLISECONDS);

		// A ConnectableObservable resembles an ordinary Observable, except that
		// it does not begin emitting items when it is subscribed to, but only
		// when its connect method is called. In this way you can wait for all
		// intended Subscribers to Observable.subscribe to the Observable before
		// the Observable begins emitting items.
		ConnectableObservable<Long> published = interval.publish();
		Subscription sub1 = b.subscribePrintAndReturn(published, "First");
		Subscription sub2 = b.subscribePrintAndReturn(published, "Second");
		published.connect();
		Subscription sub3 = null;
		try {
			Thread.sleep(1000L);
			sub3 = b.subscribePrintAndReturn(published, "[Third]");
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
		sub1.unsubscribe();
		sub2.unsubscribe();
		sub3.unsubscribe();
	}

	public void refCount() throws Exception {
		Basic<Long> b = new Basic<>();
		Observable<Long> interval = Observable.interval(200L, TimeUnit.MILLISECONDS);

		// Observable<Long> rx.observables.ConnectableObservable.refCount()
		// Returns an Observable that stays connected to this
		// ConnectableObservable as long as there is at least one subscription
		// to this ConnectableObservable.
		Observable<Long> refCount = interval.publish().refCount();
		Subscription sub1 = b.subscribePrintAndReturn(refCount, "First");

		Thread.sleep(500L);

		Subscription sub2 = b.subscribePrintAndReturn(refCount, "Second");

		Thread.sleep(1000L);

		sub1.unsubscribe();
		sub2.unsubscribe();
		Subscription sub3 = b.subscribePrintAndReturn(refCount, "---[Third]");

		Thread.sleep(1000L);

		sub3.unsubscribe();
	}

	// (2) Here, we create a PublishSubject instance—a Subject instance that
	// emits
	// to an Observer instance only those items that are emitted by the source
	// Observable instance subsequent to the time of the subscription. This
	// behavior is similar to that of the ConnectableObservable instance created
	// by the publish() method. The new Subject instance is subscribed to the
	// interval Observable instance , created by the interval factory method,
	// which is possible because the Subject class implements the Observer
	// interface. Also, note that the Subject signature has two generic
	// types—one for the type of notifications the Subject instance will receive
	// and another for the type of the notifications it will emit. The
	// PublishSubject class has the same type for its input and output
	// notifications.
	// Note that it is possible to create a PublishSubject instance without
	// subscribing to a source Observable instance. It will emit only the
	// notifications passed to its onNext() and onError() methods and will
	// complete when calling its onCompleted() method.
	public void subject() {
		Basic<Long> b = new Basic<>();

		Observable<Long> interval = Observable.interval(200L, TimeUnit.MILLISECONDS); // (1)
		Subject<Long, Long> publishSubject = PublishSubject.create(); // (2)

		// We can subscribe to the Subject instance; it is an Observable
		// instance after all.
		interval.subscribe(publishSubject);

		Subscription sub1 = b.subscribePrintAndReturn(publishSubject, "First");
		Subscription sub2 = b.subscribePrintAndReturn(publishSubject, "Second");
		Subscription sub3 = null;
		try {
			Thread.sleep(500L);

			// We can emit a custom notification at any time. It will be
			// broadcast to all the subscribers of the subject. We can even call
			// the onCompleted() method and close the notification stream.
			publishSubject.onNext(555L);

			// The third Subscriber will only receive notifications emitted
			// after it subscribes.
			sub3 = b.subscribePrintAndReturn(publishSubject, "----[Third]"); // (5)

			Thread.sleep(1000L);

			publishSubject.onNext(777L);

			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
		sub1.unsubscribe(); // (6)
		sub2.unsubscribe();
		sub3.unsubscribe();
	}

	public void blockingCallSimulator() {

		Basic<String> service = new Basic<>();
		Observable<String> ob1 = service.blockingCall1();
		System.out.println("Called the service-1");
		Observable<String> ob2 = service.blockingCall2();
		System.out.println("Called the service-2");
		ob1.subscribe(v -> System.out.println(v));
		System.out.println("Done with server-1");
		ob2.subscribe(v -> System.out.println(v));
		System.out.println("Done with server-2");

	}

	protected Observable<String> blockingCall1() {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> observer) {
				try {
					if (!observer.isUnsubscribed()) {
						for (int i = 0; i < 5; i++) {
							Thread.sleep(200L);
							System.out.println(Thread.currentThread().getName() + " : " + i);
						}

						// a real example would do work like a network call here
						observer.onNext("DATA1");
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			}
		});
	}

	protected Observable<String> blockingCall2() {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> observer) {
				try {
					if (!observer.isUnsubscribed()) {
						for (int i = 0; i < 5; i++) {
							Thread.sleep(200L);
							System.out.println(Thread.currentThread().getName() + " : " + i);
						}
						// a real example would do work like a network call here
						observer.onNext("DATA2");
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			}
		});
	}

	public static void main(String[] args) throws Exception {
		Basic<Object> b = new Basic<>();
		// b.java6Example();
		// b.from();
		// b.just();
		// b.etc();
		// b.createTest();
		// b.publish();
		// b.refCount();
		// b.subject();
		b.blockingCallSimulator();
		// b.flatMap();
		// b.scan();
		// b.zip();
		// b.combineLast();
		// b.merge();
		// b.concat();
		// b.amb();
		// b.take();
	}

	public void flatMap() {
		Observable<Integer> flatMapped = Observable.just(-1, 0, 1).map(v -> 2 / v).flatMap(v -> Observable.just(v),
				e -> Observable.just(0), () -> Observable.just(42));

		subscribePrintInt(flatMapped, "flatMap");

		Observable<Integer> flatMapped2 = Observable.just(5, 432).flatMap(v -> Observable.range(v, 2), (x, y) -> x + y);
		subscribePrintInt(flatMapped2, "flatMap");
	}

	public void groupBy() {
		List<String> albums = Arrays.asList("The Piper at the Gates of Dawn", "A Saucerful of Secrets", "More",
				"Ummagumma", "Atom Heart Mother", "Meddle", "Obscured by Clouds", "The Dark Side of the Moon",
				"Wish You Were Here", "Animals", "The Wall");
		//
		// Observable.from(albums).groupBy(album -> album.split(" ").length)
		// .subscribe(obs -> subscribePrint(obs, obs.getKey() + " word(s)"));
	}

	public void scan() {
		Observable<Integer> scan = Observable.range(1, 10).scan((p, v) -> p + v);

		subscribePrintInt(scan, "Sum");
		subscribePrintInt(scan.last(), "Final sum");
		// 1, 3 (1+2), 6 (3 + 3), 10 (6 + 4) .. 55.

	}

	public void zip() {
		Observable<Integer> zip = Observable.zip(Observable.just(1, 3, 4), Observable.just(5, 2, 6), (a, b) -> a + b);
		subscribePrintInt(zip, "Simple zip");
		System.out.println("-------------------------------");

		Observable<String> timedZip = slowDown(Observable.from(Arrays.asList("Z", "I", "P", "P")), 300);
		blockingSubscribePrintString(timedZip, "Timed zip");

		Observable<String> timedZip2 = Observable.from(Arrays.asList("Z", "I", "P", "P"))
				.zipWith(Observable.interval(300L, TimeUnit.MILLISECONDS), (value, skip) -> value);
		blockingSubscribePrintString(timedZip2, "Timed zip");
	}

	// Combines three source Observables by emitting an item that aggregates the
	// latest values of each of the source Observables each time an item is
	// received from any of the source Observables
	public void combineLast() {
		Observable<String> greetings = Observable.just("Hello", "Hi", "Howdy", "Zdravei", "Yo", "Good to see ya")
				.zipWith(Observable.interval(1L, TimeUnit.SECONDS), this::onlyFirstArg);

		Observable<String> names = Observable.just("Meddle", "Tanya", "Dali", "Joshua")
				.zipWith(Observable.interval(1500L, TimeUnit.MILLISECONDS), this::onlyFirstArg);

		Observable<String> punctuation = Observable.just(".", "?", "!", "!!!", "...")
				.zipWith(Observable.interval(1100L, TimeUnit.MILLISECONDS), this::onlyFirstArg);

		Observable<String> combined = Observable.combineLatest(greetings, names, punctuation,
				(greeting, name, puntuation) -> greeting + " " + name + puntuation);
		blockingSubscribePrintString(combined, "Sentences");
	}

	// Flattens three Observables into a single Observable, without any
	// transformation.
	public void merge() {
		Observable<String> greetings = Observable.just("Hello", "Hi", "Howdy", "Zdravei", "Yo", "Good to see ya")
				.zipWith(Observable.interval(1L, TimeUnit.SECONDS), this::onlyFirstArg);

		Observable<String> names = Observable.just("Meddle", "Tanya", "Dali", "Joshua")
				.zipWith(Observable.interval(1500L, TimeUnit.MILLISECONDS), this::onlyFirstArg);

		Observable<String> punctuation = Observable.just(".", "?", "!", "!!!", "...")
				.zipWith(Observable.interval(1100L, TimeUnit.MILLISECONDS), this::onlyFirstArg);

		Observable<String> merged = Observable.merge(greetings, names, punctuation);
		blockingSubscribePrintString(merged, "Words");
	}

	// The main difference between the merge() and concat() operators is that
	// merge()subscribes to all source Observable instances at the same time,
	// whereas concat()has exactly one subscription at any time.
	public void concat() {
		Observable<String> greetings = Observable.just("Hello", "Hi", "Howdy", "Zdravei", "Yo", "Good to see ya")
				.zipWith(Observable.interval(1L, TimeUnit.SECONDS), this::onlyFirstArg);

		Observable<String> names = Observable.just("Meddle", "Tanya", "Dali", "Joshua")
				.zipWith(Observable.interval(1500L, TimeUnit.MILLISECONDS), this::onlyFirstArg);

		Observable<String> punctuation = Observable.just(".", "?", "!", "!!!", "...")
				.zipWith(Observable.interval(1100L, TimeUnit.MILLISECONDS), this::onlyFirstArg);

		Observable<String> concat = Observable.concat(greetings, names, punctuation);
		blockingSubscribePrintString(concat, "Concat");
	}

	public <A, B> A onlyFirstArg(A arg1, B arg2) {
		return arg1;
	}

	public <A> Observable<A> slowDown(Observable<A> o, long ms) {
		return o.zipWith(Observable.interval(ms, TimeUnit.MILLISECONDS), (elem, i) -> elem);
	}

	// Given two Observables, mirrors the one that first either emits an item or
	// sends a termination notification.
	public void amb() {
		Observable<String> words = Observable.just("Some", "Other");
		Observable<Long> interval = Observable.interval(500L, TimeUnit.MILLISECONDS).take(2);
		subscribePrintObject(Observable.amb(words, interval), "Amb 1");

		Random r = new Random();

		Observable<String> source1 = Observable.just("data from source 1").delay(1500, TimeUnit.MILLISECONDS);

		Observable<String> source2 = Observable.just("data from source 2").delay(1000, TimeUnit.MILLISECONDS);

		blockingSubscribePrintString(Observable.amb(source1, source2), "Amb 2");
	}

	public void take() {
		Observable<String> words = Observable // (1)
				.just("one", "way", "or", "another", "I'll", "learn", "RxJava")
				.zipWith(Observable.interval(200L, TimeUnit.MILLISECONDS), (x, y) -> x);

		Observable<Long> interval = Observable.interval(500L, TimeUnit.MILLISECONDS);

		blockingSubscribePrintString(words.takeUntil(interval), "takeUntil"); // (2)
		blockingSubscribePrintString(words.takeWhile(word -> word.length() > 2), "takeWhile");
		blockingSubscribePrintString(words.skipUntil(interval), "skipUntil"); // (4)
	}

	void subscribePrint(Observable<T> observable, String name) {
		observable.subscribe((v) -> System.out.println(name + " : " + v), (e) -> {
			System.err.println("Error from " + name + ":");
			System.err.println(e.getMessage());
		} , () -> System.out.println(name + " ended!"));
	}

	Subscription subscribePrintAndReturn(Observable<T> observable, String name) {
		return observable.subscribe((v) -> System.out.println(name + " : " + v), (e) -> {
			System.err.println("Error from " + name + ":");
			System.err.println(e.getMessage());
		} , () -> System.out.println(name + " ended!"));
	}

	Subscription subscribePrintWithReturn(Observable<T> observable, String name) {
		return observable.subscribe((v) -> System.out.println(name + " : " + v), (e) -> {
			System.err.println("Error from " + name + ":");
			System.err.println(e.getMessage());
		} , () -> System.out.println(name + " ended!"));
	}

	void subscribePrintLong(Observable<Long> observable, String name) {
		observable.subscribe((v) -> System.out.println(name + " : " + v), (e) -> {
			System.err.println("Error from " + name + ":");
			System.err.println(e.getMessage());
		} , () -> System.out.println(name + " ended!"));
	}

	void subscribePrintInt(Observable<Integer> observable, String name) {
		observable.subscribe((v) -> System.out.println(name + " : " + v), (e) -> {
			System.err.println("Error from " + name + ":");
			System.err.println(e.getMessage());
		} , () -> System.out.println(name + " ended!"));
	}

	void subscribePrintString(Observable<String> observable, String name) {
		observable.subscribe((v) -> System.out.println(name + " : " + v), (e) -> {
			System.err.println("Error from " + name + ":");
			System.err.println(e.getMessage());
		} , () -> System.out.println(name + " ended!"));
	}

	void subscribePrintObject(Observable<Object> observable, String name) {
		observable.subscribe((v) -> System.out.println(name + " : " + v), (e) -> {
			System.err.println("Error from " + name + ":");
			System.err.println(e.getMessage());
		} , () -> System.out.println(name + " ended!"));
	}

	/**
	 * Subscribes to an observable, printing all its emissions. Blocks until the
	 * observable calls onCompleted or onError.
	 */
	public void blockingSubscribePrintString(Observable<String> observable, String name) {
		CountDownLatch latch = new CountDownLatch(1);
		subscribePrintString(observable.doAfterTerminate(() -> latch.countDown()), name);
		try {
			latch.await();
		} catch (InterruptedException e) {
		}
	}
}
