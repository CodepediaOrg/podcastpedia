<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="container theme-showcase" role="main">
	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<h1>Hello, Podcastpedia Admins!</h1>
		<p>In this web app you can insert new podcasts, update existing
			ones, delete podcasts, create sitemaps etc.</p>
		<sec:authorize access="!hasRole('ROLE_ADMIN')"> 			
			<p>
				<a href="<c:url value="/users/login"/>" class="btn btn-primary btn-lg" role="button">Login &raquo;</a>
			</p>
		</sec:authorize>
	</div>
</div><!-- /container -->
