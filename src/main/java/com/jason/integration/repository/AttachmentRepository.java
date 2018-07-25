package com.jason.integration.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.jason.integration.model.Attachment;

/**
 * CrudRepository responsible to manage crud operations for the email attachments
 * @author jcanto
 */

public interface AttachmentRepository extends CrudRepository<Attachment, Long>{

	public Attachment findByEmailIdAndId(final Long emailId, final Long attachmentId);

	public Page<Attachment> findAll(Pageable pageable);

	public Page<Attachment> findByEmailId(final Long emailId, final Pageable pageable);

	public Page<Attachment> findByFileName(final String fileName, final Pageable pageable);

	public Page<Attachment> findByStatus(final Boolean status, final Pageable pageable);

}
