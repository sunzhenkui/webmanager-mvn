<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibsForLogin.jsp" %>
<!DOCTYPE html>
<html style="width: 100%;height: 100%;overflow: hidden;">
    <head>
        <title>管理系统</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" id="easyuiTheme"  type="text/css" href="${base}/ui/themes/bootstrap/easyui.css">
		<link rel="stylesheet" type="text/css" href="${base}/css/icon.css">
        <link rel="stylesheet" href="${base}/css/login.css" type="text/css" media="screen" />
        
        
	<script type="text/javascript" src="${base}/ui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${base}/ui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/ui/locale/easyui-lang-zh_CN.js"></script>
	
        <script type="text/javascript" src="${base}/js/login.js"></script>
    </head>
    <body style="width: 100%;height: 100%;overflow: hidden;padding: 0;margin: 0;">
        <form id="form-body"  action="${base}/login" method="POST">
            <ul>
                <li><label>账	号 </label> <input class="easyui-validatebox account form-textbox" type="text" name="name" value="root" required="required"></li>
                <li><label>密	码 </label> <input class="easyui-validatebox  password form-textbox" type="password" name="password" value="123456" required="required"></li>
            </ul>
        </form>

        <div id="logo"  style="display: none;">
            <h1>后台管理系统</h1>
        </div>
    </body>
</html>