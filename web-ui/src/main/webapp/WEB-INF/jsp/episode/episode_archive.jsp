
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<c:url var="jwplayerURL" value="/static/js/jwplayer/jwplayer.js"/>
<script type='text/javascript' src='${jwplayerURL}'></script>
<script type="text/javascript">jwplayer.key="fr4dDcJMQ2v5OaYJSBDXPnTeK6yHi8+8B7H3bg==";</script>

<!-- based on number of episodes will proceed further
	if number of episodes > 20 will display 20 per page, if not 10
-->
<div class="results_list">
  <tags:episodes/>
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

<!-- jquery dialogs -->
<div id="media_player_modal_dialog" title="Media player">
  <div id='mediaspace_modal'>Loading...</div>
</div>

<!-- javascript libraries required -->
<script src="//code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="<c:url value="/static/js/podcast/main.js" />"></script>
<script src="//code.jquery.com/ui/1.11.3/jquery-ui.min.js"></script>
