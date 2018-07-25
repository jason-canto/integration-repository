package com.jason.integration.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity responsible to map the email received
 * @author jcanto
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EMAIL_INBOX")
public class Email implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_email")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "email_origin")
	private String sender;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "email", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Attachment> attachments;

	@NotNull
	@Column(name = "email_destination")
	private String receiver;

	@NotNull
	@Column(name = "total_attachments")
	private Integer totalAttachments;

	@Column(name = "sent_date")
	private Date dateSent;

	@NotNull
	@Column(name = "consumed_date")
	private Date dateProcessed;


	@Column(name = "content_type")
	private String contentType;
}
