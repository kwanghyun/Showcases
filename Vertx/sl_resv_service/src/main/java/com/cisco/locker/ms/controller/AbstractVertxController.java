package com.cisco.locker.ms.controller;

import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;

public abstract class AbstractVertxController {

	Router router;
	MongoClient mongo;
	
	public AbstractVertxController(Router router, MongoClient mongo){
		this.router = router;
		this.mongo = mongo;
//		loadRoutes();
	}
	
	public abstract void loadRoutes();
}
