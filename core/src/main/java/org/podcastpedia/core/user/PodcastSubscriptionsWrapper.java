package org.podcastpedia.core.user;

import org.podcastpedia.common.domain.Podcast;

import java.util.List;

/**
 * Created by ama on 06/12/15.
 */
public class PodcastSubscriptionsWrapper {

    List<Podcast> subscriptions;
    List<String> playlists;

    public List<Podcast> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Podcast> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<String> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<String> playlists) {
        this.playlists = playlists;
    }
}
