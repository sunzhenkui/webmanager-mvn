<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
<jsp:include page="/inc.jsp"></jsp:include>
</head>
<body>  


<script>
function changeThemeFun(themeName) {
	if ($.cookie('easyuiThemeName')) {
		$('#layout_north_pfMenu').menu('setIcon', {
			target : $('#layout_north_pfMenu div[title=' + $.cookie('easyuiThemeName') + ']')[0],
			iconCls : 'emptyIcon'
		});
	}
	$('#layout_north_pfMenu').menu('setIcon', {
		target : $('#layout_north_pfMenu div[title=' + themeName + ']')[0],
		iconCls : 'tick'
	});

	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';

	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for ( var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			try {
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			} catch (e) {
				try {
					ifr.contentWindow.document.getElementById('easyuiTheme').href = href;
				} catch (e) {
				}
			}
		}
	}

	$.cookie('easyuiThemeName', themeName, {
		expires : 7
	});

};

function exit() {
	url = "/logout";
	 $.post(url,function(obj){
		 if(obj.code==0){
			location.href='/login';
		 }
		 else{
			 $.messager.show({
                 title: '提示信息',
                 msg: obj.msg
             });
		 }
     },'json');
}

function showLoginDialog(){
	$('#loginDialog').show().dialog({
		modal : true,
		closable : false,
		buttons : [ {
			text : '取消',
			handler : function() {
				$('#loginDialog').dialog('close');
			}
		}, {
			text : '登录',
			handler : function() {
				loginFun();
			}
		} ]
	});
}

function loginFun(){
	var url=  "/ulogin";
    $('#fm-loginDialog').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        var obj = jQuery.parseJSON(result);

   		 if(obj.code==0){
			location.href='/index';
   		 }
   		 else{
			 $.messager.show({
                 title: '提示信息',
                 msg: obj.msg
             });
   		 }
        }
    });
}


function logout() {
	url = "/logout";
	 $.post(url,function(obj){
		 if(obj.code==0){
			 showLoginDialog();
		 }
		 else{
			 $.messager.show({
                 title: '提示信息',
                 msg: obj.msg
             });
		 }
     },'json');
}

function editCurrentUserPwd() {
	 loginDialog = $('#changePwdDialog').show().dialog({
			modal : true,
			closable : false,
			buttons : [ {
				text : '取消',
				handler : function() {
					$('#changePwdDialog').dialog('close');
				}
			}, {
				text : '修改',
				handler : function() {
					url = "/mapper.rbac/personal/changePsd";
				    $('#fm-changePwd').form('submit',{
				        url: url,
				        onSubmit: function(){
				            return $(this).form('validate');
				        },
				        success: function(result){
				        var obj = jQuery.parseJSON(result);

				   		 if(obj.code==0){
								$('#changePwdDialog').dialog('close');
				   		 }
				   		 else{
							 $.messager.show({
				                 title: '提示信息',
				                 msg: obj.msg
				             });
				   		 }
				        }
				    });
				}
			} ]
		});
}

</script>

<div style="position: absolute; right: 0px; bottom: 0px;">
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_pfMenu',iconCls:'cog'">更换皮肤</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_kzmbMenu',iconCls:'cog'">控制面板</a> <a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#layout_north_zxMenu',iconCls:'cog'">注销</a>
</div>

<div id="layout_north_pfMenu" style="width: 120px; display: none;">
	<div onclick="changeThemeFun('default');" title="default">default</div>
	<div onclick="changeThemeFun('gray');" title="gray">gray</div>
	<div onclick="changeThemeFun('metro');" title="metro">metro</div>
	<div onclick="changeThemeFun('bootstrap');" title="bootstrap">bootstrap</div>
	<div onclick="changeThemeFun('black');" title="black">black</div>
	<div class="menu-sep"></div>
	<div onclick="changeThemeFun('cupertino');" title="cupertino">cupertino</div>
	<div onclick="changeThemeFun('dark-hive');" title="dark-hive">dark-hive</div>
	<div onclick="changeThemeFun('pepper-grinder');" title="pepper-grinder">pepper-grinder</div>
	<div onclick="changeThemeFun('sunny');" title="sunny">sunny</div>
	<div class="menu-sep"></div>
	<div onclick="changeThemeFun('metro-blue');" title="metro-blue">metro-blue</div>
	<div onclick="changeThemeFun('metro-gray');" title="metro-gray">metro-gray</div>
	<div onclick="changeThemeFun('metro-green');" title="metro-green">metro-green</div>
	<div onclick="changeThemeFun('metro-orange');" title="metro-orange">metro-orange</div>
	<div onclick="changeThemeFun('metro-red');" title="metro-red">metro-red</div>
</div>

<div id="sessionInfoDiv" style="position: absolute; right: 0px; top: 0px;" class="loguser">
	[<strong> ${ sessionScope.USER_SESSION.name}  </strong>]，欢迎你！
</div>



<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
	<div onclick="editCurrentUserPwd();">修改密码</div>
<!-- 	<div class="menu-sep"></div>
	<div onclick="currentUserRole();">我的角色</div>
	<div class="menu-sep"></div>
	<div onclick="currentUserResource();">我的权限</div> -->
</div>
<div id="layout_north_zxMenu" style="width: 100px; display: none;">
	<div onclick="showLoginDialog();">锁定窗口</div>
	<div class="menu-sep"></div>
	<div onclick="logout();">重新登录</div>
	<div onclick="exit()">退出系统</div>
</div>

</body>
</html>