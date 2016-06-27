//package com.cisco.locker.ms.reactive;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import rx.Observable;
//import rx.Observable.OnSubscribe;
//import rx.Observer;
//import rx.Subscriber;
//import rx.functions.Func1;
//import rx.functions.Func2;
//import rx.observables.ConnectableObservable;
//
//public class Calculator {
//	static ConnectableObservable<String> from(final InputStream stream) {
//		return from(new BufferedReader(new InputStreamReader(stream)));// (1)
//	}
//
//	static ConnectableObservable<String> from(final BufferedReader reader) {
//		  return (ConnectableObservable<String>) Observable.create(new OnSubscribe<String>() { // (2)
//		    public void call(Subscriber<? super String> subscriber) {
//		      if (subscriber.isUnsubscribed()) {  // (3)
//		        return;
//		      }
//		      try {
//		        String line;
//		        while(!subscriber.isUnsubscribed() &&
//		          (line = reader.readLine()) != null) { // (4)
//		            if (line == null || line.equals("exit")) { // (5)
//		              break;
//		            }
//		            subscriber.onNext(line); // (6)
//		          }
//		        }
//		        catch (IOException e) { // (7)
//		          subscriber.onError(e);
//		        }
//		        if (!subscriber.isUnsubscribed()) // (8)
//		        subscriber.onCompleted();
//		      }
//		    }
//		  }).publish(); // (9)
//
//	public static Observable<Double> varStream(final String name, Observable<String> input) {
//		final Pattern pattern = Pattern.compile("\\s*" + name + "\\s*[:|=]\\s*(-?\\d+\\.?\\d*)$");
//		return input.map(pattern::matcher) // (1)
//				.filter(m -> m.matches() && m.group(1) != null) // (2)
//				.map(matcher -> matcher.group(1)) // (3)
//				.map(Double::parseDouble); // (4)
//	});
//
//	public static final class ReactiveSum implements Observer<Double> { // (1)
//		private double sum;
//
//		public ReactiveSum(Observable<Double> a, Observable<Double> b) {
//			this.sum = 0;
//			Observable.combineLatest(a, b, new Func2<Double, Double, Double>() { // (5)
//				public Double call(Double a, Double b) {
//					return a + b;
//				}
//			}).subscribe(this); // (6)
//		}
//
//		public void onCompleted() {
//			System.out.println("Exiting last sum was : " + this.sum); // (4)
//		}
//
//		public void onError(Throwable e) {
//			System.err.println("Got an error!"); // (3)
//			e.printStackTrace();
//		}
//
//		public void onNext(Double sum) {
//			this.sum = sum;
//			System.out.println("update : a + b = " + sum); // (2)
//		}
//	}
//
//	public static void main(String[] args) {
//		ConnectableObservable<String> input = from(System.in); // (1)
//
//		Observable<Double> a = varStream("a", input);
//		Observable<Double> b = varStream("b", input);
//
//		ReactiveSum sum = new ReactiveSum(a, b);
//
//		input.connect();
//	}
//}
