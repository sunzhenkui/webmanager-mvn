<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/classes/tlds/sys.tld" prefix="sys" %>
<%@ page import="com.imt.common.Global" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="base" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<c:set var="pagesize" value="<%=Global.PAGE_SIZE %>"/>
<c:set var="pname" value="pno"/>
<% 
if(session.getAttribute(Global.LOGON_SESSION_KEY)==null)
	response.sendRedirect("/login");
%>
