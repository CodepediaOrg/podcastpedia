package org.podcastpedia.api.search;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.podcastpedia.common.exception.BusinessException;
import org.podcastpedia.common.types.OrderByOption;
import org.podcastpedia.core.searching.SearchData;
import org.podcastpedia.core.searching.SearchResult;
import org.podcastpedia.core.searching.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/search-results")
public class SearchResource {
	
	@Autowired
	SearchService searchService;
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getPodcastById(SearchData searchData) throws BusinessException, UnsupportedEncodingException {
		SearchResult searchResult = searchService.getResultsForSearchCriteria(searchData);
		return Response.status(200)
				.entity(searchResult)
				.build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getSearches(
						@QueryParam("searchTarget") String searchTarget,
						@QueryParam("mediaType") org.podcastpedia.common.types.MediaType mediaType,
						@QueryParam("orderBy") OrderByOption orderBy,
						@QueryParam("searchMode") String searchMode,
						@NotNull @QueryParam("numberResultsPerPage") Integer numberResultsPerPage,
						@NotNull @QueryParam("currentPage") Integer currentPage,
						@QueryParam("categId") List<Integer> categoryId,
						@QueryParam("tagId") Integer tagId,
						@QueryParam("queryText") String queryText,
						@QueryParam("anyOfTheseWords") String anyOfTheseWords,
						@QueryParam("allTheseWords") String allTheseWords,
						@QueryParam("exactPhrase") String exactPhrase,
						@QueryParam("noneOfTheseWords") String noneOfTheseWords
						) throws BusinessException, UnsupportedEncodingException {
		
		SearchData searchData = new SearchData.Builder()
									.searchTarget(searchTarget)
									.queryText(queryText)
									.mediaType(mediaType)
									.orderBy(orderBy)
									.searchMode(searchMode)
									.numberResultsPerPage(numberResultsPerPage)
									.currentPage(currentPage)
									.tagId(tagId)
									.categId(categoryId)
									.anyOfTheseWords(anyOfTheseWords)
									.allTheseWords(allTheseWords)
									.noneOfTheseWords(noneOfTheseWords)
									.exactPhrase(exactPhrase)
									.build();
		
		SearchResult searchResult = searchService.getResultsForSearchCriteria(searchData);
		return Response.status(200)
				.entity(searchResult)
				.build();
	}	
}
