package org.podcastpedia.core.searching;

import org.podcastpedia.common.types.LanguageCode;
import org.podcastpedia.common.types.MediaType;
import org.podcastpedia.common.types.OrderByOption;

import java.io.Serializable;
import java.util.List;

/**
 * Encapsulates search criteria.
 * Currently strongly tied to MySQL natural and boolean search.
 *
 */
public class SearchData implements Serializable {

	private static final long serialVersionUID = 4682314801277970962L;

	/** id of the search to be identified in the database */
	private long searchId;

	/** holds query text in natural mode search - what the user typically inputs in the search bar*/
	private String queryText;

	/** query text in natural mode */
	private String queryTextInNaturalMode;

	/** any of these words */
	private String anyOfTheseWords;

	/** all of these words **/
	private String allTheseWords;

	/** exact pharse **/
	private String exactPhrase;

	/** none of these words */
	private String noneOfTheseWords;

	/** Language code the podcasts should be in */
	private LanguageCode languageCode;

	/** search mode type - at the moment either natural or boolean */
	private String searchMode;

	/** number of results per page */
	private Integer numberResultsPerPage;
	private Integer currentPage;
	private Integer firstItemOnPage; //=(currentPage - 1)*numberResultsPerPage
	private boolean isOrderByPopularity;

	/** where to look for the given search criteria - at the moment podcast and episodes is availabel */
	private String searchTarget;

	/** List of selected categories id to be looked for */
	private List<Integer> categId;

	/** look for videos or audio files, or both (identified by "all") */
	private MediaType mediaType;

	/** contains the target where to search the terms/words in **/
	private String termsSearchTarget;

	/** order by criteria **/
	private OrderByOption orderBy;

	/**flag to mark that the model attribute is for feed generation */
	private boolean isForFeed;

	/** id of the tags that is being looked for */
	private Integer tagId;

	/** placeholder for the query string to be passed to the next request */
	private StringBuilder queryString;

	/** number of result pages - transmitted via the url */
	private Integer nrResultPages;

	/** number of results found for the search criteria - transmitted also via the url */
	private Integer nrOfResults;

	public static class Builder {

		String queryText;
		String searchTarget;
		MediaType mediaType;
		OrderByOption orderBy;
		String searchMode;
		Integer numberResultsPerPage;
		Integer currentPage;
		List<Integer> categId;
		String anyOfTheseWords;
		String allTheseWords;
		String exactPhrase;
		String noneOfTheseWords;
		Integer tagId;
        LanguageCode languageCode;

		public SearchData build(){
			return new SearchData(this);
		}

		public Builder queryText(String val){
			queryText = val;
			return this;
		}

		public Builder tagId(Integer val){
			tagId = val;
			return this;
		}

		public Builder exactPhrase(String val){
			exactPhrase = val;
			return this;
		}

		public Builder noneOfTheseWords(String val){
			noneOfTheseWords = val;
			return this;
		}

		public Builder anyOfTheseWords(String val){
			anyOfTheseWords = val;
			return this;
		}

		public Builder allTheseWords(String val){
			allTheseWords = val;
			return this;
		}

		public Builder categId(List<Integer> val){
			if(categId!=null && val.isEmpty()) categId=null;
            else categId = val;
			return this;
		}

		public Builder searchTarget(String val){
			searchTarget = val;
			return this;
		}

		public Builder mediaType(MediaType val){
			mediaType = val;
			return this;
		}

		public Builder orderBy(OrderByOption val){
			orderBy = val;
			return this;
		}

        public Builder languageCode(LanguageCode val){
            languageCode = val;
            return this;
        }
		public Builder searchMode(String val){
			searchMode = val;
			return this;
		}

		public Builder numberResultsPerPage(Integer val){
			numberResultsPerPage = val;
			return this;
		}

		public Builder currentPage(Integer val){
			currentPage = val;
			return this;
		}

	}

	public Integer getNrResultPages() {
		return nrResultPages;
	}


	public void setNrResultPages(Integer nrResultPages) {
		this.nrResultPages = nrResultPages;
	}


	public Integer getNrOfResults() {
		return nrOfResults;
	}


	public void setNrOfResults(Integer nrOfResults) {
		this.nrOfResults = nrOfResults;
	}


	/**
	 * default constructor
	 */
	public SearchData(){};


	public SearchData(Builder builder) {
		queryText = builder.queryText;
		searchTarget = builder.searchTarget;
		mediaType = builder.mediaType;
		orderBy = builder.orderBy;
		searchMode = builder.searchMode;
		numberResultsPerPage = builder.numberResultsPerPage;
		currentPage = builder.currentPage;
		tagId = builder.tagId;
		categId = builder.categId;
		anyOfTheseWords = builder.anyOfTheseWords;
		allTheseWords = builder.allTheseWords;
		noneOfTheseWords = builder.noneOfTheseWords;
		exactPhrase = builder.exactPhrase;
        languageCode = builder.languageCode;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((allTheseWords == null) ? 0 : allTheseWords.hashCode());
		result = prime * result
				+ ((anyOfTheseWords == null) ? 0 : anyOfTheseWords.hashCode());
		result = prime * result + ((categId == null) ? 0 : categId.hashCode());
		result = prime * result
				+ ((currentPage == null) ? 0 : currentPage.hashCode());
		result = prime * result
				+ ((exactPhrase == null) ? 0 : exactPhrase.hashCode());
		result = prime * result
				+ ((firstItemOnPage == null) ? 0 : firstItemOnPage.hashCode());
		result = prime * result + (isForFeed ? 1231 : 1237);
		result = prime * result + (isOrderByPopularity ? 1231 : 1237);
		result = prime * result
				+ ((languageCode == null) ? 0 : languageCode.hashCode());
		result = prime * result
				+ ((mediaType == null) ? 0 : mediaType.hashCode());
		result = prime
				* result
				+ ((noneOfTheseWords == null) ? 0 : noneOfTheseWords.hashCode());
		result = prime
				* result
				+ ((numberResultsPerPage == null) ? 0 : numberResultsPerPage
						.hashCode());
		result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
		result = prime * result
				+ ((queryText == null) ? 0 : queryText.hashCode());
		result = prime
				* result
				+ ((queryTextInNaturalMode == null) ? 0
						: queryTextInNaturalMode.hashCode());
		result = prime * result + (int) (searchId ^ (searchId >>> 32));
		result = prime * result
				+ ((searchMode == null) ? 0 : searchMode.hashCode());
		result = prime * result
				+ ((searchTarget == null) ? 0 : searchTarget.hashCode());
		result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
		result = prime
				* result
				+ ((termsSearchTarget == null) ? 0 : termsSearchTarget
						.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchData other = (SearchData) obj;
		return this.hashCode() == other.hashCode();
	}


	public StringBuilder getQueryString() {
		return queryString;
	}


	public void setQueryString(StringBuilder queryString) {
		this.queryString = queryString;
	}


	public Integer getTagId() {
		return tagId;
	}


	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}


	public boolean isForFeed() {
		return isForFeed;
	}


	public void setForFeed(boolean isForFeed) {
		this.isForFeed = isForFeed;
	}


	public OrderByOption getOrderBy() {
		return orderBy;
	}


	public void setOrderBy(OrderByOption orderBy) {
		this.orderBy = orderBy;
	}


	public String getTermsSearchTarget() {
		return termsSearchTarget;
	}

	public void setTermsSearchTarget(String termsSearchTarget) {
		this.termsSearchTarget = termsSearchTarget;
	}

	public String getExactPhrase() {
		return exactPhrase;
	}


	public void setExactPhrase(String exactPhrase) {
		this.exactPhrase = exactPhrase;
	}

	public String getQueryTextInNaturalMode() {
		return queryTextInNaturalMode;
	}

	public void setQueryTextInNaturalMode(String queryTextInNaturalMode) {
		this.queryTextInNaturalMode = queryTextInNaturalMode;
	}

	public String getAnyOfTheseWords() {
		return anyOfTheseWords;
	}

	public void setAnyOfTheseWords(String anyOfTheseWords) {
		this.anyOfTheseWords = anyOfTheseWords;
	}


	public String getAllTheseWords() {
		return allTheseWords;
	}


	public void setAllTheseWords(String allTheseWords) {
		this.allTheseWords = allTheseWords;
	}


	public String getNoneOfTheseWords() {
		return noneOfTheseWords;
	}


	public void setNoneOfTheseWords(String noneOfTheseWords) {
		this.noneOfTheseWords = noneOfTheseWords;
	}


	public String getQueryText() {
		return queryText;
	}


	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}


	public long getSearchId() {
		return searchId;
	}

	public void setSearchId(long searchId) {
		this.searchId = searchId;
	}



	public MediaType getMediaType() {
		return mediaType;
	}


	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}


	public String getSearchTarget() {
		return searchTarget;
	}

	public void setSearchTarget(String searchTarget) {
		this.searchTarget = searchTarget;
	}


	public List<Integer> getCategId() {
		return categId;
	}


	public void setCategId(List<Integer> categId) {
		this.categId = categId;
	}


	public Integer getFirstItemOnPage() {
		return firstItemOnPage;
	}

	public void setFirstItemOnPage(Integer firstItemOnPage) {
		this.firstItemOnPage = firstItemOnPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getNumberResultsPerPage() {
		return numberResultsPerPage;
	}

	public void setNumberResultsPerPage(Integer numberResultsPerPage) {
		this.numberResultsPerPage = numberResultsPerPage;
	}


	public LanguageCode getLanguageCode() {
		return languageCode;
	}


	public void setLanguageCode(LanguageCode languageCode) {
		this.languageCode = languageCode;
	}


	public boolean isOrderByPopularity() {
		return isOrderByPopularity;
	}

	public void setOrderByPopularity(boolean isOrderByPopularity) {
		this.isOrderByPopularity = isOrderByPopularity;
	}

	public void setSearchMode(String searchMode) {
		this.searchMode = searchMode;
	}

	public String getSearchMode() {
		return searchMode;
	}



}
