<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="${base}/ui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/ui/themes/icon.css">
	<script type="text/javascript" src="${base}/ui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${base}/ui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/ui/locale/easyui-lang-zh_CN.js"></script>
	
</head>

<body>  
	<script>
	$.messager.alert('错误提示','当前用户无此操作权限','error');
	</script>
</body>  
</html>