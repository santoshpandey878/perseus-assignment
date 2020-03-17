package com.perseus.userservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;

/**
 * DTO class for Email used with client interactions
 */
@ApiModel(description="All details about the Email.")
public class EmailDto {

	private int id;
	private String mail;

	public EmailDto() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@NotEmpty(message = "Email cannot be empty")
	@Size(max = 100, message = "Email cannot be greater than 100 characters")
	@Email
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
