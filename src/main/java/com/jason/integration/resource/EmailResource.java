package com.jason.integration.resource;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
@Relation(collectionRelation = "emails")
public class EmailResource extends ResourceSupport {

	private Long emailId;

	private String sender;

	private String receiver;

	private Integer totalAttachments;

	private Date dateSent;

	private Date dateArrived;

	private Date dateProcessed;

	private String resourceCode;

	private String contentType;
}
