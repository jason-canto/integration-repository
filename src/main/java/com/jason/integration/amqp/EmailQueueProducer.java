package com.jason.integration.amqp;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class EmailQueueProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${amqp.email.service.queue}")
	private String queueName;

	public void send(String message) {
		rabbitTemplate.convertAndSend(queueName, message);
	}
}
