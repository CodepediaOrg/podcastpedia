package org.podcastpedia.core.suggestpodcast;

import org.podcastpedia.common.types.LanguageCode;
import org.podcastpedia.common.types.MediaType;
import org.podcastpedia.common.types.UpdateFrequencyType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * Class that mirrors the add podcast form
 * 
 * @author amasia
 *
 */
public class SuggestedPodcast implements Serializable {

	private static final long serialVersionUID = 304114844240727621L;
	
	/** name - mandatory */
	private String name;
	
	/** email address of the one suggesting the podcast */
	private String email; 

	/** url for the suggested podcast */
	private String feedUrl;
	
	/** list with category names added for podcast */
	private List<String> categories;

	/** string with comma separated categories, language, mediatype, update frequence, tags from the list above */
	private String metadataLine;
	
	/** text with suggested tags separated by spaces */
	private String suggestedTags;

	/** datetime when podcast was suggested */
	private Date insertionDate;
	
	/** place holder for error on invalid captcha */
	private String invalidRecaptcha;	
	
	/** message left by visitor when suggesting podcast */
	private String message;
	
	private MediaType mediaType;
	
	private LanguageCode languageCode;
	
	private UpdateFrequencyType updateFrequency; 
	
	/** fan facebook page */
	private String facebookPage;
	
	/** fan twitter page */
	private String twitterPage;
	
	/** fan google plus page */
	private String gplusPage;
	
	/** podcast identifier on Podcastpedia - e.g. http://www.podcastpedia.org/<strong>quarks</strong> */
	private String identifier;
		
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFacebookPage() {
		return facebookPage;
	}

	public void setFacebookPage(String facebookPage) {
		this.facebookPage = facebookPage;
	}

	public String getTwitterPage() {
		return twitterPage;
	}

	public void setTwitterPage(String twitterPage) {
		this.twitterPage = twitterPage;
	}

	public String getGplusPage() {
		return gplusPage;
	}

	public void setGplusPage(String gplusPage) {
		this.gplusPage = gplusPage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public LanguageCode getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(LanguageCode languageCode) {
		this.languageCode = languageCode;
	}

	public UpdateFrequencyType getUpdateFrequency() {
		return updateFrequency;
	}

	public void setUpdateFrequency(UpdateFrequencyType updateFrequency) {
		this.updateFrequency = updateFrequency;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInvalidRecaptcha() {
		return invalidRecaptcha;
	}

	public void setInvalidRecaptcha(String invalidRecaptcha) {
		this.invalidRecaptcha = invalidRecaptcha;
	}


	public String getMetadataLine() {
		return metadataLine;
	}

	public void setMetadataLine(String metadataLine) {
		this.metadataLine = metadataLine;
	}

	public Date getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(Date insertionDate) {
		this.insertionDate = insertionDate;
	}

	public String getFeedUrl() {
		return feedUrl;
	}

	public void setFeedUrl(String feedUrl) {
		this.feedUrl = feedUrl;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public String getSuggestedTags() {
		return suggestedTags;
	}

	public void setSuggestedTags(String suggestedTags) {
		this.suggestedTags = suggestedTags;
	}
		
}
