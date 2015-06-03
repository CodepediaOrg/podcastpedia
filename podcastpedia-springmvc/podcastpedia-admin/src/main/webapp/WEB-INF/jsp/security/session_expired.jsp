<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<div class="container">
	<h1>Session expired</h1>
	<p>
		<a href="<c:url value="/users/login"/>" class="btn btn-primary btn-lg" role="button">Login
			&raquo;</a>
	</p>
</div>
<!-- container -->
