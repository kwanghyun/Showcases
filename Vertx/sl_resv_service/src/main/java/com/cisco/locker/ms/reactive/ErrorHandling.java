package com.cisco.locker.ms.reactive;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;

public class ErrorHandling {

	public void onError(){
		Observable<Integer> numbers = Observable
				  .just("1", "2", "three", "4", "5")
				  .map(Integer::parseInt)
				  .onErrorReturn(e -> -1);
		
		Utils.subscribePrint(numbers, "Error returned");
	}
	
	// Instructs an Observable to pass control to another Observable rather than
	// invoking onError if it encounters an java.lang.Exception.
	public void onExceptionResumeNext(){
		Observable<Integer> defaultOnError =
				  Observable.just(5, 4, 3, 2, 1);
		
		Observable<Integer> numbers = Observable
				  .just("1", "2", "three", "4", "5")
				  .map(Integer::parseInt)
				  .onExceptionResumeNext(defaultOnError);
				
		Utils.subscribePrint(numbers, "Exception resumed");
	}
	
	
	class FooException extends RuntimeException {
		public FooException() {
			super("Foo!");
		}
	}

	class BooException extends RuntimeException {
		public BooException() {
			super("Boo!");
		}
	}

	class ErrorEmitter implements OnSubscribe<Integer> {
		private int throwAnErrorCounter = 5;

		@Override
		public void call(Subscriber<? super Integer> subscriber) {
			subscriber.onNext(1);
			subscriber.onNext(2);
			if (throwAnErrorCounter > 4) {
				throwAnErrorCounter--;
				subscriber.onError(new FooException());
				return;
			}
			if (throwAnErrorCounter > 0) {
				throwAnErrorCounter--;
				subscriber.onError(new BooException());
				return;
			}
			subscriber.onNext(3);
			subscriber.onNext(4);
			subscriber.onCompleted();
		}
	}

	public void exceptionTest(){
		Observable<Integer> when = Observable.create(
				new ErrorEmitter()).retryWhen(attempts -> {
			return attempts.flatMap(error -> {
				if (error instanceof FooException) {
					System.err.println("Delaying...");
					return Observable.timer(1L, TimeUnit.SECONDS);
				}
				return Observable.error(error);
			});
		}).retry((attempts, error) -> {
			System.err.println("Boo Exception ...");
			return (error instanceof BooException) && attempts < 3;
		});
		
		Utils.blockingSubscribePrint(when, "retryWhen");
	}
	
	public static void main(String[] args) {
		ErrorHandling obj = new ErrorHandling();
		// obj.onError();
		// obj.onExceptionResumeNext();
		obj.exceptionTest();
	}
}
