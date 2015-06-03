package org.podcastpedia.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Class to hold email subscriptions for podcasts
 * 
 * @author ama
 *
 */
public class Subscription implements Serializable{

	private static final long serialVersionUID = -573702663120114938L;

	/** email address where the new episodes information is sent to */
	private String email;
	
	/** name of the visitor subscribing */
	private String name;
	
	/** when the subscription was inserted in the database */
	private Date subscriptionDate;
	
	/** podcast the subscription is for */
	private Integer podcastId; 
	
	public Integer getPodcastId() {
		return podcastId;
	}

	public void setPodcastId(Integer podcastId) {
		this.podcastId = podcastId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(Date subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}
			
}
