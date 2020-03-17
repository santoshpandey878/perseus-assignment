package com.perseus.userservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.perseus.userservice.core.constant.MessageConstant;
import com.perseus.userservice.core.exceptions.ResourceNotFoundException;
import com.perseus.userservice.core.exceptions.ServiceException;
import com.perseus.userservice.core.utils.CollectionUtil;
import com.perseus.userservice.core.utils.MessageUtil;
import com.perseus.userservice.dao.UserRepository;
import com.perseus.userservice.entities.Email;
import com.perseus.userservice.entities.PhoneNumber;
import com.perseus.userservice.entities.User;

/**
 *Class responsible to handle business logic of User operations
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired private UserRepository userRepository;
	@Autowired private MessageUtil message;


	@Transactional
	@Override
	public User getUserById(int userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(message.get(MessageConstant.USER_NOT_FOUND, userId)));
	}

	@Transactional
	@Override
	public List<User> getUserByName(String name) {
		return userRepository.findAllByFirstNameOrLastName(name, name);
	}

	@Transactional
	@Override
	public User createUser(User user) {
		user.addEmail();
		user.addPhoneNumber();
		return userRepository.save(user);
	}

	@Transactional
	@Override
	public void deleteUser(int userId) {
		User user = getUserById(userId);
		userRepository.delete(user);
	}

	@Transactional
	@Override
	public User addAdditionalContactData(int userId, List<Email> emails, List<PhoneNumber> phoneNumbers) {
		User user = getUserById(userId);

		if(CollectionUtil.isNotEmpty(emails)) {
			emails.forEach(email -> email.setUser(user));
			user.getEmails().addAll(emails);
		}

		if(CollectionUtil.isNotEmpty(phoneNumbers)) {
			phoneNumbers.forEach(phoneNumber -> phoneNumber.setUser(user));
			user.getPhoneNumbers().addAll(phoneNumbers);
		}

		return userRepository.save(user);
	}

	@Transactional
	@Override
	public User updateExistingContactData(int userId, List<Email> emails, List<PhoneNumber> phoneNumbers) {
		User user = getUserById(userId);

		// form emails
		if(CollectionUtil.isNotEmpty(emails)) {
			formEmails(emails, user);
		}

		// form phone numbers
		if(CollectionUtil.isNotEmpty(phoneNumbers)) {
			formPhoneNumbers(phoneNumbers, user);
		}

		return userRepository.save(user);
	}

	/**
	 * Method to form emails with new provided email
	 * @param emails
	 * @param user
	 */
	private void formEmails(List<Email> emails, User user) {
		for(Email newEmail : emails) {
			if(newEmail.getId() == 0) throw new ServiceException(MessageConstant.INVALID_EMAIL_ID);

			for(Email existingEmail : user.getEmails()) {
				if(newEmail.getId() == existingEmail.getId()) {
					existingEmail.setMail(newEmail.getMail());
					break;
				}
			}
		}
	}

	/**
	 * Method to form phone numbers with new provided numbers
	 * @param phoneNumbers
	 * @param user
	 */
	private void formPhoneNumbers(List<PhoneNumber> phoneNumbers, User user) {
		for(PhoneNumber newPhoneNumber : phoneNumbers) {
			if(newPhoneNumber.getId() == 0) throw new ServiceException(MessageConstant.INVALID_PHONE_NUMBER_ID);

			for(PhoneNumber existingPhoneNumber : user.getPhoneNumbers()) {
				if(newPhoneNumber.getId() == existingPhoneNumber.getId()) {
					existingPhoneNumber.setNumber(newPhoneNumber.getNumber());
					break;
				}
			}
		}
	}

}
