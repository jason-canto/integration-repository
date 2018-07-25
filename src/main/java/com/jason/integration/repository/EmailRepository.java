package com.jason.integration.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jason.integration.model.Email;

/**
 * CrudRepository responsible to manage crud operations for the emails received
 * @author jcanto
 */

public interface EmailRepository extends PagingAndSortingRepository<Email, Long>{

	public Page<Email> findAll(Pageable pageable);

	public Page<Email> findBySender(final String sender, final Pageable pageable);

	public Page<Email> findByReceiver(final String receiver, final Pageable pageable);

	public Page<Email> findByDateSentBetween(final Date endDate, final Date startDate, final Pageable pageable);

	public Page<Email> findByDateProcessedBetween(final Date endDate, final Date startDate, final Pageable pageable);

}
