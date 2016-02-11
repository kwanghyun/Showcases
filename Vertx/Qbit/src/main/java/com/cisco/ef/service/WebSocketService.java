package com.cisco.ef.service;

import io.advantageous.qbit.http.server.HttpServer;

import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;


import static io.advantageous.qbit.http.server.HttpServerBuilder.httpServerBuilder;
/**
 * Created by kwjang on 2/6/16.
 */
public class WebSocketService {
    final static private Logger logger = Logger.getLogger(WebSocketService.class);

    public static void main(String... args) {


        /* Create an HTTP server. */
        HttpServer httpServer = httpServerBuilder()
                .setPort(8080).build();

        /* Setting up a request Consumer with Java 8 Lambda expression. */
        httpServer.setHttpRequestConsumer(httpRequest -> {

            Map<String, Object> results = new HashMap<>();
            results.put("method", httpRequest.getMethod());
            results.put("uri", httpRequest.getUri());
            results.put("body", httpRequest.getBodyAsString());
            results.put("headers", httpRequest.getHeaders());
            results.put("params", httpRequest.getParams());
            JsonObject jsonObj = new JsonObject(results);
            httpRequest.getReceiver()
                    .response(200, "application/json", jsonObj.encodePrettily());
        });


        /* Start the server. */
        httpServer.start();
        logger.info("Server started port at 8080.....");
    }

}
