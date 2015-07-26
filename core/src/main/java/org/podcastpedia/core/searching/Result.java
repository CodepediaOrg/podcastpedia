package org.podcastpedia.core.searching;

import org.podcastpedia.common.types.MediaType;

import java.util.Date;

/**
 * Class contains data to be displayed when doing searches. It acts like a placeholder to simplify search results handling
 * on the front end.
 * The data comes either from episodes or podcasts, depending on the search target.
 */
public class Result {

    /** title of podcast or episode found*/
    private String title;

    /** publication date of Episode or last publication date of */
    private Date publicationDate;

    /** podcast or episode description */
    private String description;

    /** link of audio/video to be played of the episode or last episode if podcast */
    private String mediaUrl;

    /** relative link of episode/podcast on Podcastpedia.org - can be used for sharing on social media */
    private String relativeLink;

    /** this is only valid for episodes */
    private boolean isNew;

    /** media type of the podcast (either audio, video or videoHD) */
    private MediaType mediaType;

    /** flag signalizing whether the result is an episode or a podcast */
    private boolean isEpisode;

    /** attributes relevant only to search rss/atom feeds */
    private int podcastId;
    private int episodeId;
    private Long length;
    private String enclosureType;


    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getRelativeLink() {
        return this.relativeLink;
    }

    public void setRelativeLink(String relativeLink) {
        this.relativeLink = relativeLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public boolean getIsEpisode() {
        return this.isEpisode;
    }

    public void setIsEpisode(boolean isEpisode) {
        this.isEpisode = isEpisode;
    }

    public int getPodcastId() {
        return this.podcastId;
    }

    public void setPodcastId(int podcastId) {
        this.podcastId = podcastId;
    }

    public int getEpisodeId() {
        return this.episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    public Long getLength() {
        return this.length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public String getEnclosureType() {
        return this.enclosureType;
    }

    public void setEnclosureType(String enclosureType) {
        this.enclosureType = enclosureType;
    }
}
