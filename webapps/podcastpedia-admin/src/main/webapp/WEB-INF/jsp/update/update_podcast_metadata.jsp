<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<h2>Update metadata of podcast</h2>
	<br />
	<p> Loads existing metadata(categories, tags, frequency, language,
		mediaType) for podcast, that be update it</p>
	<div id="update_podcasts">
		<p class="admin_command_link">
			<c:url value="/admin/update/metadata" var="updateOwnMetadata_url" />
			<form:form method="POST"
				modelAttribute="updatePodcastOwnMetadataByFeedUrlForm"
				action="${updateOwnMetadata_url}">
				<input name="feedUrl" size="100" placeholder="Feed URL of the podcast"/>
				<input type="submit" value="Go to metadata form" />
			</form:form>
		</p>
	</div>
</div>
<!-- /.container -->