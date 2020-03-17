package com.perseus.userservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perseus.userservice.core.utils.DtoConverter;
import com.perseus.userservice.dto.ContactDataDto;
import com.perseus.userservice.dto.UserDto;
import com.perseus.userservice.entities.User;
import com.perseus.userservice.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *User Controller responsible to handle all client requests for users and return the response.
 *DTO is used with client interaction
 */
@RestController
@RequestMapping("users")
@CrossOrigin
@Api(value = "User Controller responsible to handle all client requests for users.")
public class UserController {

	@Autowired private UserService userService;
	@Autowired private DtoConverter dtoConverter;

	/**
	 * API to get user details by user id.
	 * @param userId
	 * @return 
	 */
	@GetMapping("/{userId}")
	@ApiOperation(value = "API to get user details by user id.")
	public UserDto getUserById(@PathVariable int userId) {
		User user = userService.getUserById(userId);

		return dtoConverter.convert(user, UserDto.class);
	}

	/**
	 * API to get user details by name.
	 * @param name
	 * @return 
	 */
	@GetMapping
	@ApiOperation(value = "API to get users by name.")
	public List<UserDto> getUserByName(@RequestParam("name") String name) {
		List<User> users = userService.getUserByName(name);

		return users.stream()
				.map(user -> dtoConverter.convert(user, UserDto.class))
				.collect(Collectors.toList());
	}

	/**
	 * API to create user with contact data
	 * @param userDto
	 * @return  return created user
	 */
	@PostMapping
	@ApiOperation(value = "API to create user with contact data")
	public UserDto createUser(@Valid @RequestBody UserDto userDto) {
		User user = userService.createUser(dtoConverter.convert(userDto, User.class));

		return dtoConverter.convert(user, UserDto.class);
	}

	/**
	 * API to add additional contact data(email/phone) for a user
	 * @param contactDataDto
	 * @return  return user with additional contact data
	 */
	@PostMapping("/{userId}/contactdata")
	@ApiOperation(value = "API to add additional contact data(email/phone) for a user")
	public UserDto addAdditionalContactData(@PathVariable int userId, @Valid @RequestBody ContactDataDto contactDataDto) {
		User user = userService.addAdditionalContactData(userId, 
				contactDataDto.getEmails(), 
				contactDataDto.getPhoneNumbers());

		return dtoConverter.convert(user, UserDto.class);
	}

	/**
	 * API to update existing contact data(email/phone) for a user
	 * @param contactDataDto
	 * @return  return user with updated contact data
	 */
	@PutMapping("/{userId}/contactdata")
	@ApiOperation(value = "API to update existing contact data(email/phone) for a user")
	public UserDto updateExistingContactData(@PathVariable int userId, @Valid @RequestBody ContactDataDto contactDataDto) {
		User user = userService.updateExistingContactData(userId, 
				contactDataDto.getEmails(), 
				contactDataDto.getPhoneNumbers());

		return dtoConverter.convert(user, UserDto.class);
	}

	/**
	 * API to delete a user by id.
	 * @param userId
	 */
	@DeleteMapping("/{userId}")
	@ApiOperation(value = "API to delete a user by id.")
	public void deleteUser(@PathVariable int userId) {
		userService.deleteUser(userId);
	}

}
