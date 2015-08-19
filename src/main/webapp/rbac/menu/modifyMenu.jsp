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
    	<div class="fm">
	        <form  id="fm-tbmenu-mod" method="post" novalidate>
	        <input name="menuId"  type="hidden" value="${model.menuId}">
	            <div class="fitem">
	                <label>菜单名称:</label>
	                <input name="name" class="easyui-validatebox" required="true"   value="${model.name}">
	            </div>
	            <div class="fitem">
	                <label>父节点:</label>
	                <input name="parentId" class="easyui-combotree"  value="<c:if test="${model.parentId != 0}">${model.parentId}</c:if>" data-options="url:'${base}/rbac/menu/getUserMenus',method:'get'" >
	            </div>
	            <div class="fitem">
	                <label>节点类型:</label>
		            <select name="nodeType" class="easyui-combobox" panelHeight="auto" style="width:100px">
		                <option value="1"  <c:if test="${model.nodeType == 1}">selected</c:if>>目录</option>
		                <option value="2"  <c:if test="${model.nodeType == 2}">selected</c:if>>节点</option>
		            </select>
	            </div>
	            <div class="fitem">
	                <label>排序:</label>
	                <input name="orderNo" class="easyui-validatebox" required="true"  value="${model.orderNo}">
	            </div>
	            <div class="fitem">
	                <label>链接:</label>
	                <input name="url" class="easyui-validatebox" required="true"   value="${model.url}">
	            </div>
	        </form>
        </div>
    </div>
    <div class="clear"></div>
    <div class="btns">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="modify_tbmenu()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dg_close_tbmenu()">取消</a>
    </div>
    <!-- 添加层结束 -->
    
<script>

function modify_tbmenu(){
    var url = '/rbac/menu/modifyMenu';
    $('#fm-tbmenu-mod').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        var obj = jQuery.parseJSON(result);

   		 if(obj.code==0){
             $('#dg-tbmenu').datagrid('reload');
             $('#tree-tbmenu').tree('reload');
             $('#dlg-tbmenu').dialog('close'); 
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

function dg_close_tbmenu(){
	$('#dlg-tbmenu').dialog('close'); 
}


</script>
    
<style type="text/css">
@import url(/css/sys.css);
</style> 

</body>  
</html>