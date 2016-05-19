package com.cisco.jms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.cisco.concurrency.KillerScheduledExecutor;
import com.cisco.concurrency.MsgPublishingTask;
import com.cisco.utils.RandomEventGenerator;

@SpringBootApplication
// @EnableScheduling
@ComponentScan(basePackages = { "com.cisco.concurrency", "com.cisco.utils" })
public class AmqpPublisherApplication/* implements CommandLineRunner */ {

	private static final Logger LOG = LoggerFactory.getLogger(AmqpPublisherApplication.class);

	@Autowired
	AnnotationConfigApplicationContext context;

	@Autowired
	MsgPublishingTask task;
	
	KillerScheduledExecutor executorService;
	ConcurrentMap<String, ScheduledFuture<?>> taskController = new ConcurrentHashMap<>();

	@Value("${rabbitmq.publisher.maxmsg}")
	private int maxMsgCount;

	@Value("${rabbitmq.publisher.intialdelay}")
	private int initialDelay;

	@Value("${rabbitmq.publisher.interval}")
	private int msgInterval;

	@Value("${rabbitmq.publisher.maxthread}")
	private int maxThreads;

	@PostConstruct
	public void init() {
		LOG.info("init......");
		executorService = new KillerScheduledExecutor(maxThreads, maxMsgCount);
		executorService.setApp(this);
		for (int i = 0; i < maxThreads; i++) {
			startTask(i);
		}
	}

	public void startTask(int idx) {
		LOG.info("Start task {}", idx);
		LOG.info("@ maxMsgCount : {}", maxMsgCount);
		LOG.info("@ initialDelay : {}", initialDelay);
		ScheduledFuture<?> scheduledTask = executorService.scheduleAtFixedRate(task, initialDelay, msgInterval,
				TimeUnit.MILLISECONDS);
		taskController.putIfAbsent("task" + idx, scheduledTask);
	}

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(AmqpPublisherApplication.class, args);
	}

	public void shutdown() {
		if (executorService != null) {
			executorService.shutdownNow();
			try {
				if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
					LOG.info("Pool scheduler termination timeout expired!!!!!!!!!!");
				}
			} catch (final InterruptedException e) {
				// Ignore
			}
		}
		LOG.info("Shutting down Spring boot......");
		context.close();
	}

	public void stopTasks() {
		LOG.info("Signal Stopping tasks");
		Iterator<ScheduledFuture<?>> it = taskController.values().iterator();
		while (it.hasNext()) {
			ScheduledFuture<?> future = it.next();
			future.cancel(true);
		}
		// Uncomment me : For Dev only
		shutdown();
	}
}
