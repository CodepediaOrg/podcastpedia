<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="net.tanesha.recaptcha.ReCaptcha" %>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory" %>

<%@ include file="/WEB-INF/jsp/common/recaptcha_options.jsp" %>


<div id="user_registration_form_wrapper" class="bg_color border_radius shadowy common_mar_pad">

  <h2 class="title_before_form"><spring:message code="user.password_forgotten.header" text="Password reset"/></h2>
  <hr class="before_form_header_line"/>

  <c:url value="/users/password-forgotten" var="submitEmailForPasswordResetUrl"/>
  <form:form name="password_reset_form" class="vertical_style_form"
             method="POST" modelAttribute="user" action="${submitEmailForPasswordResetUrl}" >

    <!-- password field -->
    <div id="label_above_elements">
      <label for="password" class="label">
        <spring:message code="user.registration.label.new_password" text="New password"/>
      </label>
    </div>
    <p>
      <form:password path="password" id="password" class="form_input"/>
      <form:errors path="password" cssClass="error_form_validation"/>
    </p>

    <!-- confirm password field -->
    <div id="label_above_elements">
      <label for="matchingPassword" class="label">
        <spring:message code="user.registration.new_password_confirmation" text="Confirm password"/>
      </label>
    </div>
    <p>
      <form:password path="matchingPassword" id="matchingPassword" class="form_input"/>
      <form:errors path="matchingPassword" cssClass="error_form_validation"/>
    </p>

    <spring:message var="submit_btn" code="submit" text="Submit registration"/>
    <input type="submit" value="${submit_btn}"  id="send_message" class="submit_form_button"/>
    <div class="clear"></div>
  </form:form>
</div>
