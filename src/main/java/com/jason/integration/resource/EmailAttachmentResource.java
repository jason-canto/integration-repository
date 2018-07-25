package com.jason.integration.resource;

import java.util.Date;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.jason.integration.model.Attachment;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class EmailAttachmentResource extends ResourceSupport {

	private Long emailId;

	private String sender;

	private List<Attachment> attachments;

	private String receiver;

	private Integer totalAttachments;

	private Date dateSent;

	private Date dateArrived;

	private Date dateProcessed;

	private String resourceCode;

	private String contentType;
}
