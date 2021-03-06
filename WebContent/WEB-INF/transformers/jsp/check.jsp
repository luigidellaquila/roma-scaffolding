<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	
	HtmlViewRenderable component = (HtmlViewRenderable)request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);
	
	String part = (String) request.getAttribute(RequestConstants.CURRENT_COMPONENT_PART_IN_TRANSFORMER);
%>
<%if (!("raw".equals(part) || "label".equals(part))){   %>
<div class="<%=JaniculumWrapper.cssClass(component, "check", null)%>" style="<%=JaniculumWrapper.inlineStyle(component, null)%>" id="<%=JaniculumWrapper.id(component, null)%>">

<%if (!("raw".equals(part) || "label".equals(part))){   %>
	<input id="<%=JaniculumWrapper.id(component, "reset")%>" type="hidden" name="<%=JaniculumWrapper.fieldName(component)%>_reset" />
	<input id="<%=JaniculumWrapper.id(component, "content")%>" style="<%=JaniculumWrapper.inlineStyle(component, null)%>" type="checkbox" name="<%=JaniculumWrapper.fieldName(component)%>" 
	title="<%=JaniculumWrapper.i18NHint(component)%>" 
	<%if(JaniculumWrapper.checked(component)){%> checked="checked"<%}%> 
	<%if(JaniculumWrapper.disabled(component)){%> disabled="disabled" <%}%> 
	
	<% 
	boolean existsClickEvent = false;
	for(String event: JaniculumWrapper.availableEvents(component)){
	%>
	on<%=event%>="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>'); romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>_reset'); romaEvent('<%=JaniculumWrapper.fieldName(component)%>', '<%=event%>')"
	<%	if(event.equals("click")){
			existsClickEvent = true;
		}		
	} 
	
	if(!existsClickEvent) {
	%>onclick="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>'); romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>_reset'); romaSendAjaxRequest()"
	<%} %> />
			
		<%if (!JaniculumWrapper.isValid(component)){%>
			<span class="<%=JaniculumWrapper.cssClass(component, "check", "validation_message")%>"></span>	
		<%} %>
	
	<%} %>
</div>
<%} %>
<%if ("raw".equals(part)){  %><%= JaniculumWrapper.content(component, true)==null?"":JaniculumWrapper.content(component, true) %><%} %>

<%if ("label".equals(part)){%>  
<label id="<%=JaniculumWrapper.id(component, "label")%>" class="<%=JaniculumWrapper.cssClass(component, "check", "label")%>" for="<%=JaniculumWrapper.id(component, "content")%>"><%=JaniculumWrapper.i18NLabel(component)%></label>
<%}%>