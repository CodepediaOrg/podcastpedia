<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<script type="text/javascript">   
	$(document).ready(function() {	
		
		$('.item_wrapper').on('click', '.icon-share-episode', function (e) {
			var currentDiv=$(this).closest("div.item_wrapper");
			var epUrl= currentDiv.find("span.item_url").text();
			
			//verify existence of player to manipulate distance to div containing sharing buttons
			var playerDiv = currentDiv.find("div.shown");
			if(playerDiv.length > 0){
				playerDiv.css("margin-bottom","75px");
			} else {
				//player not shown widen the distance to insert the sharing buttons 
				currentDiv.find("div.ep_desc").css("margin-bottom","75px");
				currentDiv.find("div.ep_desc_bigger").css("margin-bottom","75px");	
				currentDiv.find("div.pod_desc").css("margin-bottom","55px");
				currentDiv.find("div.pod_desc_bigger").css("margin-bottom","55px");
			}
			//the share button is being replaced with social media buttons
			$(e.target).remove();
			var socialAndDownload = currentDiv.find("div.social_and_download");
			socialAndDownload.prepend(
				 "<div class='share_buttons'>" 
					+ "<div class='fb_like'>"
					+ " <div class='fb-like' data-href='"+ epUrl + " data-send='false' data-layout='button_count' data-width='70' data-height='72'></div>"
					+ "</div> "						
					+ "<div class='twitter_share'> "
					+ " <a href='https://twitter.com/share' class='twitter-share-button' data-url='" + epUrl + "' data-hashtags='podcastpedia'>Tweet</a>"	
					+ "</div>"
					+ "<div class='google_share'> "					
					+ "  <div class='g-plusone' data-size='medium' data-annotation='bubble' data-href='" + epUrl + "'></div>"						
				    + "</div>"
			    + "</div>"
			);
			
			loadTwitter();
			loadFacebook();
			loadGooglePlus();
			
		 });
		
		$('#social_and_download_curr_ep').on('click', '.icon-share-episode', function (e) {
			var currentDiv=$(this).closest("div#social_and_download_curr_ep");
			var epUrl= currentDiv.find("span.item_url_ep").text();
			
			//widen the distance to insert the sharing buttons 
			$('#current_episode').find(".ep_desc").css("margin-bottom","75px");
			$('#current_episode').find(".ep_desc_bigger").css("margin-bottom","75px");	

			//the share button is being replaced with social media buttons
			$(e.target).remove();
			currentDiv.prepend(
				 "<div class='share_buttons'>" 
					+ "<div class='fb_like'>"
					+ " <div class='fb-like' data-href='"+ epUrl + " data-send='false' data-layout='button_count' data-width='70' data-height='72'></div>"
					+ "</div> "						
					+ "<div class='twitter_share'> "
					+ " <a href='https://twitter.com/share' class='twitter-share-button' data-url='" + epUrl + "' data-hashtags='podcastpedia'>Tweet</a>"	
					+ "</div>"
					+ "<div class='google_share'> "					
					+ "  <div class='g-plusone' data-size='medium' data-annotation='bubble' data-href='" + epUrl + "'></div>"						
				    + "</div>"
			    + "</div>"
			);
			
			loadTwitter();
			loadFacebook();
			loadGooglePlus();
			
		 });		
					
		function loadTwitter()
		{
		    if (typeof (twttr) != 'undefined'){
		        twttr.widgets.load();
		    } else {
		        $.getScript('http://platform.twitter.com/widgets.js');
		    }
		}		
		
		function loadFacebook()
		{
		    if (typeof (FB) != 'undefined') {
		        FB.init({ status: true, cookie: true, xfbml: true });
		    } else {
		        $.getScript("http://connect.facebook.net/en_US/all.js#xfbml=1", function () {
		            FB.init({ status: true, cookie: true, xfbml: true });
		        });
		    }
		}		
		
		function loadGooglePlus()
		{
			var gbuttons = $(".g-plusone");
			if (gbuttons.length > 0) {
			    if (typeof (gapi) != 'undefined') {
			        gbuttons.each(function () {
			            gapi.plusone.render($(this).get(0));
			        });
			    } else {
			        $.getScript('https://apis.google.com/js/plusone.js');
			    }
			}
		}		
    });  
</script>