package com.perseus.userservice.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;

/**
 * DTO class for Phone Number used with client interactions
 */
@ApiModel(description="All details about the PhoneNumber.")
public class PhoneNumberDto {

	private int id;
	private String number;

	public PhoneNumberDto() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@NotEmpty(message = "Phone number cannot be empty")
	@Size(min = 10,  max = 20, message = "Phone number should be between 10 and 20 digits")
	@Pattern(regexp = "^[+]{0,1}[0-9]{10,20}$", message="Phone number contains invalid characters")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

}
