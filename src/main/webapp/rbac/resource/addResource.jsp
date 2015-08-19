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
	        <form  id="fm-tbresource-add" method="post" novalidate>
	        <input type="hidden"  name="menuId"  value="${menuId}" />
	            <div class="fitem">
	                <label>资源名称:</label>
	                <input name="name" class="easyui-validatebox"   size="30"  required="true" >
	            </div>
	            <div class="fitem">
	                <label>所属节点:</label>
	                <input name="menuId" class="easyui-combotree" value="${menuId}"   data-options="url:'${base}/rbac/menu/getUserMenus'" required="true" >
	            </div>
	            <div class="fitem">
	                <label>资源地址:</label>
	                <input name="url" class="easyui-validatebox"   size="50"  required="true" >
	            </div>
	        </form>
        </div>
    </div>
    <div class="clear"></div>
    <div class="btns">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="create_tbresource()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dg_close_tbresource()">取消</a>
    </div>
    <!-- 添加层结束 -->
    
<script>
function create_tbresource(){
    var url = '/rbac/resource/addResource';
    $('#fm-tbresource-add').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        var obj = jQuery.parseJSON(result);

   		 if(obj.code==0){
   			var url = '${base}/rbac/resource/getResourceList';
   		    $('#fm-tbresource-query').form('submit',{
   		        url: url,
   		        success: function(result){
   			        var obj = jQuery.parseJSON(result);
   			        $('#dg-tbresource').datagrid('loadData',obj);  
   			     	$('#dlg-tbresource').dialog('close'); 
   		        }
   		    });
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

function dg_close_tbresource(){
	$('#dlg-tbresource').dialog('close'); 
}


</script>
    
<style type="text/css">
@import url(/css/sys.css);
</style> 


</body>  
</html>