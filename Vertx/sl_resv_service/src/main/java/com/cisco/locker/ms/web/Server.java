package com.cisco.locker.ms.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

import java.util.List;

import com.cisco.locker.ms.controller.LockersController;
import com.cisco.locker.ms.controller.ReservationController;
import com.cisco.locker.ms.controller.StagedOrderController;
import com.cisco.locker.ms.util.Properties;
import com.cisco.locker.ms.util.Runner;

public class Server extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(Server.class);

	MongoClient mongo;
	EventBus eb;
	Router router;
	MessageConsumer<JsonObject> consumer;

	@Override
	public void start() throws Exception {

		initEventBus();

		vertx.deployVerticle("com.cisco.common.acmq.ACMQProducer");
		vertx.deployVerticle("com.cisco.common.acmq.ACMQConsumer");

		mongo = MongoClient.createShared(vertx,
				new JsonObject().put("db_name", "demo").put("host", Properties.MONGODB_HOST));

		logger.info("Properties.MONGODB_HOST => " + Properties.MONGODB_HOST);

		router = Router.router(vertx);

		// Allow outbound traffic to the news-feed address
		BridgeOptions options = new BridgeOptions()
				.addOutboundPermitted(new PermittedOptions().setAddress("channel.sl.edge.events"))
				.addInboundPermitted(new PermittedOptions().setAddress("channel.sl.edge.events"));

		router.route("/eventbus/*").handler(SockJSHandler.create(vertx).bridge(options));
		router.route().handler(BodyHandler.create());

		ReservationController rs = new ReservationController(router, mongo);
		rs.loadRoutes();

		StagedOrderController soc = new StagedOrderController(router, mongo);
		soc.loadRoutes();

		LockersController lc = new LockersController(router, mongo);
		lc.loadRoutes();

		// Create a router endpoint for the static content.
		router.route().handler(StaticHandler.create());
		vertx.createHttpServer().requestHandler(router::accept).listen(8080);
	}

	private void initEventBus() {

		eb = vertx.eventBus();

		consumer = eb.consumer("channel.sl.edge.events");
		consumer.handler(message -> {
			try {
				logger.info(message.body());

				JsonObject jsonObj = message.body();
				logger.info("[Server] I have received a edge message: " + jsonObj.encodePrettily());

				eb.send("channel.emit.events.acmq", jsonObj);

			} catch (ClassCastException e) {
				message.reply("Fail to cast string to Json");
				e.printStackTrace();
			}

			message.reply("Message Received");
		});

		consumer.completionHandler(res -> {
			if (res.succeeded()) {
				logger.info("The handler registration has reached all nodes");
			} else {
				logger.info("Registration failed!");
			}
		});
	}

	@Override
	public void stop() throws Exception {
		consumer.unregister(res -> {
			if (res.succeeded()) {
				logger.info("The handler un-registration has reached all nodes");
			} else {
				logger.info("Un-registration failed!");
			}
		});
	}

	public static void main(String[] args) {
		Runner.runExample(Server.class);
	}

}
