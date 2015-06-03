package org.podcastpedia.web.contact;

import java.util.Date;

import org.podcastpedia.web.userinteraction.UserInteractionDao;
import org.springframework.beans.factory.annotation.Autowired;

public class ContactServiceImpl implements ContactService {
	
	@Autowired
	private UserInteractionDao userInteractionDao;

	public void sendContactMessage(ContactForm contactForm) {
		contactForm.setSubmissionDate(new Date());
		userInteractionDao.insertContactMessage(contactForm);
	}

	
}
