package org.podcastpedia.common.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {

	private static final long serialVersionUID = -1351647841998878425L;

	/** in podcastpedia username is the email address for simplicity */
	private String username;

	private String password;

    /** role of the user in spring security */
    private String role;

    /** used by the registration process as matching password field */
    private String matchingPassword;

	/** the display name of the user - e.g. "J. Doe", introduced by use at registration*/
	private String displayName;

    /** the date the user submit her registration*/
    private Date registrationDate;

    /** flag telling if the user is enabled(=1) or not(=0)*/
    private int enabled;

    /** registration token - UUID to be confirmed by the user when she clicks the link from email */
    private String registrationToken;

    /** place holder for error on invalid captcha */
    private String invalidRecaptcha;

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

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getMatchingPassword() {
        return this.matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getInvalidRecaptcha() {
        return invalidRecaptcha;
    }

    public void setInvalidRecaptcha(String invalidRecaptcha) {
        this.invalidRecaptcha = invalidRecaptcha;
    }
}
