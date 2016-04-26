$('.podcast_wrapper').on('click', '.icon-last-episodes', function () {
  var currentDiv=$(this).closest("div.podcast_wrapper");
  var lastEpisodesDiv = currentDiv.find("div.last_episodes");
  var lastEpisodesButton = currentDiv.find("a.icon-last-episodes");
  var sharingPodcastWasClicked = currentDiv.find("div.share_buttons").length;
  if(lastEpisodesDiv.hasClass('not_shown')){
    currentDiv.find("div.pod_desc").css("margin-bottom","15px");
    currentDiv.find("div.pod_desc_bigger").css("margin-bottom","15px");
    lastEpisodesDiv.removeClass('not_shown').addClass('shown');
    lastEpisodesButton.removeClass('icon-arrow-down').addClass('icon-arrow-up');
    if(sharingPodcastWasClicked){
      lastEpisodesDiv.css("margin-bottom","70px");
    }
  } else {
    if(sharingPodcastWasClicked){
      currentDiv.find("div.pod_desc").css("margin-bottom", "70px");
      currentDiv.find("div.pod_desc_bigger").css("margin-bottom", "70px");
    } else {
      currentDiv.find("div.pod_desc").css("margin-bottom", "40px");
      currentDiv.find("div.pod_desc_bigger").css("margin-bottom", "40px");
    }

    lastEpisodesDiv.removeClass('shown').addClass('not_shown');
    lastEpisodesButton.removeClass('icon-arrow-up').addClass('icon-arrow-down');
  }



});
