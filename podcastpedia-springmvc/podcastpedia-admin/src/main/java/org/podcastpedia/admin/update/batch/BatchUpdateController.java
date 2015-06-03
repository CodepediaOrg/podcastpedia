package org.podcastpedia.admin.update.batch;

import java.io.IOException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.podcastpedia.admin.update.UpdateService;
import org.podcastpedia.admin.util.forms.UpdatePodcastsByFrequencyForm;
import org.podcastpedia.admin.util.read.ReadService;
import org.podcastpedia.admin.util.restclient.RestClient;
import org.podcastpedia.common.controllers.propertyeditors.UpdateFrequencyTypeEditor;
import org.podcastpedia.common.types.UpdateFrequencyType;
import org.podcastpedia.common.util.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rometools.rome.io.FeedException;

@Controller
@RequestMapping("/admin/update/batch")
public class BatchUpdateController {

	private static final Integer DEFAULT_NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS = 10;

	protected static Logger LOG = Logger.getLogger(BatchUpdateController.class);

	@Autowired
	private UpdateService updateService;
	
	@Autowired
	private BatchUpdateService batchUpdateService;

	@Autowired
	private ReadService readService;

	@Autowired
	private RestClient restClient;

	@Autowired
	private ConfigBean configBean;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(UpdateFrequencyType.class,
				new UpdateFrequencyTypeEditor(UpdateFrequencyType.class));
	}

	@RequestMapping(method = RequestMethod.GET)
	public String prepareForms(Model model) {

		model.addAttribute("updatePodcastsByFrequencyForm",
				new UpdatePodcastsByFrequencyForm());

		return "batch_update_def"; // for the time being this is the home
									// page, it shouldn't be like that
	}

	/** UPDATE podcast by id or feed url **/
	@RequestMapping(value = "all_podcasts", method = RequestMethod.GET)
	public String updateAllPodcasts(ModelMap model) {

		LOG.debug("------ received request to update all podcasts -----");

		Integer numberOfWorkerThreads = Integer.valueOf(configBean
				.get("NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS"));
		// set default value if data not available inthe database
		if (numberOfWorkerThreads == null) {
			numberOfWorkerThreads = DEFAULT_NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS;
		}

		Integer totalNumberOfPodcasts = readService.getNumberOfPodcasts();

		Integer mod = totalNumberOfPodcasts % numberOfWorkerThreads;
		Integer chunkSize = totalNumberOfPodcasts / numberOfWorkerThreads;

		for (int i = 0; i < numberOfWorkerThreads; i++) {
			int startRow = i * chunkSize;
			if (i == numberOfWorkerThreads - 1) {
				// the last thread will also update the "mod" podcasts
				chunkSize = chunkSize + mod;
				LOG.debug("Thread[" + i + "] - [" + startRow + "," + chunkSize
						+ "]");
				Future<String> future = batchUpdateService.updatePodcastsFromRange(
						startRow, chunkSize);
			} else {
				LOG.debug("Thread[" + i + "] - [" + startRow + "," + chunkSize
						+ "]");
				batchUpdateService.updatePodcastsFromRange(startRow, chunkSize);
			}
		}

		restClient.invokeRefreshAllCaches();

		return "redirect:/admin/update/batch";
	}

	/**
	 * UPDATE podcast by id or feed url
	 * 
	 * @throws FeedException
	 * @throws IllegalArgumentException
	 * @throws IOException
	 **/
	@RequestMapping(value = "podcasts_by_frequency", method = RequestMethod.POST)
	public String updatePodcastsWithFrequency(
			@ModelAttribute("updatePodcastsByFrequencyForm") UpdatePodcastsByFrequencyForm updatePodcastsByFrequencyForm,
			BindingResult bindingResult, ModelMap model) {

		if (bindingResult.hasErrors()) {
			return "redirect:/admin";
		}

		LOG.debug("------ executing updatePodcastsWithFrequency -----");

		Integer podcastsUpdateFrequencyCode = updatePodcastsByFrequencyForm
				.getUpdateFrequency() != null ? updatePodcastsByFrequencyForm
				.getUpdateFrequency().getCode() : null;

		// if the input parameter is missing than we get it from the database
		if (podcastsUpdateFrequencyCode == null) {
			Integer.valueOf(configBean.get("PODCAST_FREQUENCY_TYPE_TO_UPDATE"));
		}

		Integer totalNumberOfPodcasts = null;

		totalNumberOfPodcasts = readService
				.getNumberOfPodcastsWithUpdateFrequency(podcastsUpdateFrequencyCode);

		// set number of worker threads - comes from form, if not, looks in the
		// db, if not then static value from this class
		Integer numberOfWorkerThreads = updatePodcastsByFrequencyForm
				.getNumberOfWorkingThreads();
		if (numberOfWorkerThreads == null) {
			numberOfWorkerThreads = Integer.valueOf(configBean
					.get("NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS"));
		}
		// set default value if data not available in the database
		if (numberOfWorkerThreads == null) {
			numberOfWorkerThreads = DEFAULT_NO_WORKER_THREADS_FOR_UPDATE_ALL_PODCASTS;
		}

		Integer mod = totalNumberOfPodcasts % numberOfWorkerThreads;
		Integer chunkSize = totalNumberOfPodcasts / numberOfWorkerThreads;

		for (int i = 0; i < numberOfWorkerThreads; i++) {
			int startRow = i * chunkSize;
			if (i == numberOfWorkerThreads - 1) {
				chunkSize = chunkSize + mod;
				LOG.debug("Thread[" + i + "] - [" + startRow + "," + chunkSize
						+ "]");
				Future<String> future = batchUpdateService.updatePodcastsFromRange(
						startRow, chunkSize, podcastsUpdateFrequencyCode);
			} else {
				batchUpdateService.updatePodcastsFromRange(startRow, chunkSize,
						podcastsUpdateFrequencyCode);
			}
		}

		return "redirect:/admin/update/batch";
	}

}
