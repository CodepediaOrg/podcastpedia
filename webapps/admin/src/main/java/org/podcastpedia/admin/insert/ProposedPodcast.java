package org.podcastpedia.admin.insert;

import org.podcastpedia.common.domain.Podcast;

public class ProposedPodcast {

	/** set podcast attributes from the proposed */
	private Podcast podcast;
	
	/** email address of the person who proposed the podcast */
	private String name;
	
	/** email address of the person who proposed the podcast */
	private String email;

	public Podcast getPodcast() {
		return podcast;
	}

	public void setPodcast(Podcast podcast) {
		this.podcast = podcast;
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
		
}
