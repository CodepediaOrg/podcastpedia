package org.podcastpedia.admin.general;

import java.io.Serializable;

public class FbLike implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5494277178625261644L;

	private String id;
	
	Integer shares;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getShares() {
		return shares;
	}

	public void setShares(Integer shares) {
		this.shares = shares;
	}
	
	
}
