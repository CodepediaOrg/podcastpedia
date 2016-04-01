$(function (){

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

  bindShowLastEpisodes();
  //delegate again...
  function bindShowLastEpisodes(){
    $('.podcast_wrapper').delegate('.icon-last-episodes', 'click',  function () {
      var currentDiv=$(this).closest("div.podcast_wrapper");
      var lastEpisodesDiv = currentDiv.find("div.last_episodes")
      lastEpisodesDiv.removeClass('not_shown').addClass('shown');
    });
  }

  bindUnsubscribeFromPodcast();
  //delegate again...
  function bindUnsubscribeFromPodcast(){
    $('.podcast_wrapper').delegate('.icon-unsubcribe-podcast', 'click',  function () {
      var currentDiv=$(this).closest("div.podcast_wrapper");
      var podcastId = currentDiv.find("input[name=podcastId]").val();

      $.post("/users/subscriptions/unsubscribe",
        {
          podcastId:  podcastId,
          _csrf: $( "input[name='_csrf']" ).val()
        },

        function(data){
          //we have received valid response, we hide it
          currentDiv.addClass("not_shown");
        });
    });
  }

  removeFromSubscriptionCategory();
  //delegate again...
  function removeFromSubscriptionCategory(){
    $('.podcast_wrapper').delegate('.icon-remove-from-subscription-category', 'click',  function () {
      var currentDiv=$(this).closest("div.podcast_wrapper");
      var podcastId = currentDiv.find("input[name=podcastId]").val();
      var subscriptionCategory = currentDiv.find("input[name=subscriptionCategory]").val();

      $.post("/users/subscriptions/remove-from-subscription-category",
        {
          podcastId:  podcastId,
          subscriptionCategory: subscriptionCategory,
          _csrf: $( "input[name='_csrf']" ).val()
        },

        function(data){
          //we have received valid response, we hide it
          currentDiv.addClass("not_shown");
        });
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

  bindDynamicSocialSharingPodcasts();
  function bindDynamicSocialSharingPodcasts(){
    $('.podcast_wrapper').on('click', '.icon-share-podcast', function (e) {
      var currentDiv=$(this).closest("div.podcast_wrapper");
      var podcastUrl= currentDiv.find("span.podcast_url").text();

      currentDiv.find("div.last_episodes").css("margin-bottom","70px");

      //the share button is being replaced with social media buttons
      $(e.target).remove();
      var socialAndDownload = currentDiv.find("div.social_and_download_podcast");
      socialAndDownload.prepend(
        "<div class='share_buttons'>"
        + "<div class='fb_like'>"
        + " <div class='fb-like' data-href='"+ podcastUrl + " data-send='false' data-layout='button_count' data-width='70' data-height='72'></div>"
        + "</div> "
        + "<div class='twitter_share'> "
        + " <a href='//twitter.com/share' class='twitter-share-button' data-url='" + podcastUrl + "' data-hashtags='podcastpedia'>Tweet</a>"
        + "</div>"
        + "<div class='google_share'> "
        + "  <div class='g-plusone' data-size='medium' data-annotation='bubble' data-href='" + podcastUrl + "'></div>"
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
