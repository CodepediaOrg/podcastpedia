<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<h1>Mark podcast as not reachable</h1>
	<p> The podcast's availability will be set to not_reachable (404),
		so that the podcast won't be picked up anymore in the update batch
		jobs, and it and its episodes won't be reachable from the main web
		application </p>
	<div class="delete_podcast_form">
		<!-- deletes podcast by Id form -->
		<c:url value="/admin/delete/mark_podcast_by_id" var="markPodcastById_url" />
		<form:form method="POST" modelAttribute="markPodcastByIdForm"
			action="${markPodcastById_url}">
			<input name="podcastId" placeholder="Podcast id"/>
			<button type="submit" class="btn btn-default">Mark unavailable</button>
		</form:form>
	</div>

	<div class="delete_podcast_form">
		<!-- deletes podcast by feedUrl form -->
		<c:url value="/admin/delete/mark_podcast_by_feedUrl"
			var="markPodcastByFeedUrl_url" />
		<form:form method="POST" modelAttribute="markPodcastByUrlForm"
			action="${markPodcastByFeedUrl_url}">
			<input name="feedUrl" size="120" placeholder="URL feed of the podcast"/>
			<button type="submit" class="btn btn-default">Mark unavailable</button>
		</form:form>
	</div>

	<h1>Delete podcast</h1>
	<p>
		<span style="color: red; font-size: larger;">Use with care</span> The
		podcast, its episodes, the categories and tags associated with it will
		be removed complete from the db.
	</p>

	<div class="delete_podcast_form">
		<!-- deletes podcast by Id form -->
		<c:url value="/admin/delete/podcast_by_id" var="deletePodcastById_url" />
		<form:form method="POST" modelAttribute="deletePodcastByIdForm"
			action="${deletePodcastById_url}">
			<input name="podcastId" placeholder="Podcast id"/>
			<button type="submit" class="btn btn-default">Delete</button>
		</form:form>
	</div>

	<div class="delete_podcast_form">
		<!-- deletes podcast by feedUrl form -->
		<c:url value="/admin/delete/podcast_by_feedUrl"
			var="deletePodcastByFeedUrl_url" />
		<form:form method="POST" modelAttribute="deletePodcastByFeedUrlForm"
			action="${deletePodcastByFeedUrl_url}">
			<input name="feedUrl" size="120" placeholder="URL feed of the podcast"/>
			<button type="submit" class="btn btn-default">Delete</button>
		</form:form>
	</div>

  <h1>Delete user</h1>
  <p>
    Removes user related data from users, authorities and subscriptions
  </p>

  <div class="delete_user_form">
    <!-- deletes podcast by Id form -->
    <c:url value="/admin/delete/user_by_email" var="deleteUsertByEmail_url" />
    <form:form method="POST" modelAttribute="deleteUserByEmailForm"
               action="${deleteUsertByEmail_url}">
      <input name="email" placeholder="User email"/>
      <button type="submit" class="btn btn-default">Delete</button>
    </form:form>
  </div>

</div>
<!-- /.container -->
