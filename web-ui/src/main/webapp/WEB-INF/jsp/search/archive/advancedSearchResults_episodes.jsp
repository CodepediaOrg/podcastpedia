<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<c:url var="jwplayerURL" value="/static/js/jwplayer/jwplayer.js"/>
<script type='text/javascript' src='${jwplayerURL}'></script>

<div id="search_feed_link">
	<c:url value="/static/images/feed-icon-14x14.png" var="urlFeedPic"/>
	<c:url value="/static/images/atomfeed14.png" var="urlFeedPicAtom"/>
	<c:url value="/feeds/search/episodes.rss?${queryString}&amp;currentPage=${advancedSearchResult.currentPage}" var="rssURL"/>
	<c:url value="/feeds/search/episodes.atom?${queryString}&amp;currentPage=${advancedSearchResult.currentPage}" var="atomURL"/>
	<spring:message code="search.subscribe_via_feed"/>
	<a href="${atomURL}" target="_blank"> Atom <img src="${urlFeedPicAtom}" alt="atom"></a> &nbsp;
	<a href="${rssURL}" target="_blank"> RSS <img src="${urlFeedPic}" alt="rss"></a>
</div>
<div class="clear"></div>

<div class="pagination pagination_top">
	<%@ include file="pagination_page_episodes.jsp" %>
</div>

<div class="results_list">
	<c:forEach items="${advancedSearchResult.episodes}" var="episode" varStatus="loop">
		<c:url var="episodeUrl" value="/podcasts/${episode.podcastId}/${episode.podcast.titleInUrl}/episodes/${episode.episodeId}/${episode.titleInUrl}"/>
	    <div class="bg_color shadowy item_wrapper">
			<div class="rating_media_type">
				<div class="star_rating">
					<form>
						<c:set var="ratingValue" value="${fn:substringBefore(episode.rating, '.')}"/>
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
				<img alt="${episode.mediaType}" src='${episode.podcast.urlOfImageToDisplay}' class="search_image">
			</div>
	    	<div class="metadata_desc">
				<a href="${episodeUrl}"> <c:out value="${episode.title}"/> </a>
				<div class="pub_date_media_type">
					<div class="pub_date">
						<fmt:formatDate pattern="yyyy-MM-dd" value="${episode.publicationDate}" />
						<c:choose>
							<c:when test="${episode.isNew == 1}">
								<span class="ep_is_new"><spring:message code="new"/></span>
							</c:when>
						</c:choose>
					</div>
				</div>
				<div class="ep_desc">
					${fn:substring(episode.description,0,600)}
				</div>
			</div>
			<div class="clear"></div>
			<div class="not_shown">
				<c:url var="currentEpisodeURL" value="/podcasts/${episode.podcastId}/${podcast_title_in_url}/episodes/${episode.episodeId}/${episode.titleInUrl}?show_other_episodes=true"/>
				<div id='mediaspace${loop.index}' class="jwp"><spring:message code="ep_details.pl_not_shown_part1" text="If player not shown please"/> <a href="${currentEpisodeURL}"> <spring:message code="global.click_here" text="click here"/> </a></div>

				<!-- switch player CAN or CANNOT be displayed -->
				<c:choose>
					<c:when test="${episode.mediaType == 'Audio'}">
						<script type='text/javascript'>
						  jwplayer('mediaspace${loop.index}').setup({
						    'controlbar': 'bottom',
						    'width': '100%',
						    'aspectratio': '16:3',
						    'file': '${episode.mediaUrl}'
						  });
						</script>
					</c:when>
					<c:otherwise>
						<script type='text/javascript'>
						  jwplayer('mediaspace${loop.index}').setup({
						    'controlbar': 'bottom',
						    'width': '100%',
						    'aspectratio': '16:9',
						    'file': '${episode.mediaUrl}'
						  });
						</script>
					</c:otherwise>
				</c:choose>
			</div>
			<img alt="Play ${episode.mediaType}" src='<c:url value="/static/images/media_type/play_${episode.mediaType}.png"/>' class="play">
			<div class="social_and_download">
					<img class="social_share"  src='<c:url value="/static/images/logos/social_logos.png"/>'>
					<span class="item_url">http://www.podcastpedia.org/podcasts/${episode.podcastId}/${episode.podcast.titleInUrl}/episodes/${episode.episodeId}/${episode.titleInUrl}</span>
				<div class="dwnld_ep">
					<a class="tooltip" href="${episode.mediaUrl}">
						<spring:message code="global.dwnld.s" text="Download last episode"/>
						<span class="custom help"><img src="<c:url value="/static/images/Floppy_Disk_clip_art_small.png"/>" alt="Download" height="48" width="48" /><em><spring:message code="global.dwnld.s" text="Download last episode"/></em><spring:message code="global.dwnld.ep.tip" text="Use \"Save as...\" to download"/></span>
					</a>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</c:forEach>
</div>

	<!--  format to display results " 1 PREVIOUS 4 5 6 7 8 NEXT 20 " -->
<div class="pagination pagination_bottom">
	<%@ include file="pagination_page_episodes.jsp" %>
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
