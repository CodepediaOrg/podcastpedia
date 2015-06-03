package org.podcastpedia.admin.sitemaps;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.podcastpedia.common.controllers.propertyeditors.UpdateFrequencyTypeEditor;
import org.podcastpedia.common.types.UpdateFrequencyType;
import org.podcastpedia.common.util.config.ConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/create")
public class SitemapsController {
	
	protected static Logger LOG = Logger.getLogger(SitemapsController.class);

	@Autowired
	private ConfigBean configBean;

	@Autowired
	private SitemapService sitemapService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(UpdateFrequencyType.class,
				new UpdateFrequencyTypeEditor(UpdateFrequencyType.class));
	}

	@RequestMapping(value = "sitemaps", method = RequestMethod.GET)
	public String prepareCreateSitemapsForm(
			@ModelAttribute("createSitemapByFrequencyForm") SitemapByFrequencyForm createSitemapByFrequencyForm,
			BindingResult bindingResult, ModelMap model)
			throws MalformedURLException {

		model.addAttribute("createSitemapByFrequencyForm", new SitemapByFrequencyForm());
		model.addAttribute("updateFrequencies", UpdateFrequencyType.values());

		return "sitemaps_def";
	}	
	
	@RequestMapping(value = "sitemaps", method = RequestMethod.POST)
	public String createSitemapForPodcastsWithFrequency(
			@ModelAttribute("createSitemapByFrequencyForm") SitemapByFrequencyForm createSitemapByFrequencyForm,
			BindingResult bindingResult, ModelMap model)
			throws MalformedURLException {

		LOG.debug("------ executing createSitemapForPodcastsWithFrequency -----"
				+ createSitemapByFrequencyForm.getUpdateFrequency());

		if (bindingResult.hasErrors()) {
			return "redirect:/admin";
		}

		String sitemapsDirectoryPath = configBean
				.get("SITEMAPS_DIRECTORY_PATH");

		sitemapService.createSitemapForPodcastsWithFrequency(
				createSitemapByFrequencyForm.getUpdateFrequency(),
				sitemapsDirectoryPath);
		sitemapService.createSitemapIndexFile(sitemapsDirectoryPath);

		return "redirect:/admin/create/sitemaps";
	}
}
