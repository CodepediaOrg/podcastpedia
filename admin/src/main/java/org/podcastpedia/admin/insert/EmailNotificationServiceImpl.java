package org.podcastpedia.admin.insert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.podcastpedia.common.util.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;


public class EmailNotificationServiceImpl implements EmailNotificationService {

	public static final String EMAIL_SUBJECT = "Your podcast has been added to Podcastpedia.org";
	public static final String EMAIL_FROM = "contact@podcastpedia.org";

	private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;

    @Autowired
    private ConfigService configService;


	public void sendPodcastAdditionConfirmation(final String name, final String email, final String podcastUrl) {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void prepare(MimeMessage mimeMessage) throws Exception {
			     MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			     message.setTo(email);
	             message.setBcc("adrianmatei@gmail.com");
			     message.setFrom(configService.getValue("admin.email.from.podcast.addition.confirmation"));
			     message.setSubject(EMAIL_SUBJECT);
			     message.setSentDate(new Date());

			     Map model = new HashMap();
			     model.put("name", name);
			     model.put("podcastUrl", podcastUrl);

			     String text = VelocityEngineUtils.mergeTemplateIntoString(
			        velocityEngine, "velocity/podcast_addition_notification.vm", "UTF-8", model);

			     message.setText(text, true);

			}
		};
		       mailSender.send(preparator);
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

}

