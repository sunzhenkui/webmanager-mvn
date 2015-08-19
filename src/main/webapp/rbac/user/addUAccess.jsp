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
	        <form  id="fm-tbuaccess-add" method="post" novalidate>
	        <input type="hidden"  name="userId" value="${user.userId}" checked>
	         <div class="ftitle">${user.name}</div>
	          
	        	<c:forEach items="${roleList}" var="role"> 
	        			<c:set value=""  var="isCK" ></c:set>
	        			<c:forEach items="${userRoleList}" var="ur"> 
	        					<c:if test="${ur.roleId == role.roleId}"><c:set value="checked"  var="isCK" ></c:set></c:if>
	        			</c:forEach> 
		            <div class="fitem">
		                <label>${role.name}:<input type="checkbox"  name="roleId" value="${role.roleId}" ${isCK}></label>
		            </div>
	            </c:forEach> 
	        </form>
        </div>
    </div>
    <div class="clear"></div>
    <div  class="btns">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="create_tbuaccess()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dg_close_tbuaccess()">取消</a>
    </div>
    <!-- 添加层结束 -->
    
<script>

function create_tbuaccess(){
    var url = '/rbac/user/addUAccess';
    $('#fm-tbuaccess-add').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
        var obj = jQuery.parseJSON(result);

   		 if(obj.code==0){
             $('#dlg-tbuaccess').dialog('close'); 
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

function dg_close_tbuaccess(){
	$('#dlg-tbuaccess').dialog('close'); 
}


</script>
    
<style type="text/css">
@import url(/css/sys.css);
</style> 


</body>  
</html>