package org.podcastpedia.web.startpage;

import java.util.List;

import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.types.LanguageCode;
import org.springframework.stereotype.Service;

/**
 * Service interface used to provide elements used mainly in the start page -
 * intention is that those are kept forever in memory
 * 
 * @author amasia
 * 
 */
@Service("startPageService")
public interface StartPageService {

	/**
	 * Returns top rated podcasts (ORDER BY rating DESC)
	 * 
	 * 
	 * @param numberOfPodcasts
	 *            (number of podcasts to be returned)
	 * @return
	 */
	public List<Podcast> getTopRatedPodcasts(Integer numberOfPodcasts);

	/**
	 * Returns top rated podcasts (ORDER BY rating DESC) - 10 such podcasts
	 * 
	 * 
	 * @param languageCode
	 *            (code of the language of the shown podcasts)
	 * @return
	 */
	public List<Podcast> getTopRatedPodcastsWithLanguage(
			LanguageCode languageCode, int numberOfResults);

	/**
	 * Returns recommended podcasts for chart.
	 * 
	 * @return
	 */
	public List<Podcast> getRandomPodcasts(Integer numberOfPodcasts);

	/**
	 * Returns recommended podcasts for chart.
	 * 
	 * @return
	 */
	public List<Podcast> getRecommendedPodcasts();

	public List<Podcast> getLastUpdatedPodcasts();

	/**
	 * Returns a list with newest podcasts from the given language
	 * 
	 * @param languageCode
	 * @return
	 */
	public List<Podcast> getLastUpdatedPodcasts(LanguageCode languageCode);

	public List<Podcast> getNewEntries();

}
