package com.jason.integration.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;
import com.jason.integration.model.Email;
import com.jason.integration.service.EmailService;


/**
 * @author jcanto
 *
 */

@Component
public class EmailQueueConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(EmailQueueConsumer.class);

	private static final Gson GSON = new Gson();

	@Autowired
	private EmailService emailService;

	@RabbitListener(queues = {"${amqp.email.service.queue}"})
	public void receive(@Payload byte [] payload) {

		try {
			LOG.info("Dto received from queue: " + payload);
			Email email = GSON.fromJson(new String(payload), Email.class);
			email.getAttachments().forEach(a -> a.setEmail(email));
			emailService.addEmail(email);
		} catch (Throwable t) {
			LOG.error("Error consuming payload [" + new String(payload) + "].", t);
		}
	}
}
