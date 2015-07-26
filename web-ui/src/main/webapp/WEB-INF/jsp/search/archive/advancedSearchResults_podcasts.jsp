<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<div id="search_feed_link">
	<c:url value="/static/images/feed-icon-14x14.png" var="urlFeedPic"/>
	<c:url value="/static/images/atomfeed14.png" var="urlFeedPicAtom"/>
	<c:url value="/feeds/search/podcasts.rss?${queryString}&amp;currentPage=${advancedSearchResult.currentPage}" var="rssURL"/>
	<c:url value="/feeds/search/podcasts.atom?${queryString}&amp;currentPage=${advancedSearchResult.currentPage}" var="atomURL"/>
	<spring:message code="search.subscribe_via_feed"/>
	<a href="${atomURL}" target="_blank"> Atom <img src="${urlFeedPicAtom}" alt="atom"></a> &nbsp;
	<a href="${rssURL}" target="_blank"> RSS <img src="${urlFeedPic}" alt="rss"> </a>
</div>
<div class="clear"></div>

<div class="pagination pagination_top">
	<%@ include file="pagination_page_podcasts.jsp" %>
</div>

	<div class="results_list">
		<c:forEach items="${advancedSearchResult.podcasts}" var="podcast" varStatus="loop">
			<div class="bg_color shadowy item_wrapper">
		    	<div class="rating_media_type">
					<div class="star_rating">
						<form>
							<c:set var="ratingValue" value="${fn:substringBefore(podcast.rating, '.')}"/>
							<c:forEach var="i" begin="1" end="20" step="1">
								<c:choose>
									<c:when test="${i == ratingValue}">
										<input type="radio" name="rating" value="${i}" class="star {split:4}" checked="checked"  disabled="disabled"/>
									</c:when>
									<c:otherwise>
										<input type="radio" name="rating" value="${i}" class="star {split:4}"  disabled="disabled"/>
									</c:otherwise>
								</c:choose>
							</c:forEach>
					 	</form>
					</div>
					<img alt="${podcast.mediaType}" src='${podcast.urlOfImageToDisplay}' class="search_image">
				</div>
		    	<div class="metadata_desc">
					<a href="<c:url value="/podcasts/${podcast.podcastId}/${podcast.titleInUrl}"/>">
						<c:out value="${podcast.title}"/>
					</a>
					<div class="pub_date_media_type">
						<div class="pub_date">
							<fmt:formatDate pattern="yyyy-MM-dd" value="${podcast.publicationDate}" />
						</div>
					</div>
				</div>
				<div>
					${fn:substring(podcast.description,0,600)}
				</div>
				<div class="clear"></div>
				<div class="social_and_download">
					<c:choose>
						<c:when test="${podcast.identifier == null}">
							<c:set var="podcast_link" value="http://www.podcastpedia.org/podcasts/${podcast.podcastId}/${podcast.titleInUrl}"/>
						</c:when>
						<c:otherwise>
							<c:set var="podcast_link" value="http://www.podcastpedia.org/${podcast.identifier}"/>
						</c:otherwise>
					</c:choose>
					<img class="social_share"  src='<c:url value="/static/images/logos/social_logos.png"/>'>
					<span class="item_url">${podcast_link}</span>
				</div>
				<div class="clear"></div>
			</div>
		</c:forEach>
	</div>

<div class="pagination pagination_bottom">
	<%@ include file="pagination_page_podcasts.jsp" %>
</div>
<div class="clear"></div>

<!-- javascript libraries required -->
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script type='text/javascript' src="<c:url value="/static/js/jquery/jquery.rating.pack.js"/>"></script>
<script type='text/javascript' src='<c:url value="/static/js/jquery/jquery.metadata.js"/>'></script>

<!-- include social scripts - fb, twitter, gplus -->
<%@ include file="/WEB-INF/jsp/common/social_scripts.jsp" %>
<!-- include loading dynamic player page -->
<%@ include file="/WEB-INF/jsp/common/load_player_dynamically.jsp" %>
<!-- dynamic social share -->
<%@ include file="/WEB-INF/jsp/common/social_share_dynamically.jsp" %>

