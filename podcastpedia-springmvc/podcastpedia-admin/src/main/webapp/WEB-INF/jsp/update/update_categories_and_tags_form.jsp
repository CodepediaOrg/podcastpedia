<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container">
	<form:form method="POST" modelAttribute="podcast">
		<!-- when no action attribute is specified, the current URL is used -->
		<p class="add_podcast_par_in_form">
			<label for="url" class="label"> URL podcast: </label>
			<form:input path="url" size="100" />
		</p>

		<p class="add_podcast_par_in_form">
			<label for="categoryIDs" class="label"> Categories: </label>
			<form:select id="category_select" path="categoryIDs" multiple="true"
				items="${allCategories}" itemLabel="name" itemValue="categoryId" />
		</p>
		<p class="add_podcast_par_in_form">
			<label for="languageCode"> Podcast language: </label>
			<form:select path="languageCode" multiple="false">
				<form:options items="${languageCodes}" />
			</form:select>
		</p>
		<p class="add_podcast_par_in_form">
			<label> Please select media type of podcast : </label>
			<form:select path="mediaType" multiple="false">
				<form:options items="${mediaTypes}" />
			</form:select>
		</p>
		<p class="add_podcast_par_in_form">
			<label> Please select update frequency </label>
			<form:select path="updateFrequency" multiple="false">
				<form:options items="${updateFrequencies}" />
			</form:select>
		</p>

		<p class="add_podcast_par_in_form">
			<label for="tagsStr" class="label"> Tags (separated by ","):
			</label>
			<form:input path="tagsStr" size="100" />
		</p>
		<form:hidden path="podcastId" value="${podcast.podcastId}" />
		<p class="add_podcast_par_in_form">
			<input type="submit" value="Update podcast" />
		</p>
	</form:form>
</div><!-- /.container -->