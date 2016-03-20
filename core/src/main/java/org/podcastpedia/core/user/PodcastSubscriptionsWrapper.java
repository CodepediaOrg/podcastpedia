package org.podcastpedia.core.user;

import org.podcastpedia.common.domain.Podcast;

import java.util.List;

/**
 * Created by ama on 06/12/15.
 */
public class PodcastSubscriptionsWrapper {

    List<Podcast> subscriptions;
    List<String> subscriptionCategories;

    public List<Podcast> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Podcast> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<String> getSubscriptionCategories() {
        return subscriptionCategories;
    }

    public void setSubscriptionCategories(List<String> subscriptionCategories) {
        this.subscriptionCategories = subscriptionCategories;
    }
}
