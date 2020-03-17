package com.perseus.userservice.services;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.perseus.userservice.core.exceptions.ResourceNotFoundException;
import com.perseus.userservice.core.utils.MessageUtil;
import com.perseus.userservice.dao.UserRepository;
import com.perseus.userservice.dto.ContactDataDto;
import com.perseus.userservice.entities.Email;
import com.perseus.userservice.entities.PhoneNumber;
import com.perseus.userservice.entities.User;
import com.perseus.userservice.utils.FormDataUtil;

@RunWith(SpringRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private MessageUtil message;

	@InjectMocks
	private UserServiceImpl userService;


	@Test(expected = ResourceNotFoundException.class)
	public void suppliedInValidUserId_whenGetUserById_thenThrowResourceNotFoundException() {

		Mockito.when(userRepository.findById(Mockito.anyInt()))
		.thenThrow(ResourceNotFoundException.class);

		userService.getUserById(1);
	}

	@Test
	public void givenUser_whenGetUserById_thenReturnObject() throws Exception {

		User user = FormDataUtil.formUserResponse();

		Mockito.when(userRepository.findById(Mockito.anyInt()))
		.thenReturn(Optional.of(user));

		// get user by id
		User response = userService.getUserById(1);

		assertEquals(user.getId(), response.getId());
		assertEquals(user.getFirstName(), response.getFirstName());
		assertEquals(user.getLastName(), response.getLastName());
	}

	@Test
	public void givenUser_whenGetUserByName_thenReturnObject() throws Exception {

		User user = FormDataUtil.formUserResponse();

		Mockito.when(userRepository.findAllByFirstNameOrLastName(Mockito.anyString(), Mockito.anyString()))
		.thenReturn(Arrays.asList(user));

		// get user by name
		List<User> response = userService.getUserByName("Santosh");

		assertEquals(1, response.size());
	}

	@Test
	public void givenUser_whenCreateUser_thenReturnObject() throws Exception {

		User user = new User();
		user.setFirstName("Santosh");
		user.setLastName("Pandey");

		Email email = new Email();
		email.setMail("sp878@gmail.com");

		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setNumber("9878285763");

		user.setEmails(Arrays.asList(email));
		user.setPhoneNumbers(Arrays.asList(phoneNumber));

		User userResponse = FormDataUtil.formUserResponse();

		Mockito.when(userRepository.save(Mockito.any(User.class)))
		.thenReturn(userResponse);

		// create user
		User response = userService.createUser(user);

		assertEquals(1, response.getId());
		assertEquals(user.getFirstName(), response.getFirstName());
		assertEquals(user.getLastName(), response.getLastName());
	}

	@Test
	public void whenDeleteUser_thenDoNothing() throws Exception {

		User user = FormDataUtil.formUserResponse();

		Mockito.when(userRepository.findById(Mockito.anyInt()))
		.thenReturn(Optional.of(user));

		Mockito.doNothing().when(userRepository).delete(Mockito.any(User.class));

		userService.deleteUser(1);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void givenContactDataWithInvalidId_whenAddAdditionalContactData_thenThrowResourceNotFoundException() throws Exception {

		ContactDataDto contactData = FormDataUtil.formContactData();

		User user = new User();
		user.setId(1);
		user.setFirstName("Santosh");
		user.setLastName("Pandey");

		Email email1 = new Email();
		email1.setId(1);
		email1.setMail("sp878@gmail.com");

		Email email2 = new Email();
		email2.setId(2);
		email2.setMail("sp882@gmail.com");

		PhoneNumber phoneNumber1 = new PhoneNumber();
		phoneNumber1.setId(1);
		phoneNumber1.setNumber("9878285763");

		PhoneNumber phoneNumber2 = new PhoneNumber();
		phoneNumber2.setId(2);
		phoneNumber2.setNumber("9878285756");

		user.setEmails(Arrays.asList(email1, email2));
		user.setPhoneNumbers(Arrays.asList(phoneNumber1, phoneNumber2));


		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

		userService.addAdditionalContactData(1, contactData.getEmails(), contactData.getPhoneNumbers());

	}

}