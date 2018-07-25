package com.jason.integration.resource.assembler;

import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.jason.integration.controller.EmailController;
import com.jason.integration.model.Attachment;
import com.jason.integration.resource.AttachmentResource;

@Component
public class AttachmentAssembler extends ResourceAssemblerSupport<Attachment, AttachmentResource> {

	public AttachmentAssembler() {
		super(EmailController.class, AttachmentResource.class);
	}

	@Override
	public AttachmentResource toResource(final Attachment attachment) {
		AttachmentResource resource = null;
		if (attachment != null) {
			resource = AttachmentResource.builder()
					.idAttachment(attachment.getId())
					.emailId(attachment.getEmail().getId())
					.fileName(attachment.getFileName())
					.observation(attachment.getObservation())
					.status(attachment.getStatus())
					.build();

			resource.add(ControllerLinkBuilder.linkTo(EmailController.class)
					.slash("emails")
					.slash(attachment.getEmail().getId())
					.withRel("email"));
			resource.add(ControllerLinkBuilder.linkTo(EmailController.class)
					.slash("emails")
					.slash(attachment.getEmail().getId())
					.slash("attachments")
					.slash(attachment.getId())
					.withSelfRel());
		}
		return resource;
	}

}
