package com.cisco.locker.ms.reactive;

import java.util.concurrent.atomic.AtomicReference;

/*
 * The compareAndSet(V expect, V update) API method atomically sets 
 * the value to the given updated value, only if the current value is equal to the expected value.
 * 
 * The getAndSet(V newValue) API method atomically sets to the given value and returns the old value.
 * The lazySet(V newValue) API method eventually sets to the given value.
 * The set(V newValue) API method sets to the given value.
 * The get() API method gets the current value.*/
public class AtomicReferenceExample {

	private static String message;
	private static Person person;
	private static AtomicReference<String> aRmessage;
	private static AtomicReference<Person> aRperson;

	static class MyRun1 implements Runnable {

		public void run() {
			aRmessage.compareAndSet(message, "Thread 1");
			message = message.concat("-Thread 1!");
			person.setAge(person.getAge()+1);
			person.setName("Thread 1");
			aRperson.getAndSet(new Person("Thread 1", 1));
			System.out.println("\n" + Thread.currentThread().getName() +" Values " 
					+ message + " - " + person.toString());
			System.out.println("\n" + Thread.currentThread().getName() +" Atomic References " 
					+ message + " - " + person.toString());
		}		
	}
	
	static class MyRun2 implements Runnable {

		public void run() {
			message = message.concat("-Thread 2");
			person.setAge(person.getAge()+2);
			person.setName("Thread 2");
			/*Eventually sets to the given value.*/			
			aRmessage.lazySet("Thread 2");
			aRperson.set(new Person("Thread 2", 2));
			System.out.println("\n" + Thread.currentThread().getName() +" Values: " 
					+ message + " - " + person.toString());
			System.out.println("\n" + Thread.currentThread().getName() +" Atomic References: " 
					+ aRmessage.get() + " - " + aRperson.get().toString());
		}		
	}
	
	static class Person {
		
		private String name;
		private int age;
		
		public Person(String name, int age) {
			this.name = name;
			this.age= age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}
		
		public void setAge(int age) {
			this.age = age;
		}	
		
		@Override
		public String toString() {
			return "[name " + this.name + ", age " + this.age + "]";
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new MyRun1());
		Thread t2 = new Thread(new MyRun2());
		message = "hello";
		person = new Person("Phillip", 23);
		aRmessage = new AtomicReference<String>(message);
		aRperson = new AtomicReference<Person>(person);
		System.out.println("Message is: " + message
				+ "\nPerson is " + person.toString());
		System.out.println("Atomic Reference of Message is: " + aRmessage.get()
				+ "\nAtomic Reference of Person is " + aRperson.get().toString());
		t1.start();
		t2.start();
		t1.join();
		t2.join();	
		System.out.println("\nNow Message is: " + message 
				+ "\nPerson is " + person.toString());
		System.out.println("Atomic Reference of Message is: " + aRmessage.get()
				+ "\nAtomic Reference of Person is " + aRperson.get().toString());
	}
		
}
