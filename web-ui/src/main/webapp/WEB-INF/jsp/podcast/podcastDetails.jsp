
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url var="jwplayerURL" value="/static/js/jwplayer/jwplayer.js"/>
<script type='text/javascript' src='${jwplayerURL}'></script>

<div id="podcast_metadata" class="bg_color shadowy">
	<img src="${podcast.urlOfImageToDisplay}" alt="Podcast image" id="pod_image">
	<div id="media_type">
		<h2 id="website_anchor">
			${podcast.title}
			<c:choose>
				<c:when test="${podcast.mediaType == 'Audio'}">
					<span class="icon-audio-pod"></span>
				</c:when>
				<c:otherwise>
					<span class="icon-video-pod"></span>
				</c:otherwise>
			</c:choose>
		</h2>
	</div>
	<p>
		<a href="${podcast.link}" target="_blank" class="icon-globe-producer producer-social" title="Website"></a>
		<a href="${podcast.url}" target="_blank"  class="icon-feed-producer  producer-social" title="Feed"></a>
		<c:if test="${not empty podcast.twitterPage}">
			<a href="${podcast.twitterPage}" target="_blank" class="icon-twitter-producer producer-social" title="Twitter"></a>
		</c:if>
		<c:if test="${not empty podcast.fbPage}">
			<a href="${podcast.fbPage}" target="_blank" class="icon-facebook-producer producer-social" title="Facebook Fan Page"></a>
		</c:if>
		<c:if test="${not empty podcast.gplusPage}">
			<a href="${podcast.gplusPage}" target="_blank" class="icon-google-plus-producer producer-social" title="Google+"></a>
		</c:if>
	</p>
	<c:if test="${podcast.updateFrequency == 'DAILY' || podcast.updateFrequency == 'WEEKLY' || podcast.updateFrequency == 'MONTHLY'}">
    <p>
      <!-- if not authenticated will be asked to log in -->
      <sec:authorize access="isAnonymous()">
        <a href="#" class="btn-share ask-for-login" id="subscribe-ask-for-login" style="background-color: orangered"><spring:message code="user.subscribe" text="Subscribe"/></a>
      </sec:authorize>
      <!-- if authenticated can subscribe automatically -->
      <sec:authorize access="isAuthenticated()">
        <a href="#" class="btn-share" id="subscribe-to-podcast">Subscribe</a>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      </sec:authorize>
    </p>
	</c:if>
	<div id="categs">
		<b><spring:message code="header.menu.categories" text="Categories"/> </b>
		<ul>
			<c:forEach items="${podcast.categories}" var="category" varStatus="loop">
				<li>
					<c:url value="/categories/${category.categoryId}/${category.name}" var="urlCategory" />
					<a href="${urlCategory}" class="btn-metadata2"><spring:message code="${category.name}"/></a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div id="keywords">
		<b><spring:message code="pod_details.tags" text="Tags"/> </b>
		<ul>
			<c:forEach items="${podcast.tags}" var="tag" varStatus="loop">
				<li>
					<c:url value="/tags/${tag.tagId}/${tag.name}" var="urlTag" />
					<a href="${urlTag}" class="btn-metadata2"><span>${tag.name}</span></a>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div class="clear"></div>
	<div class="item_wrapper">
		<div class="pod_desc"> ${fn:substring(podcast.description,0,500)}</div>
		<div class="pod_desc_bigger"> ${fn:substring(podcast.description,0,750)}</div>
		<div id="pod_like" class="social_and_download">
			<c:choose>
				<c:when test="${podcast.identifier == null}">
					<c:set var="podcast_link" value="https://www.podcastpedia.org/podcasts/${podcast.podcastId}/${podcast.titleInUrl}"/>
				</c:when>
				<c:otherwise>
					<c:set var="podcast_link" value="https://www.podcastpedia.org/${podcast.identifier}"/>
				</c:otherwise>
			</c:choose>
			<a href="#-1" class="icon-share-episode btn-share">Share</a>

      <span class="item_url">${podcast_link}</span>
		</div>
    <div id="pod-details-voting">
      <!-- if not authenticated will be asked to log in -->
      <sec:authorize access="isAnonymous()">
        <a href="#-3" class="icon-thumbs-down ask-for-login" id="vote-down-ask-for-login">${podcast.downVotes}</a>
      </sec:authorize>
      <!-- if authenticated can subscribe automatically -->
      <sec:authorize access="isAuthenticated()">
        <a href="#-3" class="icon-thumbs-down" id="vote-down-podcast">${podcast.downVotes}</a>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      </sec:authorize>

      <!-- if not authenticated will be asked to log in -->
      <sec:authorize access="isAnonymous()">
        <a href="#-3" class="icon-thumbs-up ask-for-login" id="vote-up-ask-for-login">${podcast.upVotes}</a>
      </sec:authorize>
      <!-- if authenticated can subscribe automatically -->
      <sec:authorize access="isAuthenticated()">
        <a href="#-3" class="icon-thumbs-up" id="vote-up-podcast">${podcast.upVotes}</a>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      </sec:authorize>
    </div>
		<div class="clear"></div>
	</div>
</div>

<!-- recent episodes of the podcast -->
<div class="results_list">
	<h3><spring:message code="pod_details.recent_episodes" text="Recent episodes: "/></h3>

	<!-- only if the podcast has more than 10 episodes will display an archive link with all the episodes,
          else all episodes of the podcast will be displayed -->

	<c:forEach items="${lastEpisodes}" var="episode" varStatus="loop">
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
        <c:url var="episodeURL" value="/podcasts/${podcast.podcastId}/${podcast.titleInUrl}/episodes/${episode.episodeId}/${episode.titleInUrl}"/>
        <a href="${episodeURL}" class="item_title">${episode.title}</a>
				<div class="pub_date">
					<fmt:formatDate pattern="yyyy-MM-dd" value="${episode.publicationDate}" />
					<c:choose>
						<c:when test="${episode.isNew == 1}">
							<span class="ep_is_new"><spring:message code="new"/></span>
						</c:when>
					</c:choose>
				</div>
				<div class="clear"></div>
			</div>
			<hr>
			<div class="ep_desc">
				<a href="${episodeURL}" class="item_desc">
						${fn:substring(episode.description,0,280)}
				</a>
			</div>
			<div class="ep_desc_bigger">
				<a href="${episodeURL}" class="item_desc">
						${fn:substring(episode.description,0,600)}
				</a>
			</div>
			<div class="clear"></div>
			<div class="not_shown">
				<div id='mediaspace${loop.index}'>Flashplayer not supported</div>
				<!-- switch player CAN or CANNOT be displayed -->
				<c:choose>
					<c:when test="${episode.mediaType == 'Audio'}">
						<script type='text/javascript'>
							jwplayer('mediaspace${loop.index}').setup({
								'controlbar': 'bottom',
								'width': '100%',
								'aspectratio': '16:5',
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
				<a href="#${2*loop.index}" class="icon-play-episode btn-share">Play</a>
				<a href="#${2*loop.index + 1}" class="icon-share-episode btn-share">Share</a>
				<a class="icon-download-ep btn-share" href="${episode.mediaUrl}" download>
					<spring:message code="global.dwnld.s" text="Download last episode"/>
				</a>
				<span class="item_url">https://www.podcastpedia.org/podcasts/${podcast.podcastId}/${podcast.titleInUrl}/episodes/${episode.episodeId}/${episode.titleInUrl}</span>
			</div>
		</div>
	</c:forEach>
	<input type="hidden" name="offset" id="offset-data-id" value="5"/>
</div>

<button type="button" id="more-episodes" style="display: block;border-radius:5px;padding:5px; width:100%;font-family:arial,sans-serif; font-size:1.5em;color:#2F4051;" class="shadowy"><strong><spring:message code="ep_details.more_episodes" text="More"/></strong></button>
<p id="archive_all_episodes">
	<c:url var="allEpisodesUrl" value="/podcasts/${podcast.podcastId}/${podcast.titleInUrl}/episodes/archive/pages/1"/>
	<a href="${allEpisodesUrl}" ><spring:message code="pod_details.archive" text="Archive - all episodes"/></a>
</p>
<div class="clear"></div>

<p id="podcast_copyright" class="bg_color common_radius shadowy">
	<b>Copyright:</b> ${podcast.copyright}. <br/>
	<spring:message code="podcast_copyright_part1" text="Podcast copyright"/><i>${podcast.title}</i>.
	<spring:message code="podcast_copyright_part2" text="Podcast copyright"/>
</p>

<!-- javascript libraries required -->
<script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="//code.jquery.com/ui/1.11.3/jquery-ui.min.js"></script>

<script src="<c:url value="/static/js/podcast/main.js" />"></script>

<!-- jquery dialogs -->
<div id="ask-for-login-form" title="Sign in">
  <p><spring:message code="user.login.perform_operation" text="Please log in"/></p>
</div>

<div id="subscribe-form" title="Podcast subscription">
	<form class="vertical_style_form">
		<div id="label_above_elements">
			<label for="email" class="label">
				<spring:message code="label.email" text="Email - required, verified but never shown"/>
			</label>
		</div>
		<p>
			<input name="email" id="sub_email" class="form_input" style='width:200px'/>
		</p>
		<input type="hidden" name="podcastId" id="sub_podcastId" value="${podcast.podcastId}"/>
	</form>
</div>

<!-- 			  -->
<div id="dialog-subscribed" title="Subscription successful">
	<p>
		<spring:message code="pod_details.subscription.thanks" text="Thank your for subscribing to the podcast. Will will send you an email with new episodes when they become available."/>
	</p>
</div>

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
	<noscript>Please enable JavaScript to view the <a href="//disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
	<a href="//disqus.com" class="dsq-brlink">comments powered by <span class="logo-disqus">Disqus</span></a>
</div>
