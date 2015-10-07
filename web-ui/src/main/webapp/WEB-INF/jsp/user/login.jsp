<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="user_login_form_wrapper" class="bg_color border_radius shadowy common_mar_pad">

  <c:if test="${isConfirmedEmail != null}">
    <div id="email_confirmed_message" class="common_radius bg_color shadowy common_mar_pad">
      <spring:message code="user.registration.complete" text="Registration process is complete. Log in below"/>
    </div>
  </c:if>

	<h2 class="title_before_form"><spring:message code="user.login.form.header" text="Log in with Email and password"/></h2>
  <hr class="before_form_header_line"/>

  <form name='loginForm' class="vertical_style_form"
        action="${pageContext.request.contextPath}/j_spring_security_check" method='POST'>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    <c:if test="${not empty error}">
      <div class="error"><spring:message code="user.login.incorrect_credentials" text="Email and password are not valid"/></div>
    </c:if>
    <div class="label_above_elements">
      <label for="username" class="label">
        <spring:message code="user.login.label.email" text="Email"/>
      </label>
    </div>
    <p>
      <input type='text' id="username" name='username' value='' class="form_input"/>
    </p>

    <div class="label_above_elements">
      <label for="password" class="label">
        <spring:message code="user.login.label.password" text="password"/>
      </label>
    </div>
    <p>
      <input type='password' name='password' id="password" class="form_input"/>
    </p>

    <input name="submit" type="submit" value="Log in" class="submit_form_button"/>
	</form>
  <br><br>
  <spring:message code="user.login.form.not_registered" text="Not register yet, then click here to register"/>
</div>
