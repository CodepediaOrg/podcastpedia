package org.podcastpedia.core.user;

import org.podcastpedia.common.domain.User;
import org.podcastpedia.core.contact.ContactForm;

public interface UserEmailNotificationService {

	public void sendUserRegistrationNotificationToAdmin(User user);

    void sendRegistrationEmailConfirmation(User user);

    void sendPasswortResetEmailConfirmation(User user);
}
