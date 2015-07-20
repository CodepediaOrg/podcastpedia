package org.podcastpedia.admin.util.restclient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class RestController {

	protected static Logger LOG = Logger.getLogger(RestController.class);

	@Autowired
	private RestClient restClient;

	@RequestMapping(value = "rest/refresh_recommended_and_newest_podcasts", method = RequestMethod.GET)
	public String refreshNewestAndRecommendedPodcasts() {

		LOG.debug("------ refreshNewestAndRecommendedPodcasts  -----");
		restClient.invokeRefreshNewestAndRecommendedPodcasts();

		return "redirect:/admin";
	}

	@RequestMapping(value = "rest/refresh_reference_data", method = RequestMethod.GET)
	public String refreshReferenceData() {

		LOG.debug("------ refreshReferenceData  -----");
		restClient.invokeRefreshReferenceData();

		return "redirect:/admin";
	}

}
