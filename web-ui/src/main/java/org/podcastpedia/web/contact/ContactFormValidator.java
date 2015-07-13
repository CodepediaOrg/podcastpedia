package org.podcastpedia.web.contact;

import org.podcastpedia.core.contact.ContactForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Contact form validator
 * 
 * @author ama
 *
 */
public class ContactFormValidator implements Validator{

	public boolean supports(Class<?> clazz) {		
		return ContactForm.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		ContactForm contactForm = (ContactForm)target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "invalid.required.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "invalid.required.email"); 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "invalid.required.message"); 		
		if(!isValidEmail(contactForm.getEmail())){
			errors.rejectValue("email", "invalid.required.email");
		}		
		
	}

	private boolean isValidEmail(String email) {
		// TODO Auto-generated method stub
		return true;
	}

}
