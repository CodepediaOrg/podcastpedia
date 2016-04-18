bindDynamicSocialSharing();
function bindDynamicSocialSharing(){
  $('.item_wrapper').on('click', '.icon-share-episode', function (e) {
    var currentDiv=$(this).closest("div.item_wrapper");
    var itemUrl= currentDiv.find("span.item_url").text();
    var itemTitle= currentDiv.find("span.item_sharing_title").text();


    //the share button is being replaced with social media buttons
    $(e.target).remove();
    var socialAndDownload = currentDiv.find("div.social_and_download");
    socialAndDownload.prepend(
      "<div class='share_buttons'>"
      + "<div class='fb_like'>"
      + " <div class='fb-share-button' data-href='"+ itemUrl + " data-send='false' data-layout='button' data-mobile-iframe='true'></div>"
      + "</div> "
      + "<div class='twitter_share'> "
      + " <a href='//twitter.com/intent/tweet' class='twitter-share-button' data-url='" + itemUrl + "' data-text='"+ itemTitle +"' data-via='podcastpedia'>Tweet</a>"
      + "</div>"
      + "<div class='google_share'> "
      + "  <div class='g-plusone' data-size='medium' data-annotation='none' data-href='" + itemUrl + "'></div>"
      + "</div>"
      + "</div>"
    );

    loadTwitter();
    loadFacebook();
    loadGooglePlus();

  });
}
