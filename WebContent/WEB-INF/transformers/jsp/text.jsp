<%@page import="org.romaframework.scaffolding.helper.JSRegexHelper"%>
<%@page
	import="org.romaframework.aspect.validation.feature.ValidationFieldFeatures"%>
<%@page
	import="org.romaframework.aspect.view.html.component.HtmlViewContentComponent"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page
	import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@page
	import="org.romaframework.aspect.view.html.constants.RequestConstants"%>
<%@page import="java.util.Set"%>
<%@page
	import="org.romaframework.aspect.view.html.transformer.jsp.JspTransformer"%>
<%@page
	import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%>
<%@page
	import="org.romaframework.aspect.view.html.constants.RequestConstants"%>
<%@page import="org.romaframework.core.Roma"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>

<%
	HtmlViewRenderable component = (HtmlViewRenderable) request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);

	String part = (String) request.getAttribute(RequestConstants.CURRENT_COMPONENT_PART_IN_TRANSFORMER);
	HtmlViewContentComponent c = (HtmlViewContentComponent) component;

	String regex = Roma.component(JSRegexHelper.class).getRegex(c.getSchemaField().getFeature(ValidationFieldFeatures.UI_VALIDATION));

	if (!("raw".equals(part) || "label".equals(part))) {
%>
<div class="<%=JaniculumWrapper.cssClass(component, "text", null)%>"
	style="<%=JaniculumWrapper.inlineStyle(component, null)%>"
	id="<%=JaniculumWrapper.id(component, null)%>">
	<input title="<%=JaniculumWrapper.i18NHint(component)%>"
		id="<%=JaniculumWrapper.id(component, "content")%>"
		style="<%if (!JaniculumWrapper.isValid(component)) {%>border-color:red;<%}%><%=JaniculumWrapper.inlineStyle(component, null)%>"
		type="text" autocomplete="off" name="<%=JaniculumWrapper.fieldName(component)%>"
		value="<%=JaniculumWrapper.content(component, true) == null ? "" : JaniculumWrapper.content(component, true).toString().replaceAll("<", "&lt;").replaceAll(">", "&gt;")%>"
		<%if (JaniculumWrapper.disabled(component)) {%> disabled="disabled"
		<%}
				boolean existsChangeEvent = false;
				for (String event : JaniculumWrapper.availableEvents(component)) {%>
		on<%=event%>="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>'); romaEvent('<%=JaniculumWrapper.fieldName(component)%>', '<%=event%>')"
			<%if ("change".equals(event)) {
						existsChangeEvent = true;
					}
				}
				if (!existsChangeEvent) {
					if (regex == null) {%>
		onchange="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>')"
		<%} else {%>
		onchange="romaFieldChanged('<%=JaniculumWrapper.fieldName(component)%>')"
		onKeyUp="validate(this, <%=regex%>);" <%}
				}%> />
	<%
		if (!JaniculumWrapper.isValid(component)) {
	%>
	<span
		class="<%=JaniculumWrapper.cssClass(component, "text", "validation_message")%>"><%=JaniculumWrapper.validationMessage(component) == null ? "Invalid" : JaniculumWrapper.validationMessage(component)%></span>
	<%
		}
	%>
</div>
<%
	}
	if ("raw".equals(part)) {
%><%=JaniculumWrapper.content(component, true) == null ? "" : JaniculumWrapper.content(component, true)%>
<%
	} else if ("label".equals(part)) {
%><label id="<%=JaniculumWrapper.id(component, "label")%>"
	class="<%=JaniculumWrapper.cssClass(component, "text", "label")%>"
	for="<%=JaniculumWrapper.id(component, "content")%>"><%=JaniculumWrapper.i18NLabel(component)%></label>
<%
	}
%>