package org.podcastpedia.web.user;

import org.podcastpedia.common.domain.User;
import org.podcastpedia.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Contact form validator
 *
 * @author ama
 *
 */
public class PasswordForgottenFormValidator implements Validator{

    @Autowired
    UserService userService;

	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		User user = (User)target;

        if(!userService.isExistingUser(user.getUsername())){
            errors.rejectValue("username", "invalid.email.not_registered");
        }

		if(!isValidEmail(user.getUsername())){
			errors.rejectValue("username", "invalid.required.email");
		}


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "invalid.required.password");

        if(!user.getPassword().equals(user.getMatchingPassword())){
            errors.rejectValue("matchingPassword", "password.missmatch");
        }

	}

	private boolean isValidEmail(String email) {
		// TODO Auto-generated method stub
		return true;
	}

}
