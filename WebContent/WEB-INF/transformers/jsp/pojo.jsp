<%@page import="org.romaframework.aspect.view.feature.ViewClassFeatures"%>
<%@page
	import="org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm"%>
<%@page
	import="org.romaframework.aspect.view.html.area.HtmlViewFormAreaInstance"%>
<%@page
	import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%><%@ page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%@page import="java.util.Map"%><%@page
	import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%><%@page
	import="org.romaframework.aspect.view.html.constants.RequestConstants"%><%@page
	import="org.romaframework.aspect.view.form.ViewComponent"%><%@page
	import="org.romaframework.aspect.view.html.HtmlViewAspectHelper"%>
<%
	HtmlViewRenderable component = (HtmlViewRenderable) request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);

	String style = "";
	if (component instanceof HtmlViewConfigurableEntityForm) {
		HtmlViewConfigurableEntityForm instance = (HtmlViewConfigurableEntityForm) component;
		if (instance.getSchemaObject().getFeature(ViewClassFeatures.STYLE) != null) {
			String areastyle = instance.getSchemaObject().getFeature(ViewClassFeatures.STYLE);
			if (areastyle == null || areastyle.length() < 2 || !"{".equals(areastyle.substring(0, 1))) {
			} else {
				style = areastyle.substring(1, areastyle.length() - 1);
			}
		}
	}
%><div
	class="<%=JaniculumWrapper.cssClass(component, "pojo", null)%>"
	id="<%=JaniculumWrapper.id(component, null)%>" style="<%=style%>">
	<%
		HtmlViewAspectHelper.renderByJsp((ViewComponent) component, request, pageContext.getOut());
	%>
</div>