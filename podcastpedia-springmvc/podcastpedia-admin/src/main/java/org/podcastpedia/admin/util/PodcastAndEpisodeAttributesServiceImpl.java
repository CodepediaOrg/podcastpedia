package org.podcastpedia.admin.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.podcastpedia.common.domain.Episode;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.types.MediaType;
import org.podcastpedia.common.util.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEnclosureImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndImage;
import com.rometools.rome.io.FeedException;

public class PodcastAndEpisodeAttributesServiceImpl implements PodcastAndEpisodeAttributesService {

	private static Logger LOG = Logger.getLogger(PodcastAndEpisodeAttributesServiceImpl.class);

	private static final int MAX_LENGTH_DESCRIPTION = 1000;

	private static final int TITLE_IN_URL_MAX_LENGTH = 100;

	private static final int MAX_PERMITTED_TITLE_LENGTH = 500;

	@Autowired
	ConfigBean configBean;
	
	@Autowired
	SyndFeedService syndFeedService;

	@SuppressWarnings("unchecked")
	public void setPodcastFeedAttributes(Podcast podcast,
			boolean feedPropertyHasBeenSet) throws IllegalArgumentException,
			FeedException, IOException {

		SyndFeed syndFeed = null;
		if (!feedPropertyHasBeenSet) {
			syndFeed = syndFeedService.getSyndFeedForUrl(podcast.getUrl());
			podcast.setPodcastFeed(syndFeed);
		}

		if (syndFeed != null) {
			// set DESCRIPTION for podcast - used in search
			if (syndFeed.getDescription() != null
					&& !syndFeed.getDescription().equals("")) {
				String description = syndFeed.getDescription();
				// out of description remove tags if any exist and store also
				// short description
				String descWithoutTabs = description
						.replaceAll("\\<[^>]*>", "");
				if (descWithoutTabs.length() > MAX_LENGTH_DESCRIPTION) {
					podcast.setDescription(descWithoutTabs.substring(0,
							MAX_LENGTH_DESCRIPTION));
				} else {
					podcast.setDescription(descWithoutTabs);
				}

			}

			// set TITLE - used in search
			String podcastTitle = syndFeed.getTitle();
			podcast.setTitle(podcastTitle);

			// build the title that will appear in the URL when accessing a
			// podcast from the main application
			String titleInUrl = podcastTitle.trim().replaceAll(
					"[^a-zA-Z0-9\\-\\s\\.]", "");
			titleInUrl = titleInUrl.replaceAll("[\\-| |\\.]+", "-");
			if (titleInUrl.length() > TITLE_IN_URL_MAX_LENGTH) {
				podcast.setTitleInUrl(titleInUrl.substring(0,
						TITLE_IN_URL_MAX_LENGTH));
			} else {
				podcast.setTitleInUrl(titleInUrl);
			}

			// set author
			podcast.setAuthor(syndFeed.getAuthor());

			// set COPYRIGHT
			podcast.setCopyright(syndFeed.getCopyright());

			// set LINK
			podcast.setLink(syndFeed.getLink());

			// set url link of the podcast's image when selecting the podcast in
			// the main application - mostly used through <a
			// href="urlOfImageToDisplay"....
			SyndImage podcastImage = syndFeed.getImage();
			if (null != podcastImage) {
				if (podcastImage.getUrl() != null) {
					podcast.setUrlOfImageToDisplay(podcastImage.getUrl());
				} else if (podcastImage.getLink() != null) {
					podcast.setUrlOfImageToDisplay(podcastImage.getLink());
				} else {
					podcast.setUrlOfImageToDisplay(configBean
							.get("NO_IMAGE_LOCAL_URL"));
				}
			} else {
				podcast.setUrlOfImageToDisplay(configBean
						.get("NO_IMAGE_LOCAL_URL"));
			}

			podcast.setPublicationDate(null);// default value is null, if cannot
												// be set

			// set url media link of the last episode - this is used when
			// generating the ATOM and RSS feeds from the Start page for example
			for (SyndEntry entry : (List<SyndEntry>) syndFeed
					.getEntries()) {
				// get the list of enclosures
				List<SyndEnclosure> enclosures = (List<SyndEnclosure>) entry
						.getEnclosures();

				if (null != enclosures) {
					// if in the enclosure list is a media type (either audio or
					// video), this will set as the link of the episode
					for (SyndEnclosure enclosure : enclosures) {
						if (null != enclosure) {
							podcast.setLastEpisodeMediaUrl(enclosure.getUrl());
							break;
						}
					}
				}

				if (entry.getPublishedDate() == null) {
					LOG.warn("PodURL["
							+ podcast.getUrl()
							+ "] - "
							+ "COULD NOT SET publication date for podcast, default date 08.01.1983 will be used ");
				} else {
					podcast.setPublicationDate(entry.getPublishedDate());
				}
				// first episode in the list is last episode - normally (are
				// there any exceptions?? TODO -investigate)
				break;
			}
		}

	}

	public void setEpisodeAttributes(Episode episode, Podcast podcast,
			SyndEntry entry) {
		// set DESCRIPTION for episode - used in search
		if (null != entry.getDescription()) {

			String episodeDesc = entry.getDescription().getValue();
			// tags are removed from description
			String descWithoutTabs = episodeDesc.replaceAll("\\<[^>]*>", "");
			// carriage returns are removed from description - for player
			String descWithoutEndOfLine = descWithoutTabs.replaceAll("\\n", "");
			if (descWithoutEndOfLine.length() > MAX_LENGTH_DESCRIPTION) {
				episode.setDescription(descWithoutEndOfLine.substring(0,
						MAX_LENGTH_DESCRIPTION));
			} else {
				episode.setDescription(descWithoutEndOfLine);
			}

		}

		// set author
		episode.setAuthor(entry.getAuthor());

		// set title for episode - used in search
		String episodeTitle = entry.getTitle();
		if (episodeTitle != null) {
			// removes quotes to display properly in player
			episodeTitle = episodeTitle.replaceAll("\"", "");
			if (episodeTitle.length() > MAX_PERMITTED_TITLE_LENGTH) {
				episodeTitle = episodeTitle.substring(0,
						MAX_PERMITTED_TITLE_LENGTH);
			}
			episode.setTitle(episodeTitle);
			String titleInUrl = episodeTitle.trim().replaceAll(
					"[^a-zA-Z0-9\\-\\s\\.]", "");
			titleInUrl = titleInUrl.replaceAll("[\\-| |\\.]+", "-");
			if (titleInUrl.length() > TITLE_IN_URL_MAX_LENGTH) {
				episode.setTitleInUrl(titleInUrl.substring(0,
						TITLE_IN_URL_MAX_LENGTH));
			} else {
				episode.setTitleInUrl(titleInUrl);
			}
		}

		episode.setLink(entry.getLink());

		// in the beginning inherit the media type from the podcast
		episode.setMediaType(podcast.getMediaType());

		// get the list of enclosures
		@SuppressWarnings("unchecked")
		List<SyndEnclosure> enclosures = (List<SyndEnclosure>) entry
				.getEnclosures();

		List<String> audioMimeTypesList = Arrays.asList(audioMimeTypesArray);
		List<String> videoMimeTypesList = Arrays.asList(videoMimeTypesArray);

		// set media url for the episode - this will be played in the player
		if (null != enclosures) {
			// if in the enclosure list is a media type (either audio or video),
			// this will set as the link of the episode
			for (SyndEnclosure enclosure : enclosures) {
				if (null != enclosure) {
					episode.setMediaUrl(enclosure.getUrl());
					if (enclosure.getLength() >= 0)
						episode.setLength(enclosure.getLength());
					// when adding a new podcast media type is selected for the
					// podcast based on an initial view, but it can be that is a
					// mixed podcast so both audio
					// and video should be considered and in that case PRIORITY
					// has the type of the episode if any...
					if (null != enclosure.getType()) {
						episode.setEnclosureType(enclosure.getType().trim());
						if (audioMimeTypesList.contains(enclosure.getType()
								.trim())) {
							episode.setMediaType(MediaType.Audio);
							break;
						}
						if (videoMimeTypesList.contains(enclosure.getType()
								.trim())) {
							episode.setMediaType(MediaType.Video);
							break;
						}
					}
				}
			}
		} else {
			episode.setMediaUrl("noMediaUrl");
		}

		if (episode.getMediaUrl() == null) {
			episode.setMediaUrl("noMediaUrl");
		}

		if (episode.getMediaUrl() == null
				|| episode.getMediaUrl().equals("noMediaUrl")) {
			LOG.warn("PodcastId[" + podcast.getPodcastId() + "] - "
					+ "COULD NOT SET MEDIA URL - " + "epTitle["
					+ entry.getTitle() + "]" + "feed[" + podcast.getUrl() + "]");
		}

		// set link attribute
		episode.setLink(entry.getLink());

		episode.setPublicationDate(entry.getPublishedDate());
		updatePodcastPublicationDateAndLastMediaUrl(episode, podcast);

		if (episode.getPublicationDate() == null) {
			LOG.warn("PodcastId[" + podcast.getPodcastId() + "] - "
					+ "COULD NOT SET publication date " + "epTitle["
					+ entry.getTitle() + "]" + "feed[" + podcast.getUrl() + "]");
		}
	}

	/**
	 * Set the podcast's publication date to the episode's if it is more recent
	 * 
	 * @param episode
	 * @param podcast
	 * @param episodePublicationDate
	 */
	private void updatePodcastPublicationDateAndLastMediaUrl(Episode episode,
			Podcast podcast) {
		Date podcastPublicationDate = podcast.getPublicationDate();
		boolean episodePubDateIsMoreRecent = episode.getPublicationDate() != null
				&& (podcastPublicationDate == null || (podcastPublicationDate != null && podcastPublicationDate
						.before(episode.getPublicationDate())));

		if (episodePubDateIsMoreRecent) {
			podcast.setPublicationDate(episode.getPublicationDate());
			podcast.setLastEpisodeMediaUrl(episode.getMediaUrl());
		}
	}

	public ConfigBean getConfigBean() {
		return configBean;
	}

	public void setConfigBean(ConfigBean configBean) {
		this.configBean = configBean;
	}

	private static String[] audioMimeTypesArray = {
	/* most common at the beginning */
	"audio/basic", "audio/L24", "audio/mp3", "audio/mp4", "audio/ogg",
			"audio/mpg", "audio/mpeg", "audio/mpeg-url", "audio/mpeg3",
			"audio/vorbis", "audio/vnd.wave", "audio/webm",

			/* the rest in alphabetical order */
			"audio/3gpp", "audio/aas", "audio/ac3", "audio/aiff", "audio/AMR",
			"audio/AMR-WB", "audio/annodex", "audio/asf", "audio/au",
			"audio/audible", "audio/avi", "audio/flac", "audio/gsm",
			"audio/iklax", "audio/it", "audio/m", "audio/m-mpeg", "audio/mad",
			"audio/make", "audio/mdz", "audio/med", "audio/mgp", "audio/mid",
			"audio/midi", "audio/mobile-xmf", "audio/mod", "audio/module-xm",
			"audio/mp4a-latm", "audio/nspaudio", "audio/pat", "audio/prs.sid",
			"audio/psid", "audio/rmf", "audio/s3m", "audio/scpls", "audio/sds",
			"audio/sfr", "audio/sidtune", "audio/snd", "audio/songsafe",
			"audio/soundtrack", "audio/tsp-audio", "audio/tsplayer",
			"audio/tta", "audio/vnd.lucent.voice ", "audio/vnd.nortel.vbk",
			"audio/vnd.qcelp", "audio/vnd.rn-realaudio",
			"audio/vnd.rn-realaudio-secure", "audio/vnd.rn-realvideo",
			"audio/vnd.rrn-realvideo", "audio/vnd.sealedmedia.softseal.mpeg",
			"audio/voc", "audio/x-voc", "audio/voxware", "audio/wav",
			"audio/wave", "audio/x-ahx", "audio/x-aifc", "audio/x-aiff",
			"audio/x-au", "audio/x-basic", "audio/x-dspeech", "audio/x-gsm",
			"audio/x-la-lqt", "audio/x-laplayer-reg", "audio/x-liquid",
			"audio/x-liquid-file", "audio/x-liquid-secure",
			"audio/x-liveaudio", "audio/x-mad", "audio/x-mid", "audio/x-midi",
			"audio/x-mod", "audio/x-mp3", "audio/x-mpeg", "audio/x-mpeg3",
			"audio/x-mpeg-3", "audio/x-mpegaudio", "audio/x-mpegurl",
			"audio/x-mpg", "audio/x-ms-wax", "audio/x-ms-wma",
			"audio/x-nspaudio", "audio/x-ntx", "audio/x-pat",
			"audio/x-pm-realaudio-plugin", "audio/x-pn-aiff", "audio/x-pn-au",
			"audio/x-pn-audibleaudio", "audio/x-pn-realaudio",
			"audio/x-pn-realvideo", "audio/x-pn-wav",
			"audio/x-pn-realaudio-plugin", "audio/x-realaudio",
			"audio/x-realaudio-secure", "audio/x-rmf", "audio/x-s3m",
			"audio/x-scpls", "audio/x-sd2", "audio/x-sidtune", "audio/x-stx",
			"audio/x-tfmx", "audio/x-tta", "audio/x-twinvq",
			"audio/x-twinvq-plugin", "audio/x-ulaw", "audio/x-wav",
			"audio/x-xm", "audio/x-zipped-mod", "audio/xm" };

	private static String[] videoMimeTypesArray = {
	/* most common at the beginning */
	"video/mpeg", "video/mp4", "video/ogg", "video/quicktime", "video/webm",
			"video/x-matroska", "video/x-ms-wmv", "video/x-flv",

			/* the rest in alphabetical order */
			"video/3gpp", "video/animaflex", "video/annodex", "video/avi",
			"video/avs-video", "video/dl", "video/dvd", "video/flc",
			"video/fli", "video/gl", "video/mng", "video/mpeg2", "video/mpg",
			"video/msvideo", "video/sgi-movie", "video/theora",
			"video/video cd", "video/vdo", "video/vivo",
			"video/vnd-rn-realvideo", "video/vnd.rn-realvideo",
			"video/vnd.rn-realvideo-secure", "video/vnd.sealed.mpeg1",
			"video/vnd.sealed.mpeg4", "video/vnd.sealed.swf",
			"video/vnd.sealedmedia.softseal.mov", "video/vnd.vivo",
			"video/vosaic", "video/x-acad-anim", "video/x-amt-demorun",
			"video/x-amt-showrun", "video/x-atomic3d-feature", "video/x-dl",
			"video/x-dv", "video/x-flc", "video/x-fli", "video/x-gl",
			"video/x-isvideo", "video/x-ivf", "video/x-m4v",
			"video/x-matroska", "video/x-la-asf", "video/x-mng",
			"video/x-motion-jpeg", "video/x-mpeg", "video/x-mpeg2-system",
			"video/x-mpeq2a", "video/x-mpegurl", "video/x-mpg",
			"video/x-ms-asf", "video/x-ms-asf-plugin", "video/x-ms-wm",
			"video/x-ms-wmp", "video/x-ms-wmx", "video/x-ms-wvv",
			"video/x-msvideo", "video/x-pn-realvideo",
			"video/x-pn-realvideo-plugin", "video/vnd.rn-realvideo",
			"video/x-qtc", "video/x-quicktime", "video/x-quicktimeplayer",
			"video/x-screencam", "video/x-sgi-movie", "video/xmpg2",
			"video/x-scm", "video/x-sgi-movie" };


	public boolean setPodcastFeedAttributesWithWarnings(Podcast podcast)
			throws IllegalArgumentException, FeedException, IOException {

		boolean feedAttributesModified = false;

		// the syndFeed is newly created or it might have been loaded from a
		// local file
		SyndFeed syndFeed = podcast.getPodcastFeed() != null ? podcast
				.getPodcastFeed() : syndFeedService.getSyndFeedForUrl(podcast.getUrl());

		if (syndFeed != null) {

			// set DESCRIPTION for podcast - used in search
			if (syndFeed.getDescription() != null
					&& !syndFeed.getDescription().equals("")) {
				String description = syndFeed.getDescription();
				// out of description remove tags if any exist and store also
				// short description
				String descWithoutTabs = description
						.replaceAll("\\<[^>]*>", "");
				if (descWithoutTabs.length() > MAX_LENGTH_DESCRIPTION) {
					boolean descriptionModified = !(podcast.getDescription() != null && podcast
							.getDescription().equals(descWithoutTabs));
					if (descriptionModified) {
						LOG.warn("Podcast description has been modified "
								+ "\n OLD " + podcast.getDescription()
								+ "\n NEW " + descWithoutTabs);
						feedAttributesModified = true;
					}
					podcast.setDescription(descWithoutTabs.substring(0,
							MAX_LENGTH_DESCRIPTION));
				} else {
					boolean descriptionModified = !(podcast.getDescription() != null && podcast
							.getDescription().equals(descWithoutTabs));
					if (descriptionModified) {
						LOG.warn("Podcast description has been modified "
								+ "\n OLD " + podcast.getDescription()
								+ "\n NEW " + descWithoutTabs);
						feedAttributesModified = true;
					}
					podcast.setDescription(descWithoutTabs);
				}
			}

			// set TITLE - used in search
			String podcastTitle = syndFeed.getTitle();
			if (!podcast.getTitle().equals(podcastTitle)) {
				LOG.warn("PodcastTitle has been modified " + "\n OLD "
						+ podcast.getTitle() + "\n NEW " + podcastTitle);
				feedAttributesModified = true;
			}
			if (podcastTitle != null) {
				podcast.setTitle(podcastTitle);
			}

			// build the title that will appear in the URL when accessing a
			// podcast from the main application
			String titleInUrl = podcastTitle.trim().replaceAll(
					"[^a-zA-Z0-9\\-\\s\\.]", "");
			titleInUrl = titleInUrl.replaceAll("[\\-| |\\.]+", "-");
			if (titleInUrl.length() > TITLE_IN_URL_MAX_LENGTH) {
				podcast.setTitleInUrl(titleInUrl.substring(0,
						TITLE_IN_URL_MAX_LENGTH));
			} else {
				podcast.setTitleInUrl(titleInUrl);
			}

			// set author
			boolean authorModified = syndFeed.getAuthor() != null
					&& (podcast.getAuthor() == null || !podcast.getAuthor()
							.equals(syndFeed.getAuthor()));
			if (authorModified) {
				LOG.warn("PodcastTitle has been modified " + "\n OLD "
						+ podcast.getTitle() + "\n NEW " + podcastTitle);
				feedAttributesModified = true;
			}
			if (syndFeed.getAuthor() != null) {
				podcast.setAuthor(syndFeed.getAuthor());
			}

			// set COPYRIGHT
			boolean copyrightModified = syndFeed.getCopyright() != null
					&& (podcast.getCopyright() == null || !podcast
							.getCopyright().equals(syndFeed.getCopyright()));
			if (copyrightModified) {
				LOG.warn("Copyright has been modified " + "\n OLD "
						+ podcast.getCopyright() + "\n NEW"
						+ syndFeed.getCopyright());
				feedAttributesModified = true;
			}
			if (syndFeed.getCopyright() != null) {
				podcast.setCopyright(syndFeed.getCopyright());
			}

			// set LINK
			boolean linkModified = syndFeed.getLink() != null
					&& (podcast.getLink() == null || !podcast.getLink().equals(
							syndFeed.getLink()));
			if (linkModified) {
				LOG.warn("Copyright has been modified " + "\n OLD "
						+ podcast.getLink() + "\n NEW" + syndFeed.getLink());
				feedAttributesModified = true;
			}
			if (syndFeed.getLink() != null) {
				podcast.setLink(syndFeed.getLink());
			}

			// set url link of the podcast's image when selecting the podcast in
			// the main application - mostly used through <a
			// href="urlOfImageToDisplay"....
			SyndImage podcastImage = syndFeed.getImage();
			String newImageUrl = null;
			if (null != podcastImage) {
				if (podcastImage.getUrl() != null) {
					newImageUrl = podcastImage.getUrl();
				} else if (podcastImage.getLink() != null) {
					newImageUrl = podcastImage.getLink();
				} else {
					newImageUrl = configBean.get("NO_IMAGE_LOCAL_URL");
				}
			} else {
				newImageUrl = configBean.get("NO_IMAGE_LOCAL_URL");
			}

			boolean imageModified = !podcast.getUrlOfImageToDisplay().equals(
					newImageUrl);
			if (imageModified) {
				LOG.warn("Image has been modified " + "\n OLD "
						+ podcast.getUrlOfImageToDisplay() + "\n NEW"
						+ newImageUrl);
				feedAttributesModified = true;
			}
			podcast.setUrlOfImageToDisplay(newImageUrl);

		}

		return feedAttributesModified;

	}

}
