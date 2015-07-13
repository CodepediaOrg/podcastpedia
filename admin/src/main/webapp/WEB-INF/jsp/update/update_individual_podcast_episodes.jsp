<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<div class="update_podcast_form">
		<c:url value="/admin/update/episodes/podcasts_by_ids"
			var="updatePodcastById_url" />
		<form:form method="POST" modelAttribute="updatePodcastByIdForm"
			action="${updatePodcastById_url}">
			<input name="podcastIds" size="50"
				placeholder="Podcast(s) id(s) - separated by comma" />
			<br />
			<label for="isFeedLoadedFromLocalFile">Load feed from local
				file</label>
			<form:checkbox path="isFeedLoadedFromLocalFile" />
			<br />
			<input type="submit" value="Update episodes" />
		</form:form>
	</div>
	<div class="update_podcast_form">
		<c:url value="/admin/update/episodes/podcast_by_feedUrl"
			var="updatePodcastByFeedUrl_url" />
		<form:form method="POST" modelAttribute="updatePodcastByFeedUrlForm"
			action="${updatePodcastByFeedUrl_url}">
			<input name="feedUrl" size="100"
				placeholder="Insert podcast feed url" />
			<input type="submit" value="Update episodes" />
		</form:form>
	</div>

</div>
<!-- /.container -->