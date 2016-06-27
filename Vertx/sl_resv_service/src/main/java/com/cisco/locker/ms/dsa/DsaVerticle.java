package com.cisco.locker.ms.dsa;

import java.util.concurrent.CountDownLatch;

import org.dsa.iot.dslink.DSLinkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cisco.locker.ms.web.Server;

import io.vertx.core.AbstractVerticle;

public class DsaVerticle extends AbstractVerticle {
	private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

	DsaHandler dsaHandler;
	CountDownLatch latch = new CountDownLatch(1);
	
	@Override
	public void start() throws Exception {

		dsaHandler = new DsaHandler(latch);
		String dsaBrokerUrl = "http://10.106.9.143:8080/conn";
		String dsLinkPath = "dslink.json";

		LOGGER.info("dsaBrokerUrl : " + dsaBrokerUrl);
		LOGGER.info("dsLinkPath : " + dsLinkPath);

		final String[] args = { "--broker", dsaBrokerUrl, "-d", dsLinkPath, "--name", "Vertx-demo", "--log", "info" };

		Thread dsaThread = new Thread(() -> {
			DSLinkFactory.start(args, dsaHandler);
		});
		dsaThread.setDaemon(true);
		dsaThread.start();
		LOGGER.info("DSA Handler Started ...");
		try {
			// TODO: Implement retry in case of timeout.
			latch.await();
		} catch (InterruptedException ie) {
			LOGGER.error("The world is cruel and full of interruptions!");
			throw new RuntimeException("Couldn't start the DSA Requester");
		}

		LOGGER.info("DSA Handler Connected...");

	}
}
