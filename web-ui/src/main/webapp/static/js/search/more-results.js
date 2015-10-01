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
			success: function(searchResult){
				var results = searchResult.results;
				if(results.length == 0){
					moreResultsBtn.attr("disabled","disabled");
          moreResultsBtn.remove();
				} else {
					currentPageDataId.val(currentPage);//update offset hidden field value
					$.each(results, function(i, result){
						var resultDiv = buildResultDiv(result, i, currentPage);
						$resultsList.append(resultDiv).fadeIn(800);
					});

					bindDynamicPlaying();
					bindDynamicSocialSharing();
					bindDynamicSharringCurrentEpisode();
					console.log("success", results);
				}
			}
		});

	});

	function buildResultDiv(result, i, currentPage){
		var offset=currentPage*10;
		var resultDiv=""
			+ "<div class='bg_color shadowy item_wrapper'>"
			+ "<div class='title-and-pub-date'>";
			if(result.mediaType == 'Audio'){
				resultDiv += "<div class='icon-audio-episode'></div>";
			} else {
				resultDiv += "<div class='icon-video-episode'></div>";
			}
			var resultUrl='http://www.podcastpedia.org' + result.relativeLink;
			resultDiv += '<a href='+ resultUrl +' class="item_title">' + result.title + '</a>';

			resultDiv +='<div class="pub_date">';
        var resultPubDate = new Date(result.publicationDate);
        resultDiv += resultPubDate.getFullYear() + "-" + resultPubDate.getMonth() + "-" + resultPubDate.getDate();
        if(result.isNew){
          resultDiv +='<span class="ep_is_new">&nbsp;new</span>';
        }
			resultDiv +='</div>';

			resultDiv += '<div class="clear"></div>';
			resultDiv +='</div>';
			resultDiv +='<hr>';
			resultDiv +='<div class="ep_desc">';
			resultDiv += '<a href='+ resultUrl +' class="item_desc">';
			if(result.description != null) resultDiv += result.description.substring(0,280);
			resultDiv +='</a>';
			resultDiv +='</div>';

			resultDiv +='<div class="ep_desc_bigger">';
			resultDiv +='<a href='+ resultUrl +' class="item_desc">';
			if(result.description != null) resultDiv += result.description.substring(0,600);
			resultDiv +='</a>';
			resultDiv +='</div>';

			resultDiv +='<div class="clear"></div>';

			resultDiv +='<div class="not_shown">';
			resultDiv +='<div id=mediaspace'+parseInt(offset+i) +'>Flashplayer not supported</div>';


			//change with requireJs sometime soon
			resultDiv +="<script type='text/javascript'>";
			if(result.mediaType == 'Audio'){
				resultDiv +="jwplayer(mediaspace"+ parseInt(offset+i) + ").setup({'controlbar': 'bottom',  'width': '100%',   'aspectratio': '16:5', 'file': '" + result.mediaUrl + "'});";
			} else {
				resultDiv +="jwplayer(mediaspace"+ parseInt(offset+i) + ").setup({'controlbar': 'bottom',  'width': '100%',   'aspectratio': '16:9', 'file': '" + result.mediaUrl + "'});";
			}
			resultDiv +="</script>";
			resultDiv +='</div>';

			resultDiv +='<div class="social_and_download">';
			resultDiv +='<a href="#' + parseInt(2*(offset+i)) + '" class="icon-play-episode btn-share">Play </a>';
			resultDiv +='<a href="#' + parseInt(2*(offset+i)+1) + '" class="icon-share-episode btn-share">Share </a>';
			resultDiv+= '<a class="icon-download-ep btn-share" href="'+ result.mediaUrl +'" download>';
			resultDiv+= 'download&nbsp;';
			resultDiv +='</a>';
			resultDiv +='<span class="item_url">' + resultUrl + '</span>';
			resultDiv +='</div>';

			return resultDiv;
	}


	bindDynamicPlaying();
	//delegate again...
	function bindDynamicPlaying(){
		$('.item_wrapper').delegate('.icon-play-episode', 'click',  function () {
			var currentDiv=$(this).closest("div.item_wrapper");
			var playerDiv = currentDiv.find("div.not_shown")
			playerDiv.removeClass('not_shown').addClass('shown');
			var jwpId = currentDiv.find("div.jwplayer ").attr("id")

			// if we load the player, the div containing the player will set the distance to the share, play and download buttons
			currentDiv.find("div.ep_desc").css("margin-bottom","5px");

			//and also widen the distance to the share buttonsif they are available
			var shareButtonsDiv = currentDiv.find("div.share_buttons");
			if(shareButtonsDiv.length > 0){
				playerDiv.css("margin-bottom","75px");
			}

			$('html, body').animate({
				scrollTop: currentDiv.offset().top
			}, 150);
			if(typeof jwpId != 'undefined'){
				jwplayer(jwpId).play();
			}
		 });
	}


	bindDynamicSocialSharing();
	function bindDynamicSocialSharing(){
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
					+ " <a href='//twitter.com/share' class='twitter-share-button' data-url='" + epUrl + "' data-hashtags='podcastpedia'>Tweet</a>"
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
	}

	bindDynamicSharringCurrentEpisode();
	function bindDynamicSharringCurrentEpisode(){
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
					+ " <a href='//twitter.com/share' class='twitter-share-button' data-url='" + epUrl + "' data-hashtags='podcastpedia'>Tweet</a>"
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

	}

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

