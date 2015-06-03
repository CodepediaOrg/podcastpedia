package org.podcastpedia.common.domain;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.podcastpedia.common.types.LanguageCode;
import org.podcastpedia.common.types.MediaType;
import org.podcastpedia.common.types.UpdateFrequencyType;
import org.podcastpedia.common.xmladapters.DateAdapter;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;


/**
 * Simple JavaBean domain object representing a podcast. 
 * 
 * @author amasia
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Podcast implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** id of the podcast - primary key in db */
	protected Integer podcastId;	
	
	/** e.g. http://www.podcastpedia.org/<strong>quarks</strong> */
	protected String identifier;

	/** stores the date when new episodes were added to the podcast  */
	protected Date lastUpdate;
	
	/** the rating of this podcast it goes from 1 to 10. 7 means 3 stars and a half.*/
	protected Float rating; 
	
	/** rounded valued needed for displayin star rating */
	private Integer ratingInt;
	
	/** number of ratings for the podcast */
	protected Integer numberRatings;
	
	/** feed url of the podcast - based on this with rome will get further details */ 
	protected String url;
	
	/** date when the podcast is inserted in the database */ 
	protected Date insertionDate;
	
	/** language of the podcast */
	protected String podcastLanguage;
	
	protected Integer numberOfVisitors;
	
	/** placeholder for the tags that are given as input when adding a new podcas */ 
	protected String tagsStr;
	
	/** feed of the podcast obtained with the Rome framework */
	@XmlTransient 
	protected SyndFeed podcastFeed; 
	
	/** media type of the podcast (either audio, video or videoHD) */ 
	protected MediaType mediaType; 
	
	/** the link to the current episode to be displayed in the jwplayer */ 
	protected String episodeLinkToDisplay;
	
	/** index of episode in the entries to display */
	protected int episodeIndexToDisplay;
	
	/** description of the podcast */
	protected String description;
		
	/** title of the podcast */
	protected String title;
	
	/** link of the image that represents the podcast 
	 * - all these three last fields are for performance purposes 
	 */
	protected String urlOfImageToDisplay;
	
	/** the categories the podcast belongs to*/ 
	protected List<Category> categories; 
	
	/** list of category IDs the podcasts belongs to
	 * used when inserting a podcast to map category IDs from list.
	 * TBD - need to implement a mapper to set the IDs directly in the "categories" element
	 * @return
	 */
	protected List<Integer> categoryIDs;
		
	/** number of episodes for the podcast - added the field to make it clearer when 
	 * needing it without getting it over entries.size(). And maybe when using the database data for podcasts
	 * can be automatically updated overnight */
	protected int numberOfEpisodes;
	
	/** list of episodes for the podcast - these are retrieved from the database */
	protected List<Episode> episodes;
	
	/** list of tags for the podcast */
	protected List<Tag> tags;
	
	/** copyright of the podcast */ 
	protected String copyright; 
		
	/** podcast publication date -  contains the date of the last published episode */
	@XmlJavaTypeAdapter(DateAdapter.class)	
	protected Date publicationDate;
	
	/** link of the podcast */
	protected String link; 
	
	/** short description - it is basically the first 320 of the description if it is longer than that */
	protected String shortDescription; 
	
	/** media url of the last episode */
	protected String lastEpisodeMediaUrl;
	
	/** stores the "etag" value in the HTTP header for the podcast feed */
	protected String etagHeaderField;
	
	/** stores the "last modified" in the HTTP header for the podcast feed */
	protected Date lastModifiedHeaderField;
	
	/** same as above just this time is stored as string TODO - in the end one of these has to disappear*/
	protected String lastModifiedHeaderFieldStr;

	/** last episode of the Podcast - used just for generated feeds */
	protected Episode lastEpisode;	
	
	/** holds the title to be displayed in the url "quarks & co" becomes "quarks-co"*/
	protected String titleInUrl;
	
	/** holds the the httpStatus for the podcasts's url  - see org.apache.http.HttpStatus for the codes semnification  and extra exception 
	 * codes and modification */
	private Integer availability;
	
	/** stores in the database the language code - for example Romanian will be stored as "ro" */
	private LanguageCode languageCode;
	
	/**	holds popularity of the podcast - current popularity is ordered by the rating, could include fb likes or number of subscriptions */
	private Float popularity;
	
	/** author of the podcast */
	private String author; 
	
	/** Facebook fan page */
	private String fbPage;
	
	/** twitter fan page */
	private String twitterPage;
	
	/** Gplus fan page */
	private String gplusPage;
			
	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getFbPage() {
		return fbPage;
	}

	public void setFbPage(String fbPage) {
		this.fbPage = fbPage;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public LanguageCode getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(LanguageCode languageCode) {
		this.languageCode = languageCode;
	}

	public Integer getAvailability() {
		return availability;
	}

	public void setAvailability(Integer availability) {
		this.availability = availability;
	}

	/** update frequency */
	protected UpdateFrequencyType updateFrequency;
	
	public UpdateFrequencyType getUpdateFrequency() {
		return updateFrequency;
	}

	public void setUpdateFrequency(UpdateFrequencyType updateFrequency) {
		this.updateFrequency = updateFrequency;
	}

	public String getTagsStr() {
		return tagsStr;
	}

	public void setTagsStr(String tagsStr) {
		this.tagsStr = tagsStr;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getTitleInUrl() {
		return titleInUrl;
	}

	public void setTitleInUrl(String titleInUrl) {
		this.titleInUrl = titleInUrl;
	}

	public Episode getLastEpisode() {
		return lastEpisode;
	}

	public void setLastEpisode(Episode lastEpisode) {
		this.lastEpisode = lastEpisode;
	}

	public String getLastModifiedHeaderFieldStr() {
		return lastModifiedHeaderFieldStr;
	}

	public void setLastModifiedHeaderFieldStr(String lastModifiedHeaderFieldStr) {
		this.lastModifiedHeaderFieldStr = lastModifiedHeaderFieldStr;
	}

	public String getEtagHeaderField() {
		return etagHeaderField;
	}

	public void setEtagHeaderField(String etagHeaderField) {
		this.etagHeaderField = etagHeaderField;
	}

	public Date getLastModifiedHeaderField() {
		return lastModifiedHeaderField;
	}

	public void setLastModifiedHeaderField(Date lastModifiedHeaderField) {
		this.lastModifiedHeaderField = lastModifiedHeaderField;
	}

	public String getLastEpisodeMediaUrl() {
		return lastEpisodeMediaUrl;
	}

	public void setLastEpisodeMediaUrl(String lastEpisodeMediaUrl) {
		this.lastEpisodeMediaUrl = lastEpisodeMediaUrl;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public List<Episode> getEpisodes() {
		return episodes;
	}

	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}

	public int getNumberOfEpisodes() {
		return numberOfEpisodes;
	}

	public void setNumberOfEpisodes(int numberOfEpisodes) {
		this.numberOfEpisodes = numberOfEpisodes;
	}

	public List<Integer> getCategoryIDs() {
		return categoryIDs;
	}

	public void setCategoryIDs(List<Integer> categoryIDs) {
		this.categoryIDs = categoryIDs;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrlOfImageToDisplay() {
		return urlOfImageToDisplay;
	}

	public void setUrlOfImageToDisplay(String urlOfImageToDisplay) {
		this.urlOfImageToDisplay = urlOfImageToDisplay;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getEpisodeIndexToDisplay() {
		return episodeIndexToDisplay;
	}

	public void setEpisodeIndexToDisplay(int episodeIndexToDisplay) {
		this.episodeIndexToDisplay = episodeIndexToDisplay;
	}

	public String getEpisodeLinkToDisplay() {
		return episodeLinkToDisplay;
	}

	public void setEpisodeLinkToDisplay(String episodeLinkToDisplay) {
		this.episodeLinkToDisplay = episodeLinkToDisplay;
	}

	/**
	 * This is an empty constructor
	 */
	public Podcast(){}
	
	/**
	 * Creates the podcastFeed from the url provided 
	 * @param url
	 */
	public Podcast(String url) {
		try{
			URL theURL = new URL(url);
			XmlReader reader = null;
			
			reader = new XmlReader(theURL);
			podcastFeed = new SyndFeedInput().build(reader);
			
		} catch(IOException e){
			
		} catch (FeedException fe){
			
		}

	}
	
	//annotation used so that the podcastFeed field is not considered, as it's an interface and cannot by parsed by JAXB
	public SyndFeed getPodcastFeed() {
		return podcastFeed;
	}

	public void setPodcastFeed(SyndFeed podcastFeed) {
		this.podcastFeed = podcastFeed;
	}

	public Date getInsertionDate() {
		return insertionDate;
	}
	
	public void setInsertionDate(Date insertion_date) {
		this.insertionDate = insertion_date;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int hashCode() {
		return super.hashCode();
	}

	public String getPodcastLanguage() {
		return podcastLanguage;
	}

	public void setPodcastLanguage(String lang) {
		this.podcastLanguage = lang;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date last_update) {
		this.lastUpdate = last_update;
	}

	
	public Integer getPodcastId() {
		return podcastId;
	}

	public void setPodcastId(Integer podcastId) {
		this.podcastId = podcastId;
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

	public Integer getNumberOfVisitors() {
		return numberOfVisitors;
	}

	public void setNumberOfVisitors(Integer numberOfVisitors) {
		this.numberOfVisitors = numberOfVisitors;
	}
	
}
