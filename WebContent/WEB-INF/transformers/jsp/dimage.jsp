<%@ taglib uri="/WEB-INF/roma.tld" prefix="roma"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	
	HtmlViewRenderable component = (HtmlViewRenderable)request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);
	
	String part = (String) request.getAttribute(RequestConstants.CURRENT_COMPONENT_PART_IN_TRANSFORMER);
	pageContext.setAttribute("part", part);
	if(!("raw".equals(part) || "label".equals(part))){
		if(JaniculumWrapper.content(component)!=null){
%>
<div class="<%=JaniculumWrapper.cssClass(component, "image", null)%>" style="<%=JaniculumWrapper.inlineStyle(component, null)%>" id="<%=JaniculumWrapper.id(component, null)%>">
<img id="<%=JaniculumWrapper.id(component, "content")%>"  src="<%=JaniculumWrapper.contextPath()%>/image.png?imagePojo=<%=JaniculumWrapper.imageId(component)%>" 		
<%
			for(String event: JaniculumWrapper.availableEvents(component)){
%>			
on<%=event%>="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>'); romaEvent('<%=JaniculumWrapper.fieldName(component)%>', '<%=event%>')"
<%
			}
%>
/></div>
<%
		}
	}else if("label".equals(part)){
%><label id="<%=JaniculumWrapper.id(component, "label")%>" class="<%=JaniculumWrapper.cssClass(component, "image", "label")%>" for="<%=JaniculumWrapper.id(component, "content")%>"><%=JaniculumWrapper.i18NLabel(component)%></label>
<%	}%>


<roma:addjs>

	$("#<%=JaniculumWrapper.id(component, null)%>").droppable({ 
		tolerance: 'pointer',
		drop: function() {window.currentStopEvent = function() {var id = '<%=JaniculumWrapper.fieldName(component)%>'; romaEvent(id,'drop')} }
	});
		 
</roma:addjs>