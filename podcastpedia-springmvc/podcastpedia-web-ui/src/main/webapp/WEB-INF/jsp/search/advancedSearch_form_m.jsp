<?xml version="1.0" encoding="UTF-8"?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>


<c:url var="jsURL" value="/static/js/advanced_search_form.js"/>
<script type='text/javascript' src='${jsURL}'></script>

<script type="text/javascript">   
    $(document).ready(function() {
    	$('input#natural_search').attr('checked', 'checked');
    	$('div#boolean_mode_part').hide();    	
        $("#natural_search").click(showNaturalPart);    
        $("#boolean_search").click(showBooleanPart);       	
    }); 
    
    function showNaturalPart(){
    	$('input#natural_search').attr('checked', 'checked');
       	$('div#boolean_mode_part').hide('slow');
       	$('div#natural_mode_part').show('slow'); 
    } 
    
    function showBooleanPart(){
    	$('input#boolean_search').attr('checked', 'checked');
    	$('div#natural_mode_part').hide('slow');
    	$('div#boolean_mode_part').show('slow');  
	};    
</script>

<div id="advanced_search_wrapper" class="bg_color border_radius shadowy">
	<h2 class="title_before_form"><spring:message code="search.advancedSearchURL" text="Advanced search"/></h2>	
	<hr id="before_form_header_line"/>
	<c:url value="/search/advanced_search/results" var="advancedSearchResultsURL" />
	<c:if test="${noResultsFound}">
		<p>
			<spring:message code="search.no_results" text="No results"/>
		</p> 
	</c:if>
	
	<form:form id="advanced_search_form" method="GET" modelAttribute="advancedSearchData" action="${advancedSearchResultsURL}" >
		<div class="label_above_elements">		
			<label for="whereToLook" class="label">
				<spring:message code="search.label.search_target" text="where to look "/>
			</label>
		</div>				
		<form:select path="searchTarget" id="whereToLook" class="form_input">
			<spring:message var="podcasts_label" code="quick_search.podcasts" text="Podcasts"/>
			<form:option value="podcasts" label="${podcasts_label}"/>
			<spring:message var="episodes_label" code="quick_search.episodes" text="Episodes"/>
			<form:option value="episodes" label="${episodes_label}"/>
		</form:select>
		<fieldset data-role="controlgroup">	
			<legend>
				<spring:message code="search.legend.words_filter" text="Words filter"/>
			</legend>
			<div>
				<p id="search_mode_title"> 
					<spring:message code="search.search_mode_title" text="Please select a search mode"/> 
				</p>
				<p class="search_type_selection">
					<form:radiobutton path="searchMode" value="natural" id="natural_search"/>	
					<form:label path="searchMode">
						Natural  
					</form:label>
				</p>
				<p class="search_type_selection">
					<form:radiobutton path="searchMode" value="boolean" id="boolean_search"/>
					<form:label path="searchMode">
						Boolean
					</form:label>	
				</p>							
			</div>		
			<hr id="separating_search_mode_line"/>		
			<div id="natural_mode_part">
				<div class="label_above_elements">				
					<label for="queryText" class="label" id="queryTextLabel">
						<spring:message code="search.label.query_text" text="search text"/>
					</label>
				</div>
				<spring:message var="textGetAllPodcastsI18N" code="search.defaultSearchText"/>
				<script type="text/javascript">
					function inputFocus(i){
					    if(i.value==i.defaultValue){ i.value=""; i.style.color="#000"; }
					}
					function inputBlur(i){
					    if(i.value==""){ i.value=i.defaultValue; i.style.color="#848484"; }
					}
				</script>	
				<p>			
					<input name="queryText" id="queryText" value="${textGetAllPodcastsI18N}" onFocus="inputFocus(this)" onBlur="inputBlur(this)" class="form_input"/>
					<span id="nat_text_tip">
						<spring:message code="search.text.natural_tip" text="Type as you would speak"/>
					</span>
				<p/>
					
			</div>	
			<div id="boolean_mode_part">	
				<div class="label_above_elements">	
					<label for="allTheseWords">
						<strong><spring:message code="search.boolean.all_these_words" text="all these words"/></strong>
						(<spring:message code="search.text.all_these_words" text="Put + sign before words"/>)					
					</label>
				</div>		
				<p>
					<form:input path="allTheseWords" id="allTheseWords" class="form_input"/>
				<p/>				
				<div class="label_above_elements">
					<label for="exactPhrase">
						<strong><spring:message code="search.boolean.exact_phrase" text="exact phrase"/></strong>
						(<spring:message code="search.text.exact_phrase" text="Put in quotes the exact phrase"/>)
					</label>
				</div>	
				<p>
					<form:input path="exactPhrase" id="exactPhrase" class="form_input"/>
				<p/>							
				<div class="label_above_elements">
					<label for="anyOfTheseWords">
						<strong><spring:message code="search.boolean.any_words" text="any of these words"/></strong>
					</label>
					(<spring:message code="search.text.any_words" text="Put empty spaces between the words you want"/>)
				</div>
				<p>
					<form:input path="anyOfTheseWords" id="anyOfTheseWords" class="form_input"/>
				<p/>				
				<div class="label_above_elements">
					<label for="noneOfTheseWords">
						<strong><spring:message code="search.boolean.none_words" text="none of these words"/></strong>
						(<spring:message code="search.text.none_words" text="Put - before the words you don't want"/>)
					</label>
				</div>	
				<p>
					<form:input path="noneOfTheseWords" id="noneOfTheseWords" class="form_input"/>
				<p/>				
			</div>											
		</fieldset>		
		<fieldset>
			<legend>
				<spring:message code="search.legend.filter_by" text="Filter by"/>
			</legend>
			<div class="label_above_elements">	
				<label for="language" class="label">
					<spring:message code="search_form.language" text="Language"/> 
				</label>
			</div>
			<p>
				<form:select path="languageCode" class="form_input">
					<spring:message var="all_label" code="global.all" text="All"/>
					<form:option value="" label="${all_label}"/>
					<c:forEach items="${languageCodes}" var="languageCode">
						<spring:message var="langCodeVar" code="${languageCode}"/>						
					    <form:option value="${languageCode.code}" label='${langCodeVar}'/>
					</c:forEach>					
				</form:select>	
			<p/>							
			<div class="label_above_elements">
				<label for="mediaType" class="label">
					<spring:message code="search_form.mediaType" text="Media Type"/> 
				</label>	
			</div>	
			<p>	
				<form:select path="mediaType" multiple="false" class="form_input">
					<form:option label="Audio & Video" value="" />
					<form:options items="${mediaTypes}" />
				</form:select>
			<p/>					
			<div class="label_above_elements">
				<label for="category" class="label">
					<spring:message code="search_form.category" text="Category"/> 
				</label>
			</div>
			<p>
				<form:select path="categId" class="form_input">
					<spring:message var="all_label" code="global.all" text="All"/>
					<form:option value="NONE" label="${all_label}"/>
					<c:forEach items="${allCategories}" var="category">
						<form:option value="${category.categoryId}">
							<spring:message code="${category.name}"/>
						</form:option>
					</c:forEach>						
				</form:select>		
				<span id="category_comment">
					<spring:message code="search.text.category_ctrl" text="Use Ctrl key to select multiple categories "/>
				</span>											 							
			<p/>						
		</fieldset>					
			
		<fieldset>
			<legend><spring:message code="search.legend.other_criteria" text="Other criteria"/></legend>		
			<div class="label_above_elements">
				<label for="resultsPerPage" class="label">
					<spring:message code="search.label.results_page" text="Results per page?"/>
				</label>
			</div>	
			<p>				 
				<form:select path="numberResultsPerPage" id="resultsPerPage" class="form_input">
					<form:option value="10" label="10"/>						
					<form:option value="20" label="20"/>
					<form:option value="50" label="50"/>
				</form:select>
			<p/>				
		</fieldset>		
		<fieldset>
			<legend>
				<spring:message code="quick_search.label.order_by" text="Order by"/>
			</legend>
				<p class="orderBy_selection">
					<form:radiobutton path="orderBy" value="" checked="true"/>	
					<form:label path="orderBy">
						<spring:message code="search.order_by.default" text="Default"/>
					</form:label>
				</p>			
				<p class="orderBy_selection">
					<form:radiobutton path="orderBy" value="PUBLICATION_DATE" />	
					<form:label path="orderBy">
						<spring:message code="PUBLICATION_DATE" text="recently updated podcasts (publication date)"/>
					</form:label>
				</p>				
				<p class="orderBy_selection">
					<form:radiobutton path="orderBy" value="POPULARITY"/>	
					<form:label path="orderBy">
						<spring:message code="POPULARITY" text="order by popularity"/>
					</form:label>
				</p>	
				<p class="orderBy_selection">
					<form:radiobutton path="orderBy" value="NEW_ENTRIES" />	
					<form:label path="orderBy">
						<spring:message code="NEW_ENTRIES" text="new entries on podcastpedia"/>
					</form:label>
				</p>												
		</fieldset>
		<form:hidden path="currentPage"/>
		<p>
			<spring:message var="search_button" code="search.btn" text="Search"/>
			<input type="submit" name="Submit" id="subscribe" value="${search_button}" class="submit_form_button"/>
		</p>
		<dir class="clear"></dir>
	</form:form>
</div>