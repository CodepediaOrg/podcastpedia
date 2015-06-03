package org.podcastpedia.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.podcastpedia.common.types.MediaType;
import org.podcastpedia.common.xmladapters.DateAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Episode implements Serializable{

	/**
	 * automatic generated serialVersionUID 
	 */
	@XmlTransient
	private static final long serialVersionUID = -1957667986801174870L;

	/** identifies the podcast the episode belongs to */
	public Integer podcastId;
	
	/** episode id - unique identifier of a podcast's episode */ 
	public Integer episodeId;
	
	/** description of the episode */
	public String description;
	
	/** title of the episode */ 
	public String title;

	/** link of the episode - this should be the link to the episode on the provider's website
	 * Some providers set this as the url to the media file */
	public String link;
	
	/** this is the url to the media (audio or video) of this episode */
	public String mediaUrl; 
	
	/** podcast the episode belongs to */
	private Podcast podcast;
			
	/** publication date of the episode */
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date publicationDate; 
	
	/** media type (either audio or video) */
	private MediaType mediaType; 
	
	/** length of the episode */
	private Long length;
	
	/** episode's transformed title with hyphens to be added in the URL for SEO optimization */
	private String titleInUrl;
	
	/**	1 if the episodes is still reachable and 0 if not 
	 * when set to 0 it can be also used for history purposes, or retries at a later time .... */
	private int isAvailable;
	
	/** rating of the episode */
	private Float rating;
	
	/** rounded valued needed for displayin star rating */
	private Integer ratingInt;
	
	/** number of ratings for the episode */
	private Integer numberRatings;
	
	/** 
	 * holds the the httpStatus for the episode's url  - see org.apache.http.HttpStatus for the codes semnification  
	 * or custom exception code */
	private Integer availability;
		
	/**	holds popularity of the podcast - current popularity is ordered by the rating, could include fb likes or number of subscriptions */
	private Float popularity;
	
	/** any value not null or zero means the episode is new */
	private Integer isNew;
	
	/** type of the enclosure */ 
	private String enclosureType;
	
	/** author of the episode */
	private String author; 	
	
	public Episode(){}

	public Podcast getPodcast() {
		return podcast;
	}

	public void setPodcast(Podcast podcast) {
		this.podcast = podcast;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getEnclosureType() {
		return enclosureType;
	}

	public void setEnclosureType(String enclosureType) {
		this.enclosureType = enclosureType;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public Integer getRatingInt() {
		return ratingInt;
	}

	public void setRatingInt(Integer ratingInt) {
		this.ratingInt = ratingInt;
	}

	public Float getPopularity() {
		return popularity;
	}

	public void setPopularity(Float popularity) {
		this.popularity = popularity;
	}
	
	public Integer getAvailability() {
		return availability;
	}

	public void setAvailability(Integer availability) {
		this.availability = availability;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Integer getNumberRatings() {
		return numberRatings;
	}

	public void setNumberRatings(Integer numberRatings) {
		this.numberRatings = numberRatings;
	}

	public int getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(int isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getTitleInUrl() {
		return titleInUrl;
	}

	public void setTitleInUrl(String titleInUrl) {
		this.titleInUrl = titleInUrl;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
