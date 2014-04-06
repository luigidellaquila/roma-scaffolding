var nomeClasse = function(){
	
	var hideSubelements = function(areaLabel){
		$("#"+areaLabel).addClass("subelementshide");
		$("#"+areaLabel).removeClass("subelements");
	}
	var showSubelements = function (areaLabel){
		$("#"+areaLabel).removeClass("subelementshide");
		$("#"+areaLabel).addClass("subelements");
	
		}

	
	return{
		changeSubelementsVisibility : function(areaLabel){
			
			
	//	var subelements = $(areaLabel).(".subelementshide");
	//	var subelements = $(areaLabel).parent().children(".subelementshide");
		
		//alert(subelements.size());
		
	//	if(subelements.size()!=0){$('#mydiv').hasClass('foo')
		if ($('#'+areaLabel).hasClass('subelementshide')){
			showSubelements(areaLabel);
		}
		else{
			
			hideSubelements(areaLabel);
		}
	
		}
	}
}();
