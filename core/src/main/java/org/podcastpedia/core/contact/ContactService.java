package org.podcastpedia.core.contact;

public interface ContactService {
	/**
	 * method will send message to admin /write in db after a visitor submits
	 * the contact form
	 */
	public void sendContactMessage(ContactForm contactForm);
}
