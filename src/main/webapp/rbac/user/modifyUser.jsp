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
    <div id="dlg-tbuser" >
    	<div class="fm">
	        <form  id="fm-tbuser-mod" method="post" novalidate>
	            <div class="fitem">
	                <label>用户名:</label>
	                <input name="name" class="easyui-validatebox" required="true" value="${model.name}">
	            </div>
	            <div class="fitem">
	                <label>密码:</label>
	                <input name="password" type="password" class="easyui-validatebox" required="true">
	            </div>
	            <div class="fitem">
	                <label>部门:</label>
	                <input name="depId"  class="easyui-combotree"  value="${model.depId}"  required="true"  data-options="url:'${base}/rbac/department/getDepMenuData'">
	            </div>
	        </form>
        </div>
    </div>
    <div class="clear"></div>
    <div class="btns">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="modify_tbuser()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dg_close_tbuser()">取消</a>
    </div>
    <!-- 添加层结束 -->
    
<script>

function modify_tbuser(){
    var url = '/rbac/user/modifyUser';
    $('#fm-tbuser-mod').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        var obj = jQuery.parseJSON(result);

   		 if(obj.code==0){
             $('#dg-tbuser').datagrid('reload');
             $('#dlg-tbuser').dialog('close'); 
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