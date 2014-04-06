<%@page import="org.romaframework.aspect.view.html.constants.TransformerConstants"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%><%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	
	HtmlViewRenderable component = (HtmlViewRenderable)request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);
	
	String part = (String) request.getAttribute(RequestConstants.CURRENT_COMPONENT_PART_IN_TRANSFORMER);
	pageContext.setAttribute("part", part);
	
	Double content = (Double)JaniculumWrapper.content(component, true);
	int leftWidth = content==null ? 1 : (int)(content*100) + 1; 
	
	if (!("raw".equals(part) || "label".equals(part))){   %>
	<div class="<%=JaniculumWrapper.cssClass(component, "progress", null)%>" style="<%=JaniculumWrapper.inlineStyle(component, null)%> width:400px;" id="<%=JaniculumWrapper.id(component, null)%>">
	<%if(part==null || "".equals(part) || "all".equals(part)){ %>
	<table style="width: 300px; border: 1px solid lightblue">
		<tr>
			<td id="<%=JaniculumWrapper.id(component, "content")%>" style="height: 10px; background-color:lightblue; width: <%=leftWidth%>%"></td>
			<td style="background-color: white;"></td>
		</tr>
	</table>
	<%=leftWidth - 1%>%
	<%
	} %>
	</div>
<%

	
	}
if("raw".equals(part)){
%><%=JaniculumWrapper.content(component, true)==null?"":JaniculumWrapper.content(component, true)%><%
}else if("label".equals(part)){
%><label id="<%=JaniculumWrapper.id(component, "label")%>" class="<%=JaniculumWrapper.cssClass(component, "progress", "label")%>" for="<%=JaniculumWrapper.id(component, "content")%>"><%=JaniculumWrapper.i18NLabel(component)%></label>
<%
}
%>