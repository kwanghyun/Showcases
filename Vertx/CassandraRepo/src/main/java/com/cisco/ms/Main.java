package com.cisco.ms;

import com.cisco.ms.kafka.KafkaMain;
import com.cisco.ms.kafka.pub.MessageProducer;
import com.cisco.ms.kafka.sub.MessageConsumer;
import io.vertx.core.Vertx;
import org.apache.log4j.Logger;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * Created by kwjang on 2/22/16.
 */
public class Main {

    final static private Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args)throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        final Vertx vertx = Vertx.vertx();
//        vertx.deployVerticle(new CassandraRepo(), result -> {
//            if (result.succeeded()) {
//                System.out.println("Deployment id is: " + result.result());
//            } else {
//                System.out.println("Deployment failed!");
//                result.cause().printStackTrace();
//            }
//            latch.countDown();
//        });

        vertx.deployVerticle(new KafkaMain(), result -> {
            if (result.succeeded()) {
                logger.info("Deployment id is: " + result.result());
            } else {
                logger.info("Deployment failed!");
                result.cause().printStackTrace();
            }
            latch.countDown();
        });


        latch.await(5, TimeUnit.SECONDS);
        logger.info("Vertx is running......");

    }
}
