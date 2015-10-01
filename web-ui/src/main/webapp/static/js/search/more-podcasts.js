//TODO - before considering this functionality, check out i18n in Javascript, we have here new download etc...
$(function (){
	var moreResultsBtn = $('#more-results');

	//more button functionality - cannot make up my mind...
	$(moreResultsBtn).click(function(){

		var $resultsList=$('.results_list');
		var queryStringDataId=$('#queryString-data-id');
		var queryString=queryStringDataId.val();
		var currentPageDataId=$('#currentPage-data-id');
		var currentPage=parseInt(currentPageDataId.val()) + 1;

		$.ajax({
			headers: {"Accept":"application/json"},
			type: 'GET',
			url: '/api/search-results?' + queryString+ '&currentPage=' + currentPage,
			success: function(result){
				var podcasts = result.podcasts;
				if(podcasts.length == 0){
					moreResultsBtn.attr("disabled","disabled");
				} else {
					currentPageDataId.val(currentPage);//update offset hidden field value
					$.each(podcasts, function(i, podcast){
						var podcastDiv = buildPodcastDiv(podcast, i, currentPage);
						$resultsList.append(podcastDiv).fadeIn(800);
					});

          bindDynamicPodcastSocialSharing();
					console.log("success", podcasts);
				}
			}
		});

	});

	function buildPodcastDiv(podcast, i, currentPage){
		var offset=currentPage*10;
		var podcastDiv=""
			+ "<div class='bg_color shadowy item_wrapper'>"
			+ "<div class='title-and-pub-date'>";
			if(podcast.mediaType == 'Audio'){
				podcastDiv += "<div class='icon-audio-episode'></div>";
			} else {
				podcastDiv += "<div class='icon-video-episode'></div>";
			}
      var podcastUrl;
      if(podcast.identifier == null){
        podcastUrl='http://www.podcastpedia.org/podcasts/' + podcast.podcastId + '/' + podcast.titleInUrl;
      } else {
        podcastUrl='http://www.podcastpedia.org/' + podcast.identifier;
      }

			podcastDiv += '<a href='+ podcastUrl +' class="item_title">' + podcast.title + '</a>';

      podcastDiv +='<div class="pub_date_media_type">';
			  podcastDiv +='<div class="pub_date">';
          var pubDate = new Date(podcast.publicationDate);
          podcastDiv += pubDate.getFullYear() + "-" + pubDate.getMonth() + "-" + pubDate.getDate();
			  podcastDiv +='</div>';
      podcastDiv +='</div>';

			podcastDiv += '<div class="clear"></div>';
			podcastDiv +='</div>';
			podcastDiv +='<hr>';

			podcastDiv +='<div class="pod_desc">';
        podcastDiv += '<a href='+ podcastUrl +' class="item_desc">';
        if(podcast.description != null) podcastDiv += podcast.description.substring(0,350);
        podcastDiv +='</a>';
			podcastDiv +='</div>';

			podcastDiv +='<div class="ep_desc_bigger">';
        podcastDiv +='<a href='+ podcastUrl +' class="item_desc">';
        if(podcast.description != null) podcastDiv += podcast.description.substring(0,750);
        podcastDiv +='</a>';
			podcastDiv +='</div>';

			podcastDiv +='<div class="clear"></div>';


			podcastDiv +='<div class="social_and_download">';
        podcastDiv +='<a href="#' + parseInt(2*(offset+i)+1) + '" class="icon-share-podcast btn-share">Share </a>';
        podcastDiv +='</a>';
        podcastDiv +='<span class="item_url">' + podcastUrl + '</span>';
			podcastDiv +='</div>';

			return podcastDiv;
	}

  bindDynamicPodcastSocialSharing();
	function bindDynamicPodcastSocialSharing(){
    $('.item_wrapper').on('click', '.icon-share-podcast', function (e) {
      var currentDiv=$(this).closest("div.item_wrapper");
      var epUrl= currentDiv.find("span.item_url").text();

      //the share button is being replaced with social media buttons
      $(e.target).remove();
      var socialAndDownload = currentDiv.find("div.social_and_download");
      socialAndDownload.prepend(
        "<div class='fb_like'>"
        + " <div class='fb-like' data-href='"+ epUrl + " data-send='false' data-layout='button_count' data-width='70' data-height='72'></div>"
        + "</div> "
        + "<div class='twitter_share'> "
        + " <a href='//twitter.com/share' class='twitter-share-button' data-url='" + epUrl + "' data-hashtags='podcastpedia'>Tweet</a>"
        + "</div>"
        + "<div class='google_share'> "
        + "  <div class='g-plusone' data-size='medium' data-annotation='bubble' data-href='" + epUrl + "'></div>"
        + "</div>"
      );

      loadTwitter();
      loadFacebook();
      loadGooglePlus();

		 });
	}

	function loadTwitter()
	{
	    if (typeof (twttr) != 'undefined'){
	        twttr.widgets.load();
	    } else {
	        $.getScript('//platform.twitter.com/widgets.js');
	    }
	}

	function loadFacebook()
	{
	    if (typeof (FB) != 'undefined') {
	        FB.init({ status: true, cookie: true, xfbml: true });
	    } else {
	        $.getScript("//connect.facebook.net/en_US/all.js#xfbml=1", function () {
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
		        $.getScript('//apis.google.com/js/plusone.js');
		    }
		}
	}

});

