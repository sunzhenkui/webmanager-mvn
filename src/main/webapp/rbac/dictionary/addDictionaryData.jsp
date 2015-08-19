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
	        <form  id="fm-dicdata-add" method="post" novalidate>
	        <input name="dicValue"  type="hidden"  value="${dicValue}">
	            <div class="fitem">
	                <label>键名:</label>
	                <input name="dicDataName" class="easyui-validatebox" required="true" >
	            </div>
	            <div class="fitem">
	                <label>键值:</label>
	                <input name="dicDataValue" class="easyui-validatebox" required="true" >
	            </div>
	        </form>
        </div>
    </div>
    <div class="clear"></div>
    <div class="btns">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="create_dicdata()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dg_close_dicdata()">取消</a>
    </div>
    <!-- 添加层结束 -->
    
<script>
function create_dicdata(){
    var url = '/rbac/dictionary/addDictionaryData';
    $('#fm-dicdata-add').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        var obj = jQuery.parseJSON(result);

   		 if(obj.code==0){
             $('#dg-dicdata').datagrid('reload');
             //$('#dlg-dicdata').dialog('close'); 
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

function dg_close_dicdata(){
	$('#dlg-dicdata').dialog('close'); 
}




</script>
    
<style type="text/css">
@import url(/css/sys.css);
</style> 


</body>  
</html>