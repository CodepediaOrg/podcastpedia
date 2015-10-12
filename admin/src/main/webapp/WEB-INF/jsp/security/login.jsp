<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<div class="container">
	<div id="login-error">${error}</div>
  <c:url var="loginUrl" value="/login"/>
	<form class="form-signin" role="form" action="${loginUrl}" method="POST">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<h2 class="form-signin-heading">Please sign in</h2>
      <input id="username"  name="username"	class="form-control" type="text" placeholder="Username" required autofocus/>
		  <input id="password" name="password"	class="form-control" type="password" placeholder="Password" required/>

		<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
	</form>

</div>
<!-- /container -->
