package com.perseus.userservice.utils;

import java.util.Arrays;

import com.perseus.userservice.dto.ContactDataDto;
import com.perseus.userservice.dto.EmailDto;
import com.perseus.userservice.dto.PhoneNumberDto;
import com.perseus.userservice.dto.UserDto;
import com.perseus.userservice.entities.Email;
import com.perseus.userservice.entities.PhoneNumber;
import com.perseus.userservice.entities.User;

public final class FormDataUtil {
	
	public static User formUserResponse() {
		User user = new User();
		user.setId(1);
		user.setFirstName("Santosh");
		user.setLastName("Pandey");
		
		Email email = new Email();
		email.setId(1);
		email.setMail("sp878@gmail.com");
		
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setId(1);
		phoneNumber.setNumber("9878285763");
		
		user.setEmails(Arrays.asList(email));
		user.setPhoneNumbers(Arrays.asList(phoneNumber));
		
		return user;
	}
	
	public static UserDto formUserRequest() {
		UserDto user = new UserDto();
		user.setFirstName("Santosh");
		user.setLastName("Pandey");
		
		EmailDto email = new EmailDto();
		email.setMail("sp878@gmail.com");
		
		PhoneNumberDto phoneNumber = new PhoneNumberDto();
		phoneNumber.setNumber("9878285763");
		
		user.setEmails(Arrays.asList(email));
		user.setPhoneNumbers(Arrays.asList(phoneNumber));
		
		return user;
	}
	
	public static ContactDataDto formContactData() {
		ContactDataDto contactData = new ContactDataDto();
		
		Email email = new Email();
		email.setMail("sp882@gmail.com");
		
		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setNumber("9878285756");
		
		contactData.setEmails(Arrays.asList(email));
		contactData.setPhoneNumbers(Arrays.asList(phoneNumber));
		
		return contactData;
	}

}
