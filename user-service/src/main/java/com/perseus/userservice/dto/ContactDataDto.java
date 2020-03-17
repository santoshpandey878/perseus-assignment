package com.perseus.userservice.dto;

import java.util.List;

import com.perseus.userservice.core.annotations.ValidateContactData;
import com.perseus.userservice.entities.Email;
import com.perseus.userservice.entities.PhoneNumber;

import io.swagger.annotations.ApiModel;

/**
 * DTO class for ContactData used with client interactions
 */
@ApiModel(description="Model for contact data")
@ValidateContactData
public class ContactDataDto {

	private List<Email> emails;
	private List<PhoneNumber> phoneNumbers;

	public ContactDataDto() {}

	public List<Email> getEmails() {
		return emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

}