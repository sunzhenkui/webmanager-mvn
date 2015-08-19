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


	 <table id="dg-tbrole" >
    </table>
    
    <div id="tb-tbrole" style="padding:5px;height:auto">
        <div style="margin-bottom:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add_dg_tbrole()">添加</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit_dg_tbrole()">修改</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove_tbrole()">删除</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tip" plain="true" onclick="add_tbpermission()">添加资源</a>
        </div>
        <div>
         	<form  id="fm-tbrole-query" method="post" novalidate>
         	         	<input type="hidden"  name="${pname}"  value="" />
            角色名: <input class="easyui-validatebox" name="name"  style="width:80px">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query_tbrole()">查询</a>
              </form>
        </div>
    </div>
   <div id="dlg-tbrole"></div>  
   <div id="dlg-tbpermission"></div>  
    
<script>
$(function(){
	var dataUrl = "/rbac/role/getRoleList";
	var pagesize = '${pagesize}';
	var pname = '${pname}';
	$('#dg-tbrole').datagrid({
	    fitColumns:true,
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb-tbrole',
	    url:dataUrl ,
	    loadMsg:'数据加载中.....',
	    columns:[[
	        {field:'roleId',title:'ID',width:80},
	        {field:'name',title:'角色名称',width:80},
	        {field:'roleLevel',title:'角色级别',width:80}
	    ]],
	    pagination:true //这里添加分页控件
	});
	
	var p = $('#dg-tbrole').datagrid('getPager');
	if (p){
		$(p).pagination({
	        pageSize: pagesize,//每页显示的记录条数，默认为10  
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '共 {total} 条记录',  
            onSelectPage:function(pageNumber, pageSize){
            	$("#fm-tbrole-query :hidden[name='"+pname+"']").val(pageNumber);
                $('#fm-tbrole-query').form('submit',{
                    url: dataUrl,
                    onSubmit: function(){
                        return $(this).form('validate');
                    },
                    success: function(result){
                   		var obj = jQuery.parseJSON(result);
                   		 $('#dg-tbrole').datagrid('loadData',obj);   
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

function add_dg_tbrole(){
	$('#dlg-tbrole').dialog({  
	    title: '添加',  
	    width: 400,  
	    height: 200,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: '/rbac/role/addRole',  
	    modal: true  
	});  
}

function edit_dg_tbrole(){
    var row = $('#dg-tbrole').datagrid('getSelected');
	$('#dlg-tbrole').dialog({  
	    title: '修改1',  
	    width: 400,  
	    height: 200,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: "/rbac/role/modifyRole?id="+row.roleId,  
	    modal: true  
	});  
}

function query_tbrole(){
    var url = '/rbac/role/getRoleList';
    $('#fm-tbrole-query').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
       		var obj = jQuery.parseJSON(result);
       		 $('#dg-tbrole').datagrid('loadData',obj);   
        }
    });
}

function remove_tbrole(){
    var row = $('#dg-tbrole').datagrid('getSelected');
    if (row){
        $.messager.confirm('操作','确定删除么?',function(r){
            if (r){
            	var url = "/rbac/role/deleteRole/"+row.roleId;
            	 $.post(url,function(obj){
            		 if(obj.code==0)
                        $('#dg-tbrole').datagrid('reload');
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

function add_tbpermission(){
    var row = $('#dg-tbrole').datagrid('getSelected');
	$('#dlg-tbpermission').dialog({  
	    title: '添加资源',  
	    width: 700,  
	    height:400,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: "/rbac/role/addPermissions?roleId="+row.roleId,  
	    modal: true  
	});  
}
</script>

</body>  
</html>