package com.cisco.ms.kafka;

import com.cisco.ms.kafka.config.Const;
import com.cisco.ms.kafka.pub.KafkaPublisher;
import com.cisco.ms.kafka.pub.MessageProducer;

import com.cisco.ms.kafka.sub.MessageConsumer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kwjang on 2/24/16.
 */
public class KafkaMain extends AbstractVerticle {
    final static private Logger logger = Logger.getLogger(KafkaMain.class);
    final static private int TEST_INTERVAL = 3000;
    private AtomicInteger counter;

    @Override
    public void start() throws Exception {
//        vertx = Vertx.vertx();

        // Kafka Consumer sample config
        JsonObject consumerConfig = new JsonObject();
        consumerConfig.put(Const.GROUP_ID, "testGroup");
        List<String> topics = new ArrayList<>();
        topics.add("demoTopic");
        consumerConfig.put(Const.BOOTSTRAP_SERVERS, "10.106.9.157:9092");
        consumerConfig.put(Const.TOPICS, new JsonArray(topics));

        deployKafkaConsumer(consumerConfig);

        // Kafka Producer sample config
        JsonObject producerConfig = new JsonObject();
        producerConfig.put(Const.BOOTSTRAP_SERVERS, "10.106.9.157:9092");
        producerConfig.put(Const.DEFAULT_SERIALIZER_CLASS, "org.apache.kafka.common.serialization.StringSerializer");
        producerConfig.put(Const.DEFAULT_TOPIC, "demoTopic");

        deployKafkaProducer(producerConfig);

        counter = new AtomicInteger(1);
        vertx.setPeriodic(TEST_INTERVAL, id -> {
            sendMessage();
        });


        //EVENTBUS_DEFAULT_ADDRESS => "kafka.message.consumer"
        vertx.eventBus().consumer(MessageConsumer.EVENTBUS_DEFAULT_ADDRESS,
                message -> {
                    logger.info(String.format("got message: %s", message.body()));
//                    // message handling code
//                    KafkaEvent event = new KafkaEvent(message.body());
                });


    }

    public void deployKafkaConsumer(JsonObject config) {
        // use your vert.x reference to deploy the consumer verticle
        vertx.deployVerticle(MessageConsumer.class.getName(),
                new DeploymentOptions().setConfig(config),
                deploy -> {
                    if (deploy.failed()) {
                        logger.info(String.format("Failed to start kafka consumer verticle, ex: %s", deploy.cause()));
                        vertx.close();
                        return;
                    }
                    logger.info("kafka consumer verticle started");
                }
        );
    }

    public void deployKafkaProducer(JsonObject config) {
        // use your vert.x reference to deploy the consumer verticle
        vertx.deployVerticle(MessageProducer.class.getName(),
                new DeploymentOptions().setConfig(config),
                deploy -> {
                    if (deploy.failed()) {
                        System.err.println(String.format("Failed to start kafka producer verticle, ex: %s", deploy.cause()));
                        vertx.close();
                        return;
                    }
                    System.out.println("kafka producer verticle started");
                });
    }


    public void sendMessage() {
        KafkaPublisher publisher = new KafkaPublisher(vertx.eventBus());

        // send to the default topic
        publisher.send("a test message on a default topic => " + counter.getAndAdd(1));
        // send to a specific topic
//        publisher.send("SomeSpecialTopic", "a test message on a default topic");
        // send to a specific topic with custom key
//        publisher.send("SomeSpecialTopic", "aUserId", "a test message on a default topic");
        // send to a specific topic and partition
//        publisher.send("SomeSpecialTopic", "", 5, "a test message on a default topic");
    }

    @Override
    public void stop() throws Exception {


    }
}
