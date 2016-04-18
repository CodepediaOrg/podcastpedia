
  bindShowLastEpisodes();
  //delegate again...
  function bindShowLastEpisodes(){
    $('.podcast_wrapper').delegate('.icon-last-episodes', 'click',  function () {
      var currentDiv=$(this).closest("div.podcast_wrapper");
      var lastEpisodesDiv = currentDiv.find("div.last_episodes");
      var lastEpisodesButton = currentDiv.find("a.icon-last-episodes");
      if(lastEpisodesDiv.hasClass('not_shown')){
        lastEpisodesDiv.removeClass('not_shown').addClass('shown');
        lastEpisodesButton.removeClass('icon-arrow-down').addClass('icon-arrow-up');
      } else {
        lastEpisodesDiv.removeClass('shown').addClass('not_shown');
        lastEpisodesButton.removeClass('icon-arrow-up').addClass('icon-arrow-down');
      }
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


  bindDynamicSocialSharingPodcasts();
  function bindDynamicSocialSharingPodcasts(){
    $('.podcast_wrapper').on('click', '.icon-share-podcast', function (e) {
      var currentDiv=$(this).closest("div.podcast_wrapper");
      var podcastUrl= currentDiv.find("span.podcast_url").text();
      var podcastTitle= currentDiv.find("span.podcast_title").text();

      currentDiv.find("div.last_episodes").css("margin-bottom","70px");

      //the share button is being replaced with social media buttons
      $(e.target).remove();
      var socialAndDownload = currentDiv.find("div.social_and_download_podcast");
      socialAndDownload.prepend(
        "<div class='share_buttons'>"
        + "<div class='fb_like'>"
        + " <div class='fb-share-button' data-href='"+ podcastUrl + " data-send='false' data-layout='button' data-mobile-iframe='true'></div>"
        + "</div> "
        + "<div class='twitter_share'> "
        + " <a href='//twitter.com/intent/tweet' class='twitter-share-button' data-url='" + podcastUrl + "' data-text='"+ podcastTitle +"' data-via='podcastpedia'>Tweet</a>"
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

