package io.vertx.example.web;

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

	@Override
	public void start() throws Exception {

		loadEventBus();

		mongo = MongoClient.createShared(vertx,
				new JsonObject().put("db_name", "demo").put("host", Properties.MONGODB_HOST));

		router = Router.router(vertx);
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

	private void loadEventBus() {

		eb = vertx.eventBus();

		MessageConsumer<String> consumer = eb.consumer("channel.sl.edge.events");
		consumer.handler(message -> {
			logger.info("I have received a edge message: " + message.body());
			message.reply("I've got your message yo!!!");
		});

		consumer.completionHandler(res -> {
			if (res.succeeded()) {
				logger.info("The handler registration has reached all nodes");
			} else {
				logger.info("Registration failed!");
			}
		});

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
