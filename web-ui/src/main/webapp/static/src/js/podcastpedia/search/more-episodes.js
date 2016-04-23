//TODO - before considering this functionality, check out i18n in Javascript, we have here new download etc...
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

