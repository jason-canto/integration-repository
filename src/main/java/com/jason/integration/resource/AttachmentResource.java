package com.jason.integration.resource;

import org.springframework.hateoas.ResourceSupport;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class AttachmentResource extends ResourceSupport {

	private Long idAttachment;

	private Long emailId;

	private String fileName;

	private Boolean status;

	private String observation;
}
