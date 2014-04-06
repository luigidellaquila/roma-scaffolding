<%@page import="org.romaframework.aspect.view.html.component.HtmlViewContentComponent"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	
	HtmlViewRenderable component = (HtmlViewRenderable)request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);
	String part = (String) request.getAttribute(RequestConstants.CURRENT_COMPONENT_PART_IN_TRANSFORMER);
	
	if(!("raw".equals(part)||"label".equals(part))){
%>
	<div class="<%=JaniculumWrapper.cssClass(component, "text", null)%>" id="<%=JaniculumWrapper.id(component, null)%>" style="<%if(!JaniculumWrapper.isValid(component)){%>border-color:red;<%}%><%=JaniculumWrapper.inlineStyle(component, null)%> border:thin solid #000; padding:0;">
		<input style="color:#B1BABF; margin:0; border:none;" isdefaultvalue="true" defaultvalue="<%=JaniculumWrapper.content(component, true)==null?"":JaniculumWrapper.content(component, true)%>" id="<%=JaniculumWrapper.id(component, "content")%>" class="<%=JaniculumWrapper.cssClass(component, "text", "content")%>" type="text" name="<%=JaniculumWrapper.fieldName(component)%>" value="<%=JaniculumWrapper.content(component, true)==null?"":JaniculumWrapper.content(component, true)%>" 
		<%if(JaniculumWrapper.disabled(component)){%> disabled="disabled" <%}
		boolean  existsChangeEvent=false;
		for(String event: JaniculumWrapper.availableEvents(component)){
			if(!"change".equals(event)){
		
		%>
		
		
			on<%=event%>="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>'); romaEvent('<%=JaniculumWrapper.fieldName(component)%>', '<%=event%>')"
			<%}else{ 
				existsChangeEvent=true;
			}
		}
		if(existsChangeEvent){
		%>
		onchange="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>'); romaEvent('<%=JaniculumWrapper.fieldName(component)%>', 'change'); checkForChanges(this);"
		<%}else{ %>
		
		onchange="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>'); checkForChanges(this);"
		<%} %>
		
		onFocus="clearField(this);"
		
		onBlur="restoreDefaultValues(this);"
		 />
		<%if(!JaniculumWrapper.isValid(component)){%>
			<span class="<%=JaniculumWrapper.cssClass(component, "text", "validation_message")%>"><%=JaniculumWrapper.validationMessage(component)==null?"Invalid":JaniculumWrapper.validationMessage(component)%></span>	
		<%} %>
	</div>
<%} 
	if("raw".equals(part)){%><%=JaniculumWrapper.content(component, true)==null?"":JaniculumWrapper.content(component, true)%><%
	
	}else if("label".equals(part)){
			%><label id="<%=JaniculumWrapper.id(component, "label")%>" class="<%=JaniculumWrapper.cssClass(component, "text", "label")%>" for="<%=JaniculumWrapper.id(component, "content")%>"><%=JaniculumWrapper.i18NLabel(component)%></label>
<%} %>