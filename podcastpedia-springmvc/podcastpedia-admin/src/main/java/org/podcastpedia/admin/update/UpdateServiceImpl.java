package org.podcastpedia.admin.update;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.utils.DateUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
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
import org.podcastpedia.common.exception.BusinessException;
import org.podcastpedia.common.types.HttpStatusExtensionType;
import org.podcastpedia.common.util.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.InputSource;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;

public class UpdateServiceImpl implements UpdateService {

	private static final int TIMEOUT_SECONDS = 10;

	private static Logger LOG = Logger.getLogger(UpdateServiceImpl.class);

	@Autowired
	private DeleteDao deleteDao;

	@Autowired
	private ReadDao readDao;

	@Autowired
	private InsertDao insertDao;

	@Autowired
	private UpdateDao updateDao;

	@Autowired
	private ReadService readService;

	@Autowired
	private PodcastAndEpisodeAttributesService podcastAndEpisodeAttributesService;
		
	@Autowired
	SyndFeedService syndFeedService;	

	@Autowired
	private ConfigBean configBean;

	@Autowired
	private PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;  

	/**
	 * Entry point method to update the podcast
	 * 
	 * @throws FeedException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	// @Transactional TODO - uncomment this when migrate to 5.6 innodb and
	// lucene for search
	public void updatePodcastById(Podcast podcast, Boolean isCalledManually,
			boolean isFeedLoadedFromLocalFile) throws IllegalArgumentException,
			FeedException, IOException {

		Integer podcastId = podcast.getPodcastId();

		try {
			// when called manually we have to get the necessary attributes for
			// update
			if (isCalledManually) {
				podcast = readDao.getPodcastForUpdateById(podcastId);
			}
			if (podcast == null)
				throw new BusinessException("No podcast found for podcast id ["
						+ podcastId + "]");

			LOG.info("UPDATING podId[" + podcast.getPodcastId()
					+ "] with feed - " + podcast.getUrl());

			// ONLY IF has changed or does not support etag and last-modified -
			// etag and last-modified attributes are updated
			Integer podcastStatus = getFeedUpdateStatus(podcast, podcastId);
			boolean checkFeedForUpdate = (podcastStatus == HttpStatusExtensionType.URL_CONTENT_MODIFIED
					.getCode()) || isCalledManually;
			if (checkFeedForUpdate) {

				// get only the episodes that are still marked as available
				List<Episode> reachableEpisodes = readDao
						.getAvailableEpisodesFromDB(podcastId);
				podcast.setEpisodes(reachableEpisodes);

				// get the max episode id from the database - we'll add to that
				// the new episodes, if any
				int maxIndex = readDao.getMaxEpisodeIdForPodcast(podcastId);

				// get the new episodes from feed
				List<Episode> newEpisodes = getNewEpisodes(podcast, maxIndex,
						isFeedLoadedFromLocalFile);
				if (newEpisodes.size() > 0) {
					podcast.setLastUpdate(new Date());
					addNewEpisodes(newEpisodes, podcast.getPodcastId());
				}

				List<Episode> notReachableEpisodes = getNotReachableEpisodes(podcast);
				if (notReachableEpisodes.size() > 0) {
					for (Episode e : notReachableEpisodes) {
						e.setIsAvailable(0);// TODO make enum out of this
						updateDao.updateEpisodeAvailability(e);
					}
					podcast.setLastUpdate(new Date());
				}

				// now update also the podcast - the podcast is reachable, and
				// transient data (etags, publication_date, last_update, last
				// episode url etc.)
				podcast.setAvailability(HttpStatus.SC_OK);
				updateDao.updateTransientDataForPodcastById(podcast);

			} else if (podcastStatus != HttpStatus.SC_OK
					&& podcastStatus != HttpStatus.SC_NOT_MODIFIED
					&& podcastStatus != HttpStatusExtensionType.SOCKET_TIMEOUT_EXCEPTION
							.getCode()) {
				// a some sort of error must have happened so we need to update
				// the availability of the podcast
				podcast.setAvailability(podcastStatus);
				updateDao.updatePodcastAvailability(podcast);
			}

		} catch (Exception e) {

			if (e instanceof MalformedURLException) {
				LOG.error(
						"MalformedURLException podcastId [ "
								+ podcast.getPodcastId() + " ] " + "] url ["
								+ podcast.getUrl() + "] ", e);
			} else if (e instanceof IllegalArgumentException) {
				LOG.error(
						"IllegalArgumentException podcastId [ "
								+ podcast.getPodcastId() + " ] " + "] url ["
								+ podcast.getUrl() + "] ", e);
			} else if (e instanceof FeedException) {
				LOG.error("FeedException podcastId [ " + podcast.getPodcastId()
						+ " ] " + "] url [" + podcast.getUrl() + "] ", e);
			} else if (e instanceof IOException) {
				LOG.error("IOException podcastId [ " + podcast.getPodcastId()
						+ " ] " + "] url [" + podcast.getUrl() + "] ", e);
			} else if (e instanceof BusinessException) {
				LOG.error(
						"Business exception podcastId [ "
								+ podcast.getPodcastId() + " ] " + "] url ["
								+ podcast.getUrl() + "] ", e);
				return;
			} else {
				LOG.error(
						"Episodes still reachable but UNKNOWN ERROR when updating the podcastId [ "
								+ podcast.getPodcastId() + " ] " + "] url ["
								+ podcast.getUrl() + "] ", e);
			}

			podcast.setAvailability(HttpStatusExtensionType.PODCAST_IN_ERROR
					.getCode());
			updateDao.updateTransientDataForPodcastById(podcast);
		}

	}

	private void addNewEpisodes(List<Episode> newEpisodes, Integer podcastId) {

		try {
			updateDao.markAllEpisodesAsNotNew(podcastId);
			insertNewEpisodesInDB(newEpisodes);
			markNewEpisodesWithNewFlag(newEpisodes, podcastId);
		} catch (Exception e) {
			LOG.error(" Error when marking new episodes as new PodId["
					+ podcastId, e);
		}

	}

	private void insertNewEpisodesInDB(List<Episode> episodes) {
		for (Episode episode : episodes) {
			try {
				// TODO when I move to InnoDB for table and lucene search,
				// rollback the transaction with log message when updating the
				// podcast
				boolean episodeHasLinkToMediaFile = !episode.getMediaUrl()
						.equals("noMediaUrl");
				if (episodeHasLinkToMediaFile) {
					insertDao.insertEpisode(episode);
					LOG.info("PodId[" + episode.getPodcastId()
							+ "] - INSERT EPISODE epId["
							+ episode.getEpisodeId() + "] - epURL "
							+ episode.getMediaUrl());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOG.error(" PodId[" + episode.getPodcastId()
						+ "] ERROR inserting episode " + episode.getMediaUrl()
						+ " " + e.getMessage());
				continue; // do not mark it as new episode
			}
		}
	}

	private void markNewEpisodesWithNewFlag(List<Episode> newEpisodes,
			Integer podcastId) {
		// mark the new episodes as new
		InputMarkNewEpisodesAsNew input = new InputMarkNewEpisodesAsNew();
		input.setEpisodes(newEpisodes);
		input.setPodcastId(podcastId);
		updateDao.markNewEpisodesAsNew(input);
	}

	private List<Episode> getNotReachableEpisodes(Podcast podcast) {

		List<Episode> notReachableEpisodes = new ArrayList<Episode>();

		// took out of the loop the variables;
		String episodeMediaUrl = null;

		// loop through all the current episodes of the podcast
		for (Episode ep : podcast.getEpisodes()) {

			episodeMediaUrl = ep.getMediaUrl();

			if (episodeMediaUrl != null
					&& !episodeMediaUrl.equals("noMediaUrl")) {
				HttpHead headMethod = null;
				try {
					headMethod = new HttpHead(episodeMediaUrl);
				    RequestConfig requestConfig = RequestConfig.custom()
				            .setSocketTimeout(TIMEOUT_SECONDS * 1500)
				            .setConnectTimeout(TIMEOUT_SECONDS * 1500)
				            .build();		
					
				    CloseableHttpClient httpClient = HttpClientBuilder
			                .create()
			                .setDefaultRequestConfig(requestConfig)
			                .setConnectionManager(poolingHttpClientConnectionManager)
			                .build();
					HttpResponse httpResponse = httpClient.execute(headMethod);
					int statusCode = httpResponse.getStatusLine()
							.getStatusCode();

					ep.setAvailability(statusCode);

					// some sites don't allow access with the http client, but
					// it works in the player
					boolean notReachableCondition = !((statusCode == org.apache.http.HttpStatus.SC_OK)
							|| (statusCode == org.apache.http.HttpStatus.SC_UNAUTHORIZED) || (statusCode == org.apache.http.HttpStatus.SC_FORBIDDEN));
					if (notReachableCondition) {
						notReachableEpisodes.add(ep);
						LOG.info("EPISODE UNAVAILABLE - PodId["
								+ podcast.getPodcastId() + "] - httpStatus "
								+ statusCode + " ep_URL[" + ep.getMediaUrl()
								+ "] ");
						ep.setAvailability(statusCode);
					}

				} catch (IOException e) {
					if (e instanceof SocketTimeoutException) {
						LOG.warn("PodId[" + podcast.getPodcastId()
								+ "] - socket timeout exception - epId["
								+ ep.getEpisodeId() + "]" + " ep_URL["
								+ ep.getMediaUrl() + "] ");
						continue; // optimistic approach - a 404 should be
									// caught by next update
					} else if (e instanceof NoHttpResponseException) {
						LOG.error(
								"PodId["
										+ podcast.getPodcastId()
										+ "] - no http response exception- edId["
										+ ep.getEpisodeId() + "]", e);
					} else if (e instanceof UnknownHostException) { // TODO this
																	// is to
																	// avoid the
																	// npr
																	// podcasts
																	// problem,
																	// although
																	// the links
																	// are still
																	// available
																	// - verify
																	// in log
																	// after
																	// update
						LOG.warn("PodId[" + podcast.getPodcastId()
								+ "] - unknown host exception - epId["
								+ ep.getEpisodeId() + "]" + "ep_URL["
								+ ep.getMediaUrl() + "] ");
						continue; // optimistic approach - a 404 should be
									// caught by next update
					} else if (e instanceof ConnectTimeoutException) {
						LOG.warn("PodId[" + podcast.getPodcastId()
								+ "] - connect timeout exception - epId["
								+ ep.getEpisodeId() + "]" + "ep_URL["
								+ ep.getMediaUrl() + "] ");
						continue; // optimistic approach - a 404 should be
									// caught by next update
					}
					ep.setAvailability(HttpStatusExtensionType.IO_EXCEPTION
							.getCode());
					notReachableEpisodes.add(ep);
					LOG.error("PodId[" + podcast.getPodcastId()
							+ "] - IOException - epId[" + ep.getEpisodeId()
							+ "]" + "ep_URL[" + ep.getMediaUrl() + "]", e);
					continue;
				} catch (IllegalArgumentException e) {
					ep.setAvailability(HttpStatusExtensionType.ILLEGAL_ARGUMENT_EXCEPTION
							.getCode());
					notReachableEpisodes.add(ep);
					LOG.error(
							"PodId[" + podcast.getPodcastId()
									+ "] possible false URL - - epId["
									+ ep.getEpisodeId() + "]" + "ep_URL["
									+ ep.getMediaUrl() + "] ", e);
					continue;
				} catch (Exception e) {
					ep.setAvailability(HttpStatusExtensionType.EXCEPTION
							.getCode());
					notReachableEpisodes.add(ep);
					LOG.error(
							"PodId[" + podcast.getPodcastId()
									+ "] - UNKNOWN EXCEPTION - epId["
									+ ep.getEpisodeId() + "]" + "ep_URL["
									+ ep.getMediaUrl() + "]", e);
					continue;
				} finally {
					if (headMethod != null) {
						// Release the connection.
						headMethod.releaseConnection();
					}
				}
			} else {
				LOG.debug("PodId[" + podcast.getPodcastId() + "] - "
						+ "NO MEDIA Url epId[" + ep.getEpisodeId() + "]");
				notReachableEpisodes.add(ep);
			}

		}

		return notReachableEpisodes;
	}

	private List<Episode> getNewEpisodes(Podcast podcast, Integer maxIndex,
			boolean isFeedLoadedFromLocalFile) throws IOException,
			IllegalArgumentException, FeedException, BusinessException {

		List<Episode> newEpisodes = new ArrayList<Episode>();
		SyndFeed syndFeedForUrl = getSyndFeedForUpdate(podcast,
				isFeedLoadedFromLocalFile);
		podcast.setPodcastFeed(syndFeedForUrl);

		Episode newEpisode = null;
		DateTime publicationDateOfNewEpisode = null;

		boolean isAlreadyInDB = false;
		DateTime publicationDateOfStillReachableEpisode = null;

		// iterate through the episodes from the feed to find out which one are
		// new and add them to the database
		for (SyndEntry entry : (List<SyndEntry>) podcast
				.getPodcastFeed().getEntries()) {

			newEpisode = new Episode();
			newEpisode.setPodcastId(podcast.getPodcastId());

			// set new episode's attributes so that we can compare it with the
			// ones that are still reachable
			podcastAndEpisodeAttributesService.setEpisodeAttributes(newEpisode, podcast, entry);

			publicationDateOfNewEpisode = newEpisode.getPublicationDate() != null ? new DateTime(
					newEpisode.getPublicationDate()) : null;
			if (publicationDateOfNewEpisode == null) {
				LOG.warn("PodId[" + podcast.getPodcastId() + "] - "
						+ "COULD NOT GET PUBLICATION_DATE -" + "epTitle["
						+ newEpisode.getTitle() + "]");
			}

			// iterate through the stored episodes to see if we need to add it
			// or not
			Iterator<Episode> episodeIterator = podcast.getEpisodes()
					.iterator();
			while (episodeIterator.hasNext()) {
				Episode stillReachableEpisode = episodeIterator.next();
				// verify the existence of the episode in the database
				publicationDateOfStillReachableEpisode = stillReachableEpisode
						.getPublicationDate() != null ? new DateTime(
						stillReachableEpisode.getPublicationDate()) : null;
				if (publicationDateOfNewEpisode != null) {
					// with this condition is also re-broadcasting supported -
					// some producers do that
					if (publicationDateOfStillReachableEpisode != null
							&& publicationDateOfNewEpisode.getMillis() == publicationDateOfStillReachableEpisode
									.getMillis()
							&& stillReachableEpisode.getTitle().trim()
									.equals(newEpisode.getTitle().trim())
							&& stillReachableEpisode.getMediaUrl().trim()
									.equals(newEpisode.getMediaUrl().trim())) {
						// this entry is already in the database, break
						isAlreadyInDB = true;
						episodeIterator.remove();// episode is removed so that
													// won't be considered when
													// episode availability is
													// checked in the next
													// function call (spares
													// HTTP call)
						break;
					}
				} else {
					// try matching on title and media url - not that strong
					// match
					if (stillReachableEpisode.getTitle().trim()
							.equals(newEpisode.getTitle().trim())
							&& stillReachableEpisode.getMediaUrl().trim()
									.equals(newEpisode.getMediaUrl().trim())) {
						// this entry is already in the database, break
						isAlreadyInDB = true;
						episodeIterator.remove();// episode is removed so that
													// won't be considered when
													// episode availability is
													// checked in the next
													// function call (spares
													// HTTP call)
						LOG.warn("PodId[" + podcast.getPodcastId()
								+ "] - MATCHED but not on publication DATE - "
								+ "epUrl[" + newEpisode.getMediaUrl() + "]");
						// TODO maybe persist this message in the database with
						// a code ....
						break;
					}
				}
			}

			// if it is already in the database than continue
			if (isAlreadyInDB) {
				continue;
			} else {
				// new episode is only invalidated when it matches date, title
				// and media url from the db
				// set index to the next episode
				newEpisode.setEpisodeId(++maxIndex);
				// TODO maybe check if the URL is reachable with http client, be
				// we go on trust so far
				newEpisode.setAvailability(org.apache.http.HttpStatus.SC_OK);
				// add it to the response
				newEpisodes.add(newEpisode);
			}
		}

		// by this point podcast episodes should contain the old reachable that
		// are not present in the feed itself - spares HTTP calls (see "remove"
		// calls above)
		// TODO - it's matter between more HTTP calls vs. memory & processing
		// consumption if it was to select the second variant
		return newEpisodes;
	}

	private SyndFeed getSyndFeedForUpdate(Podcast podcast,
			boolean isFeedLoadedFromLocalFile) throws MalformedURLException,
			IOException, FeedException, BusinessException {
		SyndFeed syndFeed;
		if (isFeedLoadedFromLocalFile) {
			syndFeed = getSyndFeedFromLocalFile(configBean
					.get("LOCAL_PATH_FOR_FEED"));
			if (!syndFeed.getTitle().equalsIgnoreCase(podcast.getTitle())) {
				throw new BusinessException(
						"The proper file might not have been downloaded locally, please verify again");
			}

		} else {
			syndFeed = syndFeedService.getSyndFeedForUrl(podcast.getUrl());
		}
		return syndFeed;
	}

	/**
	 * calls the feed url to verify if it has been modified since the last call
	 * and returns the httpStatus if available or error if not
	 * 
	 * @param podcast
	 * @return
	 */
	private Integer getFeedUpdateStatus(Podcast podcast, int podcastId) {

		// Create a method instance.
		if (podcast == null) {
			LOG.error("No podcast anymore for podcastId - " + podcastId);
			// move on but needs to be investigated from the log file
			return HttpStatusExtensionType.PODCAST_IN_ERROR.getCode();
		}
		String podcastUrl = podcast.getUrl();
		if (podcastUrl == null) {
			LOG.error(" URL IS NULLL podcast[" + podcast.getPodcastId() + "]");
			// move on but needs to be investigated from the log file
			return HttpStatusExtensionType.PODCAST_IN_ERROR.getCode();
		}

		HttpHead headMethod = null;
		try {
			headMethod = new HttpHead(podcastUrl);
			if (podcast.getEtagHeaderField() != null) {
				headMethod.addHeader("If-None-Match",
						podcast.getEtagHeaderField());
			}

			if (podcast.getLastModifiedHeaderField() != null) {
				headMethod.addHeader("If-Modified-Since",
						podcast.getLastModifiedHeaderFieldStr());
			}
			
		    RequestConfig requestConfig = RequestConfig.custom()
		            .setSocketTimeout(TIMEOUT_SECONDS * 1000)
		            .setConnectTimeout(TIMEOUT_SECONDS * 1000)
		            .build();		
			
		    CloseableHttpClient httpClient = HttpClientBuilder
	                .create()
	                .setDefaultRequestConfig(requestConfig)
	                .setConnectionManager(poolingHttpClientConnectionManager)
	                .build();
		    
			HttpResponse httpResponse = httpClient.execute(headMethod);
			int statusCode = httpResponse.getStatusLine().getStatusCode();

			// if the podcast file has not been modified there is no need to
			// update
			if (statusCode == org.apache.http.HttpStatus.SC_NOT_MODIFIED) {
				LOG.info("PodId[ " + podcast.getPodcastId() + " ]"
						+ " pod url[ " + podcast.getUrl() + " ]"
						+ " FEED NOT MODIFIED NO UPDATE ");
				return statusCode;
			} else {
				if (statusCode != org.apache.http.HttpStatus.SC_OK) {
					LOG.error("PodId[ " + podcast.getPodcastId() + " ]"
							+ " pod url[ " + podcast.getUrl()
							+ " ] : http status code " + statusCode);

					return statusCode;
				}

				// set the new etag if existent
				org.apache.http.Header eTagHeader = httpResponse
						.getLastHeader("etag");
				if (eTagHeader != null) {
					podcast.setEtagHeaderField(eTagHeader.getValue());
				}

				// set the new "last modified" header field if existent
				org.apache.http.Header lastModifiedHeader = httpResponse
						.getLastHeader("last-modified");
				if (lastModifiedHeader != null) {
					podcast.setLastModifiedHeaderField(DateUtils
							.parseDate(lastModifiedHeader.getValue()));
					podcast.setLastModifiedHeaderFieldStr(lastModifiedHeader
							.getValue());
				}

				return HttpStatusExtensionType.URL_CONTENT_MODIFIED.getCode();
			}

		} catch (IOException e) {
			if (e instanceof SocketTimeoutException) {
				LOG.error("PodId[ " + podcast.getPodcastId() + " ]"
						+ " pod url[ " + podcast.getUrl()
						+ " ] : Socket timeout exception " + e.getMessage());
				return HttpStatusExtensionType.SOCKET_TIMEOUT_EXCEPTION
						.getCode();
			}
			LOG.error("PodId[ " + podcast.getPodcastId() + " ]" + " pod url[ "
					+ podcast.getUrl() + " ] : fatal transport error: "
					+ e.getMessage());
			return HttpStatusExtensionType.IO_EXCEPTION.getCode();

		} catch (Exception e) {
			LOG.error("PodId[ " + podcast.getPodcastId() + " ]" + " pod url[ "
					+ podcast.getUrl() + " ] : UNKNOWN EXCEPTION ");
			return HttpStatusExtensionType.EXCEPTION.getCode();

		} finally {
			if (headMethod != null) {
				// Release the connection.
				headMethod.releaseConnection();
			}
		}

	}

	public SyndFeed getSyndFeedFromLocalFile(String filePath)
			throws MalformedURLException, IOException,
			IllegalArgumentException, FeedException {

		SyndFeed feed = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			InputSource source = new InputSource(fis);
			SyndFeedInput input = new SyndFeedInput();
			feed = input.build(source);
		} finally {
			fis.close();
		}

		return feed;
	}
}
