package com.cisco.locker.ms.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class AsyncCommandHelloWorld extends HystrixObservableCommand<String> {

	private final String name;

	public AsyncCommandHelloWorld(String name) {
		super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
		this.name = name;
	}

	@Override
	protected Observable<String> construct() {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(Subscriber<? super String> observer) {
				try {
					if (!observer.isUnsubscribed()) {
						Thread.sleep(800L);
						// a real example would do work like a network call here
						observer.onNext("Hello");
						observer.onNext(name + "!");
						observer.onCompleted();
					}
				} catch (Exception e) {
					observer.onError(e);
				}
			}
		});
	}


	public static void main(String[] args) {
		Observable<String> ho1 = new AsyncCommandHelloWorld("World1").observe();
		Observable<String> ho2 = new AsyncCommandHelloWorld("World2").observe();

		// non-blocking
		// - this is a verbose anonymous inner-class approach and doesn't do
		// assertions
		System.out.println("--------------------------1");
		ho1.subscribe(new Observer<String>() {

			@Override
			public void onCompleted() {
				System.out.println("Completed");
			}

			@Override
			public void onError(Throwable e) {
				e.printStackTrace();
			}

			@Override
			public void onNext(String v) {
				System.out.println("onNext 1 : " + v);
			}
		});
		
		System.out.println("--------------------------2");
		ho2.subscribe((v) -> {
	        System.out.println("onNext 2 : " + v);
	    }, (exception) -> {
	        exception.printStackTrace();
	    });
		System.out.println("--------------------------3");
	}
}