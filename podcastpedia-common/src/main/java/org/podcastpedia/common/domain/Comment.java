package org.podcastpedia.common.domain;

import java.io.Serializable;
import java.util.Date;

/** 
 * Domain class that holds comments associated both to podcasts and episodes 
 * 
 * @author amasia
 *
 */
public class Comment implements Serializable {

	private static final long serialVersionUID = -8065317878237351777L;

	/** id of the comment */
	private Long commentId;
	
	/** id of the podcast the comment is for */
	private Integer podcastId;
	
	/** index of the episode of the podcast this comment is for or null if not present - chosen as Integer as it can support null */
	private Integer episodeId;
	
	/** date when the comment was inserted */
	private Date insertionDate;
	
	/** name - nickname of the user who inserted the comment */
	private String name;
	
	/** email of the user who inserted the podcast */
	private String email;
	
	/** text of the comment */
	private String text;
	
	/** number of likes for the comment - TODO for later implementation when having log in */
	private Integer numberOfLikes;
	
	/** number of likes for the comment - TODO for later implementation when having log in */
	private Integer numberOfDislikes;
	
	/** the comment this comment is replying to */
	private Long replyTo;
	
	/** flag marked with 1 if it marked as abused and 0 if not */
	private Integer isMarkedAbused; 
	
	/** place holder for error on invalid captcha */
	private String invalidRecaptcha;
	
	/** rating given by visitor when adding comment */ 
	private Integer rating;
	
	/** checkbox to specify if visitor is rating the podcast/episode or not */
	private Boolean visitorIsRating;
	
	/** checks if the user is subscribing to the podcast via email */ 
	private Boolean visitorIsSubscribing;
	
	/** maybe the visitor wants to promote her webiste */
	private String website;
		
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Boolean getVisitorIsRating() {
		return visitorIsRating;
	}

	public void setVisitorIsRating(Boolean visitorIsRating) {
		this.visitorIsRating = visitorIsRating;
	}

	public Boolean getVisitorIsSubscribing() {
		return visitorIsSubscribing;
	}

	public void setVisitorIsSubscribing(Boolean visitorIsSubscribing) {
		this.visitorIsSubscribing = visitorIsSubscribing;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getInvalidRecaptcha() {
		return invalidRecaptcha;
	}

	public void setInvalidRecaptcha(String invalidRecaptcha) {
		this.invalidRecaptcha = invalidRecaptcha;
	}

	public Integer getIsMarkedAbused() {
		return isMarkedAbused;
	}

	public void setIsMarkedAbused(Integer isMarkedAbused) {
		this.isMarkedAbused = isMarkedAbused;
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
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

	public Date getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getNumberOfLikes() {
		return numberOfLikes;
	}

	public void setNumberOfLikes(Integer numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}

	public Integer getNumberOfDislikes() {
		return numberOfDislikes;
	}

	public void setNumberOfDislikes(Integer numberOfDislikes) {
		this.numberOfDislikes = numberOfDislikes;
	}

	public Long getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(Long replyTo) {
		this.replyTo = replyTo;
	} 		
	
}
