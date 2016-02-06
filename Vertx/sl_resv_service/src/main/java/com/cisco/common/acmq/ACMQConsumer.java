package com.cisco.common.acmq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class ACMQConsumer extends AbstractVerticle {

	private static final Logger logger = LoggerFactory.getLogger(ACMQConsumer.class);

	ActiveMQConnectionFactory connectionFactory;
	Connection connection;
	Session session;
	MessageConsumer consumer;

	private void init() {

		try {
			// Create a ConnectionFactory
			connectionFactory = new ActiveMQConnectionFactory("vm://10.106.8.162");

			// Create a Connection
			connection = connectionFactory.createConnection();
			connection.start();

			// Create a Session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		} catch (Exception e) {
			System.out.println("Fail to create connection with ACMQ: " + e);
			e.printStackTrace();
		}
	}

	@Override
	public void start() throws Exception {
		init();

		// Create the destination (Topic or Queue)
		Destination destination = session.createQueue("TEST.FOO");

		// Create a MessageConsumer from the Session to the Topic or Queue
		consumer = session.createConsumer(destination);

		consumer.setMessageListener(m -> handleMessage(m));
//		Message message = consumer.receiveNoWait();


    }

	private void handleMessage(Message msg) {
		try{
			if (msg instanceof TextMessage) {
				TextMessage textMessage = (TextMessage) msg;
				String text = textMessage.getText();
				logger.info("[ACMQ::CONS-1] I have received a message: " + text);
			} else {
				logger.info("[ACMQ::CONS-2] I have received a message: " + msg);
			}			
		}catch(JMSException e){
			logger.info("Fail to interpret message");
			e.printStackTrace();
		}
	}

	@Override
	public void stop() throws Exception {
		// Clean up
		consumer.close();
		session.close();
		connection.close();
	}
}
