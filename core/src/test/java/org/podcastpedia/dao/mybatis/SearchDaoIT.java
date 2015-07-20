package org.podcastpedia.dao.mybatis;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.podcastpedia.common.domain.Category;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.types.LanguageCode;
import org.podcastpedia.common.types.OrderByOption;
import org.podcastpedia.common.types.SearchModeType;
import org.podcastpedia.core.categories.CategoryDao;
import org.podcastpedia.core.searching.SearchDao;
import org.podcastpedia.core.searching.SearchData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-spring-context.xml") // the Spring context file
public class SearchDaoIT {
	
	private static Logger LOG = Logger.getLogger(SearchDaoIT.class);
	
	@Autowired
	private SearchDao searchDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Test
	public void testSearchResultsInNaturalLanguageMode() throws Exception {
		LOG.debug(" \n\n------ executing SearchDaoTest.testSearchResultsInNaturalLanguageMode -------");
		LOG.debug("Attempting to search podcasts that containg <quarks> either in title or description in natural language mode");
		SearchData searchCriteria = new SearchData();
		searchCriteria.setQueryText("quarks");
		searchCriteria.setLanguageCode(LanguageCode.de);
		searchCriteria.setSearchMode(SearchModeType.NATURAL_MODE.getValue());
		searchCriteria.setMediaType(null);
		
		List<Podcast> results = searchDao.getPodcastsForSearchCriteria(searchCriteria);	
		LOG.debug("Number of podcasts found for text search <quarks> in natural language mode -  " + results.size());
		assert results.size() == 1;
	}		
	
	@Test
	public void testSearchResultsInBooleanMode() throws Exception {
		LOG.debug(" \n\n------ executing SearchDaoTest.testSearchResultsInBooleanMode -------");
		LOG.debug("Attempting to search podcasts that containing <quarks> either in title or description");
		SearchData searchCriteria = new SearchData();
		searchCriteria.setQueryText("+quarks -noWord");
		searchCriteria.setLanguageCode(LanguageCode.de);
		searchCriteria.setSearchMode(SearchModeType.BOOLEAN_MODE.getValue());
		searchCriteria.setMediaType(null);
		searchCriteria.setOrderBy(OrderByOption.NEW_ENTRIES);
		
		List<Podcast> results = searchDao.getPodcastsForSearchCriteria(searchCriteria);	
		LOG.debug("Number of podcasts found for text search <quarks> in boolean Mode -  " + results.size());
		assert results.size() == 1;		
		
	}		

	@Test
	public void testSearchResultsInBooleanModeWithFalseLanguageCodeSet() throws Exception {
		LOG.debug(" \n\n------ executing SearchDaoTest.testSearchResultsInBooleanModeWithFalseLanguageCodeSet -------");
		LOG.debug("Attempting to search podcasts that containing <quarks> either in title or description with language code set");
		SearchData searchCriteria = new SearchData();
		searchCriteria.setQueryText("+quarks -noWord");
		searchCriteria.setLanguageCode(LanguageCode.fr);
		searchCriteria.setSearchMode(SearchModeType.BOOLEAN_MODE.getValue());
		searchCriteria.setMediaType(null);
		searchCriteria.setOrderBy(OrderByOption.NEW_ENTRIES);
		
		List<Podcast> results = searchDao.getPodcastsForSearchCriteria(searchCriteria);	
		LOG.debug("Number of podcasts found for text search <quarks> in boolean Mode -  " + results.size());
		assert results.size() == 0;		
		
	}
	
	@Test
	public void testSearchResultsWithoutLanguageCodeAndSearchTextSet() throws Exception {
		LOG.debug(" \n\n------ executing SearchDaoTest.testSearchResultsWithoutLanguageCodeAndSearchTextSet -------");
		LOG.debug("Attempting to search podcasts that containing <quarks> either in title or description with language code set");
		SearchData searchCriteria = new SearchData();
		searchCriteria.setSearchMode(SearchModeType.BOOLEAN_MODE.getValue());
		searchCriteria.setMediaType(null);
		searchCriteria.setOrderBy(OrderByOption.POPULARITY);
		
		List<Podcast> results = searchDao.getPodcastsForSearchCriteria(searchCriteria);	
		LOG.debug("Number of podcasts found without search text and language is  " + results.size());
//		assert results.size() == 0;		
		
	}	

	@Test
	public void testSearchResultsWithLanguageCodeAndWithoutSearchTextSet() throws Exception {
		LOG.debug(" \n\n------ executing SearchDaoTest.testSearchResultsWithLanguageCodeAndWithoutSearchTextSet -------");
		LOG.debug("Attempting to search podcasts that containing <quarks> either in title or description with language code set");
		SearchData searchCriteria = new SearchData();
		searchCriteria.setLanguageCode(LanguageCode.de);
		searchCriteria.setSearchMode(SearchModeType.NATURAL_MODE.getValue());
		searchCriteria.setMediaType(null);
		searchCriteria.setOrderBy(OrderByOption.PUBLICATION_DATE);
		searchCriteria.setSearchTarget("episodes");
		
		List<Podcast> results = searchDao.getPodcastsForSearchCriteria(searchCriteria);	
		LOG.debug("Number of podcasts found without search text but with de language set is  " + results.size());
//		assert results.size() == 0;		
		
	}	
	
	@Test
	public void testGetNumberOfResultsInBooleanSearchMode() throws Exception {
		LOG.debug("\n\n");
		LOG.debug(" ------ executing SearchDaoTest.testGetNumberOfResultsInBooleanSearchMode ------- ");
		LOG.debug("Get number of podcasts that are in German Language");
		SearchData searchCriteria = new SearchData();
		searchCriteria.setLanguageCode(LanguageCode.de);
		searchCriteria.setSearchMode(SearchModeType.BOOLEAN_MODE.getValue());
		searchCriteria.setMediaType(null);
		
		int noOfPodcastsInGerman = searchDao.getNumberOfPodcastsForSearchCriteria(searchCriteria);	
		LOG.debug("Number of podcasts in German is " + noOfPodcastsInGerman);
//		assert results.size() == 0;		
		
	}	
	
	@Test
	public void testGetNumberOfResultsInNaturalSearchMode() throws Exception {
		LOG.debug(" \n\n------ executing SearchDaoTest.testGetNumberOfResultsInNaturalSearchMode -------");
		LOG.debug("Get number of podcasts that are in German Language");
		SearchData searchCriteria = new SearchData();
		searchCriteria.setSearchMode(SearchModeType.NATURAL_MODE.getValue());
		searchCriteria.setMediaType(null);
		
		int totalNumberOfPodcasts = searchDao.getNumberOfPodcastsForSearchCriteria(searchCriteria);	
		LOG.debug("Total number of podcasts " + totalNumberOfPodcasts);
//		assert results.size() == 0;		
		
	}
	
	@Test
	public void testGetPodcastsFromCategory() throws Exception {
		LOG.debug(" \n\n------ executing SearchDaoTest.testGetNumberOfPodcastsFromCategory -------");
		LOG.debug("Get number of podcasts that are in category TV&FILM");
		SearchData searchCriteria = new SearchData();
		
		List<Integer> categIds = new ArrayList<Integer>();
		categIds.add(29);
		searchCriteria.setCategId(categIds);
		searchCriteria.setMediaType(null);
		
		int totalNumberOfPodcasts = searchDao.getNumberOfPodcastsForSearchCriteria(searchCriteria);	
		LOG.debug("Total number of podcasts " + totalNumberOfPodcasts);
		assert totalNumberOfPodcasts > 5;		
		
	}	
	
	@Ignore @Test
	public void testGetNumberOfPodcastsWithTag() throws Exception {
		LOG.debug(" \n\n------ executing SearchDaoTest.testGetNumberOfPodcastsFromCategory -------");
		LOG.debug("Get number of podcasts that are in category TV&FILM");
		SearchData searchCriteria = new SearchData();
		
		searchCriteria.setTagId(378);
		searchCriteria.setMediaType(null);
		
		int totalNumberOfPodcasts = searchDao.getNumberOfPodcastsForSearchCriteria(searchCriteria);	
		LOG.debug("Total number of podcasts " + totalNumberOfPodcasts);
		assert totalNumberOfPodcasts > 5;		
		
	}
	
	@Ignore @Test
	public void testGetPodcastsWithTag() throws Exception {
		LOG.debug(" \n\n------ executing SearchDaoTest.testGetNumberOfPodcastsFromCategory -------");
		LOG.debug("Get number of podcasts that are in category TV&FILM");
		SearchData searchCriteria = new SearchData();
		
		searchCriteria.setTagId(378);//DW 
		searchCriteria.setMediaType(null);
		
		List<Podcast> podcasts = searchDao.getPodcastsForSearchCriteria(searchCriteria);
		LOG.debug("Total number of podcasts " + podcasts.size());
		
		assert podcasts.size() > 5;		
		
	}	
	
	@Ignore @Test
	public void testGetLimitedNumberOfPodcasts() throws Exception {
		LOG.debug(" \n\n------ executing SearchDaoTest.testGetLimitedNumberOfPodcasts -------");
		LOG.debug("Get [10-19] podcasts that are in German Language and are recorded between 10 and 19 in the database");
		SearchData searchCriteria = new SearchData();
		searchCriteria.setFirstItemOnPage(0);
		searchCriteria.setLanguageCode(LanguageCode.de);
		searchCriteria.setNumberResultsPerPage(10);
		searchCriteria.setSearchMode(SearchModeType.NATURAL_MODE.getValue());
		searchCriteria.setMediaType(null);
		
		List<Podcast> results = searchDao.getPodcastsForSearchCriteria(searchCriteria);
		for(Podcast i : results){
			LOG.debug("Podcast : " + i.getTitle());
		}		
		assert results.size() == 10;		
		
	}
	
	
//	@Test
	public void testGetPodcastsWithCategory() throws Exception {
		LOG.debug(" \n\n------ executing SearchDaoTest.testGetPodcastsFromCategory -------");
		Category category = categoryDao.getCategoryByName("TV & Film");
		
		SearchData searchCriteria = new SearchData();
		List<Integer> podcastCategIds = new ArrayList<Integer>();
		podcastCategIds.add(category.getCategoryId());
		searchCriteria.setCategId(podcastCategIds);
		searchCriteria.setSearchMode(SearchModeType.NATURAL_MODE.getValue());
		searchCriteria.setMediaType(null);
		
		List<Podcast> results = searchDao.getPodcastsForSearchCriteria(searchCriteria);
		for(Podcast i : results){
			LOG.debug("Podcast : " + i.getTitle());
		}		
		assert results.size() == 4;		
		
	}	
	
	@Test
	public void testGetNumberOfEpisodes() throws Exception {
		LOG.debug(" \n\n------ executing SearchDaoTest.testGetNumberOfEpisodesInNaturalSearchMode -------");
		LOG.debug("Get number of episodes that are in romanian language are in category TV&Film and contain mircea");
		SearchData searchCriteria = new SearchData();
		searchCriteria.setQueryText("mircea");
		searchCriteria.setSearchMode(SearchModeType.NATURAL_MODE.getValue()); 
		searchCriteria.setLanguageCode(LanguageCode.de); 
		searchCriteria.setMediaType(null);
		
		int totalNumberOfEpisodes = searchDao.getNumberOfEpisodesForSearchCriteria(searchCriteria);	
		LOG.debug("Total number of episodes that contain \"mircea\" is " + totalNumberOfEpisodes);
		
//		assert totalNumberOfEpisodes == 25;		
		
	}	
	
	@Test
	public void testGetNumberOfEpisodes2() throws Exception {
		LOG.debug(" \n\n------ executing SearchDaoTest.testGetNumberOfEpisodes2 -------");
		LOG.debug("Get number of episodes that are in romanian language are in category TV&Film and contain mircea");
		SearchData searchCriteria = new SearchData();
		searchCriteria.setQueryText("cucuvea");
		searchCriteria.setSearchMode(SearchModeType.NATURAL_MODE.getValue()); 
		searchCriteria.setLanguageCode(LanguageCode.de); 
		searchCriteria.setMediaType(null);
		
		int totalNumberOfEpisodes = searchDao.getNumberOfEpisodesForSearchCriteria(searchCriteria);	
		LOG.debug("Total number of episodes that contain \"mircea\" is " + totalNumberOfEpisodes);
		
		assert totalNumberOfEpisodes == 0;		
		
	}
	
	@Test
	public void testGetNumberOfEpisodes3() throws Exception {
		LOG.debug(" \n\n------ executing SearchDaoTest.testGetNumberOfEpisodes3 -------");
		LOG.debug("Get number of episodes that are in romanian language are in category Science and contain mircea");
		
		SearchData searchCriteria = new SearchData();
		
		Category category = categoryDao.getCategoryByName("science_technology");
		
		List<Integer> podcastCategIds = new ArrayList<Integer>();
		podcastCategIds.add(category.getCategoryId());
		searchCriteria.setCategId(podcastCategIds);
		
		
		searchCriteria.setQueryText("mircea");
		searchCriteria.setSearchMode(SearchModeType.NATURAL_MODE.getValue()); 
		searchCriteria.setLanguageCode(LanguageCode.ro); 
		searchCriteria.setMediaType(null);
		
		int totalNumberOfEpisodes = searchDao.getNumberOfEpisodesForSearchCriteria(searchCriteria);	
		LOG.debug("Total number of episodes that contain \"mircea\" is " + totalNumberOfEpisodes);
		
		//there no mircea words in science 
		assert totalNumberOfEpisodes == 0;		
		
	}	
	
	@Test
	public void testGetEpisodesWithMirceaAsTextSearch() throws Exception {
		LOG.debug("\n\n");
		LOG.debug(" ------ executing SearchDaoTest.testGetEpisodesWithMirceaAsTextSearch -------");
		LOG.debug("Get number of episodes that are in romanian language are in category TV&Film and contain mircea");
		SearchData searchCriteria = new SearchData();
		searchCriteria.setQueryText("mircea");
		searchCriteria.setSearchMode(SearchModeType.BOOLEAN_MODE.getValue()); 
		searchCriteria.setLanguageCode(LanguageCode.ro); 
		searchCriteria.setMediaType(null);
		
		List<Episode>  episodes = searchDao.getEpisodesForSearchCriteria(searchCriteria);	
		LOG.debug("Total number of episodes that contain \"mircea\" is " + episodes.size());
		
//		assert episodes.size() == 25;		
		
	}	
}
