package org.podcastpedia.api.podcast;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.podcastpedia.common.domain.Category;
import org.podcastpedia.common.exception.BusinessException;
import org.podcastpedia.core.categories.CategoryService;
import org.podcastpedia.core.episodes.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/categories")
public class CategoryResource {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private EpisodeService episodeService;
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getCategories() throws BusinessException {
		List<Category> categoriesOrderedByNoOfPodcasts = categoryService.getCategoriesOrderedByNoOfPodcasts();
		return Response.status(200)
				.entity(categoriesOrderedByNoOfPodcasts)
				.build();
	}
	
}
