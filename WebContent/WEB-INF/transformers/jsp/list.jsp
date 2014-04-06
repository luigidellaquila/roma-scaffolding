<%@page import="org.romaframework.aspect.view.html.transformer.jsp.directive.JspTransformerHelper"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Set"%><%@page import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%><%@page import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page import="java.util.Map"%><%
	
	HtmlViewRenderable component = (HtmlViewRenderable)request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);
	
	String part = (String) request.getAttribute(RequestConstants.CURRENT_COMPONENT_PART_IN_TRANSFORMER);
	pageContext.setAttribute("part", part);
%>
<div class="<%=JaniculumWrapper.cssClass(component, "list", null)%>" style="<%=JaniculumWrapper.inlineStyle(component, null)%>" id="<%=JaniculumWrapper.id(component, null)%>">
<%
String selection = "single";
if(!JaniculumWrapper.isMultiSelection(component)){
	selection = "multiple";
}
%>
        <table>
        <tr>
        <td class="list_box">
        <select id="<%=JaniculumWrapper.id(component, "content")%>" name="<%=JaniculumWrapper.fieldName(component)%>" size="5" selection="<%=selection%>" 
        <%
			boolean existsChangeEvent=false;
			for(String event: JaniculumWrapper.availableEvents(component)){
			%>
				on<%=event%>="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>'); romaEvent('<%=JaniculumWrapper.fieldName(component)%>', '<%=event%>')"
				<%if("change".equals(event)){	
					existsChangeEvent = true;
				}
			}
			if(!existsChangeEvent){
		%>
			onchange="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>'); romaSendAjaxRequest()"
			<%} %>
			style="<%if(!JaniculumWrapper.isValid(component)){%>border-color:red;<%} %>">
        <%
        int rowIndex = 0;
        for(Object o:JaniculumWrapper.getChildren(component)){
        	HtmlViewRenderable opt = (HtmlViewRenderable) o;
        %>
                    <option id="<%=JaniculumWrapper.id(component, "item")%>_<%=rowIndex%>" value="<%=JaniculumWrapper.fieldName(component)%>_<%=rowIndex%>"
                        <%=JaniculumWrapper.isSelected(component, rowIndex)?"selected=\"selected\"":""%>
                        <%if(JaniculumWrapper.isMultiSelection(component)){%>
                            onclick="romaMultiSelectChanged('<%=JaniculumWrapper.fieldName(component)%>_<%=rowIndex%>'); romaSendAjaxRequest();"
                        <%}%> >
                    <%=JspTransformerHelper.raw(opt) %>
                    </option>
            <%
            rowIndex++;
        }
            %>   
        </select>
        <%if(!JaniculumWrapper.isValid(component)){%>
          <span class="<%=JaniculumWrapper.cssClass(component, "list", "validation_message")%>"><%=JaniculumWrapper.validationMessage(component)==null?"Invalid":JaniculumWrapper.validationMessage(component)%></span> 
        <% } %>
        </td>
   
    </table>
</div>