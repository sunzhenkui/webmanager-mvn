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

	<form  id="fm-tbuaccess-add" method="post"  novalidate>
	<input type="hidden" name="userId" value="${userId}">
	 <div class="am">
		 <div class="amr">
				<div class="tul">用户名</div>
				<ul class="dul">
				<c:forEach items="${roleList}" var="role"> 
						<li>${role.name} : <input type="checkbox"  checked></li>
				</c:forEach> 
				</ul>
		</div>
<!-- 		 <div class="amr">
				<div class="tul">用户名</div>
				<ul class="dul">
						<li>test1</li>
						<li>test2</li>
						<li>test1</li>
						<li>test2</li>
						<li>test1</li>
						<li>test2</li>
						<li>test1</li>
						<li>test2</li>
						<li>test1</li>
						<li>test2</li>
						<li>test1</li>
						<li>test2</li>
				</ul>
		</div> -->
	</div>
	</form>
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
   			var tab = $('#tabs').tabs('getSelected');
   			if (tab){
   				var index = $('#tabs').tabs('getTabIndex', tab);
   				$('#tabs').tabs('close', index);
   			}
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
</script>

<style type="text/css">
@import url(/css/sys.css?1);
</style> 

</body>  
</html>