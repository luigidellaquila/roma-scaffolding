<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	
	HtmlViewRenderable component = (HtmlViewRenderable)request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);
	
	String part = (String) request.getAttribute(RequestConstants.CURRENT_COMPONENT_PART_IN_TRANSFORMER);
	pageContext.setAttribute("part", part);
%>
<div class="<%=JaniculumWrapper.cssClass(component, "bulletlist", null)%>" style="<%=JaniculumWrapper.inlineStyle(component, null)%>" id="<%=JaniculumWrapper.id(component, null)%>">

        <table>
        <tr>
        <td class="bulletlist_box">
        <ul id="<%=JaniculumWrapper.id(component, "content")%>" style="list-style-type:square">
        <%
        int rowIndex = 0;
        for(Object o:JaniculumWrapper.getChildren(component)){
        	HtmlViewRenderable opt = (HtmlViewRenderable) o;
        %>
                    <li id="<%=JaniculumWrapper.id(component, "item")%>_<%=rowIndex%>" style="display:list-item" >
                    <%=JspTransformerHelper.raw(opt) %>
                    </li>
            <%
            rowIndex++;
        }
            %>   
        </ul>
        <%if(!JaniculumWrapper.isValid(component)){%>
          <span class="<%=JaniculumWrapper.cssClass(component, "list", "validation_message")%>"><%=JaniculumWrapper.validationMessage(component)==null?"Invalid":JaniculumWrapper.validationMessage(component)%></span> 
        <% } %>
        </td>
    </table>
</div>