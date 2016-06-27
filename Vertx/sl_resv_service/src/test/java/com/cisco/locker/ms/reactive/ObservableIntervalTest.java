package com.cisco.locker.ms.reactive;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

public class ObservableIntervalTest {
	
	@Test(expected = IllegalArgumentException.class) 
	public void testExceptionWithNoGaps() {
		Utils.interval();
	}
	
	@Test
	public void testBehavesAsNormalIntervalWithOneGap() throws Exception {
		/*
		 * rx.schedulers.TestScheduler
		 * 
		 * It allows you to test schedules of events by manually advancing the
		 * clock at whatever pace you choose.
		 */
		TestScheduler testScheduler = Schedulers.test();
		Observable<Long> interval = Utils.interval(
				Arrays.asList(100L), TimeUnit.MILLISECONDS, testScheduler
				);
		
		TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
		interval.subscribe(subscriber);
		
		assertTrue(subscriber.getOnNextEvents().isEmpty());
		
		/*
		 * List<Long> rx.observers.TestSubscriber.getOnNextEvents()
		 * 
		 * Returns the sequence of items observed by this Subscriber, as an
		 * ordered List.
		 */
		testScheduler.advanceTimeBy(101L, TimeUnit.MILLISECONDS);
		assertEquals(Arrays.asList(0L), subscriber.getOnNextEvents());
		
		testScheduler.advanceTimeBy(101L, TimeUnit.MILLISECONDS);
		assertEquals(Arrays.asList(0L, 1L), subscriber.getOnNextEvents());
		
		testScheduler.advanceTimeTo(1L, TimeUnit.SECONDS);
		assertEquals(Arrays.asList(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L), subscriber.getOnNextEvents());
	}

	@Test
	public void testWithMabyGaps() throws Exception {
		TestScheduler testScheduler = Schedulers.test();
		Observable<Long> interval = Utils.interval(
				Arrays.asList(100L, 200L, 300L), TimeUnit.MILLISECONDS, testScheduler
				);
		
		TestSubscriber<Long> subscriber = new TestSubscriber<Long>();
		interval.subscribe(subscriber);
		
		assertTrue(subscriber.getOnNextEvents().isEmpty());
		
		testScheduler.advanceTimeBy(100L, TimeUnit.MILLISECONDS);
		assertEquals(Arrays.asList(0L), subscriber.getOnNextEvents());
		
		testScheduler.advanceTimeBy(100L, TimeUnit.MILLISECONDS);
		assertEquals(Arrays.asList(0L), subscriber.getOnNextEvents());
		
		testScheduler.advanceTimeTo(1L, TimeUnit.SECONDS);
		assertEquals(Arrays.asList(0L, 1L, 2L, 3L, 4L), subscriber.getOnNextEvents());
	}
}
