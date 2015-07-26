package org.podcastpedia.web.api;

import org.podcastpedia.common.exception.BusinessException;
import org.podcastpedia.common.types.LanguageCode;
import org.podcastpedia.common.types.OrderByOption;
import org.podcastpedia.core.searching.SearchData;
import org.podcastpedia.core.searching.SearchResult;
import org.podcastpedia.core.searching.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
@RequestMapping("/api/search-results")
public class SearchSpringResource {

	@Autowired
	SearchService searchService;


    @RequestMapping(method = RequestMethod.GET)
	public @ResponseBody SearchResult    getSearches(
						@RequestParam(value = "searchTarget", required = false) String searchTarget,
						@RequestParam(value = "mediaType", required = false) org.podcastpedia.common.types.MediaType mediaType,
						@RequestParam(value = "orderBy", required = false) OrderByOption orderBy,
						@RequestParam(value = "searchMode", required = false) String searchMode,
						@RequestParam(value = "numberResultsPerPage", required =true ) Integer numberResultsPerPage,
                        @RequestParam(value = "languageCode", required =false ) LanguageCode languageCode,
						@RequestParam(value = "currentPage", required = true) Integer currentPage,
						@RequestParam(value = "categId", required = false) List<Integer> categoryId,
						@RequestParam(value = "tagId", required = false) Integer tagId,
						@RequestParam(value = "queryText", required = false) String queryText,
						@RequestParam(value = "anyOfTheseWords", required = false) String anyOfTheseWords,
						@RequestParam(value = "allTheseWords", required = false) String allTheseWords,
						@RequestParam(value = "exactPhrase", required = false) String exactPhrase,
						@RequestParam(value = "noneOfTheseWords", required = false) String noneOfTheseWords
						) throws BusinessException, UnsupportedEncodingException {

		SearchData searchData = new SearchData.Builder()
									.searchTarget(searchTarget)
									.queryText(queryText)
									.mediaType(mediaType)
									.orderBy(orderBy)
									.searchMode(searchMode)
									.numberResultsPerPage(numberResultsPerPage)
                                    .languageCode(languageCode)
									.currentPage(currentPage)
									.tagId(tagId)
									.categId(categoryId)
									.anyOfTheseWords(anyOfTheseWords)
									.allTheseWords(allTheseWords)
									.noneOfTheseWords(noneOfTheseWords)
									.exactPhrase(exactPhrase)
									.build();

		SearchResult searchResult = searchService.getResultsForSearchCriteria(searchData);

        return searchResult;
	}
}
