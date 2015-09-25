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
				var episodes = result.episodes;
				if(episodes.length == 0){
					moreResultsBtn.attr("disabled","disabled");
				} else {
					currentPageDataId.val(currentPage);//update offset hidden field value
					$.each(episodes, function(i, episode){
						var episodeDiv = buildEpisodeDiv(episode, i, currentPage);
						$resultsList.append(episodeDiv).fadeIn(800);
					});

					bindDynamicPlaying();
					bindDynamicSocialSharing();
					bindDynamicSharringCurrentEpisode();
					console.log("success", episodes);
				}
			}
		});

	});

	function buildEpisodeDiv(episode, i, currentPage){
		var offset=currentPage*10;
		var episodesDiv=""
			+ "<div class='bg_color shadowy item_wrapper'>"
			+ "<div class='title-and-pub-date'>";
			if(episode.mediaType == 'Audio'){
				episodesDiv += "<div class='icon-audio-episode'></div>";
			} else {
				episodesDiv += "<div class='icon-video-episode'></div>";
			}
			var episodeUrl='http://www.podcastpedia.org/podcasts/' + episode.podcastId + '/' + episode.podcast.titleInUrl + '/episodes/' + episode.episodeId + '/' + episode.titleInUrl;
			episodesDiv += '<a href='+ episodeUrl +' class="item_title">' + episode.title + '</a>';

			episodesDiv +='<div class="pub_date">';
        var epPubDate = new Date(episode.publicationDate);
        episodesDiv += epPubDate.getFullYear() + "-" + epPubDate.getMonth() + "-" + epPubDate.getDate();
        if(episode.isNew == 1){
          episodesDiv +='<span class="ep_is_new">&nbsp;new</span>';
        }
			episodesDiv +='</div>';

			episodesDiv += '<div class="clear"></div>';
			episodesDiv +='</div>';
			episodesDiv +='<hr>';
			episodesDiv +='<div class="ep_desc">';
			episodesDiv += '<a href='+ episodeUrl +' class="item_desc">';
			if(episode.description != null) episodesDiv += episode.description.substring(0,280);
			episodesDiv +='</a>';
			episodesDiv +='</div>';

			episodesDiv +='<div class="ep_desc_bigger">';
			episodesDiv +='<a href='+ episodeUrl +' class="item_desc">';
			if(episode.description != null) episodesDiv += episode.description.substring(0,600);
			episodesDiv +='</a>';
			episodesDiv +='</div>';

			episodesDiv +='<div class="clear"></div>';

			episodesDiv +='<div class="not_shown">';
			episodesDiv +='<div id=mediaspace'+parseInt(offset+i) +'>Flashplayer not supported</div>';


			//change with requireJs sometime soon
			episodesDiv +="<script type='text/javascript'>";
			if(episode.mediaType == 'Audio'){
				episodesDiv +="jwplayer(mediaspace"+ parseInt(offset+i) + ").setup({'controlbar': 'bottom',  'width': '100%',   'aspectratio': '16:5', 'file': '" + episode.mediaUrl+ "'});";
			} else {
				episodesDiv +="jwplayer(mediaspace"+ parseInt(offset+i) + ").setup({'controlbar': 'bottom',  'width': '100%',   'aspectratio': '16:9', 'file': '" + episode.mediaUrl+ "'});";
			}
			episodesDiv +="</script>";
			episodesDiv +='</div>';

			episodesDiv +='<div class="social_and_download">';
			episodesDiv +='<a href="#' + parseInt(2*(offset+i)) + '" class="icon-play-episode btn-share">Play </a>';
			episodesDiv +='<a href="#' + parseInt(2*(offset+i)+1) + '" class="icon-share-episode btn-share">Share </a>';
			episodesDiv+= '<a class="icon-download-ep btn-share" href="'+ episode.mediaUrl +'" download>';
			episodesDiv+= 'download&nbsp;';
			episodesDiv +='</a>';
			episodesDiv +='<span class="item_url">' + episodeUrl + '</span>';
			episodesDiv +='</div>';

			return episodesDiv;
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

