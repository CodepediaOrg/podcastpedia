package org.podcastpedia.web.contact;

import java.io.Serializable;
import java.util.Date;


public class ContactForm implements Serializable{

	private static final long serialVersionUID = 681561021593125256L;

	/** name of the persion contacting podcastmania */
	private String name;
	
	/** email of the person contacting podcastmania */
	private String email;
	
	/** text written by the person when contacting podcastmania */
	private String message;
	
	/** selected topic by person when contacting podcastmania */
	private String topic;
	
	/** Timestamp when the message was submitted */
	private Date submissionDate;

    /** place holder for error on invalid captcha */
    private String invalidRecaptcha;

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

    public String getInvalidRecaptcha() {
        return invalidRecaptcha;
    }

    public void setInvalidRecaptcha(String invalidRecaptcha) {
        this.invalidRecaptcha = invalidRecaptcha;
    }
}
