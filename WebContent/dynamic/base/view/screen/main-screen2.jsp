<%@page import="org.romaframework.scaffolding.CustomApplicationConfiguration"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="/WEB-INF/roma.tld" prefix="roma"%>
<%@ page buffer="none" %>
<%@ page import="org.romaframework.core.Roma"%>
<%@ page import="org.romaframework.core.config.ApplicationConfiguration"%>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server

String referer = request.getHeader("referer");
Object originalReferrer = request.getSession().getAttribute("OriginalReferrer");
Object refererSet = request.getSession().getAttribute("ReferrerSet");
if(refererSet == null){
	request.setAttribute("ReferrerSet", true);
	request.setAttribute("OriginalReferrer", referer);
}else{
	if(referer!=null && !referer.equals(originalReferrer)){
		Roma.session().invalidateSession(session);
		return;
	}
}
	


String appName = Roma.component(ApplicationConfiguration.class).getApplicationName();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd"> 
<html>
<head>
<%--<roma:css/>--%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/base/css/style-sorter.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/base/css/ui.datepicker.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/base/css/uitabs.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/base/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/base/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/base/css/font.css" />


<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/base/css/application-style.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/base/css/header.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/base/css/simplesearch.css" />


<!--[if IE 7]>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/static/base/css/ie7.css" />
<script type="text/javascript">var iE7 = true;</script>
<%request.getSession().setAttribute(CustomApplicationConfiguration.IS_IE_SESSION_PARAMETER,Boolean.TRUE);%>
<![endif]-->
<!--[if IE 8]>
<script type="text/javascript">var iE8 = true;</script>
<%request.getSession().setAttribute(CustomApplicationConfiguration.IS_IE_SESSION_PARAMETER,Boolean.TRUE);%>
<![endif]-->

<!-- ADDITIONAL CSS -->
<link rel="icon" href="<%=request.getContextPath() %>/static/images/favicon.ico" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=appName%></title>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jquery.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jquery.timeentry.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jquery-ui.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jquerycssmenu.js"></script>  
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jquery.tablesorter.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jqDnR.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/romaAjax.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/horizontalslider.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/searchtableheaderinpututilities.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jquery.easing.1.3.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jquery.flipCounter.1.2.pack.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jshashtable.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jquery.numberformatter-1.2.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jquery.ui.datepicker-it.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/base/js/jquery.simple-color.js" type="text/javascript"></script>
<roma:inlinejs/>
<roma:inlinecss/>

<script type="text/javascript">
<%

String stileAzienda = "";

if(Roma.component(ApplicationConfiguration.class).isApplicationDevelopment()){
 %>
$(document).ready(function() {
	var res = '';
	var ids = null;	
	$('[id]').each(function(){
		  ids = $('[id='+this.id+']');
		  if(ids.length>1 && ids[0]==this) {
		      console.warn('Multiple IDs #'+this.id);
	      	  res = res+"\n"+'Multiple IDs #'+this.id;
		  }
		});
	if (res != '') {
	alert(res);	
	}
});
<%} %>
requestContextPath = "<%=request.getContextPath() %>/";
globalCharType = "charset=UTF-8";
</script>
</head>
<body>
<div id="janiculumWaitDiv" class="janiculumWaitImage" style="background-repeat: no-repeat; background-position: center center; background-image: url(<%=request.getContextPath()%>/static/base/image/wait.gif); position:absolute; width: 100%; height: 100%; left:-10000px; top: -10000px; z-index: -1000">
</div>
<div class="rootElement<%=stileAzienda%>">
	<roma:screenArea name="/"/>
</div>
</body>
</html>
