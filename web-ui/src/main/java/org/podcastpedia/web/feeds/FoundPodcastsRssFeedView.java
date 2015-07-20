package org.podcastpedia.web.feeds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.podcastpedia.common.domain.Podcast;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;



public class FoundPodcastsRssFeedView extends AbstractRssFeedView {
	
    protected void buildFeedMetadata(Map model, Channel feed, HttpServletRequest request) {
        feed.setTitle("" + model.get("feed_title"));
        feed.setDescription(" " + model.get("feed_description"));
        feed.setLink("" + model.get("feed_link"));
    }

    protected List buildFeedItems(Map model, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
    	
        List<Podcast> podcasts = (List<Podcast>) model.get("list_of_podcasts");
        List<Item> items = new ArrayList<Item>(podcasts.size());

        for (Podcast podcast : podcasts) {
            Item item = new Item();
//            String date = String.format("%1$tY-%1$tm-%1$td", podcast.getLastEpisode().getPublicationDate());
            item.setTitle(podcast.getTitle());
            item.setPubDate(podcast.getPublicationDate());
            item.setLink(model.get("HOST_AND_PORT_URL") + "/podcasts/" + podcast.getPodcastId() 
            		+ "/" + podcast.getTitleInUrl());
            
            Description podcastDescription = new Description();
            podcastDescription.setValue(podcast.getDescription());
            item.setDescription(podcastDescription);  
                        
            items.add(item);
        }

        return items;
    }
}
