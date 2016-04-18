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
