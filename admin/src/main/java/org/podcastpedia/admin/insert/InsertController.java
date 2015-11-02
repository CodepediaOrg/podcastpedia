package org.podcastpedia.admin.insert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.podcastpedia.admin.util.PodcastAndEpisodeAttributesService;
import org.podcastpedia.admin.util.read.ReadService;
import org.podcastpedia.admin.util.restclient.RestClient;
import org.podcastpedia.common.controllers.propertyeditors.MediaTypeEditor;
import org.podcastpedia.common.controllers.propertyeditors.UpdateFrequencyTypeEditor;
import org.podcastpedia.common.domain.Podcast;
import org.podcastpedia.common.types.MediaType;
import org.podcastpedia.common.types.UpdateFrequencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/insert")
public class InsertController {

	protected static Logger LOG = Logger.getLogger(InsertController.class);

	@Autowired
	private RestClient restClient;

	@Autowired
	private InsertService insertService;

	@Autowired
	private EmailNotificationService emailNotificationService;

	@Autowired
	private ReadService readService;

	@Autowired
	private PodcastAndEpisodeAttributesService podcastAndEpisodeAttributesService;

	@Autowired
	private SocialMediaService socialMediaService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {

		binder.registerCustomEditor(UpdateFrequencyType.class,
				new UpdateFrequencyTypeEditor(UpdateFrequencyType.class));
		binder.registerCustomEditor(MediaType.class, new MediaTypeEditor(
				MediaType.class));
	}

	@RequestMapping(value = "upload_file", method = RequestMethod.GET)
	public String prepareUploadFileForm(
			@ModelAttribute("uploadItem") UploadFileForm uploadItem,
			ModelMap model) {

		model.addAttribute("uploadItem", new UploadFileForm());
		return "insert_podcasts_from_file_def";

	}

	@RequestMapping(value = "upload_file", method = RequestMethod.POST)
	public String create(
			@ModelAttribute("uploadItem") UploadFileForm uploadItem,
			BindingResult result) throws UnsupportedEncodingException,
			IOException {
		if (result.hasErrors()) {
			for (ObjectError error : result.getAllErrors()) {
				LOG.error("Error: " + error.getCode() + " - "
						+ error.getDefaultMessage());
			}
		} else {

			// build logic here to parse
			MultipartFile file = uploadItem.getFileData();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					file.getInputStream(), "UTF-8"));
			String strLine = null;
			// # starts a comments line, like in properties files
			while ((strLine = in.readLine()) != null) {
				if (strLine.trim().startsWith("#") || strLine.trim().isEmpty()) {
					// if it is a comment line we igonre it and move on; same
					// behaviour for empty lines
					continue;
				} else {
					LOG.debug("Parsing line : " + strLine);
					try {
						ProposedPodcast proposedPodcast = insertService
								.getPodcastFromStringLine(strLine);
						Podcast podcast = proposedPodcast.getPodcast();
						String suggestedFeedUrl = podcast.getUrl();
						Podcast podcastForUrl = readService
								.getPodcastAttributesByFeedUrl(suggestedFeedUrl);
						if (podcastForUrl == null) {
							// it means the podcast is not in the database
							insertService.addPodcast(podcast);

							notifySubmitterAndPostOnTwitterAboutNewInsertion(proposedPodcast);
						} else {
							LOG.warn("EXISTING url " + podcastForUrl.getUrl()
									+ " with podcastId "
									+ podcastForUrl.getPodcastId().toString());
						}
					} catch (Exception e) {
						LOG.error("**** ERROR ***** line " + strLine + " ", e);
						continue;
					}
				}
			}
		}

		restClient.invokeRefreshNewestAndRecommendedPodcasts();
		restClient.invokeRefreshReferenceData();
		restClient.invokeFlushSearchResultsCache();

		return "redirect:/admin";

	}

	private void notifySubmitterAndPostOnTwitterAboutNewInsertion(
			ProposedPodcast proposedPodcast) {

		Podcast podcast = readService
				.getPodcastAttributesByFeedUrl(proposedPodcast.getPodcast()
						.getUrl());
		StringBuilder urlOnPodcastpedia = new StringBuilder(
				"https://www.podcastpedia.org");
		if (podcast.getIdentifier() != null) {
			urlOnPodcastpedia.append("/").append(podcast.getIdentifier());
		} else {
			urlOnPodcastpedia.append("/podcasts/");
			urlOnPodcastpedia.append(String.valueOf(podcast.getPodcastId()));
			urlOnPodcastpedia.append("/").append(podcast.getTitleInUrl());
		}

		// notify the submitter
		try {
			emailNotificationService.sendPodcastAdditionConfirmation(
					proposedPodcast.getName(), proposedPodcast.getEmail(),
					urlOnPodcastpedia.toString());
		} catch (Exception e) {
			LOG.error("An error occured while trying to send email message", e);
		}
		socialMediaService.postOnTwitterAboutNewPodcast(podcast,
				urlOnPodcastpedia.toString());

	}

}
