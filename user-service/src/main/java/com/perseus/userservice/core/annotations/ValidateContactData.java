package com.perseus.userservice.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.perseus.userservice.core.validators.ContactDataValidator;

/**
 * Custom validator annotation to validate contact data
 *
 */
@Constraint(validatedBy = { ContactDataValidator.class })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateContactData {

	String message() default "Please provide atleast one valid contact data(Email/Phone).";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
