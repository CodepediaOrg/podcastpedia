package org.podcastpedia.web.feeds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.podcastpedia.common.domain.Episode;
import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Link;



public class FoundEpisodesAtomFeedView extends AbstractAtomFeedView {
	
    protected void buildFeedMetadata(Map model, Feed feed, HttpServletRequest request) {
        feed.setId("" + model.get("feed_id"));
        feed.setTitle("" + model.get("feed_title"));
        Content subTitle = new Content();
        subTitle.setValue("" + model.get("feed_description"));
        feed.setSubtitle(subTitle);
    }

    protected List buildFeedEntries(Map model, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
    	
        List<Episode> episodes = (List<Episode>) model.get("list_of_episodes");
        List<Entry> entries = new ArrayList<Entry>(episodes.size());

        for (Episode episode : episodes) {
            Entry entry = new Entry();
//            String date = String.format("%1$tY-%1$tm-%1$td", new Date());
//            entry.setId(String.format("tag:podcastpedia.org,%s:%d", date, episode.getEpisodeId()));
            entry.setId("tag:podcastpedia.org,2013-04-20:podcastId-" + episode.getPodcastId() + "-episodeId-" + episode.getEpisodeId());            
            entry.setTitle(episode.getTitle());
            entry.setUpdated(episode.getPublicationDate());
            Content summary = new Content();
            summary.setValue(episode.getDescription());
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
            link.setHref( model.get("HOST_AND_PORT_URL") + "/podcasts/" + episode.getPodcastId() 
            		+ "/" + episode.getPodcast().getTitleInUrl()+ "/episodes/" + episode.getEpisodeId()
            		+ "/" + episode.getTitleInUrl());
            links.add(link);            
            
            entry.setOtherLinks(links);
            
            entries.add(entry);
        }

        return entries;
    }
}
