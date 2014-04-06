<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/roma.tld" prefix="roma"%>
<%@ page buffer="none" %>
<roma:class/>
<roma:addjs>
	if('<roma:field name="searchFilter" part="id"/>'.length>0){
		$('#<roma:field name="searchFilter" part="id"/>').keyup(function(e) {
			var key = e.which;
			if(key==13){
				$('#<roma:action name="search" part="id"/>_content').click();
			}else if(key==40){
				$('#<roma:field name="searchResult" part="id"/>_content').focus();
			}
		})
	}
	
	if('<roma:action name="search" part="id"/>'.length>0){
		$('#<roma:action name="search" part="id"/>').keyup(function(e) {
			var key = e.which;
			if(key==40 || key==38){
				$('#<roma:field name="searchResult" part="id"/>_content').focus();
			}
		});
	}
	
	//if('<roma:field name="searchFilter" part="id"/>'.length>0){
	//	var idFilter = "<roma:field name="searchFilter" part="id"/>";
	//	if(idFilter.length>0){
	//		var idSelect = ""+idFilter; 
	//		idSelect = idSelect.replace("searchFilter", "searchResult");
	//		var idConfirm = ""+idFilter; 
	//		idConfirm = idConfirm.replace("search", "confirm");
	//		if(idConfirm.length>0 && idSelect.length>0){
	//			$('#'+idSelect).keyup(function(e) {
	//				var key = e.which;
	//				if(key==13){
	//					alert("confirm");
	//					$('#'+idConfirm).click();
	//				}
	//			});
	//		}
	//	}
	//}
</roma:addjs>