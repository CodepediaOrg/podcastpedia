<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<div class="container">
	<div id="login-error">${error}</div>
	<form class="form-signin" role="form" action="<c:url value="/j_spring_security_check"/>" method="post">
		<h2 class="form-signin-heading">Please sign in</h2>
        <input id="j_username"	class="form-control" name="j_username" type="text" placeholder="Username" required autofocus/>
		<input id="j_password"	class="form-control" name="j_password" type="password" placeholder="Password" required/>

		<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
	</form>

</div>
<!-- /container -->
