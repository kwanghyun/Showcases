package com.cisco.locker.ms.dsa;

import java.util.concurrent.CountDownLatch;

import org.dsa.iot.dslink.DSLink;
import org.dsa.iot.dslink.DSLinkHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.locker.ms.web.Server;

public class DsaHandler extends DSLinkHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);
	
	CountDownLatch latch;
	
	public DsaHandler(CountDownLatch _latch) {
		latch = _latch;
	}

	@Override
	public boolean isResponder() {
		return true;
	}

	@Override
	public void onResponderInitialized(final DSLink link) {
		LOGGER.info("Initialized");
	}

	@Override
	public void onResponderConnected(final DSLink link) {
		LOGGER.info("Connected");
		latch.countDown();
	}
}
