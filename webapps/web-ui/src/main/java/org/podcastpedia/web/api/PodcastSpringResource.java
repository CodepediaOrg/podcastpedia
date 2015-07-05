package org.podcastpedia.web.api;

import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.exception.BusinessException;
import org.podcastpedia.web.episodes.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ama on 04.07.2015.
 */
@Controller
@RequestMapping("/api/podcasts")
public class PodcastSpringResource {

    @Autowired
    private EpisodeService episodeService;

    @RequestMapping(value = "{podcastId}/episodes", method = RequestMethod.GET)
    public @ResponseBody List<Episode> getRecentEpisodes(@PathVariable("podcastId") Integer podcastId, @RequestParam("offset")Integer offset, @RequestParam("count")Integer count) throws BusinessException {
        List<Episode> podcastEpisodes = episodeService.getEpisodesForPodcast(podcastId, offset, count);

        return podcastEpisodes;
    }
}
