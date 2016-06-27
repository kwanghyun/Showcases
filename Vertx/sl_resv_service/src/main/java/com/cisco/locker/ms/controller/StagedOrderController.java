package com.cisco.locker.ms.controller;

import java.time.LocalDateTime;

import org.apache.log4j.Logger;

import com.cisco.locker.ms.util.Properties;
import com.cisco.locker.ms.util.TimeUtils;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;

public class StagedOrderController extends AbstractVertxController{

	private static final Logger logger = Logger.getLogger(StagedOrderController.class);
	private static final String API_NAME = "/staged";
	private static final String COLLECTION_NAME = "staged";

	public StagedOrderController(Router router, MongoClient mongo) {
		super(router, mongo);
	}
	
	@Override
	public void loadRoutes() {

		// define some REST API
		router.get(Properties.API_ROOT + API_NAME).handler(ctx -> {
			mongo.find(COLLECTION_NAME, new JsonObject(), lookup -> {
				// error handling
				if (lookup.failed()) {
					ctx.fail(500);
					return;
				}

				// now convert the list to a JsonArray because it will be easier
				// to encode the final object as the response.
				final JsonArray jsonArr = new JsonArray();
				logger.info(API_NAME + " :: GET :: jsonArry.size => " + jsonArr.size());

				for (JsonObject o : lookup.result()) {
					jsonArr.add(o);
				}
				
				ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
				ctx.response().end(jsonArr.encodePrettily());
			});
		});

		router.get(Properties.API_ROOT + API_NAME + "/:packageId").handler(ctx -> {
			mongo.findOne(COLLECTION_NAME, new JsonObject().put("packageId", ctx.request().getParam("packageId")), null,
					lookup -> {
				// error handling
				if (lookup.failed()) {
					ctx.fail(500);
					return;
				}

				JsonObject jsonObj = lookup.result();
				logger.info(API_NAME + " :: GET :: jsonObj => " + jsonObj);

				if (jsonObj == null) {
					ctx.fail(404);
				} else {
					ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
					ctx.response().end(jsonObj.encodePrettily());
				}
			});
		});

		router.post(Properties.API_ROOT + API_NAME).handler(ctx -> {
			JsonObject newJsonObj = ctx.getBodyAsJson();
			
			logger.info(API_NAME + " :: " + newJsonObj.encodePrettily());

			mongo.findOne(COLLECTION_NAME, new JsonObject().put("packageId", newJsonObj.getString("packageId")),
					null, lookup -> {
				// error handling
				if (lookup.failed()) {
					logger.error("Lookup Failed....");
					ctx.fail(500);
					return;
				}

				JsonObject jsonObj = lookup.result();

				if (jsonObj != null) {
					logger.error("Already exists :: " + jsonObj.encodePrettily());
					// already exists
					ctx.fail(500);
				} else {

					mongo.insert(COLLECTION_NAME, newJsonObj, insert -> {
						// error handling
						if (insert.failed()) {
							logger.error("Insert Failed....");
							ctx.fail(500);
							return;
						}

						// add the generated id to the object
						newJsonObj.put("_id", insert.result());

						ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
						ctx.response().end(newJsonObj.encodePrettily());
					});
				}
			});
		});

		router.put(Properties.API_ROOT + API_NAME + "/:packageId").handler(ctx -> {
			mongo.findOne(COLLECTION_NAME, new JsonObject().put("packageId", ctx.request().getParam("packageId")), null,
					lookup -> {
				// error handling
				if (lookup.failed()) {
					ctx.fail(500);
					return;
				}

				JsonObject jsonObj = lookup.result();
				logger.info(API_NAME + " :: PUT :: jsonObj => " + jsonObj);

				if (jsonObj == null) {
					// does not exist
					ctx.fail(404);
				} else {
					logger.info(API_NAME + "[PUT] :: " + jsonObj);
					JsonObject update = ctx.getBodyAsJson();

					// TODO null check.
//					reservation.put("site", update.getString("site").isEmpty() 
//							? reservation.getString("site") : update.getString("site"));
//					reservation.put("bank", update.getString("bank").isEmpty() 
//							? reservation.getString("bank"): update.getString("bank"));
					jsonObj.put("size", update.getInteger("size") != null ? jsonObj.getInteger("size")
							: update.getInteger("size"));
//					reservation.put("packageId", update.getString("packageId").isEmpty()
//							? reservation.getString("packageId") : update.getString("packageId"));
//					reservation.put("reservationDate", update.getString("reservationDate").isEmpty()
//							? reservation.getString("reservationDate") : update.getString("reservationDate"));
//					reservation.put("expiryDate", update.getString("expiryDate").isEmpty()
//							? reservation.getString("expiryDate") : update.getString("expiryDate"));

					mongo.replace(COLLECTION_NAME, new JsonObject().put("_id", jsonObj.getString("_id")),
							jsonObj, replace -> {
						// error handling
						if (replace.failed()) {
							ctx.fail(500);
							return;
						}

						ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
						ctx.response().end(jsonObj.encodePrettily());
					});
				}
			});
		});

		router.delete(Properties.API_ROOT + API_NAME + "/:packageId").handler(ctx -> {
			mongo.findOne(COLLECTION_NAME, new JsonObject().put("packageId", ctx.request().getParam("packageId")), null,
					lookup -> {
				// error handling
				if (lookup.failed()) {
					ctx.fail(500);
					return;
				}

				JsonObject jsonObj = lookup.result();
				logger.info(API_NAME + " :: DELETE :: jsonObj => " + jsonObj);
				
				if (jsonObj == null) {
					// does not exist
					ctx.fail(404);
				} else {

					mongo.remove(COLLECTION_NAME, new JsonObject().put("packageId", ctx.request().getParam("packageId")),
							remove -> {
						// error handling
						if (remove.failed()) {
							ctx.fail(500);
							return;
						}

						ctx.response().setStatusCode(204);
						ctx.response().end();
						logger.info(API_NAME + " :: DELETE :: SUCCEE");
					});
				}
			});
		});

	}

}
