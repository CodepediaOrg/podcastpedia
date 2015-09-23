<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:url var="jwplayerURL" value="/static/js/jwplayer/jwplayer.js"/>
<script type='text/javascript' src='${jwplayerURL}'></script>

<div id="current_episode" class="bg_color common_radius shadowy">
	<h2>
		${episode.title}
	</h2>
	<div class="pub_date">
		<fmt:formatDate pattern="yyyy-MM-dd" value="${episode.publicationDate}"/>
		<c:choose>
			<c:when test="${episode.isNew == 1}">
				<span class="ep_is_new"><spring:message code="new"/></span>
			</c:when>
		</c:choose>
	</div>

	<c:url var="currentEpisodeURL" value="/podcasts/${episode.podcastId}/${podcast_title_in_url}/episodes/${episode.episodeId}/${episode.titleInUrl}?show_other_episodes=true"/>
	<div id='mediaspace'><spring:message code="ep_details.pl_not_shown_part1" text="If player not shown please"/> <a href="${currentEpisodeURL}"> <spring:message code="global.click_here" text="click here"/> </a></div>

	<!-- switch player CAN or CANNOT be displayed -->
	<c:choose>
		<c:when test="${episode.mediaType == 'Audio'}">
			<script type='text/javascript'>
			  jwplayer('mediaspace').setup({
			    'controlbar': 'bottom',
			    'width': '100%',
			    'aspectratio': '16:5',
			    'file': '${episode.mediaUrl}'
			  });
			</script>
		</c:when>
		<c:otherwise>
			<script type='text/javascript'>
			  jwplayer('mediaspace').setup({
			    'controlbar': 'bottom',
			    'width': '100%',
			    'aspectratio': '16:9',
			    'file': '${episode.mediaUrl}'
			  });
			</script>
		</c:otherwise>
	</c:choose>
	<div class="ep_desc">
		${fn:substring(episode.description,0,280)}
	</div>
	<div class="ep_desc_bigger">
		${fn:substring(episode.description,0,600)}
	</div>

	<div id="social_and_download_curr_ep">
		<a href="#-1" class="icon-share-episode btn-share">Share </a>
		<a class="icon-download-ep btn-share" href="${episode.mediaUrl}" target="_blank">
			<spring:message code="global.dwnld.s" text="Download episode"/>
		</a>
		<span class="item_url_ep">http://www.podcastpedia.org/podcasts/${episode.podcastId}/${podcast_title_in_url}/episodes/${episode.episodeId}/${episode.titleInUrl}</span>
	</div>
	<div class="clear"></div>

</div>

<div id="episode_metadata" class="bg_color common_radius shadowy" >
   	<img src="${episode.podcast.urlOfImageToDisplay}">
	<p>
		<c:choose>
			<c:when test="${episode.podcast.identifier == null}">
				<c:set var="podcast_link" value="/podcasts/${episode.podcastId}/${episode.podcast.titleInUrl}"/>
			</c:when>
			<c:otherwise>
				<c:set var="podcast_link" value="/${episode.podcast.identifier}"/>
			</c:otherwise>
		</c:choose>
		<a href="${podcast_link}" class="btn-share">${episode.podcast.title}</a>
	</p>
	<p id="feed-and-ep-link">
		<a href="${episode.podcast.link}" target="_blank" class="icon-globe-producer producer-social" title="Website"></a>
		<a href="${episode.podcast.url}" target="_blank"  class="icon-feed-producer  producer-social" title="Feed"></a>
		<c:if test="${not empty episode.podcast.twitterPage}">
			<a href="${episode.podcast.twitterPage}" target="_blank" class="icon-twitter-producer producer-social" title="Twitter"></a>
		</c:if>
		<c:if test="${not empty episode.podcast.fbPage}">
			<a href="${episode.podcast.fbPage}" target="_blank" class="icon-facebook-producer producer-social" title="Facebook Fan Page"></a>
		</c:if>
		<c:if test="${not empty episode.podcast.gplusPage}">
			<a href="${episode.podcast.gplusPage}" target="_blank" class="icon-google-plus-producer producer-social" title="Google+"></a>
		</c:if>
	</p>
	<!-- TODO when email job is ready uncomment this
	<p>
	 	<a id="subscribeItAnchor">Subscribe</a>
	</p>
	 -->
	<p style="clear:both"/>
</div>

<div class="results_list">
 	<h3><spring:message code="pod_details.recent_episodes" text="Recent episodes: "/></h3>
	<c:forEach items="${otherEpisodes}" var="episodeIterator" varStatus="loop">
	  	<div class="bg_color shadowy item_wrapper">
	    	<div class="title-and-pub-date">
				<c:choose>
					<c:when test="${episode.mediaType == 'Audio'}">
						<div class="icon-audio-episode"></div>
					</c:when>
					<c:otherwise>
						<div class="icon-video-episode"></div>
					</c:otherwise>
				</c:choose>
				<c:url var="episodeURL" value="/podcasts/${episode.podcastId}/${episode.podcast.titleInUrl}/episodes/${episodeIterator.episodeId}/${episodeIterator.titleInUrl}"/>
				<a href="${episodeURL}" class="item_title">${episodeIterator.title}</a>
				<div class="pub_date">
					<fmt:formatDate pattern="yyyy-MM-dd" value="${episodeIterator.publicationDate}" />
					<c:choose>
						<c:when test="${episodeIterator.isNew == 1}">
							<span class="ep_is_new"><spring:message code="new"/></span>
						</c:when>
					</c:choose>
				</div>
				<div class="clear"></div>
			</div>
			<hr>
			<div class="ep_desc">
				<a href="${episodeURL}" class="item_desc">
					${fn:substring(episodeIterator.description,0,280)}
				</a>
			</div>
			<div class="ep_desc_bigger">
				<a href="${episodeURL}" class="item_desc">
					${fn:substring(episodeIterator.description,0,600)}
				</a>
			</div>
			<div class="clear"></div>
			<div class="not_shown">
				<div id='mediaspace${loop.index}' class="jwp">Flashplayer not supported</div>
				<!-- switch player CAN or CANNOT be displayed -->
				<c:choose>
					<c:when test="${episodeIterator.mediaType == 'Audio'}">
						<script type='text/javascript'>
						  jwplayer('mediaspace${loop.index}').setup({
						    'controlbar': 'bottom',
						    'width': '100%',
						    'aspectratio': '16:5',
						    'file': '${episodeIterator.mediaUrl}'
						  });
						</script>
					</c:when>
					<c:otherwise>
						<script type='text/javascript'>
						  jwplayer('mediaspace${loop.index}').setup({
						    'controlbar': 'bottom',
						    'width': '100%',
						    'aspectratio': '16:9',
						    'file': '${episodeIterator.mediaUrl}'
						  });
						</script>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="social_and_download">
				<a href="#${2*loop.index}" class="icon-play-episode btn-share">Play</a>
				<a href="#${2*loop.index + 1}" class="icon-share-episode btn-share">Share</a>
				<a class="icon-download-ep btn-share" href="${episodeIterator.mediaUrl}" target="_blank"><spring:message code="global.dwnld.s" text="Download last episode"/></a>
				<span class="item_url">http://www.podcastpedia.org/podcasts/${episode.podcastId}/${episode.podcast.titleInUrl}/episodes/${episodeIterator.episodeId}/${episodeIterator.titleInUrl}</span>
			</div>
		</div>
	</c:forEach>
	<input type="hidden" name="offset" id="offset-data-id" value="5"/>
	<input type="hidden" name="podcastId" id="sub_podcastId" value="${episode.podcastId}"/>
</div>

<button type="button" id="more-episodes" style="display: block; border-radius:5px; padding:5px; width:100%;font-family:arial,sans-serif; font-size:1.5em;color:#2F4051" class="shadowy"><strong><spring:message code="ep_details.more_episodes" text="More"/></strong></button>
<p id="archive_all_episodes">
	<c:url var="allEpisodesUrl" value="/podcasts/${episode.podcastId}/${episode.titleInUrl}/episodes/archive/pages/1"/>
	<a href="${allEpisodesUrl}"> <spring:message code="pod_details.archive" text="Archive - all episodes"/> </a>
</p>

<div class="clear"></div>

<!-- javascript libraries required -->
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="<c:url value="/static/js/podcast/main.js" />"></script>

<div id="disqus_comments" class="shadowy">
    <div id="disqus_thread"></div>
    <script type="text/javascript">
        /* * * CONFIGURATION VARIABLES: EDIT BEFORE PASTING INTO YOUR WEBPAGE * * */
        var disqus_shortname = 'podcastpedia'; // required: replace example with your forum shortname

        /* * * DON'T EDIT BELOW THIS LINE * * */
        (function() {
            var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
            dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
            (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
        })();
    </script>
    <noscript>Please enable JavaScript to view the <a href="http://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
    <a href="http://disqus.com" class="dsq-brlink">comments powered by <span class="logo-disqus">Disqus</span></a>
</div>

