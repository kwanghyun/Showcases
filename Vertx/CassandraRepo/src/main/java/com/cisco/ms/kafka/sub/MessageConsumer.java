package com.cisco.ms.kafka.sub;

import com.cisco.ms.kafka.config.Const;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by kwjang on 2/23/16.
 */
public class MessageConsumer extends AbstractVerticle {
    final static private Logger logger = Logger.getLogger(MessageConsumer.class);
    public static final String EVENTBUS_DEFAULT_ADDRESS = "kafka.message.consumer";
    public static final int DEFAULT_POLL_MS = 100;

    private KafkaConsumer consumer;
    private EventBus bus;
    private AtomicBoolean running;
    private JsonObject verticleConfig;

    private ExecutorService worker;

    private List<String> topics;
    private String busAddress;
    private int pollIntervalMs;

    // This verticle takes some time to start (maybe it has to deploy other verticles or whatever)
    // So we override the async version of start(), then we can mark the verticle as started some time later
    // when all the slow startup is done, without blocking the actual start method.
    @Override
    public void start(final Future<Void> startedResult) throws Exception {

        try {
            bus = vertx.eventBus();

            running = new AtomicBoolean(true);

            verticleConfig = config();
            Properties kafkaConfig = populateKafkaConfig(verticleConfig);
            JsonArray topicConfig = verticleConfig.getJsonArray(Const.TOPICS);

            busAddress = verticleConfig.getString(Const.EVENTBUS_ADDRESS, EVENTBUS_DEFAULT_ADDRESS);
            pollIntervalMs = verticleConfig.getInteger(Const.CONSUMER_POLL_INTERVAL_MS, DEFAULT_POLL_MS); //DEFAULT_POLL_MS = 100;

            Runtime.getRuntime().addShutdownHook(new Thread() {
                // try to disconnect from ZK as gracefully as possible
                public void run() {
                    shutdown();
                }
            });

            worker = Executors.newSingleThreadExecutor();
            worker.submit(() -> {
                try {
                    consumer = new KafkaConsumer(kafkaConfig);

                    topics = new ArrayList<>();
                    for (int i = 0; i < topicConfig.size(); i++) {
                        topics.add(topicConfig.getString(i));
                        logger.info("Subscribing to topic ");
                    }

                    // signal success before we enter read loop
                    startedResult.complete();
                    consume();
                } catch (Exception ex) {
                    String error = "Failed to startup";
                    logger.error(error, ex);

                    // CONSUMER_ERROR_TOPIC = "kafka.consumer.error";
                    bus.send(Const.CONSUMER_ERROR_TOPIC, getErrorString(error, ex.getMessage()));
                    startedResult.fail(ex);
                }
            });

        } catch (Exception ex) {
            logger.error("Message consumer initialization failed with ex: {}", ex);
            startedResult.fail(ex);
        }
    }

    /**
     * Handles looping and consuming
     */
    private void consume() {
        consumer.subscribe(topics);
        while (running.get()) {
            try {
                ConsumerRecords records = consumer.poll(pollIntervalMs);

                // there were no messages
                if (records == null) {
                    continue;
                }

                Iterator<ConsumerRecord<String, String>> iterator = records.iterator();

                // roll through and put each kafka message on the event bus
                while (iterator.hasNext()) {
                    sendMsgToEventBus(iterator.next());
                }

            } catch (Exception ex) {
                String error = "Error consuming messages from kafka";
                logger.error(error, ex);
                bus.send(Const.CONSUMER_ERROR_TOPIC, getErrorString(error, ex.getMessage()));
            }
        }
    }

    /**
     * Send the inbound message to the event bus consumer.
     *
     * @param record the kafka event
     */
    private void sendMsgToEventBus(ConsumerRecord<String, String> record) {
        try {
            bus.send(busAddress, KafkaEvent.createEventForBus(record));
        } catch (Exception ex) {
            String error = String.format("Error sending messages on event bus - record: %s", record.toString());
            logger.error(error, ex);
            bus.send(Const.CONSUMER_ERROR_TOPIC, getErrorString(error, ex.getMessage()));
        }
    }

    @Override
    public void stop() {
        running.compareAndSet(true, false);
    }


    private String getErrorString(String error, String errorMessage) {
        return String.format("%s - error: %s", error, errorMessage);
    }


    private Properties populateKafkaConfig(JsonObject config) {
        Properties consumerConfig = new Properties();
        consumerConfig.put(Const.ZK_CONNECT,
                config.getString(Const.ZK_CONNECT, "localhost:2181"));
        consumerConfig.put(Const.BACKOFF_INCREMENT_MS,
                config.getString(Const.BACKOFF_INCREMENT_MS, "100"));
        consumerConfig.put(Const.AUTO_OFFSET_RESET,
                config.getString(Const.AUTO_OFFSET_RESET, "smallest"));
        consumerConfig.put(Const.BOOTSTRAP_SERVERS,
                getRequiredConfig(Const.BOOTSTRAP_SERVERS));
        consumerConfig.put(Const.KEY_DESERIALIZER_CLASS,
                config.getString(Const.KEY_DESERIALIZER_CLASS, Const.DEFAULT_DESERIALIZER_CLASS));
        consumerConfig.put(Const.VALUE_DESERIALIZER_CLASS,
                config.getString(Const.VALUE_DESERIALIZER_CLASS, Const.DEFAULT_DESERIALIZER_CLASS));
        consumerConfig.put(Const.GROUP_ID,
                getRequiredConfig(Const.GROUP_ID));
        return consumerConfig;
    }

    private String getRequiredConfig(String key) {
        String value = verticleConfig.getString(key, null);

        if (null == value) {
            throw new IllegalArgumentException(
                    String.format("Required config value not found key: %s", key));
        }
        return value;
    }

    /**
     * Handle stopping the consumer.
     */
    private void shutdown() {
        running.compareAndSet(true, false);
        try {
            if (consumer != null) {
                try {
                    consumer.unsubscribe();
                    consumer.close();
                    consumer = null;
                } catch (Exception ex) {
                }
            }

            if (worker != null) {
                worker.shutdown();
                worker = null;
            }
        } catch (Exception ex) {
            logger.error("Failed to close consumer", ex);
        }
    }
}
