package org.podcastpedia.common.domain;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
	
	private static final long serialVersionUID = -1351647841998878425L;

	/** in podcastpedia username is the email address for simplicity */
	private String username;
	
	private String password;
	
	/** the name of the user */
	private String name;
	
	/** list with podcast ids that the user is subscribing to */
	private List<Integer> subscriptions;

	public List<Integer> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Integer> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 		

}
