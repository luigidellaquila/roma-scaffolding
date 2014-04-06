<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page
	import="org.romaframework.aspect.view.html.transformer.helper.JaniculumWrapper"%>
<%@page
	import="org.romaframework.aspect.view.html.component.HtmlViewConfigurableEntityForm"%>
<%@page
	import="org.romaframework.aspect.view.html.constants.RequestConstants"%>
<%@page
	import="org.romaframework.aspect.view.html.area.HtmlViewRenderable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/roma.tld" prefix="roma"%>
<%@ page buffer="none"%>

<%@page import="org.romaframework.core.config.ApplicationConfiguration"%>
<%@page import="org.romaframework.core.*"%>

<%
	String appName = Utility.getCapitalizedString(Roma.component(ApplicationConfiguration.class).getApplicationName());
	HtmlViewRenderable component = (HtmlViewRenderable) request.getAttribute(RequestConstants.CURRENT_COMPONENT_IN_TRANSFORMER);
	HtmlViewConfigurableEntityForm form = (HtmlViewConfigurableEntityForm) component;
	HtmlViewRenderable userNameId = (HtmlViewRenderable) form.getFieldComponent("userName");
	HtmlViewRenderable passNameId = (HtmlViewRenderable) form.getFieldComponent("userPassword");
	HtmlViewRenderable loginAction = (HtmlViewRenderable) form.getChildComponent("login");
	
	String userInputId = JaniculumWrapper.id(userNameId, "content");
	String userPassId = JaniculumWrapper.id(passNameId, "content");
	String userInputName = JaniculumWrapper.fieldName(userNameId);
	String userPassName = JaniculumWrapper.fieldName(passNameId);
	String loginActionName = JaniculumWrapper.actionName(loginAction);
	
%>

<table id="loginmain" style="margin-left: auto; margin-right: auto;">
	<tr>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr>

		<td></td>
		<td>
			<table>
				<!-- <tr>
					<td align="center">
						<div class="logintitle">
							<h1>
								<%=appName.substring(0, appName.length() - 2) + appName.substring(appName.length() - 2, appName.length()).toUpperCase()%>
							</h1>
						</div>
					</td>
				</tr> -->
				<tr>
					<td>
						<div class="loginbox">
								<div class="class_Login class_CustomLogin customLoginDiv">
		<form method="post" action="app">
			<table class="render_grid  area_main customLoginDiv">
				<tbody>
					<tr class="userame">
						<td class="render_label field_userName"><div
								class="render_form_label_label   field_userName">
								<label class="render_form_label_label   field_userName"><%=Roma.i18n().resolve("$Object.userName.label")%>:<span
									class="requiredField"> *</span></label>
							</div></td>
						<td class="render_form_content field_userName"><div
								class="render_text field_userName">
								<input type="text" name="<%=userInputName%>"
									class="render_text field_userName" id="<%=userInputId%>"
									onblur="romaFieldChangedNoFocus('<%=userInputName%>');" />
							</div></td>
					</tr>
					<tr class="password">
						<td class="render_label field_userPassword"><div
								class="render_form_label_label   field_userPassword">
								<label class="render_form_label_label   field_userPassword"><%=Roma.i18n().resolve("$Object.userPassword.label")%>:<span
									class="requiredField"> *</span></label>
							</div></td>
						<td class="render_form_content field_userPassword"><div
								class="render_password field_userPassword">
								<input type="password" name="<%=userPassName%>"
									class="render_password field_userPassword" id="<%=userPassId%>"
									onblur="romaFieldChangedNoFocus('<%=userPassName%>');" />
							</div></td>
					</tr>
					<tr class="languages">
						<td><roma:field name="languages" part="label" /></td>
						<td><roma:field name="languages" /></td>
					</tr>
					<tr>
						<td class="loginButton" ><input type="submit"
							name="<%=loginActionName%>" value="<%=Roma.i18n().resolve("$Object.login.label")%>"
							class="render_button action_login"
							onclick="updateAndSendLogin('<%=userInputName%>','<%=userPassName%>','<%=loginActionName%>');"></input></td>
						<td><roma:action name="forgotPassword"/></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
						</div>
					</td>
				</tr>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
			<div class="loginassetlogo">
				<a href="#"><img
					src='<%=request.getContextPath()%>/static/base/image/poweredByRoma.jpg'
					alt="AssetData" border='0' style="margin-left: 50px;" /> </a> <br /> <span
					class="applicationVersion"><%=Roma.component(ApplicationConfiguration.class).getApplicationVersion()%></span>
			</div>
		</td>
		<td></td>
	</tr>
</table>


<style type="text/css">
.logingigrouplogo {
	padding-bottom: 1em;
	padding-top: 1em;
	padding-right: 10px;
}

.loginassetlogo {
	padding-bottom: 1em;
	padding-top: 1em;
}

.logintitle {
	color: #03639A !important;
}

.loginbox {
	padding-left: 2em;
	padding-right: 2em;
}

#loginmain {
	height: 350px;
}
</style>
