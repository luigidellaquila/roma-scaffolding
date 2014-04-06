<%@ taglib uri="/WEB-INF/roma.tld" prefix="roma"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Set"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%>
<%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%>
<%@page import="java.util.Map"%>
<%
	
	HtmlViewRenderable component = (HtmlViewRenderable)request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);
	
	String part = (String) request.getAttribute(RequestConstants.CURRENT_COMPONENT_PART_IN_TRANSFORMER);
	pageContext.setAttribute("part", part);
	String valign = JaniculumWrapper.areaVerticalAlignment(component);
	if("center".equals(valign)){
		valign = "middle";
	}
	String halign = JaniculumWrapper.areaHorizontalAlignment(component);
%>
<roma:addjs>
<%	int row = 0;
	for(Object c:JaniculumWrapper.getChildren(component)){
		HtmlViewRenderable child=(HtmlViewRenderable)c;
		 %>
$("#<%=JaniculumWrapper.id(component, null)%>_<%=row%>").draggable({ revert: true ,start: function(event, ui) {var id = '<%=JaniculumWrapper.fieldName(child)%>'; id = id.replace('.',''); id = id.replace(',','');  romaEvent(id,'drag') },stop: function(event, ui){ if(window.currentStopEvent) {window.currentStopEvent();}}});
	$("#<%=JaniculumWrapper.id(component, null)%>_<%=row%>_hole").droppable({ 
		tolerance: 'pointer',
		drop: function() { window.currentStopEvent = function() {var id = '<%=JaniculumWrapper.fieldName(child)%>'; id = id.replace('.',''); id = id.replace(',',''); romaEvent(id,'drop') ; return true;} }
	});
		 
<%
		 row++;
		 } 
%></roma:addjs>
<table cellpadding="0" cellspacing="0" id="<%=JaniculumWrapper.id(component, null)%>" class="<%=JaniculumWrapper.cssClass(component, "rowset", null)%>" style="<%=JaniculumWrapper.inlineStyle(component, null)%>">
	<%
	int row = 0;
	for(Object c:JaniculumWrapper.getChildren(component)){
		HtmlViewRenderable child=(HtmlViewRenderable)c;
	
	%>
    <tr><td id="<%=JaniculumWrapper.id(component, null)%>_<%=row%>_td" class="<%=JaniculumWrapper.cssClass(child, null, null)%>"><%JspTransformerHelper.delegate(child, null,pageContext.getOut()); %></td></tr>
   <%
	   	JspTransformerHelper.addCss("#"+JaniculumWrapper.id(component, null)+"_"+row+"_td", "vertical-align", valign);
	   	JspTransformerHelper.addCss("#"+JaniculumWrapper.id(component, null)+"_"+row+"_td", "text-align", halign);
		row++;
	}
   %>
</table>
