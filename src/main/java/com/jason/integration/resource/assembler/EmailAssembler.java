package com.jason.integration.resource.assembler;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.jason.integration.controller.EmailController;
import com.jason.integration.model.Email;
import com.jason.integration.resource.EmailResource;

@Component
public class EmailAssembler extends ResourceAssemblerSupport<Email, EmailResource> {

	public EmailAssembler() {
		super(EmailController.class, EmailResource.class);
	}

	@Override
	public EmailResource toResource(final Email email) {
		EmailResource resource = null;
		if (email != null) {
			resource = EmailResource.builder()
					.emailId(email.getId())
					.sender(email.getSender())
					.receiver(email.getReceiver())
					.dateSent(email.getDateSent())
					.dateProcessed(email.getDateProcessed())
					.totalAttachments(email.getTotalAttachments())
					.contentType(email.getContentType())
					.build();

			resource.add(ControllerLinkBuilder.linkTo(EmailController.class)
					.slash("emails")
					.slash(email.getId())
					.withSelfRel());
		}
		return resource;
	}

}
