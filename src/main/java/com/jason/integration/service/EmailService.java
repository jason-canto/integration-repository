package com.jason.integration.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jason.integration.model.Email;
import com.jason.integration.repository.EmailRepository;

/**
 * Service responsible to be the interface for the email repository
 * @author jcanto
 */

@Service
public class EmailService {

	@Autowired
	private EmailRepository emailRepository;

	public List<Email> getAllEmails() {
		List<Email> emailList = new ArrayList<Email>();
		emailRepository.findAll().forEach(emailList::add);
		return emailList;
	}

	public Page<Email> getAllEmailsByPage(final Pageable pageable) {
		Page<Email> emails = emailRepository.findAll(pageable);
		return emails;
	}

	public Optional<Email> findEmailById(final Long emailId) {
		return emailRepository.findById(emailId);
	}

	public Page<Email> findAllEmailsBySender(final String sender, final Pageable pageable) {
		return emailRepository.findBySender(sender, pageable);
	}

	public Page<Email> findAllEmailsByReceiver(final String receiver, final Pageable pageable) {
		return emailRepository.findByReceiver(receiver, pageable);
	}

	public Page<Email> findAllEmailsByDateSent(final Date start, final Date end, final Pageable pageable) {
		return emailRepository.findByDateSentBetween(start, end, pageable);
	}

	public Page<Email> findAllEmailsByDateProcessed(final Date start, final Date end, final Pageable pageable) {
		return emailRepository.findByDateProcessedBetween(start, end, pageable);
	}

	public Email addEmail(final Email email) throws Exception {
		return emailRepository.save(email);
	}

	public void removeEmail(final Long email) throws Exception {
		emailRepository.deleteById(email);
	}
}
