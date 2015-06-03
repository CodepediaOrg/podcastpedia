package org.podcastpedia.common.domain;

import java.io.Serializable;
import java.util.List;

public class RatingData implements Serializable{

	private static final long serialVersionUID = -8090474193858805392L;
	
	/** current rating for podcast/episode */
	Float rating;
	
	/** rating data for this podcast */
	Integer podcastId;
	
	/** rating data for this episode; -1 if it meant for podcast */
	Integer episodeId;
	
	/** number of ratings for the given podcast/episode */
	Integer numberOfRatings;
	
	/** sum of all ratings, so that an average can be calculated */
	Integer sumOfRatings;
	
	/** list of ip addresses who voted for the podcast/episode */
	List<String> ipAddresses;

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

	public Integer getNumberOfRatings() {
		return numberOfRatings;
	}

	public void setNumberOfRatings(Integer numberOfRatings) {
		this.numberOfRatings = numberOfRatings;
	}

	public Integer getSumOfRatings() {
		return sumOfRatings;
	}

	public void setSumOfRatings(Integer sumOfRatings) {
		this.sumOfRatings = sumOfRatings;
	}

	public List<String> getIpAddresses() {
		return ipAddresses;
	}

	public void setIpAddresses(List<String> ipAddresses) {
		this.ipAddresses = ipAddresses;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}
	
	
}
