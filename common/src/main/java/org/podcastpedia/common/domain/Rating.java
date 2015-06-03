package org.podcastpedia.common.domain;

import java.io.Serializable;
import java.util.Date;

/** 
 * Holds the user ratings for podcast
 * @author ama
 *
 */
public class Rating implements Serializable{

	private static final long serialVersionUID = -2967743518641967815L;

	/** name of visitor rating */
	private String name;
	
	/** email of the visitor rating */
	private String email;
	
	/** podcast which is being rated*/
	private Integer podcastId;
	
	/** episode (if existant) which is being rated */
	private Integer episodeId;
	
	/** rating date */
	private Date ratingDate;
	
	/** rating given - current from 1 to 20 */
	private Integer rating;

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

	public Integer getPodcastId() {
		return podcastId;
	}

	public void setPodcastId(Integer podcastId) {
		this.podcastId = podcastId;
	}

	public Integer getEpisodeId() {
		return episodeId;
	}

	public void setEpisodeId(Integer episodeId) {
		this.episodeId = episodeId;
	}

	public Date getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(Date ratingDate) {
		this.ratingDate = ratingDate;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
		
}
