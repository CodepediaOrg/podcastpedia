function init() {
	document.advanced_search_form.allTheseWords.disabled=true;
	document.advanced_search_form.exactPhrase.disabled=true;
	document.advanced_search_form.anyOfTheseWords.disabled=true;
	document.advanced_search_form.noneOfTheseWords.disabled=true;
	
	document.getElementById('boolean_mode_part').classList.add('disabled_look');
}

function makeChoice(){
	var val = 0;
	for( i = 0; i < document.advanced_search_form.searchMode.length; i++ ){
		if( document.advanced_search_form.searchMode[i].checked == true ){
			val = document.advanced_search_form.searchMode[i].value;
			if(val=='boolean'){
				document.advanced_search_form.queryText.disabled=true;
				document.advanced_search_form.allTheseWords.disabled=false;
				document.advanced_search_form.exactPhrase.disabled=false;
				document.advanced_search_form.anyOfTheseWords.disabled=false;
				document.advanced_search_form.noneOfTheseWords.disabled=false;
				
				var nat_mode_part = document.getElementById('natural_mode_part');
				nat_mode_part.classList.add('disabled_look');
				
				var bool_mode_part = document.getElementById('boolean_mode_part');
				bool_mode_part.classList.remove('disabled_look');

			} else {
				document.advanced_search_form.queryText.disabled=false;
				document.advanced_search_form.allTheseWords.disabled=true;
				document.advanced_search_form.exactPhrase.disabled=true;
				document.advanced_search_form.anyOfTheseWords.disabled=true;
				document.advanced_search_form.noneOfTheseWords.disabled=true;
				
				document.getElementById('boolean_mode_part').classList.add('disabled_look');
				document.getElementById('natural_mode_part').classList.remove('disabled_look');
			}
		}		
	}
}


window.onload = init; 