package com.cisco.common.acmq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;


public class ACMQProducer extends AbstractVerticle {
	
	private static final Logger logger = Logger.getLogger(ACMQProducer.class);
	private static final String ACMQ_EVENT_CHANNEL= "TEST.FOO";
	EventBus eb;
	
	ActiveMQConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	MessageProducer producer;
	MessageConsumer<JsonObject> consumer;

	@Override
	public void start() throws Exception {

		// Create a ConnectionFactory
		connectionFactory = new ActiveMQConnectionFactory("vm://10.106.8.162");

		// Create a Connection
		connection = connectionFactory.createConnection();
		connection.start();

		// Create a Session
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		loadEventBus();
	}

	private void loadEventBus() throws Exception{

		eb = vertx.eventBus();

		consumer = eb.consumer("channel.emit.events.acmq");
		consumer.handler(message -> {
			try{
				logger.info(message.body());
				
				JsonObject jsonObj = message.body();
				logger.info("[ACMQ::PROD] I have received a message: " + jsonObj.encodePrettily());
				sendMessage(ACMQ_EVENT_CHANNEL, jsonObj.encode());
			}catch(ClassCastException e){
				message.reply("Fail to cast string to Json");
				e.printStackTrace();
			}catch(Exception e){
				message.reply("Faile :: Exception");
				e.printStackTrace();	
			}			
			message.reply("Message Received");
		});

	}
	
	private void sendMessage(String dest, String msg) throws Exception {
		// Create the destination (Topic or Queue)
		Destination destination = session.createQueue(dest);

		// Create a MessageProducer from the Session to the Topic or Queue
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		TextMessage message = session.createTextMessage(msg);

		// Tell the producer to send the message
		producer.send(message);
		logger.info("Sent message: " + message.hashCode() + " : " + Thread.currentThread().getName());
	}

	@Override
	public void stop() throws Exception {
		// Clean up
		session.close();
		connection.close();
	}

}
