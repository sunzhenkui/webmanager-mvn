<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
<jsp:include page="/inc.jsp"></jsp:include>
</head>

<body>  
	    <!-- 添加层开始 -->
    <div>
    		<div style="padding:100px 0px 0px 0px">
				<form method="post"  id="fm-modifyPsd">
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
    <div class="clear"></div>
    <div  class="btns">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="modify_password()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="password_reset()">重置</a>
    </div>
    <!-- 添加层结束 -->
    
<script>
function password_reset(){
	$('#fm-modifyPsd')[0].reset();
}

function modify_password(){
	url = "/rbac/personal/changePsd";
    $('#fm-modifyPsd').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        var obj = jQuery.parseJSON(result);

   		 if(obj.code==0){
			 $.messager.show({
                 title: '提示信息',
                 msg: obj.msg
             });
   			$(this).reset();
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

function dg_close_tbuser(){
	$('#dlg-tbuser').dialog('close'); 
}


</script>
    
<style type="text/css">
@import url(/css/sys.css);
</style> 


</body>  
</html>