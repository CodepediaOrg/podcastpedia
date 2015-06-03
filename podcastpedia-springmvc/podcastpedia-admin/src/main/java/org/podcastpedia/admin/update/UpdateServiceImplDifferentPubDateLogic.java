package org.podcastpedia.admin.update;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.podcastpedia.admin.dao.DeleteDao;
import org.podcastpedia.admin.dao.InsertDao;
import org.podcastpedia.admin.dao.ReadDao;
import org.podcastpedia.admin.dao.UpdateDao;
import org.podcastpedia.admin.dao.helper.InputMarkNewEpisodesAsNew;
import org.podcastpedia.admin.util.PodcastAndEpisodeAttributesService;
import org.podcastpedia.admin.util.SyndFeedService;
import org.podcastpedia.admin.util.read.ReadService;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.domain.PodcastCategory;
import org.podcastpedia.common.domain.PodcastTag;
import org.podcastpedia.common.domain.Tag;
import org.podcastpedia.common.exception.BusinessException;
import org.podcastpedia.common.types.HttpStatusExtensionType;
import org.podcastpedia.common.types.UpdateFrequencyType;
import org.podcastpedia.common.util.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;

import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;

/**
 * Holding on to this just for different update logic, or new ideas when reimplementing...
 * @author ama
 *
 */
public class UpdateServiceImplDifferentPubDateLogic {
//	public class UpdateServiceImplDifferentPubDateLogic implements UpdateService {	
//
//	/** default number of threads to work on actualizing podcastws */
//	private static final Integer DEFAULT_NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS = 10;
//
//	private static Logger LOG = Logger
//			.getLogger(UpdateServiceImplDifferentPubDateLogic.class);
//
//	@Autowired
//	private DeleteDao deleteDao;
//
//	@Autowired
//	private ReadDao readDao;
//
//	@Autowired
//	private InsertDao insertDao;
//
//	@Autowired
//	private UpdateDao updateDao;
//
//	@Autowired
//	private ReadService readService;
//
//	@Autowired
//	private PodcastAndEpisodeAttributesService podcastAndEpisodeAttributesService;
//
//	@Autowired
//	SyndFeedService syndFeedService;
//
//	@Autowired
//	private ConfigBean configBean;
//
//	@Autowired
//	private PoolingClientConnectionManager poolingClientConnectionManager;
//
//	@Transactional
//	public void updatePodcastCategories(Integer podcastId,
//			List<Integer> categoryIDs) {
//
//		// delete current podcast_categories and add the new ones
//		deleteDao.deletePodcastCategoriesByPodcastId(podcastId);
//		// insert records in intersection table
//		PodcastCategory podcastCategory = new PodcastCategory();
//		podcastCategory.setPodcastId(podcastId);
//		for (Integer i : categoryIDs) {
//			podcastCategory.setCategoryId(i);
//			insertDao.insertPodcastCategory(podcastCategory);
//		}
//	}
//
//	@Transactional
//	public void updatePodcastTags(Integer podcastId, String tagsString) {
//		deleteDao.deletePodcastTagsByPodcastId(podcastId);
//
//		// split the tags-string in the different tags
//		String[] tags = tagsString.split(",");
//
//		for (String tagStr : tags) {
//			Tag tag = readDao.getTagByName(tagStr.trim());
//			if (null != tag) {
//				insertDao.insertPodcastTag(new PodcastTag(podcastId, tag
//						.getTagId()));
//			} else {
//				tag = new Tag();
//				tag.setName(tagStr.trim());
//				insertDao.insertTag(tag);
//				insertDao.insertPodcastTag(new PodcastTag(podcastId, tag
//						.getTagId()));
//			}
//		}
//	}
//
//	/**
//	 * all podcasts will be updated - episodes that are not reachable will be
//	 * removed, new episodes are added and podcast feed attributes are updated
//	 */
//	public void updateAllPodcasts() {
//
//		Integer numberOfWorkerThreads = Integer.valueOf(configBean
//				.get("NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS"));
//		// set default value if data not available inthe database
//		if (numberOfWorkerThreads == null) {
//			numberOfWorkerThreads = DEFAULT_NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS;
//		}
//
//		Integer totalNumberOfPodcasts = readDao.getNumberOfPodcasts();
//
//		Integer mod = totalNumberOfPodcasts % numberOfWorkerThreads;
//		Integer chunkSize = totalNumberOfPodcasts / numberOfWorkerThreads;
//
//		for (int i = 0; i < numberOfWorkerThreads; i++) {
//			int startRow = i * chunkSize;
//			if (i == numberOfWorkerThreads - 1) {
//				chunkSize = chunkSize + mod;
//				LOG.debug("Thread[" + i + "] - [" + startRow + "," + chunkSize
//						+ "]");
//				this.updatePodcastsFromRange(startRow, chunkSize);
//			} else {
//				LOG.debug("Thread[" + i + "] - [" + startRow + "," + chunkSize
//						+ "]");
//				this.updatePodcastsFromRange(startRow, chunkSize);
//			}
//		}
//	}
//
//	public void updatePodcastsWithFrequency(
//			UpdateFrequencyType updateFrequency, Integer numberOfWorkerThreads) {
//
//		LOG.debug("Executing updatePodcastsWithFrequency with update frequency "
//				+ updateFrequency);
//
//		Integer podcastsUpdateFrequencyCode = updateFrequency != null ? updateFrequency
//				.getCode() : null;
//
//		// if the input parameter is missing than we get it from the database
//		if (podcastsUpdateFrequencyCode == null) {
//			Integer.valueOf(configBean.get("PODCAST_FREQUENCY_TYPE_TO_UPDATE"));
//		}
//
//		Integer totalNumberOfPodcasts = null;
//
//		totalNumberOfPodcasts = readDao
//				.getNumberOfPodcastsWithUpdateFrequency(podcastsUpdateFrequencyCode);
//
//		if (numberOfWorkerThreads == null) {
//			numberOfWorkerThreads = Integer.valueOf(configBean
//					.get("NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS"));
//		}
//		// set default value if data not available inthe database
//		if (numberOfWorkerThreads == null) {
//			numberOfWorkerThreads = DEFAULT_NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS;
//		}
//
//		Integer mod = totalNumberOfPodcasts % numberOfWorkerThreads;
//		Integer chunkSize = totalNumberOfPodcasts / numberOfWorkerThreads;
//
//		for (int i = 0; i < numberOfWorkerThreads; i++) {
//			int startRow = i * chunkSize;
//			if (i == numberOfWorkerThreads - 1) {
//				chunkSize = chunkSize + mod;
//				LOG.debug("Thread[" + i + "] - [" + startRow + "," + chunkSize
//						+ "]");
//				this.updatePodcastsFromRange(startRow, chunkSize,
//						podcastsUpdateFrequencyCode);
//			} else {
//				LOG.debug("Thread[" + i + "] - [" + startRow + "," + chunkSize
//						+ "]");
//				this.updatePodcastsFromRange(startRow, chunkSize,
//						podcastsUpdateFrequencyCode);
//			}
//		}
//	}
//
//	/**
//	 * Entry point method to update the podcast
//	 * 
//	 * @throws FeedException
//	 * @throws IllegalArgumentException
//	 * @throws IOException
//	 */
//	// @Transactional TODO - uncomment this when migrate to 5.6 innodb and
//	// lucene for search
//	public void updatePodcastById(Podcast podcast, Boolean isCalledManually,
//			boolean isFeedLoadedFromLocalFile) throws IllegalArgumentException,
//			FeedException, IOException {
//
//		Integer podcastId = podcast.getPodcastId();
//
//		try {
//			// when called manually we have to get the necessary attributes for
//			// update
//			if (isCalledManually) {
//				podcast = readDao.getPodcastForUpdateById(podcastId);
//			}
//			if (podcast == null)
//				throw new BusinessException("No podcast found for podcast id ["
//						+ podcastId + "]");
//
//			LOG.info("UPDATING podId[" + podcast.getPodcastId()
//					+ "] with feed - " + podcast.getUrl());
//
//			// ONLY IF has changed or does not support etag and last-modified -
//			// etag and last-modified attributes are updated
//			Integer podcastStatus = getFeedUpdateStatus(podcast, podcastId);
//			boolean checkFeedForUpdate = (podcastStatus == HttpStatusExtensionType.URL_CONTENT_MODIFIED
//					.getCode()) || isCalledManually;
//			if (checkFeedForUpdate) {
//
//				// get only the episodes that are still marked as available
//				List<Episode> reachableEpisodes = readDao
//						.getAvailableEpisodesFromDB(podcastId);
//				podcast.setEpisodes(reachableEpisodes);
//
//				// get the max episode id from the database - we'll add to that
//				// the new episodes, if any
//				int maxIndex = readDao.getMaxEpisodeIdForPodcast(podcastId);
//
//				// get the new episodes from feed
//				List<Episode> newEpisodes = getNewEpisodes(podcast, maxIndex,
//						isFeedLoadedFromLocalFile);
//				if (newEpisodes.size() > 0) {
//					podcast.setLastUpdate(new Date());
//					addNewEpisodes(newEpisodes, podcast.getPodcastId());
//				}
//
//				List<Episode> notReachableEpisodes = getNotReachableEpisodes(podcast);
//				if (notReachableEpisodes.size() > 0) {
//					for (Episode e : notReachableEpisodes) {
//						e.setIsAvailable(0);// TODO make enum out of this
//						updateDao.updateEpisodeAvailability(e);
//					}
//					podcast.setLastUpdate(new Date());
//				}
//
//				// now update also the podcast - the podcast is reachable, and
//				// transient data (etags, publication_date, last_update, last
//				// episode url etc.)
//				podcast.setAvailability(HttpStatus.SC_OK);
//				updateDao.updateTransientDataForPodcastById(podcast);
//
//			} else if (podcastStatus != HttpStatus.SC_OK
//					&& podcastStatus != HttpStatus.SC_NOT_MODIFIED
//					&& podcastStatus != HttpStatusExtensionType.SOCKET_TIMEOUT_EXCEPTION
//							.getCode()) {
//				// a some sort of error must have happened so we need to update
//				// the availability of the podcast
//				podcast.setAvailability(podcastStatus);
//				updateDao.updatePodcastAvailability(podcast);
//			}
//
//		} catch (Exception e) {
//
//			if (e instanceof MalformedURLException) {
//				LOG.error(
//						"MalformedURLException podcastId [ "
//								+ podcast.getPodcastId() + " ] " + "] url ["
//								+ podcast.getUrl() + "] ", e);
//			} else if (e instanceof IllegalArgumentException) {
//				LOG.error(
//						"IllegalArgumentException podcastId [ "
//								+ podcast.getPodcastId() + " ] " + "] url ["
//								+ podcast.getUrl() + "] ", e);
//			} else if (e instanceof FeedException) {
//				LOG.error("FeedException podcastId [ " + podcast.getPodcastId()
//						+ " ] " + "] url [" + podcast.getUrl() + "] ", e);
//			} else if (e instanceof IOException) {
//				LOG.error("IOException podcastId [ " + podcast.getPodcastId()
//						+ " ] " + "] url [" + podcast.getUrl() + "] ", e);
//			} else if (e instanceof BusinessException) {
//				LOG.error(
//						"Business exception podcastId [ "
//								+ podcast.getPodcastId() + " ] " + "] url ["
//								+ podcast.getUrl() + "] ", e);
//				return;
//			} else {
//				LOG.error(
//						"Episodes still reachable but UNKNOWN ERROR when updating the podcastId [ "
//								+ podcast.getPodcastId() + " ] " + "] url ["
//								+ podcast.getUrl() + "] ", e);
//			}
//
//			podcast.setAvailability(HttpStatusExtensionType.PODCAST_IN_ERROR
//					.getCode());
//			updateDao.updateTransientDataForPodcastById(podcast);
//		}
//
//	}
//
//	private void addNewEpisodes(List<Episode> newEpisodes, Integer podcastId) {
//
//		try {
//			updateDao.markAllEpisodesAsNotNew(podcastId);
//			insertNewEpisodesInDB(newEpisodes);
//			markNewEpisodesWithNewFlag(newEpisodes, podcastId);
//		} catch (Exception e) {
//			LOG.error(" Error when marking new episodes as new PodId["
//					+ podcastId, e);
//		}
//
//	}
//
//	private void insertNewEpisodesInDB(List<Episode> episodes) {
//		for (Episode episode : episodes) {
//			try {
//				// TODO when I move to InnoDB for table and lucene search,
//				// rollback the transaction with log message when updating the
//				// podcast
//				boolean episodeHasLinkToMediaFile = !episode.getMediaUrl()
//						.equals("noMediaUrl");
//				if (episodeHasLinkToMediaFile) {
//					insertDao.insertEpisode(episode);
//					LOG.info("PodId[" + episode.getPodcastId()
//							+ "] - INSERT EPISODE epId["
//							+ episode.getEpisodeId() + "] - epURL "
//							+ episode.getMediaUrl());
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				LOG.error(" PodId[" + episode.getPodcastId()
//						+ "] ERROR inserting episode " + episode.getMediaUrl()
//						+ " " + e.getMessage());
//				continue; // do not mark it as new episode
//			}
//		}
//	}
//
//	private void markNewEpisodesWithNewFlag(List<Episode> newEpisodes,
//			Integer podcastId) {
//		// mark the new episodes as new
//		InputMarkNewEpisodesAsNew input = new InputMarkNewEpisodesAsNew();
//		input.setEpisodes(newEpisodes);
//		input.setPodcastId(podcastId);
//		updateDao.markNewEpisodesAsNew(input);
//	}
//
//	private List<Episode> getNotReachableEpisodes(Podcast podcast) {
//
//		List<Episode> notReachableEpisodes = new ArrayList<Episode>();
//
//		// took out of the loop the variables;
//		String episodeMediaUrl = null;
//
//		// loop through all the current episodes of the podcast
//		for (Episode ep : podcast.getEpisodes()) {
//
//			episodeMediaUrl = ep.getMediaUrl();
//
//			if (episodeMediaUrl != null
//					&& !episodeMediaUrl.equals("noMediaUrl")) {
//				HttpHead headMethod = null;
//				try {
//					headMethod = new HttpHead(episodeMediaUrl);
//					org.apache.http.client.HttpClient httpClient = new DefaultHttpClient(
//							poolingClientConnectionManager);
//
//					HttpParams params = httpClient.getParams();
//					org.apache.http.params.HttpConnectionParams
//							.setConnectionTimeout(params, 15000);
//					org.apache.http.params.HttpConnectionParams.setSoTimeout(
//							params, 15000);
//					HttpResponse httpResponse = httpClient.execute(headMethod);
//					int statusCode = httpResponse.getStatusLine()
//							.getStatusCode();
//
//					ep.setAvailability(statusCode);
//
//					// some sites don't allow access with the http client, but
//					// it works in the player
//					boolean notReachableCondition = !((statusCode == org.apache.http.HttpStatus.SC_OK)
//							|| (statusCode == org.apache.http.HttpStatus.SC_UNAUTHORIZED) || (statusCode == org.apache.http.HttpStatus.SC_FORBIDDEN));
//					if (notReachableCondition) {
//						notReachableEpisodes.add(ep);
//						LOG.info("EPISODE UNAVAILABLE - PodId["
//								+ podcast.getPodcastId() + "] - httpStatus "
//								+ statusCode + " ep_URL[" + ep.getMediaUrl()
//								+ "] ");
//						ep.setAvailability(statusCode);
//					}
//
//				} catch (IOException e) {
//					if (e instanceof SocketTimeoutException) {
//						LOG.warn("PodId[" + podcast.getPodcastId()
//								+ "] - socket timeout exception - epId["
//								+ ep.getEpisodeId() + "]" + " ep_URL["
//								+ ep.getMediaUrl() + "] ");
//						continue; // optimistic approach - a 404 should be
//									// caught by next update
//					} else if (e instanceof NoHttpResponseException) {
//						LOG.error(
//								"PodId["
//										+ podcast.getPodcastId()
//										+ "] - no http response exception- edId["
//										+ ep.getEpisodeId() + "]", e);
//					} else if (e instanceof UnknownHostException) { // TODO this
//																	// is to
//																	// avoid the
//																	// npr
//																	// podcasts
//																	// problem,
//																	// although
//																	// the links
//																	// are still
//																	// available
//																	// - verify
//																	// in log
//																	// after
//																	// update
//						LOG.warn("PodId[" + podcast.getPodcastId()
//								+ "] - unknown host exception - epId["
//								+ ep.getEpisodeId() + "]" + "ep_URL["
//								+ ep.getMediaUrl() + "] ");
//						continue; // optimistic approach - a 404 should be
//									// caught by next update
//					} else if (e instanceof ConnectTimeoutException) {
//						LOG.warn("PodId[" + podcast.getPodcastId()
//								+ "] - connect timeout exception - epId["
//								+ ep.getEpisodeId() + "]" + "ep_URL["
//								+ ep.getMediaUrl() + "] ");
//						continue; // optimistic approach - a 404 should be
//									// caught by next update
//					}
//					ep.setAvailability(HttpStatusExtensionType.IO_EXCEPTION
//							.getCode());
//					notReachableEpisodes.add(ep);
//					LOG.error("PodId[" + podcast.getPodcastId()
//							+ "] - IOException - epId[" + ep.getEpisodeId()
//							+ "]" + "ep_URL[" + ep.getMediaUrl() + "]", e);
//					continue;
//				} catch (IllegalArgumentException e) {
//					ep.setAvailability(HttpStatusExtensionType.ILLEGAL_ARGUMENT_EXCEPTION
//							.getCode());
//					notReachableEpisodes.add(ep);
//					LOG.error(
//							"PodId[" + podcast.getPodcastId()
//									+ "] possible false URL - - epId["
//									+ ep.getEpisodeId() + "]" + "ep_URL["
//									+ ep.getMediaUrl() + "] ", e);
//					continue;
//				} catch (Exception e) {
//					ep.setAvailability(HttpStatusExtensionType.EXCEPTION
//							.getCode());
//					notReachableEpisodes.add(ep);
//					LOG.error(
//							"PodId[" + podcast.getPodcastId()
//									+ "] - UNKNOWN EXCEPTION - epId["
//									+ ep.getEpisodeId() + "]" + "ep_URL["
//									+ ep.getMediaUrl() + "]", e);
//					continue;
//				} finally {
//					if (headMethod != null) {
//						// Release the connection.
//						headMethod.releaseConnection();
//					}
//				}
//			} else {
//				LOG.debug("PodId[" + podcast.getPodcastId() + "] - "
//						+ "NO MEDIA Url epId[" + ep.getEpisodeId() + "]");
//				notReachableEpisodes.add(ep);
//			}
//
//		}
//
//		return notReachableEpisodes;
//	}
//
//	@SuppressWarnings("unchecked")
//	private List<Episode> getNewEpisodes(Podcast podcast, Integer maxIndex,
//			boolean isFeedLoadedFromLocalFile) throws IOException,
//			IllegalArgumentException, FeedException, BusinessException {
//
//		List<Episode> newEpisodes = new ArrayList<Episode>();
//		SyndFeed syndFeedForUrl = getSyndFeedForUpdate(podcast,
//				isFeedLoadedFromLocalFile);
//		podcast.setPodcastFeed(syndFeedForUrl);
//		Date lastPublicationDateOfPodcast = podcast.getPublicationDate();// get
//																			// it
//																			// here
//																			// because
//																			// it
//																			// gets
//																			// modified
//																			// in
//																			// the
//																			// method
//																			// utils.setEpisodeAttributes
//
//		// iterate through the entries from the feed to find out which ones are
//		// new from the last check-up
//		for (SyndEntryImpl entry : (List<SyndEntryImpl>) podcast
//				.getPodcastFeed().getEntries()) {
//
//			Episode potentiallyNewEpisode = null;
//			boolean isNewEpisode;
//
//			potentiallyNewEpisode = new Episode();
//			potentiallyNewEpisode.setPodcastId(podcast.getPodcastId());
//
//			Date publicationDateOfPotentiallyNewEpisode = entry
//					.getPublishedDate();
//			boolean publicationDateOfEpisodeIsMoreRecent = publicationDateOfPotentiallyNewEpisode != null
//					&& lastPublicationDateOfPodcast != null
//					&& publicationDateOfPotentiallyNewEpisode
//							.after(lastPublicationDateOfPodcast);
//			if (publicationDateOfEpisodeIsMoreRecent) {
//				// no need to look further can't be a new episode
//				isNewEpisode = true;
//				podcastAndEpisodeAttributesService.setEpisodeAttributes(potentiallyNewEpisode, podcast,
//						entry);
//			} else {
//				boolean publicationDateOfEpisodeIsOlder = publicationDateOfPotentiallyNewEpisode != null
//						&& lastPublicationDateOfPodcast != null
//						&& publicationDateOfPotentiallyNewEpisode
//								.before(lastPublicationDateOfPodcast);
//				if (publicationDateOfEpisodeIsOlder) {
//					isNewEpisode = false;
//				} else {
//					// set new episode's attributes so that we can compare it
//					// with the ones that are still reachable
//					podcastAndEpisodeAttributesService.setEpisodeAttributes(potentiallyNewEpisode, podcast,
//							entry);
//					isNewEpisode = handlePubDateSpecialCases(podcast,
//							potentiallyNewEpisode,
//							publicationDateOfPotentiallyNewEpisode);
//				}
//
//			}
//
//			if (isNewEpisode) {
//				// set index to the next episode
//				potentiallyNewEpisode.setEpisodeId(++maxIndex);
//				potentiallyNewEpisode
//						.setAvailability(org.apache.http.HttpStatus.SC_OK);
//				// add it to the response
//				newEpisodes.add(potentiallyNewEpisode);
//			}
//		}
//
//		/* ********************* IMPORTANT ******************** */
//		// by this point podcast episodes should contain ONLY the old reachable
//		// that are not present in the feed to spare HTTP calls
//		return newEpisodes;
//	}
//
//	/**
//	 * 
//	 * There are special cases , where publication dates could not be
//	 * established OR some publication dates are null OR some feeds have the
//	 * same publication date for all the episodes
//	 * 
//	 * @param podcast
//	 * @param potentiallyNewEpisode
//	 * @param publicationDateOfPotentiallyNewEpisode
//	 * @return
//	 */
//	private boolean handlePubDateSpecialCases(Podcast podcast,
//			Episode potentiallyNewEpisode,
//			Date publicationDateOfPotentiallyNewEpisode) {
//
//		boolean response = true;
//
//		if (publicationDateOfPotentiallyNewEpisode == null) {
//			LOG.warn("PodId[" + podcast.getPodcastId()
//					+ "] - NO publication_date for episode_title["
//					+ potentiallyNewEpisode.getTitle() + "]");
//
//			// iterate through the stored episodes to see if we need to add it
//			// or not
//			Iterator<Episode> episodeIterator = podcast.getEpisodes()
//					.iterator();
//			while (episodeIterator.hasNext()) {
//				Episode stillReachableEpisode = episodeIterator.next();
//
//				// try matching on title and media url - not that strong match
//				boolean isMatch = stillReachableEpisode.getTitle().trim()
//						.equals(potentiallyNewEpisode.getTitle().trim())
//						&& stillReachableEpisode
//								.getMediaUrl()
//								.trim()
//								.equals(potentiallyNewEpisode.getMediaUrl()
//										.trim());
//				if (isMatch) {
//					// this entry is already in the database, break
//					response = false;
//					episodeIterator.remove();
//					LOG.warn("PodId[" + podcast.getPodcastId()
//							+ "] - MATCHED but not on publication DATE - "
//							+ "epUrl[" + potentiallyNewEpisode.getMediaUrl()
//							+ "]");
//					break;
//				}
//			}
//		} else {
//			// we end up here only if the episode publication date is the same
//			// as the podcast's publication date (it happens for feeds that have
//			// the same pub data overall)
//			// OR when publication date of the podcast is null so we still have
//			// to iterate through all the "existing" episodes
//			Iterator<Episode> episodeIterator = podcast.getEpisodes()
//					.iterator();
//			while (episodeIterator.hasNext()) {
//				Episode stillReachableEpisode = episodeIterator.next();
//
//				Date publicationDateOfStillReachableEpisode = stillReachableEpisode
//						.getPublicationDate();
//				DateTime jodaTimeExistingEpisode = new DateTime(
//						publicationDateOfStillReachableEpisode);
//				DateTime jodaTimePotentiallyNewEpisode = new DateTime(
//						publicationDateOfPotentiallyNewEpisode);
//				// with this condition is also re-broadcasting supported - some
//				// producers do that
//				boolean isStrongMatch = (jodaTimePotentiallyNewEpisode
//						.getMillis() == jodaTimeExistingEpisode.getMillis())
//						&& stillReachableEpisode
//								.getTitle()
//								.trim()
//								.equals(potentiallyNewEpisode.getTitle().trim())
//						&& stillReachableEpisode
//								.getMediaUrl()
//								.trim()
//								.equals(potentiallyNewEpisode.getMediaUrl()
//										.trim());
//				if (isStrongMatch) {
//					// this entry is already in the database, break
//					response = false;
//					episodeIterator.remove();
//					break;
//				}
//			}
//		}
//		return response;
//	}
//
//	private SyndFeed getSyndFeedForUpdate(Podcast podcast,
//			boolean isFeedLoadedFromLocalFile) throws MalformedURLException,
//			IOException, FeedException, BusinessException {
//		SyndFeed syndFeed;
//		if (isFeedLoadedFromLocalFile) {
//			syndFeed = getSyndFeedFromLocalFile(configBean
//					.get("LOCAL_PATH_FOR_FEED"));
//			if (!syndFeed.getTitle().equalsIgnoreCase(podcast.getTitle())) {
//				throw new BusinessException(
//						"The proper file might not have been downloaded locally, please verify again");
//			}
//
//		} else {
//			syndFeed = syndFeedService.getSyndFeedForUrl(podcast.getUrl());
//		}
//		return syndFeed;
//	}
//
//	/**
//	 * calls the feed url to verify if it has been modified since the last call
//	 * and returns the httpStatus if available or error if not
//	 * 
//	 * @param podcast
//	 * @return
//	 */
//	private Integer getFeedUpdateStatus(Podcast podcast, int podcastId) {
//
//		// Create a method instance.
//		if (podcast == null) {
//			LOG.error("No podcast anymore for podcastId - " + podcastId);
//			// move on but needs to be investigated from the log file
//			return HttpStatusExtensionType.PODCAST_IN_ERROR.getCode();
//		}
//		String podcastUrl = podcast.getUrl();
//		if (podcastUrl == null) {
//			LOG.error(" URL IS NULLL podcast[" + podcast.getPodcastId() + "]");
//			// move on but needs to be investigated from the log file
//			return HttpStatusExtensionType.PODCAST_IN_ERROR.getCode();
//		}
//
//		HttpHead headMethod = null;
//		try {
//			headMethod = new HttpHead(podcastUrl);
//			if (podcast.getEtagHeaderField() != null) {
//				headMethod.addHeader("If-None-Match",
//						podcast.getEtagHeaderField());
//			}
//
//			if (podcast.getLastModifiedHeaderField() != null) {
//				headMethod.addHeader("If-Modified-Since",
//						podcast.getLastModifiedHeaderFieldStr());
//			}
//			org.apache.http.client.HttpClient httpClient = new DefaultHttpClient(
//					poolingClientConnectionManager);
//
//			HttpParams params = httpClient.getParams();
//			org.apache.http.params.HttpConnectionParams.setConnectionTimeout(
//					params, 10000);
//			org.apache.http.params.HttpConnectionParams.setSoTimeout(params,
//					10000);
//			HttpResponse httpResponse = httpClient.execute(headMethod);
//			int statusCode = httpResponse.getStatusLine().getStatusCode();
//
//			// if the podcast file has not been modified there is no need to
//			// update
//			if (statusCode == org.apache.http.HttpStatus.SC_NOT_MODIFIED) {
//				LOG.info("PodId[ " + podcast.getPodcastId() + " ]"
//						+ " pod url[ " + podcast.getUrl() + " ]"
//						+ " FEED NOT MODIFIED NO UPDATE ");
//				return statusCode;
//			} else {
//				if (statusCode != org.apache.http.HttpStatus.SC_OK) {
//					LOG.error("PodId[ " + podcast.getPodcastId() + " ]"
//							+ " pod url[ " + podcast.getUrl()
//							+ " ] : http status code " + statusCode);
//
//					return statusCode;
//				}
//
//				// set the new etag if existent
//				org.apache.http.Header eTagHeader = httpResponse
//						.getLastHeader("etag");
//				if (eTagHeader != null) {
//					podcast.setEtagHeaderField(eTagHeader.getValue());
//				}
//
//				// set the new "last modified" header field if existent
//				org.apache.http.Header lastModifiedHeader = httpResponse
//						.getLastHeader("last-modified");
//				if (lastModifiedHeader != null) {
//					podcast.setLastModifiedHeaderField(DateUtil
//							.parseDate(lastModifiedHeader.getValue()));
//					podcast.setLastModifiedHeaderFieldStr(lastModifiedHeader
//							.getValue());
//				}
//
//				return HttpStatusExtensionType.URL_CONTENT_MODIFIED.getCode();
//			}
//
//		} catch (DateParseException e) {
//			LOG.error("PodId[ " + podcast.getPodcastId() + " ]" + " pod url[ "
//					+ podcast.getUrl() + " ] : Date format exception: "
//					+ e.getMessage());
//			return HttpStatusExtensionType.DATE_PARSE_EXCEPTION.getCode();
//
//		} catch (IOException e) {
//			if (e instanceof SocketTimeoutException) {
//				LOG.error("PodId[ " + podcast.getPodcastId() + " ]"
//						+ " pod url[ " + podcast.getUrl()
//						+ " ] : Socket timeout exception " + e.getMessage());
//				return HttpStatusExtensionType.SOCKET_TIMEOUT_EXCEPTION
//						.getCode();
//			}
//			LOG.error("PodId[ " + podcast.getPodcastId() + " ]" + " pod url[ "
//					+ podcast.getUrl() + " ] : fatal transport error: "
//					+ e.getMessage());
//			return HttpStatusExtensionType.IO_EXCEPTION.getCode();
//
//		} catch (Exception e) {
//			LOG.error("PodId[ " + podcast.getPodcastId() + " ]" + " pod url[ "
//					+ podcast.getUrl() + " ] : UNKNOWN EXCEPTION ");
//			return HttpStatusExtensionType.EXCEPTION.getCode();
//
//		} finally {
//			if (headMethod != null) {
//				// Release the connection.
//				headMethod.releaseConnection();
//			}
//		}
//
//	}
//
//	@Async
//	public Future<String> updatePodcastsFromRange(Integer startRow,
//			Integer chunkSize, Integer podcastsUpdateFrequency) {
//
//		List<Podcast> podcastsFromRange = null;
//
//		Map<String, Integer> params = new HashMap<String, Integer>();
//		params.put("startRow", startRow);
//		params.put("endRow", chunkSize);
//		params.put("updateFrequency", podcastsUpdateFrequency);
//
//		podcastsFromRange = readDao
//				.getPodcastsFromRangeWithUpdateFrequency(params);
//
//		for (Podcast podcast : podcastsFromRange) {
//			try {
//				this.updatePodcastById(podcast, false, false);
//			} catch (IllegalArgumentException e) {
//				LOG.error(" IllegalArgumentException has been thrown when setting attributes for podcast "
//						+ podcast.getUrl() + e.getMessage());
//				continue; // go to the next podcast
//			} catch (FeedException e) {
//				LOG.error(" FeedException has been thrown when setting attributes for podcast "
//						+ podcast.getUrl() + e.getMessage());
//				continue; // go to the next podcast
//
//			} catch (IOException e) {
//				LOG.error(" IOException has been thrown for podcast "
//						+ podcast.getUrl() + e.getMessage());
//				continue; // go to the next podcast
//			} catch (Exception e) {
//				LOG.error(" Unknown exception when updating podcast "
//						+ podcast.getUrl() + e.getMessage());
//				continue; // go to the next podcast
//			}
//		}
//
//		LOG.info("************** THREAD FINISHED startRow - " + startRow
//				+ " **************");
//		return new AsyncResult<String>("THREAD finished startRow - " + startRow);
//
//	}
//
//	@Async
//	public Future<String> updatePodcastsFromRange(Integer startRow,
//			Integer chunkSize) {
//		List<Podcast> podcastsFromRange = null;
//
//		Map<String, Integer> params = new HashMap<String, Integer>();
//		params.put("startRow", startRow);
//		params.put("endRow", chunkSize);
//
//		// all update frequencies are considered
//		podcastsFromRange = readDao.getPodcastsFromRange(params);
//
//		for (Podcast podcast : podcastsFromRange) {
//			try {
//				this.updatePodcastById(podcast, false, false);
//			} catch (IllegalArgumentException e) {
//				LOG.error(
//						" IllegalArgumentException has been thrown when setting attributes for podcast "
//								+ podcast.getUrl(), e);
//				continue; // go to the next podcast
//			} catch (FeedException e) {
//				LOG.error(
//						" FeedException has been thrown when setting attributes for podcast "
//								+ podcast.getUrl(), e);
//				continue; // go to the next podcast
//
//			} catch (IOException e) {
//				LOG.error(
//						" IOException has been thrown for podcast "
//								+ podcast.getUrl(), e);
//				continue; // go to the next podcast
//
//			} catch (Exception e) {
//				LOG.error(
//						" Unknown exception when updating podcast "
//								+ podcast.getUrl(), e);
//				continue; // go to the next podcast
//			}
//
//		}
//
//		LOG.info("************** THREAD FINISHED startRow - " + startRow
//				+ " **************");
//		return new AsyncResult<String>("THREAD finished startRow - " + startRow);
//
//	}
//
//	public void updatePodcastOwnMetadata(Podcast podcast) {
//
//		int podcastId = podcast.getPodcastId();
//
//		// first of all update the attribute of the podcast
//		updateDao.updatePodcastOwnMetadatabyId(podcast);
//
//		// first clean all the existing categories and tags
//		deleteDao.deletePodcastCategoriesByPodcastId(podcastId);
//		deleteDao.deletePodcastTagsByPodcastId(podcastId);
//
//		// insert again now the assigned categories in the intersection table
//		PodcastCategory podcastCategory = new PodcastCategory();
//		podcastCategory.setPodcastId(podcastId);
//		for (Integer i : podcast.getCategoryIDs()) {
//			podcastCategory.setCategoryId(i);
//			insertDao.insertPodcastCategory(podcastCategory);
//		}
//
//		// insert tags in the database
//		// split the tags-string in the different tags - use ";" so that
//		// complexer tags can be added like ("light cooking")
//		String[] tags = podcast.getTagsStr().split(",");
//		for (String tagName : tags) {
//			// tags should be case sensitive . e.g. in German you say Computer
//			// and in English computer -but trimmed should be; They are trimmed
//			// also by insertion
//			Tag tag = readDao.getTagByName(tagName.trim());
//			if (null != tag) {
//				insertDao.insertPodcastTag(new PodcastTag(podcastId, tag
//						.getTagId()));
//			} else {
//				tag = new Tag();
//				// make a trim on the tag to insert, to read after that properly
//				tag.setName(tagName.trim());
//				insertDao.insertTag(tag);
//				insertDao.insertPodcastTag(new PodcastTag(podcastId, tag
//						.getTagId()));
//			}
//		}
//	}
//
//	public void updateFeedAttributesForPodcastId(Integer podcastId,
//			Boolean isLoadedFromLocalFile) throws MalformedURLException,
//			IllegalArgumentException, IOException, FeedException {
//
//		Podcast podcast = readDao.getPodcastById(podcastId);
//
//		if (isLoadedFromLocalFile) {
//			podcast.setPodcastFeed(getSyndFeedFromLocalFile(configBean
//					.get("LOCAL_PATH_FOR_FEED")));
//		}
//
//		try {
//			boolean feedAttributesModified = podcastAndEpisodeAttributesService
//					.setPodcastFeedAttributesWithWarnings(podcast);
//			if (feedAttributesModified) {
//				updateDao.updatePodcastFeedAttributesById(podcast);
//				LOG.info("Feed [" + podcast.getUrl() + "] attributes MODIFIED ");
//			} else {
//				LOG.info("Feed [" + podcast.getUrl()
//						+ "] attributes NOT MODIFIED");
//			}
//		} catch (IllegalArgumentException e) {
//			LOG.error(
//					" IllegalArgumentException has been thrown when setting attributes for podcast "
//							+ podcast.getUrl(), e);
//		} catch (FeedException e) {
//			LOG.error(
//					" FeedException has been thrown when setting attributes for podcast "
//							+ podcast.getUrl(), e);
//		} catch (IOException e) {
//			LOG.error(
//					" IOException has been thrown for podcast "
//							+ podcast.getUrl(), e);
//		} catch (Exception e) {
//			LOG.error(
//					" Unknown exception when updating podcast "
//							+ podcast.getUrl(), e);
//		}
//
//	}
//
//	public SyndFeed getSyndFeedFromLocalFile(String filePath)
//			throws MalformedURLException, IOException,
//			IllegalArgumentException, FeedException {
//
//		SyndFeed feed = null;
//		FileInputStream fis = null;
//		try {
//			fis = new FileInputStream(filePath);
//			InputSource source = new InputSource(fis);
//			SyndFeedInput input = new SyndFeedInput();
//			feed = input.build(source);
//		} finally {
//			fis.close();
//		}
//
//		return feed;
//	}
}
