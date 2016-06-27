package com.cisco.locker.ms.reactive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.*;

public class ObservableUnitTest {
	private Observable<String> tested;
	private List<String> expected;

	@Before
	public void before() {
		tested = Utils.<String>sorted(
				(a, b) -> a.compareTo(b),
				"Star", "Bar", "Car", "War", "Far", "Jar");
		expected = Arrays.asList("Bar", "Car", "Far", "Jar", "Star", "War");
	}

	@After
	public void after() {
		tested = null;
		expected = null;
	}
	
	private class TestData {
		private Throwable error = null;
		private boolean completed = false;
		private List<String> result = new ArrayList<String>();
		
		public void setError(Throwable error) {
			this.error = error;
		}

		public boolean isCompleted() {
			return completed;
		}

		public void setCompleted(boolean completed) {
			this.completed = completed;
		}

		public List<String> getResult() {
			return result;
		}

		public Throwable getError() {
			return error;
		}
	}
	
	@Test
	public void testUsingNormalSubscription() {
		TestData data = new TestData();
		
		tested.subscribe(
				(v) -> data.getResult().add(v),
				(e) -> data.setError(e),
				() -> data.setCompleted(true));
		
		assertTrue(data.isCompleted());
		assertNull(data.getError());
		assertEquals(expected, data.getResult());
	}
	
	@Test
	public void testUsingBlockingObservable() {
		/* 
		 * single() :
		 * If this BlockingObservable completes after emitting a single item,
		 * return that item, otherwise throw a NoSuchElementException.
		 */
		List<String> result = tested.toList().toBlocking().single();
		
		assertEquals(expected, result);
	}

	@Test
	public void testUsingTestSubscriber() {
		TestSubscriber<String> subscriber = new TestSubscriber<String>();
		tested.subscribe(subscriber);
		
		assertEquals(expected, subscriber.getOnNextEvents());
		assertSame(1, subscriber.getOnCompletedEvents().size());
		assertTrue(subscriber.getOnErrorEvents().isEmpty());
		assertTrue(subscriber.isUnsubscribed());
	}
	
	@Test
	public void testUsingTestSubscriberAssertions() {
		TestSubscriber<String> subscriber = new TestSubscriber<String>();
		tested.subscribe(subscriber);
		
		subscriber.assertReceivedOnNext(expected);
		subscriber.assertTerminalEvent();
		subscriber.assertNoErrors();
		subscriber.assertUnsubscribed();
	}
}
