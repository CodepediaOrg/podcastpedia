package org.podcastpedia.web.feeds;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Enclosure;
import com.rometools.rome.feed.rss.Item;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.core.searching.Result;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ResultsRssFeedView extends AbstractRssFeedView {

    protected void buildFeedMetadata(Map model, Channel feed, HttpServletRequest request) {
        feed.setTitle("" + model.get("feed_title"));
        feed.setDescription(" " + model.get("feed_description"));
        feed.setLink("" + model.get("feed_link"));
    }

    protected List buildFeedItems(Map model, HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        List<Result> results = (List<Result>) model.get("list_of_results");
        List<Item> items = new ArrayList<Item>(results.size());

        for (Result result : results) {
            Item item = new Item();
            String date = String.format("%1$tY-%1$tm-%1$td", result.getPublicationDate());
            item.setTitle(result.getTitle());
            item.setPubDate(result.getPublicationDate());
            item.setLink(model.get("HOST_AND_PORT_URL") + result.getRelativeLink());

            Description episodeDescription = new Description();
            episodeDescription.setValue(result.getDescription());
            item.setDescription(episodeDescription);

            //set enclosures
            List<Enclosure> enclosures = new ArrayList<Enclosure>();
            Enclosure enclosure = new Enclosure();
            enclosure.setUrl(result.getMediaUrl());
            if(result.getLength() != null) enclosure.setLength(result.getLength());
            if(result.getEnclosureType() != null ) enclosure.setType(result.getEnclosureType());
            enclosures.add(enclosure);
            item.setEnclosures(enclosures);

            items.add(item);
        }

        return items;
    }
}
