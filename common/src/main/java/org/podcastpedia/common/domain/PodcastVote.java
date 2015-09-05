package org.podcastpedia.common.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by matad on 21.08.15.
 */
public class PodcastVote implements Serializable {

    /** the user identified by username who gives the value */
    private String username;

    /** id of the podcast the value has been given for */
    private Integer podcastId;

    /** holds 1 for "like" and -1 for "dislike */
    private Integer vote;

    /** the date when the value was given */
    private Date createdOn;

    public Integer getPodcastId() {
        return this.podcastId;
    }

    public void setPodcastId(Integer podcastId) {
        this.podcastId = podcastId;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer vote) {
        this.vote = vote;
    }

    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
