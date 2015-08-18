package org.podcastpedia.web.user;

import org.podcastpedia.common.domain.User;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Contact form validator
 *
 * @author ama
 *
 */
public class UserRegistrationFormValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		User user = (User)target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "invalid.required.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "invalid.required.message");
		if(!isValidEmail(user.getUsername())){
			errors.rejectValue("email", "invalid.required.email");
		}

	}

	private boolean isValidEmail(String email) {
		// TODO Auto-generated method stub
		return true;
	}

}
