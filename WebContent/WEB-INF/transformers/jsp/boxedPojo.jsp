<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%><%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%><%@page import="java.util.Map"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="org.romaframework.aspect.view.form.ViewComponent"%><%@page import="org.romaframework.aspect.view.html.HtmlViewAspectHelper"%><%
	
HtmlViewRenderable component = (HtmlViewRenderable)request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);
%><fieldset class="<%=JaniculumWrapper.cssClass(component, "pojo", null)%>" id="<%=JaniculumWrapper.id(component, null)%>">
<legend class="<%=JaniculumWrapper.cssClass(component, "pojo", null)%>" ><%= JaniculumWrapper.i18NObjectLabel(component)%></legend>
<div class="<%=JaniculumWrapper.cssClass(component, "pojo_container", null)%>">
<%
	HtmlViewAspectHelper.renderByJsp((ViewComponent)component, request, pageContext.getOut());
%></div></fieldset>
