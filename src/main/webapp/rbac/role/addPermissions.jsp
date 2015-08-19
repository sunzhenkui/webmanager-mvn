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

    <div class="easyui-panel" title="Nested Panel" style="width:max;height:800px;padding:10px;"  data-options="fit:true">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'west',split:true" style="width:250px;padding:10px">
                <ul class="easyui-tree" id="tree-tbmenu-permission" data-options="url:'${base}/rbac/menu/getUserMenus',animate:true" style="float:left;margin: 10px 10px"></ul>
            </div>
            <div data-options="fit:true,region:'center'" style="padding:10px">
					
			    <form  id="fm-tbpermission-add" method="post" action="">
			    <input type="hidden"  name="roleId" value="${role.roleId}" >
			    
				 <table id="dg-tbmenu-permission"  style="float:left;margin: 10px 10px">
			       <thead>
			            <tr>
			                <th data-options="field:'resourceId',checkbox:true"></th>
			                <th data-options="field:'name',width:100">资源名称</th>
			                <th data-options="field:'url',width:180,align:'right'">链接地址</th>
			            </tr>
			        </thead>
				 </table>

			    <div class="clear"></div>
<!-- 			    <div class="btns">
			        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="create_tbpermission()">添加</a>
			        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="dg_close_tbpermission()">关闭</a>
			    </div> -->
				</form>
            </div>
        </div>
    </div>

	
	<form  id="fm-tbresource-find" method="post" action="">
		<input name="menuId" type="hidden" value="">
	</form>
    
    
<script>
var tbmenu_permission_isLoadOk = false;//选择框状态是否加载完毕
$('#tree-tbmenu-permission').tree({
	onClick: function(node){
		var url = '${base}/rbac/resource/getResourceList';
		$("#fm-tbresource-find input[name='menuId']").val(node.id);
	    $('#fm-tbresource-find').form('submit',{
	        url: url,
	        onSubmit: function(){
	            return $(this).form('validate');
	        },
	        success: function(result){
	        	tbmenu_permission_isLoadOk = false;
		        var obj = jQuery.parseJSON(result);
		        $('#dg-tbmenu-permission').datagrid('loadData',obj);  
		        
		        //动态选择，将选择框默认选中
 				var perUrl = "${base}/rbac/role/getPermissionList?roleId=${role.roleId}";
		        $.getJSON(perUrl, function(perJsons){
		        	var item =  $('#dg-tbmenu-permission').datagrid('getRows');
		        	//var perJsons = jQuery.parseJSON(rst);
		        	 if (item) {
		                 for (var i=0; i<item.length ; i++) {
		                   	var resourceId = item[i].resourceId;
		                   	for(var t=0;t<perJsons.rows.length;t++){
 		                   		if(resourceId==perJsons.rows[t].resourceId){
 		                   			$('#dg-tbmenu-permission').datagrid("selectRow",i);
		                   		}
		                   	}
		                 }
		                 tbmenu_permission_isLoadOk = true;
		             }
		        }); 
		        
	        }
	    });
	}
});

$(function(){
	var dataUrl = "/rbac/menu/menuList";
	var pagesize = '${pagesize}';
	var pname = '${pname}';

	$('#dg-tbmenu-permission').datagrid({
	    fitColumns:true,
	    rownumbers:true,
	    singleSelect:true,
	    url:dataUrl ,
	    singleSelect:false,
	    pagination:true, //这里添加分页控件
	    onCheck:function(rowIndex, rowData){
	    	//alert("oncheck:"+rowIndex+"--"+tbmenu_permission_isLoadOk);
         	if(tbmenu_permission_isLoadOk){
        		var para = {"roleId":"${role.roleId}" , "resourceId":rowData.resourceId};
        		var addUrl = "${base}/rbac/role/addPermissions";
        		$.post(addUrl, para);
        	} 
        },
        onUncheck:function(rowIndex, rowData){
	    	//alert("onUncheck:"+tbmenu_permission_isLoadOk);
         	if(tbmenu_permission_isLoadOk){
        		var addUrl = "${base}/rbac/role/removePermissions";
        		var para = {"roleId":"${role.roleId}" , "resourceId":rowData.resourceId};
        		$.post(addUrl , para);
        	} 
        },
        //此处可以优化，批量删除和添加
        onCheckAll:function(rows){
	    	//alert(rows.length+"--"+tbmenu_permission_isLoadOk);
         	if(tbmenu_permission_isLoadOk){
        		for(var i=0;i<rows.length;i++){
            		var para = {"roleId":"${role.roleId}" , "resourceId":rows[i].resourceId};
            		var addUrl = "${base}/rbac/role/addPermissions";
            		$.post(addUrl, para);
        		}
        	} 
        },
        onUncheckAll:function(rows){
	    	//alert(rows.length+"--"+tbmenu_permission_isLoadOk);
         	if(tbmenu_permission_isLoadOk){
        		for(var i=0;i<rows.length;i++){
            		var para = {"roleId":"${role.roleId}" , "resourceId":rows[i].resourceId};
            		var addUrl = "${base}/rbac/role/removePermissions";
            		$.post(addUrl, para);
        		}
        	}
        },
	});
	
	var p = $('#dg-tbmenu-permission').datagrid('getPager');
	if (p){
		$(p).pagination({
	        pageSize: pagesize,//每页显示的记录条数，默认为10  
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '共 {total} 条记录',  
            onSelectPage:function(pageNumber, pageSize){
            	$.getJSON(dataUrl+"?"+pname+"="+pageNumber, function(json){
            		 $('#dg-tbmenu-permission').datagrid('loadData',json);   
            	});
            },
            onBeforeRefresh:function(pageNumber,pageSize){
                $(this).pagination('loading');
            },
            onRefresh:function(){
                $(this).pagination('loaded');
            }
        });
	}
});

</script>

</body>  
</html>