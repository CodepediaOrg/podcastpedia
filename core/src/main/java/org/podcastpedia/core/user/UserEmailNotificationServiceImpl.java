package org.podcastpedia.core.user;

import org.apache.velocity.app.VelocityEngine;
import org.podcastpedia.common.domain.User;
import org.podcastpedia.common.util.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;
import java.util.*;

public class UserEmailNotificationServiceImpl implements UserEmailNotificationService {

    private static String[] supportedLanguages = {"en", "fr", "de" };

	@Autowired
	private ConfigService configService;
    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;

    @Override
	public void sendUserRegistrationNotificationToAdmin(final User user) {
	      MimeMessagePreparator preparator = new MimeMessagePreparator() {
	        @SuppressWarnings({ "rawtypes", "unchecked" })
			public void prepare(MimeMessage mimeMessage) throws Exception {
	             MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
	             message.setTo(configService.getValue("EMAIL_TO_CONTACT_MESSAGE"));
	             message.setBcc("adrianmatei@gmail.com");
	             message.setSubject("New user registration request");
                 message.setFrom("no-reply@podcastpedia.org");
	             message.setSentDate(new Date());
	             Map model = new HashMap();
	             model.put("user", user);

	             String text = buildEmailText(model, velocityEngine,  "newUserRegistrationEmailConfirmation");
	             message.setText(text, true);
	          }
	       };
	       this.mailSender.send(preparator);
	}

    @Override
    public void sendRegistrationEmailConfirmation(final User user) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(user.getUsername());
                message.setBcc("adrianmatei@gmail.com");
                message.setSubject("Podcastpedia.org - confirm registration");
                message.setSentDate(new Date());
                message.setFrom("no-reply@podcastpedia.org");
                Map model = new HashMap();
                model.put("user", user);

                String text = buildEmailText(model, velocityEngine, "newUserRegistrationEmailConfirmation");
                message.setText(text, true);
            }
        };
        this.mailSender.send(preparator);
    }

    @Override
    public void sendPasswortResetEmailConfirmation(final User user) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @SuppressWarnings({ "rawtypes", "unchecked" })
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(user.getUsername());
                message.setBcc("adrianmatei@gmail.com");
                message.setSubject("Podcastpedia.org - reset your password");
                message.setSentDate(new Date());
                message.setFrom("no-reply@podcastpedia.org");
                Map model = new HashMap();
                model.put("user", user);

                String text = buildEmailText(model, velocityEngine, "passwordResetEmailConfirmation");
                message.setText(text, true);
            }
        };
        this.mailSender.send(preparator);
    }

    private String buildEmailText(Map model, VelocityEngine velocityEngine, String templateName) {
        String templateLocation;

        Locale locale = LocaleContextHolder.getLocale();
        if(Arrays.asList(supportedLanguages).contains(locale.getLanguage())){
            templateLocation = "velocity/" + templateName + "_" + locale.getLanguage() + ".vm";
        } else {
            templateLocation = "velocity/"+ templateName + ".vm";
        }
        return VelocityEngineUtils.mergeTemplateIntoString(
            velocityEngine, templateLocation, "UTF-8", model);
    }

    public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

}
