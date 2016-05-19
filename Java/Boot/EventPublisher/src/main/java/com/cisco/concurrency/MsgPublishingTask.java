package com.cisco.concurrency;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.cisco.jms.Receiver;
import com.cisco.utils.RandomEventGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MsgPublishingTask implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(MsgPublishingTask.class);

	@Value("${rabbitmq.publisher.host}")
	private static final String rabbitMqHost = "10.106.8.80";

	@Value("${rabbitmq.publisher.qname}")
	private final static String queueName = "spring-boot";

	@Value("${rabbitmq.publisher.topicname}")
	private final static String topicName = "spring-boot-exchange";

	@Value("${rabbitmq.publisher.username}")
	private final static String username = "admin";

	@Value("${rabbitmq.publisher.password}")
	private final static String password = "admin";

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	RandomEventGenerator eventGenerator;
	
//	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicName);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	public ConnectionFactory rabbitConnectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMqHost);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		return connectionFactory;
	}

	@Bean
	Receiver receiver() {
		return new Receiver();
	}

	@Bean
	MessageListenerAdapter listenerAdapter(Receiver receiver) {
		return new MessageListenerAdapter(receiver, "receiveMessage");
	}

	@Override
	public void run() {
		// String now = dateFormat.format(new Date());
		// LOG.info("Sending message... " + now);

		Random r = new Random();
		int metaIdx = r.nextInt(eventGenerator.getMetaInfos().size());
		ObjectMapper mapper = new ObjectMapper();

		try {
			// String json =
			// mapper.writeValueAsString(eventGenerator.generateRandomData(metaIdx));
			String json = mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(eventGenerator.generateRandomData(metaIdx));
			LOG.info("Generated DATA :: \n {} ", json);

			// rabbitTemplate.convertAndSend(queueName, json);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
