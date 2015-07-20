package org.podcastpedia.admin.insert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.mock_javamail.Mailbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.mail.Address;
import javax.mail.MessagingException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-spring-admin-context.xml") // the Spring context file
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback = true)
public class EmailNotificationServiceImplTest {

	public static final String EMAIL_SUBJECT = "Your podcast has been added to Podcastpedia.org";
	public static final String EMAIL_FROM = "contact@podcastpedia.org";

	@Autowired
	EmailNotificationService emailNotificationService;

	@Before
	public void setUp(){
		Mailbox.clearAll();
	}

	@Test
	public void testSendNotificationEmail() throws MessagingException {

		emailNotificationService.sendPodcastAdditionConfirmation("Gigi", "gigi@podcastpedia.org", "feed.xxx");

		Mailbox inbox = Mailbox.get("gigi@podcastpedia.org");

		assert(inbox.size()==1);
		assert(inbox.get(0).getSubject().equals(EMAIL_SUBJECT));

		Address[] addresses = inbox.get(0).getFrom();
		assert(addresses[0].toString().equals(EMAIL_FROM));
	}

}
