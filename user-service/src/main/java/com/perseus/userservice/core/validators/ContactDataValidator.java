package com.perseus.userservice.core.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.perseus.userservice.core.annotations.ValidateContactData;
import com.perseus.userservice.core.utils.CollectionUtil;
import com.perseus.userservice.dto.ContactDataDto;

/**
 * Custom Validator to validate contact data
 *
 */
public class ContactDataValidator implements ConstraintValidator<ValidateContactData, ContactDataDto> {

	@Override
	public boolean isValid(ContactDataDto contactDataDto, ConstraintValidatorContext context) {

		return CollectionUtil.isNotEmpty(contactDataDto.getEmails()) 
				|| CollectionUtil.isNotEmpty(contactDataDto.getPhoneNumbers());
	}
}
