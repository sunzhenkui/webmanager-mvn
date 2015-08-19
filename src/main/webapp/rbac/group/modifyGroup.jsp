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
    <div id="dlg-tbusergroups" >
    	<div class="fm">
	        <form  id="fm-tbusergroups-mod" method="post" novalidate>
	        	 <input name="groupId"  type="hidden" value="${model.groupId}">
	            <div class="fitem">
	                <label>组名:</label>
	                <input name="name" class="easyui-validatebox" required="true" value="${model.name}">
	            </div>
	        </form>
        </div>
    </div>
    <div class="clear"></div>
    <div class="btns">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="modify_tbusergroups()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dg_close_tbusergroups()">取消</a>
    </div>
    <!-- 添加层结束 -->
    
<script>

function modify_tbusergroups(){
    var url = '/rbac/group/modifyGroup';
    $('#fm-tbusergroups-mod').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        var obj = jQuery.parseJSON(result);

   		 if(obj.code==0){
             $('#dg-tbusergroups').datagrid('reload');
             $('#dlg-tbusergroups').dialog('close'); 
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

function dg_close_tbusergroups(){
	$('#dlg-tbusergroups').dialog('close'); 
}

</script>
<style type="text/css">
@import url(/css/sys.css);
</style> 


</body>  
</html>