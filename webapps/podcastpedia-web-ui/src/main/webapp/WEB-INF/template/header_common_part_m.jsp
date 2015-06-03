<%@page import="org.podcastpedia.web.searching.SearchData"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="logo_and_search"  class="section group">
	<!-- start header -->
	<div id="logo" class="col span_1_of_2">
		<c:url value="/" var="urlStartPage" />
		<h1><a href="${urlStartPage}" class="icon-podcast"><span id="logo_title">Podcastpedia.org</span></a></h1>
		<p id="logo_subtitle">Knowledge to go</p>		
	</div>
	
	<!-- when advanced search form do not display the search bar -->
	<c:if test="${not isAdvancedSearchPage}">
		<div id="search_bar" class="col span_1_of_2">
			<c:url value="/search/advanced_search/results" var="searchURL" />
			<form:form id ="search_bar_form" method="GET" modelAttribute="advancedSearchData" action="${searchURL}/">
				<fieldset>										
					<div id="searchImageWrapper">
						<input type="submit" id="search_image"/> 
					</div>						
					<div id="queryTextWrapper">
						<spring:message var="textGetAllPodcastsI18N" code="search.defaultSearchText"/>
						<input name="queryText" id="queryText" placeholder="${textGetAllPodcastsI18N}">
					</div>					
				</fieldset>
				<form:hidden path="numberResultsPerPage"/>
				<form:hidden path="searchMode"/>
				<form:hidden path="currentPage"/>
			</form:form>
			<c:url value="/search/advanced_search" var="advancedSearchURL" />
			<p id="search_bar_underText">
				<a href="${advancedSearchURL}"><spring:message code="search.advancedSearchURL"/></a>
			</p>
		</div>			
	</c:if>
	<div class="clear"></div>	
</div>



