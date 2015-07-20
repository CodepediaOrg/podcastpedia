
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="jwplayerURL" value="/static/js/jwplayer/jwplayer.js"/>
<script type='text/javascript' src='${jwplayerURL}'></script>

<!-- based on number of episodes will proceed further
	if number of episodes > 20 will display 20 per page, if not 10  
-->
<div class="results_list">
	<c:forEach items="${episodes}" var="episode" varStatus="loop">
		<c:url var="episodeURL" value="/podcasts/${episode.podcastId}/${podcastTitleInUrl}/episodes/${episode.episodeId}/${episode.titleInUrl}"/>
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
				<img alt="${episode.mediaType}"  class="play other_ep media_type_pic" src='<c:url value="/static/images/media_type/m_play_${episode.mediaType}_110.png"/>'>
			</div>		
	    	<div class="metadata_desc">						
				<a href="${episodeURL}"> ${episode.title} </a>
				<div class="pub_date">
					<fmt:formatDate pattern="yyyy-MM-dd" value="${episode.publicationDate}" />
					<c:choose>
						<c:when test="${episode.isNew == 1}">
							<span class="ep_is_new"><spring:message code="new"/></span>
						</c:when>	
					</c:choose>
				</div>
				<div class="ep_desc">
					${fn:substring(episode.description,0,600)}   		
				</div> 
			</div>
			<div class="clear"></div>
			<div class="not_shown">					
				<div id='mediaspace${loop.index}' class="jwp">Flashplayer not supported</div>			
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
			<div class="social_and_download">
				<img class="social_share"  src='<c:url value="/static/images/logos/social_logos.png"/>'>	
				<span class="item_url">http://www.podcastpedia.org/podcasts/${episode.podcastId}/${episode.podcastTitleInUrl}/episodes/${episode.episodeId}/${episode.titleInUrl}</span>							
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
	 	
<div class="clear"></div>
<c:if test="${numberOfEpisodePages > 1}">	
	<div class="pagination">
		<ul>			
			<c:forEach begin="1" end="${numberOfEpisodePages}" var="i">	
				<c:url var="allEpisodesUrl" value="/podcasts/${podcastId}/${podcastTitleInUrl}/episodes/archive/pages/${i}"/>
				<li>
					<c:choose>
						<c:when test="${i==currentPage}">
							<a href="${allEpisodesUrl}" class="currentpage"> ${numberOfEpisodesPerPage*(i-1) + 1} - ${numberOfEpisodesPerPage*i} </a>
						</c:when>
						<c:otherwise>
							<a href="${allEpisodesUrl}"> ${numberOfEpisodesPerPage*(i-1) + 1} - ${numberOfEpisodesPerPage*i} </a>
						</c:otherwise>
					</c:choose> 
				</li>			
			</c:forEach>
		</ul>
	</div>						
</c:if> 
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