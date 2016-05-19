package com.cisco.jms;

import java.util.concurrent.CountDownLatch;

public class Receiver {

	private CountDownLatch latch = new CountDownLatch(5);

	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}

}
