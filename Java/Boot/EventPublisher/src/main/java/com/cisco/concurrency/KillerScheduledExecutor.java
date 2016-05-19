package com.cisco.concurrency;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cisco.jms.AmqpPublisherApplication;

//@Component
public class KillerScheduledExecutor extends ScheduledThreadPoolExecutor {
	
	private static final Logger LOG = LoggerFactory.getLogger(KillerScheduledExecutor.class);
	
	private AtomicLong msgCount = new AtomicLong(0L);
	private final long maxMsgCount;
	AmqpPublisherApplication app;
	
	public KillerScheduledExecutor(int corePoolSize, long maxMsgCount) {
		super(corePoolSize);
		LOG.info("KillerScheduledExecutor created.....");
		this.maxMsgCount = maxMsgCount;
	}
	
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		if (msgCount.incrementAndGet() == maxMsgCount) {
			LOG.info("Shutting down killer executor.....");
			stopTasks();
		}
	}
	
	public void stopTasks() {
		LOG.info("KillerScheduler Shutdown() called.....");
		this.app.stopTasks();
	}

	public void setApp(AmqpPublisherApplication app) {
		this.app = app;
	}

	
}
