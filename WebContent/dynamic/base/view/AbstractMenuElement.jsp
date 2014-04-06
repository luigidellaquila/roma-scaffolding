<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/roma.tld" prefix="roma"%>
<%@ page buffer="none"%>
<%@page import="org.romaframework.core.*"%>
<%@page import="org.romaframework.core.schema.SchemaHelper"%>
<%@page import="org.romaframework.aspect.session.*"%>
<%@page import="org.romaframework.aspect.i18n.*"%>
<%@page import="org.romaframework.core.config.ApplicationConfiguration"%>
<%@page import="org.romaframework.module.users.domain.BaseAccount"%>
<%@page import="org.romaframework.aspect.authentication.AuthenticationAspect"%>

<%
    String appName = Utility.getCapitalizedString(Roma.component(ApplicationConfiguration.class).getApplicationName());
    SessionInfo sess = Roma.session().getActiveSessionInfo();
%>

<roma:action name="action" />
