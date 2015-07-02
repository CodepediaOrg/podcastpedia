package org.podcastpedia.api.podcast;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.podcastpedia.common.domain.Tag;
import org.podcastpedia.common.exception.BusinessException;
import org.podcastpedia.core.tags.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/tags")
public class TagResource {

	@Autowired
	private TagService tagService;
		
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getKeywords() throws BusinessException {
		List<Tag> tagsOrderedByNumberOfPodcasts = tagService.getTagsOrderedByNumberOfPodcasts(1, 100);
		return Response.status(200)
				.entity(tagsOrderedByNumberOfPodcasts)
				.build();
	}
	
}
