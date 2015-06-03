<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container">
	<h1>Create sitemaps</h1>
	<p>
		<c:url value="/admin/create/sitemaps" var="createSitemaps_url" />
		<form:form method="POST" modelAttribute="createSitemapByFrequencyForm"
			action="${createSitemaps_url}">
			<label> Create sitemap for podcasts with frequency </label>
			<form:select path="updateFrequency" multiple="false">
				<form:options items="${updateFrequencies}" />
			</form:select>
			<button type="submit" class="btn btn-default">Create sitemap</button>
		</form:form>
	</p>
</div>
<!-- /.container -->