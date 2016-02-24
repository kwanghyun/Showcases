package com.cisco.ms.kafka.pub;

import com.cisco.ms.kafka.config.Const;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.apache.kafka.clients.producer.*;
import org.apache.log4j.Logger;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by kwjang on 2/23/16.
 */
public class MessageProducer extends AbstractVerticle {

    final static private Logger logger = Logger.getLogger(MessageProducer.class);
    public static String EVENTBUS_DEFAULT_ADDRESS = "kafka.message.publisher";

    private KafkaProducer producer;

    private String defaultTopic;
    private String busAddress;

    private JsonObject producerConfig;
    private ExecutorService sender;

    @Override
    public void start(final Future<Void> startedResult) throws Exception {
        try {
            producerConfig = config();
            Properties properties = populateKafkaConfig();

            busAddress = producerConfig.getString(Const.EVENTBUS_ADDRESS, EVENTBUS_DEFAULT_ADDRESS);
            defaultTopic = producerConfig.getString(Const.DEFAULT_TOPIC);
            sender = Executors.newSingleThreadExecutor();

            producer = new KafkaProducer(properties);

            vertx.eventBus().consumer(busAddress,
                    (Message<JsonObject> message) -> sendMessage(message));

            Runtime.getRuntime().addShutdownHook(new Thread() {
                // try to disconnect from ZK as gracefully as possible
                public void run() {
                    shutdown();
                }
            });

            startedResult.complete();

        } catch (Exception ex) {
            logger.error("Message producer initialization failed with ex: {}", ex);
            startedResult.fail(ex);
        }
    }

    /*
     Send a message on a pre-configured defaultTopic.
     @param message the message to send
    */
    public void sendMessage(Message<JsonObject> message) {
        JsonObject payload = message.body();
        ProducerRecord<String, String> record;

        if (!payload.containsKey(KafkaPublisher.TYPE_FIELD)) {
            logger.error("Invalid message sent missing => " + message);
            return;
        }

        KafkaPublisher.MessageType type =
                KafkaPublisher.MessageType.fromInt(payload.getInteger(KafkaPublisher.TYPE_FIELD));

        String value = payload.getString(Const.VALUE_FIELD);
        switch (type) {
            case SIMPLE:
                record = new ProducerRecord(defaultTopic, value);
                break;
            case CUSTOM_TOPIC:
                record = new ProducerRecord(payload.getString(Const.TOPIC_FIELD), value);
                break;
            case CUSTOM_KEY:
                record = new ProducerRecord(payload.getString(Const.TOPIC_FIELD),
                        payload.getString(Const.KEY_FIELD),
                        value);
                break;
            case CUSTOM_PARTITION:
                record = new ProducerRecord(payload.getString(Const.TOPIC_FIELD),
                        payload.getInteger(Const.PARTITION_FIELD),
                        payload.getString(Const.KEY_FIELD),
                        value);
                break;
            default:
                String error = String.format("Invalid type submitted: {} message being thrown away: %s",
                        type.toString(), value);
                logger.error(error);
                /* Signal to the sender that processing of this message failed. */
                message.fail(-1, error);
                return;
        }

        sender.submit(() ->
                producer.send(record, (metadata, exception) -> {
                    if (exception != null) {
                        /* Puts the handler on the event queue for the current context so it will be
                        run asynchronously ASAP after all preceeding events have been handled. */
                        vertx.runOnContext(aVoid -> message.fail(-1, exception.getMessage()));
                        exception.printStackTrace();
                        logger.error("Failed to send message to kafka ex: ", exception);
                    } else {
                        vertx.runOnContext(aVoid -> message.reply(new JsonObject().put("status","success")));
                    }
                })
        );
    }

    private Properties populateKafkaConfig() {
        Properties properties = new Properties();

        properties.put(Const.BOOTSTRAP_SERVERS, getRequiredConfig(Const.BOOTSTRAP_SERVERS));

        // default serializer to the String one
        String defaultSerializer = producerConfig.getString(Const.SERIALIZER_CLASS,
                Const.DEFAULT_SERIALIZER_CLASS);

        properties.put(Const.SERIALIZER_CLASS, defaultSerializer);
        properties.put(Const.KEY_SERIALIZER_CLASS,
                producerConfig.getString(Const.KEY_SERIALIZER_CLASS, defaultSerializer));
        properties.put(Const.VALUE_SERIALIZER_CLASS,
                producerConfig.getString(Const.VALUE_SERIALIZER_CLASS, defaultSerializer));
        properties.put(Const.PRODUCER_TYPE, producerConfig.getString(Const.PRODUCER_TYPE, "async"));
        properties.put(Const.MAX_BLOCK_MS, producerConfig.getLong(Const.MAX_BLOCK_MS, new Long(60000)));
        return properties;
    }

    private String getRequiredConfig(String key) {
        String value = producerConfig.getString(key, null);

        if (null == value) {
            throw new IllegalArgumentException(String.format("Required config value not found key: %s", key));
        }
        return value;
    }

    private void shutdown() {
        try {
            if (producer != null) {
                producer.close();
                producer = null;
            }

            if (sender != null) {
                sender.shutdown();
                sender = null;
            }
        } catch (Exception ex) {
            logger.error("Failed to close producer : => " + ex.getMessage());
        }
    }
}

