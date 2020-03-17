package com.perseus.userservice.integrationtest;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.perseus.userservice.dto.ContactDataDto;
import com.perseus.userservice.dto.UserDto;
import com.perseus.userservice.utils.FormDataUtil;

import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTests extends AbstractIntegrationTest {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testCreateUser_returns_200_with_created_user() {

		UserDto userDto = FormDataUtil.formUserRequest();

		// create user
		UserDto response =  with().body(userDto).contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.when()
				.request("POST", baseUrl + "/users")
				.then()
				.statusCode(200)
				.extract()
				.response()
				.as(UserDto.class);

		assertEquals(userDto.getFirstName(), response.getFirstName());
	}


	@Test
	public void testGetUserById_returns_200_with_expected_user() {

		UserDto userDto = FormDataUtil.formUserRequest();

		// create user
		UserDto response =  with().body(userDto).contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.when()
				.request("POST", baseUrl + "/users")
				.then()
				.statusCode(200)
				.extract()
				.response()
				.as(UserDto.class);


		int userId = response.getId();

		// get user details by id
		UserDto userResponse = when().get(baseUrl + "/users/"+userId)
				.then()
				.statusCode(200)
				.extract()
				.response()
				.as(UserDto.class);

		assertEquals(userId, userResponse.getId());
		assertEquals(userDto.getFirstName(), userResponse.getFirstName());
		assertEquals(userDto.getLastName(), userResponse.getLastName());
	}

	@Test
	public void testGetUserByName_returns_200_with_expected_user() {

		UserDto userDto = FormDataUtil.formUserRequest();

		// create user
		with().body(userDto).contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.when()
		.request("POST", baseUrl + "/users")
		.then()
		.statusCode(200)
		.extract()
		.response()
		.as(UserDto.class);


		// get user details by name
		when().get(baseUrl + "/users?name="+userDto.getFirstName())
		.then()
		.statusCode(200);

	}

	@Test
	public void testDeleteUser_returns_200() {

		UserDto userDto = FormDataUtil.formUserRequest();

		// create user
		UserDto response =  with().body(userDto).contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.when()
				.request("POST", baseUrl + "/users")
				.then()
				.statusCode(200)
				.extract()
				.response()
				.as(UserDto.class);


		// get user details by name
		when().delete(baseUrl + "/users/"+response.getId())
		.then()
		.statusCode(200);

	}
	
	@Test
	public void testAddAdditionalContactData_returns_200_with_user() {

		UserDto userDto = FormDataUtil.formUserRequest();

		// create user
		UserDto response =  with().body(userDto).contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.when()
				.request("POST", baseUrl + "/users")
				.then()
				.statusCode(200)
				.extract()
				.response()
				.as(UserDto.class);
		
		ContactDataDto contactDataDto= FormDataUtil.formContactData();
		
		// add additional contact data
		UserDto finalResponse =  with().body(contactDataDto).contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.when()
				.request("POST", baseUrl + "/users/"+response.getId()+"/contactdata")
				.then()
				.statusCode(200)
				.extract()
				.response()
				.as(UserDto.class);

		assertEquals(2, finalResponse.getEmails().size());
		assertEquals(2, finalResponse.getPhoneNumbers().size());
	}

}