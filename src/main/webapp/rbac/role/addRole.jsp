<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
<jsp:include page="/inc.jsp"></jsp:include>
</head>

<body >  
	    <!-- 添加层开始 -->
    <div>
    	<div class="fm">
	        <form  id="fm-tbrole-add" method="post" novalidate>
	            <div class="fitem">
	                <label>角色名称:</label>
	                <input name="name" class="easyui-validatebox" required="true" >
	            </div>
	            <div class="fitem">
	                <label>角色级别:</label>
		            <select name="roleLevel" class="easyui-combobox" panelHeight="auto" style="width:100px">
		                <option value="1">1级</option>
		                <option value="2">2级</option>
		            </select>
	            </div>
	        </form>
        </div>
    </div>
    <div class="clear"></div>
    <div class="btns">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="create_tbrole()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dg_close_tbrole()">取消</a>
    </div>
    <!-- 添加层结束 -->
    
<script>
function create_tbrole(){
    var url = '/rbac/role/addRole';
    $('#fm-tbrole-add').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        var obj = jQuery.parseJSON(result);

   		 if(obj.code==0){
             $('#dg-tbrole').datagrid('reload');
             $('#dlg-tbrole').dialog('close'); 
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

function dg_close_tbrole(){
	$('#dlg-tbrole').dialog('close'); 
}


</script>
    
<style type="text/css">
@import url(/css/sys.css);
</style> 


</body>  
</html>