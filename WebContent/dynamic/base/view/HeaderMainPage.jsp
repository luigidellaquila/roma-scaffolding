<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>

<%@page import="org.romaframework.aspect.persistence.QueryByFilter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/roma.tld" prefix="roma"%>
<%@ page buffer="none"%>
<%@page import="org.romaframework.core.*"%>
<%@page import="org.romaframework.core.schema.SchemaHelper"%>
<%@page import="org.romaframework.aspect.session.*"%>
<%@page import="org.romaframework.aspect.i18n.*"%>
<%@page import="org.romaframework.core.config.ApplicationConfiguration"%>
<%@page import="org.romaframework.module.users.domain.BaseAccount"%>
<%@page
	import="org.romaframework.aspect.authentication.AuthenticationAspect"%>


<div id="header" >
	<div class="topbanner">
		<span> &nbsp</span>
	</div>
	<div id="headermenu">
		<roma:field name="menu" />
	</div>

	<div>
		<a id="logoutHeader"
			href='<%=request.getContextPath()%>/dynamic/common/logout.jsp'>
			 <%=Roma.i18n().resolve("$logout")%>
			</a>
		<span class="scenario"><roma:action name="disconnect" /></span>
		<span class="scenario"><roma:field name="scenario" /></span> 
	</div>




	<div style="clear: both;">
		<!-- Do not remove this div -->
	</div>
</div>