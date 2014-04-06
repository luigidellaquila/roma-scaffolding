<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Set"%>
<%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%>
<%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%>
<%@page import="java.util.Map, java.util.LinkedList"%>
<%@ taglib uri="/WEB-INF/roma.tld" prefix="roma"%>
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

<div id="<%=JaniculumWrapper.id(component, null)%>" class="<%=JaniculumWrapper.cssClass(component, "horizontalslider", null)%>">
  
	<dl id="<%=JaniculumWrapper.id(component, null)%>_dl" class="<%=JaniculumWrapper.cssClass(component, "horizontalslider", null)%>_dl">
	<%
		int row = 0;
		LinkedList<HtmlViewRenderable> auxList = new LinkedList<HtmlViewRenderable>();
		for(Object c : JaniculumWrapper.getChildren(component)){
			auxList.addFirst((HtmlViewRenderable)c);
			}
		for(HtmlViewRenderable child : auxList){	
			
			
	%>
		<dt id="<%=JaniculumWrapper.id(component, null)%>_dl_<%=row%>_dt" class="<%=JaniculumWrapper.cssClass(component, "horizontalslider", null)%>_dl_dt">
		   <%= JaniculumWrapper.i18NObjectLabel(child) %>
		</dt>
    	<dd id="<%=JaniculumWrapper.id(component, null)%>_dl_<%=row%>_dd" class="<%=JaniculumWrapper.cssClass(component, "horizontalslider", null)%>_dl_dt">    
			<%JspTransformerHelper.delegate(child, null,pageContext.getOut()); %>
    	</dd>
	    
	<%
		row++;
		}
	%>
	</dl>
</div>

<roma:addjs>

		$('#<%=JaniculumWrapper.id(component, null)%>').easyAccordion({ 
				autoStart: false	});
<%-- 		$('#<%=JaniculumWrapper.id(component, null)%>').find('dt:eq(<%= JaniculumWrapper.getChildren(component).size()-1 %>)').activateSlide(); --%>
		
</roma:addjs>