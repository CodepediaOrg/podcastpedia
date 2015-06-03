package org.podcastpedia.web.feeds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Link;



public class GenericAtomFeedView extends AbstractAtomFeedView {
	
    protected void buildFeedMetadata(Map model, Feed feed, HttpServletRequest request) {
        feed.setId("" + model.get("feed_id"));
        feed.setTitle("" + model.get("feed_title"));
        Content subTitle = new Content();
        subTitle.setValue("" + model.get("feed_description"));
        feed.setSubtitle(subTitle);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	protected List buildFeedEntries(Map model, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
    	
        List<Podcast> podcasts = (List<Podcast>) model.get("list_of_podcasts");
        List<Entry> entries = new ArrayList<Entry>(podcasts.size());

        for (Podcast podcast : podcasts) {
        	Episode episode = podcast.getLastEpisode();
            Entry entry = new Entry();
//            String date = String.format("%1$tY-%1$tm-%1$td", new Date());
//            entry.setId(String.format("tag:podcatpedia.org,%s:%d", date, podcast.getPodcastId()));
            entry.setId("tag:podcastpedia.org,2013-04-20:podcastId");            
            entry.setTitle(podcast.getLastEpisode().getTitle());
            entry.setUpdated(podcast.getPublicationDate());
            Content summary = new Content();
            summary.setValue(podcast.getLastEpisode().getDescription());
            entry.setSummary(summary);
            
            List<Link> links = new ArrayList<Link>();
            Link link = new Link();
            link.setRel("enclosure");
            link.setHref(episode.getMediaUrl());
            if(episode.getLength() != null ) link.setLength(episode.getLength());
            if(episode.getEnclosureType() != null ) link.setType(episode.getEnclosureType());
            links.add(link);
            
            link = new Link();
            link.setRel("via");
            link.setHref(model.get("HOST_AND_PORT_URL") + "/podcasts/" + podcast.getPodcastId() 
            		+ "/" + podcast.getTitleInUrl());
            links.add(link);
            
            link = new Link();
            link.setRel("alternate");
            link.setHref(model.get("HOST_AND_PORT_URL") + "/podcasts/" + podcast.getPodcastId() 
            		+ "/" + podcast.getTitleInUrl());
            links.add(link);             
            
            entry.setOtherLinks(links);
            
            entries.add(entry);
        }

        return entries;
    }
}
