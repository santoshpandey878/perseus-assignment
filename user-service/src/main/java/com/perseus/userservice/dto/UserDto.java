package com.perseus.userservice.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;

/**
 * DTO class for User used with client interactions
 */
@ApiModel(description="All details about the user.")
public class UserDto {

	private int id;
	private String firstName;
	private String lastName;
	private List<EmailDto> emails;
	private List<PhoneNumberDto> phoneNumbers;

	public UserDto() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@NotBlank(message="First Name cannot be empty")
	@Size(min=2, max=100, message="First Name must be greater than or equal to 2 characters and less than or equal to 100 characters")
	@Pattern(regexp = "^[A-Z a-z]{2,100}$", message="First Name must contain only characters")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@NotBlank(message="Last Name cannot be empty")
	@Size(min=2, max=100, message="Last Name must be greater than or equal to 2 characters and less than or equal to 100 characters")
	@Pattern(regexp = "^[A-Z a-z]{2,100}$", message="Last Name must contain only characters")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@NotEmpty(message = "Email details cannot be empty")
	@Valid
	public List<EmailDto> getEmails() {
		return emails;
	}

	public void setEmails(List<EmailDto> emails) {
		this.emails = emails;
	}

	@NotEmpty(message = "Phone Number details cannot be empty")
	@Valid
	public List<PhoneNumberDto> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<PhoneNumberDto> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

}