<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div id="share_and_quick_search">
	<div id="quick_search" class="shadowy">
		<c:url value="/search/advanced_search/results" var="advancedSearchResultsURL" />
		<form:form id="quick_search_form" method="GET" modelAttribute="advancedSearchData" action="${advancedSearchResultsURL}">
			<fieldset>
				<legend>Quick search</legend>
				<label for="searchTarget" class="label">
					<spring:message code="quick_search.label.return" text="Return"/>
					<form:select path="searchTarget" id="searchTarget">
						<spring:message var="podcasts_label" code="quick_search.podcasts" text="Podcasts"/>
						<form:option value="podcasts" label="${podcasts_label}"/>
						<spring:message var="episodes_label" code="quick_search.episodes" text="Episodes"/>
						<form:option value="episodes" label="${episodes_label}"/>
					</form:select>
				</label>
				<label id="qs-media-type" for="mediaType" class="label">
					<spring:message code="quick_search.label.media_type" text="Media type"/>
					<form:select path="mediaType" multiple="false" id="mediaType">
						<form:option label="Audio & Video" value="" />
						<form:options items="${mediaTypes}" />
					</form:select>
				</label>
				<label id="qs-lang" for="languageCode" class="label">
					<spring:message code="quick_search.label.language" text="Language"/>
					<form:select path="languageCode" id="language">
						<spring:message var="all_label" code="global.all" text="All"/>
						<form:option value="" label="${all_label}"/>
						<c:forEach items="${languageCodes}" var="languageCode">
							<spring:message var="langCodeVar" code="${languageCode}"/>
						    <form:option value="${languageCode.code}" label='${langCodeVar}'/>
						</c:forEach>
					</form:select>
				</label>

				<label for="category" class="label">
					<spring:message code="quick_search.label.category" text="Category"/>
					<form:select path="categId" multiple="false" id="category">
						<form:option value="" label="${all_label}"></form:option>
						<c:forEach items="${allCategories}" var="category">
							<form:option value="${category.categoryId}"><spring:message code="${category.name}"/></form:option>
						</c:forEach>
					</form:select>
				</label>

				<label id="qs-order-by" for="orderBy" class="label">
					<spring:message code="quick_search.label.order_by" text="Order by"/>
					<form:select path="orderBy" id="orderBy">
						<c:forEach items="${orderByOptions}" var="orderByOption">
							<spring:message var="orderByVar" code="${orderByOption}"/>
						    <form:option value="${orderByOption.code}" label='${orderByVar}'/>
						</c:forEach>
					</form:select>
				</label>
				<form:hidden path="numberResultsPerPage"/>
				<form:hidden path="searchMode"/>
				<form:hidden path="currentPage"/>
				<input type="submit" value="GO"  id="subscribe"/>
			</fieldset>
		</form:form>
	</div>
	<div class="clear"></div>
</div>
<div class="clear"></div>

<div id="most_popular_and_newest_wrapper" class="section group">
	<div id="chart_most_popular_podcasts" class="chart common_radius shadowy col span_1_of_2">
		<h2 class="header_charts"><spring:message code="header.new_entries" text="Most popular"/></h2>
		<ul>
			<c:forEach items="${newEntries}" var="podcast">
				<li  class="bg_color">
					<c:choose>
						<c:when test="${podcast.identifier == null}">
							<c:set var="urlPodcast" value="https://www.podcastpedia.org/podcasts/${podcast.podcastId}/${podcast.titleInUrl}"/>
						</c:when>
						<c:otherwise>
							<c:set var="urlPodcast" value="https://www.podcastpedia.org/${podcast.identifier}"/>
						</c:otherwise>
					</c:choose>
			    	<a href="${urlPodcast}">
			    		<img src="${podcast.urlOfImageToDisplay}" alt="Podcast image">
			    		<span class="pod_title_start_smaller">
			    			<c:out value="${fn:substring(podcast.title,0,90)}"/>
			    		</span>
			    		<span class="pod_title_start">
			    			<c:out value="${fn:substring(podcast.title,0,140)}"/>
						</span>
			    	</a>
					<p class="pub_date">
						<fmt:formatDate pattern="yyyy-MM-dd" value="${podcast.publicationDate}" />
					</p>
				</li>
			</c:forEach>
		</ul>
		<div class="end_of_chart_div">
			<c:url value="/static/images/feed-icon-14x14.png" var="urlFeedPic"/>
			<c:url value="/static/images/atomfeed14.png" var="urlFeedPicAtom"/>
			<span class="feed_links">
				<span class="feed_link">
					<c:url value="/feeds/new-entries.rss" var="urlNewEntriesFeedRSS" />
					<a href="${urlNewEntriesFeedRSS}" target="_blank" class="icon-feed-rss">RSS</a>
				</span>
				<span class="feed_link">
					<c:url value="/feeds/new-entries.atom" var="urlNewEntriesFeedAtom" />
					<a href="${urlNewEntriesFeedAtom}" target="_blank" class="icon-feed-atom">Atom</a>
				</span>
			</span>
		</div>
	</div>
	<div id="chart_newest_podcasts" class="chart common_radius shadowy col span_1_of_2">
		<h2 class="header_charts"><spring:message code="header.newest" text="Newest"/></h2>
		<ul>
			<c:forEach items="${lastUpdatedPodcasts}" var="podcast">
				<li  class="bg_color">
					<c:choose>
						<c:when test="${podcast.identifier == null}">
							<c:set var="urlPodcast" value="https://www.podcastpedia.org/podcasts/${podcast.podcastId}/${podcast.titleInUrl}"/>
						</c:when>
						<c:otherwise>
							<c:set var="urlPodcast" value="https://www.podcastpedia.org/${podcast.identifier}"/>
						</c:otherwise>
					</c:choose>
					<a href="${urlPodcast}">
						<img src="${podcast.urlOfImageToDisplay}" alt="Podcast image">
			    		<span class="pod_title_start_smaller">
			    			<c:out value="${fn:substring(podcast.title,0,90)}"/>
			    		</span>
			    		<span class="pod_title_start">
			    			<c:out value="${fn:substring(podcast.title,0,140)}"/>
						</span>
					</a>
					<p class="pub_date">
						<fmt:formatDate pattern="yyyy-MM-dd" value="${podcast.publicationDate}" />
					</p>
				</li>
			</c:forEach>
		</ul>
		<div class="end_of_chart_div">
			<span class="feed_links">
				<span class="feed_link">
					<c:url value="/feeds/newest.rss" var="urlNewestFeedRSS" />
					<a href="${urlNewestFeedRSS}" target="_blank" class="icon-feed-rss">RSS</a>
				</span>
				<span class="feed_link">
					<c:url value="/feeds/newest.atom" var="urlNewestFeedAtom" />
					<a href="${urlNewestFeedAtom}" target="_blank" class="icon-feed-atom">Atom</a>
				</span>
			</span>
		</div>
	</div>
</div>

<div class="clear"></div>

<div id = "wrapper" class="section group">
	<div id="chart_most_popular_language" class="chart common_radius shadowy  col span_1_of_2">
			<h2 class="header_charts"><spring:message code="header.random_pods" text="Random podcasts"/></h2>
			<ul>
				<c:forEach items="${randomPodcasts}" var="podcast">
					<li  class="bg_color">
						<c:choose>
							<c:when test="${podcast.identifier == null}">
								<c:set var="urlPodcast" value="https://www.podcastpedia.org/podcasts/${podcast.podcastId}/${podcast.titleInUrl}"/>
							</c:when>
							<c:otherwise>
								<c:set var="urlPodcast" value="https://www.podcastpedia.org/${podcast.identifier}"/>
							</c:otherwise>
						</c:choose>
						<a href="${urlPodcast}">
							<img src="${podcast.urlOfImageToDisplay}" alt="Podcast image">
			    		<span class="pod_title_start_smaller">
			    			<c:out value="${fn:substring(podcast.title,0,90)}"/>
			    		</span>
			    		<span class="pod_title_start">
			    			<c:out value="${fn:substring(podcast.title,0,140)}"/>
						</span>
						</a>
						<p class="pub_date">
							<fmt:formatDate pattern="yyyy-MM-dd" value="${podcast.publicationDate}" />
						</p>
					</li>
				</c:forEach>
			</ul>
		<div class="end_of_chart_div">
			<c:url value="/static/images/feed-icon-14x14.png" var="urlFeedPic"/>
			<span class="feed_links">
				<span class="feed_link">
					<c:url value="/feeds/random.rss" var="urlRandomFeedRSS" />
					<a href="${urlRandomFeedRSS}" target="_blank" class="icon-feed-rss">RSS</a>
				</span>
				<span class="feed_link">
					<c:url value="/feeds/random.atom" var="urlRandomFeedAtom" />
					<a href="${urlRandomFeedAtom}" target="_blank" class="icon-feed-atom">Atom</a>
				</span>
			</span>
		</div>
	</div>
	<div id="chart_recommended" class="chart common_radius shadowy  col span_1_of_2">
			<h2 class="header_charts" ><spring:message code="header.chart.recommendations" text="Recommendations"/></h2>
			<ul>
				<c:forEach items="${recommendedPodcasts}" var="podcast">
					<li  class="bg_color">
						<c:choose>
							<c:when test="${podcast.identifier == null}">
								<c:set var="urlPodcast" value="https://www.podcastpedia.org/podcasts/${podcast.podcastId}/${podcast.titleInUrl}"/>
							</c:when>
							<c:otherwise>
								<c:set var="urlPodcast" value="https://www.podcastpedia.org/${podcast.identifier}"/>
							</c:otherwise>
						</c:choose>
						<a href="${urlPodcast}">
							<img src="${podcast.urlOfImageToDisplay}" alt="Podcast image">
				    		<span class="pod_title_start_smaller">
				    			<c:out value="${fn:substring(podcast.title,0,90)}"/>
				    		</span>
				    		<span class="pod_title_start">
				    			<c:out value="${fn:substring(podcast.title,0,140)}"/>
							</span>
						</a>
						<p class="pub_date">
							<fmt:formatDate pattern="yyyy-MM-dd" value="${podcast.publicationDate}" />
						</p>
					</li>
				</c:forEach>
			</ul>
		<div class="end_of_chart_div">
			<c:url value="/static/images/feed-icon-14x14.png" var="urlFeedPic"/>
			<span class="feed_links">
				<span class="feed_link">
					<c:url value="/feeds/recommended.rss" var="urlRecommendedFeedRSS" />
					<a  target="_blank" href="${urlRecommendedFeedRSS}" class="icon-feed-rss">RSS</a>
				</span>
				<span class="feed_link">
					<c:url value="/feeds/recommended.atom" var="urlRecommendedFeedAtom" />
					<a  target="_blank"  href="${urlRecommendedFeedAtom}" class="icon-feed-atom">Atom</a>
				</span>
			</span>
		</div>
	</div>
</div>



