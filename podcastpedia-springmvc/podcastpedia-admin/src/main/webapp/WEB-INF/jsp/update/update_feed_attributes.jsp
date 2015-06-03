<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<h2>UPDATE feed attributes</h2>
	<hr />
	<p>Verifies if the feed behind a podcast has been modified and updates
		the properties accordingly (like title, description, copyright, author etc.)</p>
	<div class="update_podcast_form">
		<c:url value="/admin/update/feed/podcast_by_id"
			var="updatePodcastById_url" />
		<form:form method="POST" modelAttribute="updatePodcastByIdForm"
			action="${updatePodcastById_url}">
			<br />
			<label for="isFeedLoadedFromLocalFile">Load feed from local
				file</label>
			<form:checkbox path="isFeedLoadedFromLocalFile" />
			<br />
			<input name="podcastId" size="10" placeholder="Podcast id"/>
			<input type="submit" value="Update feed attributes for podcast id" />
		</form:form>
	</div>
	<div class="update_podcast_form">
		<c:url value="/admin/update/feed/podcast_by_feedUrl"
			var="updatePodcastByFeedUrl_url" />
		<form:form method="POST" modelAttribute="updatePodcastByFeedUrlForm"
			action="${updatePodcastByFeedUrl_url}">
			<br />
			<label for="isFeedLoadedFromLocalFile">Load feed from local
				file</label>
			<form:checkbox path="isFeedLoadedFromLocalFile" />
			<br />
			<input name="feedUrl" size="100" placeholder="Feed url of the podcast"/>
			<input type="submit" value="Update feed attributes for feed url" />
		</form:form>
	</div>
</div>
<!-- /.container -->