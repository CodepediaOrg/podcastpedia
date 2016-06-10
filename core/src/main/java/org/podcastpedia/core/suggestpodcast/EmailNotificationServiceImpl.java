package org.podcastpedia.core.suggestpodcast;

import org.apache.velocity.app.VelocityEngine;
import org.podcastpedia.common.util.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EmailNotificationServiceImpl implements EmailNotificationService {

	@Autowired
	private ConfigService configService;
    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;

    @Override
	public void sendSuggestPodcastNotification(final SuggestedPodcast suggestedPodcast) {
	      MimeMessagePreparator preparator = new MimeMessagePreparator() {
		        @SuppressWarnings({ "rawtypes", "unchecked" })
				public void prepare(MimeMessage mimeMessage) throws Exception {
		             MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                     String[] emailsTo = {configService.getValue("EMAIL_TO_CONTACT_MESSAGE"), "adrianmatei@gmail.com"};
                     message.setTo(emailsTo);
		             message.setFrom(new InternetAddress(configService.getValue("EMAIL_FROM_CONTACT_MESSAGE")));
		             message.setSubject("New suggested podcast");
		             message.setSentDate(new Date());
		             Map model = new HashMap();
		             model.put("newPodcast", suggestedPodcast);
		             String text = VelocityEngineUtils.mergeTemplateIntoString(
		                velocityEngine, "velocity/suggestPodcastNotificationMessage.vm", "UTF-8", model);
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
