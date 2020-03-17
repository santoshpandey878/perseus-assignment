package com.perseus.userservice.services;

import java.util.List;

import com.perseus.userservice.entities.Email;
import com.perseus.userservice.entities.PhoneNumber;
import com.perseus.userservice.entities.User;

/**
 *Interface responsible to provide methods for user operations
 */
public interface UserService {

	/**
	 * Method to get user details by id.
	 * @param userId
	 * @return
	 */
	User getUserById(int userId);

	/**
	 * Method to get users by name.
	 * @param name
	 * @return
	 */
	List<User> getUserByName(String name);

	/**
	 * Method to create user with contact data
	 * @param user
	 * @return
	 */
	User createUser(User user);

	/**
	 * Method to add additional contact data for a user
	 * @param userId
	 * @param emails
	 * @param phoneNumbers
	 * @return  return user with additional contact data
	 */
	User addAdditionalContactData(int userId, List<Email> emails, List<PhoneNumber> phoneNumbers);

	/**
	 * Method to update existing contact data for a user
	 * @param userId
	 * @param emails
	 * @param phoneNumbers
	 * @return  return user with updated contact data
	 */
	User updateExistingContactData(int userId, List<Email> emails, List<PhoneNumber> phoneNumbers);

	/**
	 * Method to delete user by id
	 * @param userId
	 */
	void deleteUser(int userId);

}
