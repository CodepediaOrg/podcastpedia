
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<c:url var="jwplayerURL" value="/static/js/jwplayer/jwplayer.js"/>
<script type='text/javascript' src='${jwplayerURL}'></script>
<script type="text/javascript">jwplayer.key="fr4dDcJMQ2v5OaYJSBDXPnTeK6yHi8+8B7H3bg==";</script>

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
        <a href="#" class="btn-share" id="subscribe-to-podcast"><spring:message code="user.subscribe" text="Subscribe"/></a>
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
      <span class="item_sharing_title">${podcast.title}</span>
		</div>
    <div id="pod-details-voting">
      <!-- if not authenticated will be asked to log in -->
      <sec:authorize access="isAnonymous()">
        <a href="#-3" class="icon-thumbs-down ask-for-login" id="vote-down-ask-for-login">${podcast.downVotes}</a>
      </sec:authorize>
      <!-- if authenticated can vote down podcast -->
      <sec:authorize access="isAuthenticated()">
        <a href="#-3" class="icon-thumbs-down" id="vote-down-podcast">${podcast.downVotes}</a>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
      </sec:authorize>

      <!-- if not authenticated will be asked to log in -->
      <sec:authorize access="isAnonymous()">
        <a href="#-3" class="icon-thumbs-up ask-for-login" id="vote-up-ask-for-login">${podcast.upVotes}</a>
      </sec:authorize>
      <!-- if authenticated can vote up podcast -->
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

  <tags:episodes/>
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

<script src="<c:url value="/static/target/js/app.js?v=0.1" />"></script>

<!-- jquery dialogs -->
<div id="ask-for-login-form" title="Sign in">
  <p><spring:message code="user.login.perform_operation" text="Please log in"/></p>
</div>

<div id="media_player_modal_dialog" title="Media player">
  <div id='mediaspace_modal'>Loading...</div>
</div>

<div id="subscribe-form-subscription-category" title="<spring:message code="user.subscriptions.select_category.title" text="Select category"/>">
  <form class="vertical_style_form">
    <sec:authorize access="isAuthenticated()">
      <p>
        <select id="subscription_categories" class="form_input">
          <option value="" label="<spring:message code="user.subscriptions.select_category.existing" text="Existing category"/>"/>
          <c:forEach items="${subscriptionCategories}" var="subscriptionCategory">
            <option value="${subscriptionCategory}">${subscriptionCategory}</option>
          </c:forEach>
        </select>
      </p>
    </sec:authorize>
    <div id="label_above_elements">
      <label for="newPlayist" class="label">
        <spring:message code="user.subscriptions.select_category.new" text="Add new category"/>
      </label>
    </div>
    <p>
      <input name="newSubscriptionCategory" id="newSubscriptionCategory" class="form_input" style='width:200px'/>
    </p>
    <input type="hidden" name="podcastId" id="sub_podcastId" value="${podcast.podcastId}"/>
  </form>
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
