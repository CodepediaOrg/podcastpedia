<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="<c:url value="/admin"/>">Podcastpedia Admin</a>
    </div>
    <div class="collapse navbar-collapse">
      <ul class="nav navbar-nav">
		<sec:authorize access="hasRole('ROLE_ADMIN')">      
	        <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Insert <b class="caret"></b></a>
	          <ul class="dropdown-menu">
	            <li><a href="<c:url value="/admin/insert/upload_file"/>">Podcast(s) from file</a></li>
	          </ul>
	        </li>
	        <li class="dropdown">
	          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Update <b class="caret"></b></a>
	          <ul class="dropdown-menu">
	            <li class="dropdown-header">Batch</li>          
	            <li><a href="<c:url value="/admin/update/batch"/>">Update by frequency</a></li>
	            <li><a href="<c:url value="/admin/update/batch"/>">Update all podcasts (careful!!!)</a></li>
	            <li class="divider"></li>
	            <li class="dropdown-header">Individual</li>
	            <li><a href="<c:url value="/admin/update/episodes"/>">Update episodes</a></li>
	            <li><a href="<c:url value="/admin/update/feed"/>">Podcast's feed attributes</a></li>
	            <li><a href="<c:url value="/admin/update/metadata"/>">Podcast metadata (categories, tags, etc.)</a></li>
	          </ul>
	        </li>        
	        <li><a href="<c:url value="/admin/delete"/>">Delete</a></li>
	        <li><a href="<c:url value="/admin/create/sitemaps"/>">Sitemaps</a></li>
        </sec:authorize>
		<sec:authorize access="!hasRole('ROLE_ADMIN')"> 	        
        	<li><a href="<c:url value="/users/login"/>">Login</a></li>
        </sec:authorize>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</div>




