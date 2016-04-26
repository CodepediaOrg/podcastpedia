
  $('.podcast_wrapper').on('click', '.icon-unsubcribe-podcast', function () {
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

  /**
   * Remove from category subscriptions
   */
  $('.podcast_wrapper').on('click', '.icon-remove-from-subscription-category',  function () {
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

