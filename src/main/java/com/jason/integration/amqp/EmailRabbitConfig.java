package com.jason.integration.amqp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailRabbitConfig {

	@Value("${amqp.email.service.queue}")
	private String queueName;

	@Value("${amqp.email.service.dlq}")
	private String queueDLQName;

	@Value("${amqp.email.service.topic}")
	private String topicName;

	@Value("${amqp.email.service.dlq.exchange}")
	private String exchangeDLQ;

	@Value("${amqp.email.service.dlq.rk}")
	private String routingKeyDLQ;

	@Bean
	TopicExchange exchange() {
		return new TopicExchange(topicName);
	}

	@Bean
	Queue deadLetterQueue() {
		return new Queue(queueDLQName, true);
	}

	@Bean
	Queue queue() {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-dead-letter-exchange", exchangeDLQ);
		args.put("x-dead-letter-routing-key", routingKeyDLQ);
		return new Queue(queueName, true, false, false, args);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}

	@Bean
	Binding bindingDeadLetter(Queue deadLetterQueue, TopicExchange exchange) {
		return BindingBuilder.bind(deadLetterQueue).to(exchange).with(routingKeyDLQ);
	}

	@Bean
	public EmailQueueConsumer consumer() {
		return new EmailQueueConsumer();
	}

	@Bean
	public EmailQueueProducer producer() {
		return new EmailQueueProducer();
	}

}
