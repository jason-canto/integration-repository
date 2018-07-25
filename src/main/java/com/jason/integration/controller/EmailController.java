package com.jason.integration.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jason.integration.model.Attachment;
import com.jason.integration.model.Email;
import com.jason.integration.resource.AttachmentResource;
import com.jason.integration.resource.EmailResource;
import com.jason.integration.resource.assembler.AttachmentAssembler;
import com.jason.integration.resource.assembler.EmailAssembler;
import com.jason.integration.service.AttachmentService;
import com.jason.integration.service.EmailService;

/**
 * Restful Service Interface responsible to manage Http requests for the mail repository
 * @author jcanto
 */

@RestController
@RequestMapping("/integration/v1")
public class EmailController {

	@Autowired
	private EmailService emailService;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private EmailAssembler emailAssembler;

	@Autowired
	private AttachmentAssembler attachmentAssembler;

	private static final Logger LOG = LoggerFactory.getLogger(EmailController.class);

	@GetMapping(value = "/emails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<EmailResource>> getAllEmailsByPage(@PageableDefault Pageable pageable,
			PagedResourcesAssembler<Email> assembler) {
		Page<Email> pages = emailService.getAllEmailsByPage(pageable);
		EmailAssembler resourceAssembler = new EmailAssembler();
		PagedResources<EmailResource> pagedResources = assembler.toResource(pages, resourceAssembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

	@GetMapping(value = "/emails/{emailId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EmailResource> getEmailById(@PathVariable Long emailId) {
		Email email = emailService.findEmailById(emailId).orElse(null);
		EmailResource emailResource = emailAssembler.toResource(email);
		return new ResponseEntity<EmailResource>(emailResource, HttpStatus.OK);
	}

	@GetMapping(value = "/emails/sender/{sender}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<EmailResource>> getEmailBySender(@PageableDefault Pageable pageable,
			PagedResourcesAssembler<Email> assembler, @PathVariable String sender) {
		Page<Email> pages = emailService.findAllEmailsBySender(sender, pageable);
		EmailAssembler resourceAssembler = new EmailAssembler();
		PagedResources<EmailResource> pagedResources = assembler.toResource(pages, resourceAssembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

	@GetMapping(value = "/emails/sender/{receiver}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<EmailResource>> getEmailByReceiver(@PageableDefault Pageable pageable,
			PagedResourcesAssembler<Email> assembler, @PathVariable String receiver) {
		Page<Email> pages = emailService.findAllEmailsByReceiver(receiver, pageable);
		EmailAssembler resourceAssembler = new EmailAssembler();
		PagedResources<EmailResource> pagedResources = assembler.toResource(pages, resourceAssembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

	@PostMapping("/emails")
	public ResponseEntity<Void> createEmail(@Valid @RequestBody Email email) {
		try {
			email.getAttachments().forEach(a -> a.setEmail(email));
			emailService.addEmail(email);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception ex) {
			LOG.error("Error adding new email " + email, ex);
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/emails/{emailId}")
	public ResponseEntity<Void> deleteEmail(@PathVariable Long emailId) {
		try {
			emailService.removeEmail(emailId);
			return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		} catch (Exception ex) {
			LOG.error("Error deleting emailId " + emailId, ex);
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(value = "/attachments", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<AttachmentResource>> getAllAttachments(@PageableDefault Pageable pageable,
			PagedResourcesAssembler<Attachment> assembler) {
		Page<Attachment> pages = attachmentService.findAllAttachmentsByPage(pageable);
		AttachmentAssembler resourceAssembler = new AttachmentAssembler();
		PagedResources<AttachmentResource> pagedResources = assembler.toResource(pages, resourceAssembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

	@GetMapping(value = "/attachments/{attachmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AttachmentResource> getAttachmentById(@PathVariable Long attachmentId) {
		Attachment attachment = attachmentService.findAttachmentById(attachmentId).orElse(null);
		AttachmentResource emailResource = attachmentAssembler.toResource(attachment);
		return new ResponseEntity<AttachmentResource>(emailResource, HttpStatus.OK);
	}

	@GetMapping(value = "emails/{emailId}/attachments/{attachmentId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AttachmentResource> getAttachmentByFullPath(@PathVariable Long emailId, @PathVariable Long attachmentId) {
		Attachment attachment = attachmentService.findAttachmentByIdAndEmailId(emailId, attachmentId);
		AttachmentResource emailResource = attachmentAssembler.toResource(attachment);
		return new ResponseEntity<AttachmentResource>(emailResource, HttpStatus.OK);
	}

	@GetMapping(value = "emails/{emailId}/attachments", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PagedResources<AttachmentResource>> getAllAttachmentsByEmail(@PathVariable Long emailId, 
			@PageableDefault Pageable pageable, PagedResourcesAssembler<Attachment> assembler) {
		Page<Attachment> pages = attachmentService.findAllAttachmentsByEmailId(emailId, pageable);
		AttachmentAssembler resourceAssembler = new AttachmentAssembler();
		PagedResources<AttachmentResource> pagedResources = assembler.toResource(pages, resourceAssembler);
		return new ResponseEntity<>(pagedResources, HttpStatus.OK);
	}

	@PostMapping("/emails/{emailId}/attachments")
	public ResponseEntity<Void> createAttachment(@PathVariable Long emailId, @Valid @RequestBody Attachment attachment) {
		try {
			Optional<Email> email = emailService.findEmailById(emailId);
			if (email.isPresent()) {
				attachment.setEmail(email.get());
			} else {
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
			attachmentService.addAttachment(attachment);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception ex) {
			LOG.error("Error adding new attachment " + attachment, ex);
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/emails/{emailId}/attachments/{attachmentId}")
	public ResponseEntity<Void> updateAttachment(@PathVariable Long emailId, @PathVariable Long attachmentId,
			@Valid @RequestBody Attachment attachment) {
		try {
			Optional<Email> email = emailService.findEmailById(emailId);
			if (email.isPresent() && attachmentId != null) {
				attachment.setEmail(email.get());
				attachment.setId(attachmentId);
			} else {
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
			attachmentService.addAttachment(attachment);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception ex) {
			LOG.error("Error adding new attachment " + attachment, ex);
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/emails/{emailId}/attachments/{attachmentId}")
	public ResponseEntity<Void> deleteAttachmentFullPath(@PathVariable Long attachmentId) {
		try {
			attachmentService.removeAttachment(attachmentId);
			return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		} catch (Exception ex) {
			LOG.error("Error deleting attachment " + attachmentId, ex);
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/attachments/{attachmentId}")
	public ResponseEntity<Void> deleteAttachment(@PathVariable Long attachmentId) {
		try {
			attachmentService.removeAttachment(attachmentId);
			return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
		} catch (Exception ex) {
			LOG.error("Error deleting attachment " + attachmentId, ex);
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

}
