package com.cisco.ms.kafka.sub;

import com.cisco.ms.kafka.config.Const;
import io.vertx.core.json.JsonObject;
import org.apache.kafka.clients.consumer.ConsumerRecord;

/**
 * Created by kwjang on 2/24/16.
 * <p>
 * Represents a Kafka message event from the event bus.
 */
public class KafkaEvent {
    public final String topic;
    public final String key;
    public final String value;
    public final int partition;

    public KafkaEvent(JsonObject event) {
        topic = event.getString(Const.TOPIC_FIELD);
        key = event.getString(Const.KEY_FIELD);
        value = event.getString(Const.VALUE_FIELD);
        partition = event.getInteger(Const.PARTITION_FIELD);
    }

    @Override
    public String toString() {
        return "KafkaEvent{" +
                "topic='" + topic + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", partition=" + partition +
                '}';
    }

    /**
     * Convert a Kafka ConsumerRecord into an event bus event.
     *
     * @param record the Kafka record
     * @return the record to send over the event bus
     */
    public static JsonObject createEventForBus(ConsumerRecord<String, String> record) {
        return new JsonObject()
                .put(Const.TOPIC_FIELD, record.topic())
                .put(Const.KEY_FIELD, record.key())
                .put(Const.VALUE_FIELD, record.value())
                .put(Const.PARTITION_FIELD, record.partition());
    }
}
