package org.podcastpedia.web.feeds;

import com.rometools.rome.feed.atom.Content;
import com.rometools.rome.feed.atom.Entry;
import com.rometools.rome.feed.atom.Feed;
import com.rometools.rome.feed.atom.Link;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.core.searching.Result;
import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ResultsAtomFeedView extends AbstractAtomFeedView {

    protected void buildFeedMetadata(Map model, Feed feed, HttpServletRequest request) {
        feed.setId("" + model.get("feed_id"));
        feed.setTitle("" + model.get("feed_title"));
        Content subTitle = new Content();
        subTitle.setValue("" + model.get("feed_description"));
        feed.setSubtitle(subTitle);
    }

    protected List buildFeedEntries(Map model, HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        List<Result> results = (List<Result>) model.get("list_of_results");
        List<Entry> entries = new ArrayList<Entry>(results.size());

        for (Result result : results) {
            Entry entry = new Entry();
//            String date = String.format("%1$tY-%1$tm-%1$td", new Date());
//            entry.setId(String.format("tags:podcastpedia.org,%s:%d", date, episode.getEpisodeId()));
            entry.setId("tags:podcastpedia.org,2013-04-20:podcastId-" + result.getPodcastId() + "-episodeId-" + result.getEpisodeId());
            entry.setTitle(result.getTitle());
            entry.setUpdated(result.getPublicationDate());
            Content summary = new Content();
            summary.setValue(result.getDescription());
            entry.setSummary(summary);

            List<Link> links = new ArrayList<Link>();
            Link link = new Link();
            link.setRel("enclosure");
            link.setHref(result.getMediaUrl());
            if(result.getLength() != null ) link.setLength(result.getLength());
            if(result.getEnclosureType() != null ) link.setType(result.getEnclosureType());
            links.add(link);

            link = new Link();
            link.setRel("via");
            link.setHref( model.get("HOST_AND_PORT_URL") + result.getRelativeLink());
            links.add(link);

            entry.setOtherLinks(links);

            entries.add(entry);
        }

        return entries;
    }
}
