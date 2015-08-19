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
	<div class="easyui-panel" title="" style="width:max;height:800px;padding:10px;">
		<div class="easyui-layout" data-options="fit:true">
	            <div data-options="region:'west',split:true" style="width:250px;padding:10px">
	                    <ul class="easyui-tree" id="tree-tbmenu" data-options="url:'${base}/rbac/menu/getUserMenus',animate:true" style="float:left;margin: 10px 10px"></ul>
	            </div>
				<div data-options="region:'center'" style="padding:10px">
				    	<table id="dg-tbresource" > </table>
					    <div id="tb-tbresource" style="padding:5px;height:auto">
					        <div style="margin-bottom:5px">
					            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add_dg_tbresource()">添加</a>
					            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit_dg_tbresource()">修改</a>
					            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove_tbresource()">删除</a>
					        </div>
					        <div>
					         	<form  id="fm-tbresource-query" method="post" novalidate>
					         	<input type="hidden"  name="${pname}"  value="" />
					         	<input type="hidden"  name="menuId"  value="" />
<!-- 					            资源名: <input class="easyui-validatebox" name="name"  style="width:80px">
					            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query_tbuser()">查询</a> -->
					              </form>
					        </div>
					    </div>
		        		<div id="dlg-tbresource"></div>  
				</div>
	         </div>
    </div>
<script>
$('#tree-tbmenu').tree({
	onClick: function(node){
		var dataUrl = "/rbac/resource/getResourceList";
		$('#dg-tbresource').datagrid({url:dataUrl });
		var menuId = node.id;
		$("#fm-tbresource-query input[name='menuId']").val(menuId);
	    queryResources(menuId);
	}
});

function queryResources(menuId){
	var url = '${base}/rbac/resource/getResourceList';
    $('#fm-tbresource-query').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
	        var obj = jQuery.parseJSON(result);
	        $('#dg-tbresource').datagrid('loadData',obj);  
        }
    });
}

$(function(){
	var dataUrl = "/rbac/resource/getResourceList";
	var pagesize = '${pagesize}';
	var pname = '${pname}';
	$('#dg-tbresource').datagrid({
	    fitColumns:true,
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb-tbresource',
	    //url:dataUrl ,
	    loadMsg:'数据加载中.....',
	    columns:[[
	        {field:'resourceId',title:'ID',width:80},
	        {field:'menu.name',title:'所属菜单',width:80},
	        {field:'name',title:'资源名称',width:80},
	        {field:'url',title:'资源地址',width:80}
	    ]],
	    pagination:true //这里添加分页控件
	});
	
	var p = $('#dg-tbresource').datagrid('getPager');
	if (p){
		$(p).pagination({
	        pageSize: pagesize,//每页显示的记录条数，默认为10  
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '共 {total} 条记录',  
            onSelectPage:function(pageNumber, pageSize){
            	$("#fm-tbresource-query :hidden[name='"+pname+"']").val(pageNumber);
                $('#fm-tbresource-query').form('submit',{
                    url: dataUrl,
                    onSubmit: function(){
                        return $(this).form('validate');
                    },
                    success: function(result){
                   		var obj = jQuery.parseJSON(result);
                   		 $('#dg-tbresource').datagrid('loadData',obj);   
                    }
                });
            },
            onBeforeRefresh:function(pageNumber,pageSize){
                $(this).pagination('loading');
                //progress bar
            },
            onRefresh:function(){
                $(this).pagination('loaded');
            }
        });
	}
});

function add_dg_tbresource(){
	var menuId = $("#fm-tbresource-query input[name='menuId']").val();
	$('#dlg-tbresource').dialog({  
	    title: '添加',  
	    width: 600,  
	    height: 200,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: '/rbac/resource/addResource?menuId='+menuId,  
	    modal: true  
	});  
}

function edit_dg_tbresource(){
    var row = $('#dg-tbresource').datagrid('getSelected');
	$('#dlg-tbresource').dialog({  
	    title: '修改1',  
	    width: 400,  
	    height: 200,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: "/rbac/resource/modifyResource?id="+row.resourceId,  
	    modal: true  
	});  
}

function remove_tbresource(){
    var row = $('#dg-tbresource').datagrid('getSelected');
    if (row){
        $.messager.confirm('操作','确定删除么?',function(r){
            if (r){
            	var url = "/rbac/resource/deleteResource?id="+row.resourceId;
            	 $.post(url,function(obj){
            		 if(obj.code==0){
            			 var menuId = $("#fm-tbresource-query input[name='menuId']").val();
            			 queryResources(menuId);
            			 //$('#dg-tbresource').datagrid('reload');
            		 }
            		 else{
            			 $.messager.show({
                             title: '提示信息',
                             msg: obj.msg
                         });
            		 }
                 },'json');
            }
        });
    }
}
</script>

</body>  
</html>