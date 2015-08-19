<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>管理系统</title>
	<link rel="stylesheet" id="easyuiTheme"  type="text/css" href="${base}/ui/themes/<c:out value="${cookie.easyuiThemeName.value}" default="bootstrap"/>/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/css/icon.css">
	<link rel="stylesheet" type="text/css" href="${base}/css/main.css">
<style type="text/css">
<!--
a {
 font-size: 10pt; text-decoration: none; color: #000000;
}
-->
</style>
	<script type="text/javascript" src="${base}/ui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${base}/ui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/ui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${base}/js/commons.js"></script>
	<script type="text/javascript" src="${base}/js/extJquery.js"></script>

</head>

<body >  
		<div id="index_layout">
		        <div data-options="region:'north',href:'/layout/north.jsp'" class="top" >
		        </div>  
		        <div data-options="region:'west',split:true" title="导航菜单" style="width:200px;">
		            <div class="easyui-accordion" data-options="fit:true,border:false">  
<!-- 		            	<ul class="easyui-tree" data-options="url:'js/tree_data1.json',method:'get',animate:true,lines:true"></ul> -->
						${treeHtml}
		            </div>
		        </div>
		        
		        <div data-options="region:'center',title:'管理系统',iconCls:'house'">  
		            <div id="index_tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
		              <div title="首页" data-options="border:false" style="overflow: hidden;">
		              		<iframe src="/layout/main.jsp" frameborder="0" style="border: 0; width: 100%; height: 98%;"></iframe>
		              </div>
		            </div>  
		        </div>
		</div>
		        
        <!-- 右键菜单 -->
      <div id="index_tabsMenu" style="width: 120px; display: none;">
			<div title="refresh" data-options="iconCls:'transmit'">刷新</div>
			<div class="menu-sep"></div>
			<div title="close" data-options="iconCls:'delete'">关闭</div>
			<div title="closeOther" data-options="iconCls:'delete'">关闭其他</div>
			<div title="closeAll" data-options="iconCls:'delete'">关闭所有</div>
		</div>
		
    <div id="loginDialog"   title="用户登录" style="width: 330px; height: 180px; overflow: hidden; display: none;text-align:center">
				<form id="fm-loginDialog" method="post">
					<table style="margin: 0 auto;">
						<tr>
							<th>登录名</th>
							<td><input name="name" type="text" placeholder="请输入登录名" class="easyui-validatebox" data-options="required:true" value=""></td>
						</tr>
						<tr>
							<th>密码</th>
							<td><input name="pwd" type="password" placeholder="请输入密码" class="easyui-validatebox" data-options="required:true" value=""></td>
						</tr>
					</table>
				</form>
    </div>

    <div id="changePwdDialog"   title="修改密码" style="width: 330px; height: 200px; overflow: hidden; display: none;text-align:center">
				<form method="post"  id="fm-changePwd">
					<table style="margin: 0 auto;">
						<tr>
							<th>原密码</th>
							<td><input name="oldPsd" type="password" placeholder="请输入原密码" class="easyui-validatebox" data-options="required:true" value=""></td>
						</tr>
						<tr>
							<th>新密码</th>
							<td><input name="newPsd"  type="password" placeholder="请输入新密码" class="easyui-validatebox" data-options="required:true" value=""></td>
						</tr>
						<tr>
							<th>重复新密码</th>
							<td><input name="vPsd" type="password" placeholder="再次输入新密码" class="easyui-validatebox" data-options="required:true" value=""></td>
						</tr>
					</table>
				</form>
    </div>
    
</body>  
</html>