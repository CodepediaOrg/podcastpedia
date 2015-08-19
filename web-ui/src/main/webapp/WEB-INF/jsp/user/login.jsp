<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div id="user_login_form_wrapper" class="bg_color border_radius shadowy common_mar_pad">

	<h2 class="title_before_form">Login with Email and Password</h2>
  <hr class="before_form_header_line"/>

  <form name='loginForm' action="${pageContext.request.contextPath}/j_spring_security_check" method='POST'>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

    <div class="label_above_elements">
      <label for="username" class="label">
        <spring:message code="login.label.email" text="Email"/>
      </label>
    </div>
    <input type='text' name='username' value=''class="form_input"/>

    <div class="label_above_elements">
      <label for="password" class="label">
        <spring:message code="login.label.password" text="password"/>
      </label>
    </div>
    <input type='password' name='password'  class="form_input"/>
    <input name="submit" type="submit" value="submit" />
    <hr class="before_form_header_line"/>
    Not registered yet - click <a href="<c:url value='/users/registration'/>">here</a> to register.
    <!--
    <table>
      <tr>
        <td>User:</td>
        <td><input type='text' name='username' value=''></td>
      </tr>
      <tr>
        <td>Password:</td>
        <td><input type='password' name='password' /></td>
      </tr>
      <tr>
        <td colspan='2'>
          <input name="submit" type="submit" value="submit" />
        </td>
      </tr>
	  </table>
	  -->
	</form>
</div>
