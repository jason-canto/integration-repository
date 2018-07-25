package com.jason.integration.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jason.integration.model.Attachment;
import com.jason.integration.repository.AttachmentRepository;

/**
 * Service responsible to be the interface for the email attachment repository
 * @author jcanto
 */

@Service
public class AttachmentService {

	@Autowired
	private AttachmentRepository attachmentRepository;

	public Attachment findAttachmentByIdAndEmailId(final Long emailId, final Long attachmentId) {
		return attachmentRepository.findByEmailIdAndId(emailId, attachmentId);
	}

	public List<Attachment> findAllAttachments() {
		List<Attachment> attachmentList = new ArrayList<Attachment>();
		attachmentRepository.findAll().forEach(attachmentList::add);
		return attachmentList;
	}

	public Page<Attachment> findAllAttachmentsByPage(final Pageable pageable) {
		Page<Attachment> emails = attachmentRepository.findAll(pageable);
		return emails;
	}

	public Optional<Attachment> findAttachmentById(final Long attachmentId) {
		return attachmentRepository.findById(attachmentId);
	}

	public Page<Attachment> findAllAttachmentsByEmailId(final Long emailId, final Pageable pageable) {
		return attachmentRepository.findByEmailId(emailId, pageable);
	}

	public Page<Attachment> findAllAttachmentsByFileName(final String fileName, final Pageable pageable) {
		return attachmentRepository.findByFileName(fileName, pageable);
	}

	public Page<Attachment> findAllAttachmentsByStatus(final Boolean status, final Pageable pageable) {
		return attachmentRepository.findByStatus(status, pageable);
	}

	public Attachment addAttachment(final Attachment attachment) throws Exception {
		return attachmentRepository.save(attachment);
	}

	public void removeAttachment(final Long attachmentId) throws Exception {
		attachmentRepository.deleteById(attachmentId);
	}
}
