package com.perseus.userservice.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.IOException;
import java.util.Arrays;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perseus.userservice.core.utils.DtoConverter;
import com.perseus.userservice.dto.ContactDataDto;
import com.perseus.userservice.dto.UserDto;
import com.perseus.userservice.entities.Email;
import com.perseus.userservice.entities.PhoneNumber;
import com.perseus.userservice.entities.User;
import com.perseus.userservice.services.UserService;
import com.perseus.userservice.utils.FormDataUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserService userService;

	@TestConfiguration
	static class UserControllerTestContextConfiguration {

		@Bean
		public DtoConverter getDtoConverter() {
			return new DtoConverter();
		}

		@Bean
		public ModelMapper getModelMapper() {
			return new ModelMapper();
		}
	}


	@Test
	public void suppliedInvalidTypeUserId_whenGetUserById_thenThrowException() throws Exception {

		mvc.perform(get("/users/{userId}","sp878"))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}


	@Test
	public void givenUser_whenGetUserById_thenReturnJsonObject() throws Exception {

		User userResponse = FormDataUtil.formUserResponse();

		Mockito.when(userService.getUserById(Mockito.anyInt())).thenReturn(userResponse);

		mvc.perform(get("/users/{userId}",1))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.firstName", is("Santosh")))
		.andExpect(jsonPath("$.lastName", is("Pandey")))
		.andExpect(jsonPath("$.emails", hasSize(1)))
		.andExpect(jsonPath("$.phoneNumbers", hasSize(1)));
	}

	@Test
	public void givenUser_whenGetUserByName_thenReturnJsonObject() throws Exception {

		User userResponse = FormDataUtil.formUserResponse();

		Mockito.when(userService.getUserByName(Mockito.anyString())).thenReturn(Arrays.asList(userResponse));

		mvc.perform(get("/users?name=Santosh"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[0].firstName", is("Santosh")))
		.andExpect(jsonPath("$[0].lastName", is("Pandey")))
		.andExpect(jsonPath("$[0].emails", hasSize(1)))
		.andExpect(jsonPath("$[0].phoneNumbers", hasSize(1)));
	}

	@Test
	public void givenUserDataWithInvalidFirstName_whenCreateUser_thenThrowException() throws Exception {

		UserDto userRequest = FormDataUtil.formUserRequest();
		userRequest.setFirstName("sp878");

		mvc.perform(post("/users").content(getJSONBody(userRequest))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void givenUserDataWithInvalidLastName_whenCreateUser_thenThrowException() throws Exception {

		UserDto userRequest = FormDataUtil.formUserRequest();
		userRequest.setLastName("sp878");

		mvc.perform(post("/users").content(getJSONBody(userRequest))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void givenUserDataWithInvalidEmail_whenCreateUser_thenThrowException() throws Exception {

		UserDto userRequest = FormDataUtil.formUserRequest();
		userRequest.setEmails(null);

		mvc.perform(post("/users").content(getJSONBody(userRequest))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void givenUserDataWithInvalidPhoneNumber_whenCreateUser_thenThrowException() throws Exception {

		UserDto userRequest = FormDataUtil.formUserRequest();
		userRequest.setPhoneNumbers(null);

		mvc.perform(post("/users").content(getJSONBody(userRequest))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void givenValidUserData_whenCreateUser_thenReturnJsonObject() throws Exception {

		UserDto userRequest = FormDataUtil.formUserRequest();

		User userResponse = FormDataUtil.formUserResponse();

		Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(userResponse);

		mvc.perform(post("/users").content(getJSONBody(userRequest))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.firstName", is("Santosh")))
		.andExpect(jsonPath("$.lastName", is("Pandey")))
		.andExpect(jsonPath("$.emails", hasSize(1)))
		.andExpect(jsonPath("$.phoneNumbers", hasSize(1)));
	}

	@Test
	public void givenInvalidContactData_whenAddAdditionalContactData_thenThrowException() throws Exception {

		ContactDataDto contactData = new ContactDataDto();

		mvc.perform(post("/users/{userId}/contactdata",1).content(getJSONBody(contactData))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void givenValidContactData_whenAddAdditionalContactData_thenReturnJsonObject() throws Exception {

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

		Mockito.when(userService.addAdditionalContactData(Mockito.anyInt(), Mockito.anyList(), Mockito.anyList())).thenReturn(user);

		mvc.perform(post("/users/{userId}/contactdata",1).content(getJSONBody(contactData))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.firstName", is("Santosh")))
		.andExpect(jsonPath("$.lastName", is("Pandey")))
		.andExpect(jsonPath("$.emails", hasSize(2)))
		.andExpect(jsonPath("$.phoneNumbers", hasSize(2)));
	}

	@Test
	public void givenValidContactData_whenUpdateExistingContactData_thenReturnJsonObject() throws Exception {

		ContactDataDto contactData = new ContactDataDto();

		Email email = new Email();
		email.setId(1);
		email.setMail("sp882@gmail.com");

		PhoneNumber phoneNumber = new PhoneNumber();
		phoneNumber.setId(1);
		phoneNumber.setNumber("9878285756");

		contactData.setEmails(Arrays.asList(email));
		contactData.setPhoneNumbers(Arrays.asList(phoneNumber));

		User user = new User();
		user.setId(1);
		user.setFirstName("Santosh");
		user.setLastName("Pandey");

		user.setEmails(Arrays.asList(email));
		user.setPhoneNumbers(Arrays.asList(phoneNumber));

		Mockito.when(userService.updateExistingContactData(Mockito.anyInt(), Mockito.anyList(), Mockito.anyList())).thenReturn(user);

		mvc.perform(put("/users/{userId}/contactdata",1).content(getJSONBody(contactData))
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.firstName", is("Santosh")))
		.andExpect(jsonPath("$.lastName", is("Pandey")))
		.andExpect(jsonPath("$.emails", hasSize(1)))
		.andExpect(jsonPath("$.phoneNumbers", hasSize(1)));
	}

	@Test
	public void whenDeleteUser_thenDoNothing() throws Exception {

		Mockito.doNothing().when(userService).deleteUser(Mockito.anyInt());

		mvc.perform(delete("/users/{userId}",1))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}


	/**
	 * Method to convert object to json string
	 * @param obj
	 * @return
	 * @throws JSONException
	 */
	private String getJSONBody(Object obj) throws JSONException {
		ObjectMapper objectMapper = new ObjectMapper();

		String jsonStr = null;
		try { 
			jsonStr = objectMapper.writeValueAsString(obj); 
		} 
		catch (IOException e) { 
			e.printStackTrace(); 
		}
		return jsonStr; 
	}

}