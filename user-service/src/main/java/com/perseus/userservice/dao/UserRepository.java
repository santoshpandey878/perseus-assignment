package com.perseus.userservice.dao;

import java.util.List;

import com.perseus.userservice.entities.User;

/**
 * Repository to handle all database operation for User entity.
 */
public interface UserRepository extends BaseRepository<User, Integer>{

	/**
	 * Method to get all users by name
	 * @param name
	 * @return
	 */
	List<User> findAllByFirstNameOrLastName(String firstName, String lastName);

}
