<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<h1>Batch update</h1>
	<hr />
	<h2>UPDATE by frequency</h2>
	<hr />
	<p>
		It will look
		for new episodes in the feeds and mark the non reachable
		correspondingly (404) - For podcasts with the <strong>selected frequency</strong>
	</p>
	<div class="update_podcast_form">
		<c:url value="/admin/update/batch/podcasts_by_frequency"
			var="updatePodcastWithFrequency_url" />
		<form:form method="POST"
			modelAttribute="updatePodcastsByFrequencyForm"
			action="${updatePodcastWithFrequency_url}">
			<label> Please select update frequency </label>
			<form:select path="updateFrequency" multiple="false">
				<form:options items="${updateFrequencies}" />
			</form:select>
			<label for="podcastId">Number of worker threads</label>
			<input name="numberOfWorkingThreads" size="50" placeholder="Number of worker threads for the job"/>
			<input type="submit" value="Start updating" />
		</form:form>
	</div>

	<h2>update
		all podcasts</h2>
	<p style="color:red">Careful - it could take long, or eat lots of memory </p>
	<p class="admin_command_link">
		<c:url value="/admin/update/batch/all_podcasts"
			var="updateAllPodcasts_url" />
		<a href="${updateAllPodcasts_url}"> Update episodes for podcasts
		</a> (adds new episodes and marks non-reachable episodes for ALL PODCASTS
		in the database)
	</p>
</div>
<!-- /.container -->