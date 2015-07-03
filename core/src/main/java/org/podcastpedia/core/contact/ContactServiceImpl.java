package org.podcastpedia.core.contact;

import org.podcastpedia.core.userinteraction.UserInteractionDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class ContactServiceImpl implements ContactService {
	
	@Autowired
	private UserInteractionDao userInteractionDao;

	public void sendContactMessage(ContactForm contactForm) {
		contactForm.setSubmissionDate(new Date());
		userInteractionDao.insertContactMessage(contactForm);
	}

	
}
