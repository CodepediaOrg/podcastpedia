
  //TODO merge this function with the one in podcast-details to offer the same functionality
  $('div.subscribe-form-subscription-category-cls').dialog({autoOpen: false, modal: true});
  $(".subscribe-to-podcast-cls").click(function(){
    //var theDiv=$(this).next("div.subscribe-form-subscription-category");
    var divId=this.id.replace('subscribe-to-podcast','');
    var theDiv=$('#subscribe-form-subscription-category' + divId);
    theDiv.dialog("open");
    $(theDiv).dialog({
      autoOpen: false,
      height: 220,
      width: 370,
      modal: true,
      buttons: {
        "Submit": function() {
          var inputNewSubscriptionCategory = $("input#newSubscriptionCategory" + divId);
          var selectedSubscriptionCategoryValue = $("#subscription_categories"+ divId + " option:selected").val();
          var xorInputOrSelectSubscriptionCategory = (selectedSubscriptionCategoryValue != "" && inputNewSubscriptionCategory.val() == "") ||(selectedSubscriptionCategoryValue == "" && inputNewSubscriptionCategory.val() != "");
          if(xorInputOrSelectSubscriptionCategory){
            postSubscribeToSubscriptionCategoryFromPodcasts(divId);
          } else {
            inputNewSubscriptionCategory.after("<br/><span style='color: red'>Please either add to existing category OR create a new one</span>");
            return false;
          }
        },
        Cancel: function() { $( this ).dialog( "close" ); }
      },
      close: function() {
      }
    });
  });

  function postSubscribeToSubscriptionCategoryFromPodcasts(divId){
    $.post("/users/subscriptions",
      {
        podcastId:  $("input#sub_podcastId" + divId).val(),
        _csrf: $( "input[name='_csrf']" ).val(),
        newSubscriptionCategory: $( "#newSubscriptionCategory" + divId).val(),
        existingSubscriptionCategory: $( "#subscription_categories" + divId).val()
      },

      function(data){
        //we have received valid response
        $("#subscribe-to-podcast"+divId).css({ 'background-color': '#185B8B' });
        $("#subscribe-to-podcast"+divId).css({ 'pointer-events': 'none' });
        $("#subscribe-form-subscription-category"+divId).dialog("close");
      });
  }

