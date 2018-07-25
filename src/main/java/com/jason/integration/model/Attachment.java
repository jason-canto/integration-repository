package com.jason.integration.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "EMAIL_ATTACHMENT")
public class Attachment implements Serializable {

	private static final long serialVersionUID = -3774599316972195359L;

	@Id
	@Column(name = "id_attachment")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name="id_email")
	private Email email;

	@NotNull
	@Column(name = "nm_file")
	private String fileName;

	@NotNull
	@Column(name = "bl_passed")
	private Boolean status;

	@Column(name = "ds_observation")
	private String observation;

}
