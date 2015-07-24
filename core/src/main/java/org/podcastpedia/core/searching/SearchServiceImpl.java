package org.podcastpedia.core.searching;

import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.types.OrderByOption;
import org.podcastpedia.common.types.SearchModeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SearchServiceImpl implements SearchService {

	protected static Logger LOG = Logger.getLogger(SearchServiceImpl.class);

	@Autowired
	private SearchDao searchDao;

	public void setSearchDao(SearchDao searchDao) {
		this.searchDao = searchDao;
	}

	/** list of international tex for "return all" - used in quick search bar **/
	private static final String[] textGetAllPodcastsI18N = {"Search...", "Căutare...",
			"Buscar...", "Suchen...", "Rechercher..."};

	@Cacheable(value="searchResults")
	public SearchResult getResultsForSearchCriteria(SearchData searchData) throws UnsupportedEncodingException {

		SearchResult response = new SearchResult();
		if(searchData.getCategId()!=null && searchData.getCategId().isEmpty()){
			searchData.setCategId(null);
		}
		searchData.setFirstItemOnPage((searchData.getCurrentPage()-1) * searchData.getNumberResultsPerPage());

		LOG.debug("Received request in service to invoke dao to make a search in podcasts " +
				" for text : " + searchData.getQueryText());

		buildSQLQuerySearchText(searchData);

		response.setNumberOfItemsPerPage(searchData.getNumberResultsPerPage());
		response.setCurrentPage(searchData.getCurrentPage());

		if( isTargetPodcasts(searchData) ){
            List<Podcast> podcasts = searchDao.getPodcastsForSearchCriteria(searchData);
            response.setResults(mapPodcastsToResults(podcasts));
            enrichResponseWithPodcasts(searchData, response);
		} else {
            List<Episode> episodes = searchDao.getEpisodesForSearchCriteria(searchData);
            response.setResults(mapEpisodesToResults(episodes));
            //if not podcasts it must be episodes then, and episodes is also the default search target
			enrichResponseWithEpisodes(searchData, response);
		}

		return response;
	}

    private List<Result> mapPodcastsToResults(List<Podcast> podcasts) {
        List<Result> results = new ArrayList<>();
        for(Podcast podcast : podcasts){
            Result result = new Result();
            result.setTitle(podcast.getTitle());
            result.setPublicationDate(podcast.getPublicationDate());
            result.setMediaType(podcast.getMediaType());
            result.setDescription(podcast.getDescription());
            result.setMediaUrl(podcast.getLastEpisodeMediaUrl());
            if(podcast.getIdentifier()!=null){
                result.setLink("http://www.podcastpedia.org/" + podcast.getIdentifier());
            } else {
                result.setLink("http://www.podcastpedia.org/" + podcast.getPodcastId() + "/" + podcast.getTitleInUrl());
            }

            results.add(result);
        }

        return results;
    }

    private List<Result> mapEpisodesToResults(List<Episode> episodes) {
        List<Result> results = new ArrayList<>();
        for(Episode episode : episodes){
            Result result = new Result();
            result.setTitle(episode.getTitle());
            result.setPublicationDate(episode.getPublicationDate());
            result.setMediaType(episode.getMediaType());
            StringBuilder episodeLink = new StringBuilder("http://www.podcastpedia.org/");
            episodeLink.append("/podcasts").append(episode.getPodcastId()).append("/").append(episode.getPodcast().getTitleInUrl());
            episodeLink.append("/episodes/").append(episode.getEpisodeId()).append("/").append(episode.getTitleInUrl());
            result.setLink(episodeLink.toString());
            result.setDescription(episode.getDescription());
            result.setMediaUrl(episode.getMediaUrl());

            results.add(result);
        }

        return results;
    }


    private boolean isTargetPodcasts(SearchData searchData) {
		return searchData.getSearchTarget() !=null && searchData.getSearchTarget().equals("podcasts");
	}

	private void enrichResponseWithEpisodes(SearchData searchData,
			SearchResult response) {
		//first get the number of results for search criteria - this is used for pagination
		if(searchData.getNrOfResults() == null){
			int numberOfEpisodesFound = searchDao.getNumberOfEpisodesForSearchCriteria(searchData);
			response.setNumberOfItemsFound(numberOfEpisodesFound);

			int mod = numberOfEpisodesFound % searchData.getNumberResultsPerPage();
			int dividedBy = numberOfEpisodesFound / searchData.getNumberResultsPerPage();
			int numberOfPages = mod > 0 ? dividedBy +1 : dividedBy;
			// Casting to an int implicitly drops any decimal part. No need to call Math.floor().
			response.setNumberOfPages(numberOfPages);
		} else {
			response.setNumberOfPages(searchData.getNrResultPages());
			response.setNumberOfItemsFound(searchData.getNrResultPages());
		}

		List<Episode> episodes = null;
		episodes = searchDao.getEpisodesForSearchCriteria(searchData);
		response.setEpisodes(episodes);

	}

	private void enrichResponseWithPodcasts(SearchData searchData, SearchResult response) {
		if(searchData.getNrOfResults()  == null){
			int numberOfPodcastsFound = searchDao.getNumberOfPodcastsForSearchCriteria(searchData);
			response.setNumberOfItemsFound(numberOfPodcastsFound);
			// Casting to an int implicitly drops any decimal part. No need to call Math.floor().
			int mod = numberOfPodcastsFound%searchData.getNumberResultsPerPage();
			int dividedBy = numberOfPodcastsFound/searchData.getNumberResultsPerPage();
			int numberOfPages = mod > 0 ? dividedBy +1 : dividedBy;
			response.setNumberOfPages(numberOfPages);
		} else {
			response.setNumberOfPages(searchData.getNrResultPages());
			response.setNumberOfItemsFound(searchData.getNrOfResults());
		}

		List<Podcast> podcasts = null;
		podcasts = searchDao.getPodcastsForSearchCriteria(searchData);
		response.setPodcasts(podcasts);
	}

	private void buildSQLQuerySearchText(SearchData searchData){
		if(searchData.getSearchMode().equals(SearchModeType.NATURAL_MODE.getValue())) {
			//if it is the default "return all podcasts" as query text than all podcasts will be returned
			//QueryText can be null when the request is coming from quick search on the start page
			if(isDefaultSearchText(searchData)) {
				searchData.setQueryText(null);
				//if no order by is selected in this case
				if(searchData.getOrderBy() == null){
					//this cannot happen from quick search filters from start page
					searchData.setOrderBy(OrderByOption.PUBLICATION_DATE);
				}
			} else {
				//if some words are introduced then we should decide the full text indexing to bring the most relevant in front
				searchData.setOrderBy(null);
			}
			// else it means the user has given some input text for natural mode search
		}

        //in boolean search mode (see - https://dev.mysql.com/doc/refman/5.6/en/fulltext-boolean.html)
        // the corresponding operators are provided by the client of the api
		if(searchData.getSearchMode().equals(SearchModeType.BOOLEAN_MODE.getValue())){

			StringBuilder queryTextBuffer = new StringBuilder();

			//start with the all of these words part
			if(searchData.getAllTheseWords()!=null
					&& !searchData.getAllTheseWords().trim().equals("")){
				String[] allWords = searchData.getAllTheseWords().split(" ");
				for(String s: allWords){
					queryTextBuffer.append(" ").append(s);
				}
			}
			// none of these words
			if(searchData.getNoneOfTheseWords()!=null
					&& !searchData.getNoneOfTheseWords().trim().equals("")){
				String[] noneOfWords = searchData.getNoneOfTheseWords().split(" ");
				for(String s: noneOfWords){
					queryTextBuffer.append(" ").append(s);
				}
			}
			//any of these words - is simply just added
			if(searchData.getAnyOfTheseWords()!=null
					&& !searchData.getAnyOfTheseWords().trim().equals("")){
				queryTextBuffer.append(" ").append(searchData.getAnyOfTheseWords());
			}
			//exact phrase
			if(searchData.getExactPhrase()!=null
					&& !searchData.getExactPhrase().trim().equals("")){
                //the client will provided only the exact phrase between quotes, and we use the plus sign (+) to force its existence
				queryTextBuffer.append(" +").append(searchData.getExactPhrase());
			}

			//set the queryText
			searchData.setQueryText(queryTextBuffer.toString());
		}
	}

	private boolean isDefaultSearchText(SearchData searchData) {
		return searchData.getQueryText() == null || Arrays.asList(textGetAllPodcastsI18N).contains(searchData.getQueryText().trim());
	}


}
