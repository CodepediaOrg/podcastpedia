//attach autocomplete
$("#tagQuery").autocomplete({
  minLength: 1,
  delay: 500,

  //define callback to format results
  source: function (request, response) {
    $.getJSON("/tags/get_tag_list", request, function(result) {
      response($.map(result, function(item) {
        return {
          // following property gets displayed in drop down
          label: item.name + "(" + item.nrOfPodcasts + ")",
          // following property gets entered in the textbox
          value: item.name,
          // following property is added for our own use
          tag_url: "https://" + window.location.host + "/tags/" + item.tagId + "/" + item.name
        }

      }));
    });
  },

  //define select handler
  select : function(event, ui) {
    if (ui.item) {
      event.preventDefault();
      $("#selected_tags span").append('<a href=' + ui.item.tag_url + ' class="btn-metadata2">'+ ui.item.label +'</a>');
      //$("#tagQuery").value = $("#tagQuery").defaultValue
      var defValue = $("#tagQuery").prop('defaultValue');
      $("#tagQuery").val(defValue);
      $("#tagQuery").blur();
      return false;
    }
  }

});
