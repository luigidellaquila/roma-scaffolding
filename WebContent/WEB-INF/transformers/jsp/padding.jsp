<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Set"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%>
<%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%>
<%@page import="java.util.Map"%>
<%@page import="java.io.Writer"%>
<%@page import="java.io.PrintWriter"%>
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
<div class="<%=JaniculumWrapper.cssClass(component, null,null)%>" style="display:block ; height: <%=JaniculumWrapper.formatNumberContent(component)%>px" id="<%=JaniculumWrapper.id(component,null)%>">	
</div>
