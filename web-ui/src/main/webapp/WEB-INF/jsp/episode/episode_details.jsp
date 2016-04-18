<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<c:url var="jwplayerURL" value="/static/js/jwplayer/jwplayer.js"/>
<script type='text/javascript' src='${jwplayerURL}'></script>
<script type="text/javascript">jwplayer.key="fr4dDcJMQ2v5OaYJSBDXPnTeK6yHi8+8B7H3bg==";</script>

<div id="current_episode" class="bg_color common_radius shadowy">
	<h2>
		${episodeDetails.title}
	</h2>
	<div class="pub_date">
		<fmt:formatDate pattern="yyyy-MM-dd" value="${episodeDetails.publicationDate}"/>
		<c:choose>
			<c:when test="${episodeDetails.isNew == 1}">
				<span class="ep_is_new"><spring:message code="new"/></span>
			</c:when>
		</c:choose>
	</div>

	<c:url var="currentEpisodeURL" value="/podcasts/${episodeDetails.podcastId}/${podcast_title_in_url}/episodes/${episodeDetails.episodeId}/${episodeDetails.titleInUrl}?show_other_episodes=true"/>
	<div id='mediaspace'><spring:message code="ep_details.pl_not_shown_part1" text="If player not shown please"/> <a href="${currentEpisodeURL}"> <spring:message code="global.click_here" text="click here"/> </a></div>

	<!-- switch player CAN or CANNOT be displayed -->
	<c:choose>
		<c:when test="${episodeDetails.mediaType == 'Audio'}">
			<script type='text/javascript'>
			  jwplayer('mediaspace').setup({
			    'controlbar': 'bottom',
			    'width': '100%',
			    'aspectratio': '16:5',
			    'file': '${episodeDetails.mediaUrl}'
			  });
			</script>
		</c:when>
		<c:otherwise>
			<script type='text/javascript'>
			  jwplayer('mediaspace').setup({
			    'controlbar': 'bottom',
			    'width': '100%',
			    'aspectratio': '16:9',
			    'file': '${episodeDetails.mediaUrl}'
			  });
			</script>
		</c:otherwise>
	</c:choose>
	<div class="ep_desc">
		${fn:substring(episodeDetails.description,0,280)}
	</div>
	<div class="ep_desc_bigger">
		${fn:substring(episodeDetails.description,0,600)}
	</div>

	<div id="social_and_download_curr_ep">
		<a href="#-1" class="icon-share-episode btn-share">Share </a>
		<a class="icon-download-ep btn-share" href="${episodeDetails.mediaUrl}" download>
			<spring:message code="global.dwnld.s" text="Download episode"/>
		</a>
		<span class="item_url_ep">https://www.podcastpedia.org/podcasts/${episodeDetails.podcastId}/${podcast_title_in_url}/episodes/${episodeDetails.episodeId}/${episodeDetails.titleInUrl}</span>
    <span class="item_title_ep">${episodeDetails.title}</span>
	</div>
	<div class="clear"></div>

</div>

<div id="episode_metadata" class="bg_color common_radius shadowy" >
   	<img src="${episodeDetails.podcast.urlOfImageToDisplay}">
	<p>
		<c:choose>
			<c:when test="${episodeDetails.podcast.identifier == null}">
				<c:set var="podcast_link" value="/podcasts/${episodeDetails.podcastId}/${episodeDetails.podcast.titleInUrl}"/>
			</c:when>
			<c:otherwise>
				<c:set var="podcast_link" value="/${episodeDetails.podcast.identifier}"/>
			</c:otherwise>
		</c:choose>
		<a href="${podcast_link}" class="btn-share">${episodeDetails.podcast.title}</a>
	</p>
	<p id="feed-and-ep-link">
		<a href="${episodeDetails.podcast.link}" target="_blank" class="icon-globe-producer producer-social" title="Website"></a>
		<a href="${episodeDetails.podcast.url}" target="_blank"  class="icon-feed-producer  producer-social" title="Feed"></a>
		<c:if test="${not empty episodeDetails.podcast.twitterPage}">
			<a href="${episodeDetails.podcast.twitterPage}" target="_blank" class="icon-twitter-producer producer-social" title="Twitter"></a>
		</c:if>
		<c:if test="${not empty episodeDetails.podcast.fbPage}">
			<a href="${episodeDetails.podcast.fbPage}" target="_blank" class="icon-facebook-producer producer-social" title="Facebook Fan Page"></a>
		</c:if>
		<c:if test="${not empty episodeDetails.podcast.gplusPage}">
			<a href="${episodeDetails.podcast.gplusPage}" target="_blank" class="icon-google-plus-producer producer-social" title="Google+"></a>
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
  <tags:episodes/>
	<input type="hidden" name="offset" id="offset-data-id" value="5"/>
	<input type="hidden" name="podcastId" id="sub_podcastId" value="${episodeDetails.podcastId}"/>
</div>

<button type="button" id="more-episodes" style="display: block; border-radius:5px; padding:5px; width:100%;font-family:arial,sans-serif; font-size:1.5em;color:#2F4051" class="shadowy"><strong><spring:message code="ep_details.more_episodes" text="More"/></strong></button>
<p id="archive_all_episodes">
	<c:url var="allEpisodesUrl" value="/podcasts/${episodeDetails.podcastId}/${episodeDetails.titleInUrl}/episodes/archive/pages/1"/>
	<a href="${allEpisodesUrl}"> <spring:message code="pod_details.archive" text="Archive - all episodes"/> </a>
</p>

<div class="clear"></div>

<!-- jquery dialogs -->
<div id="media_player_modal_dialog" title="Media player">
  <div id='mediaspace_modal'>Loading...</div>
</div>

<!-- javascript libraries required -->
<script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="<c:url value="/static/js/podcast/main.js" />"></script>
<script src="//code.jquery.com/ui/1.11.3/jquery-ui.min.js"></script>

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

