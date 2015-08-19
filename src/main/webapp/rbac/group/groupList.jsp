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
<input type="hidden" value="${pagesize}" id="pagesize">
<input type="hidden" value="${pname}" id="pname">
	 <table id="dg-tbusergroups" >
    </table>
    
    <div id="tb-tbusergroups" style="padding:5px;height:auto">
        <div style="margin-bottom:5px">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add_dg_tbusergroups()">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"  onclick="edit_dg_tbusergroups()">修改</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove_tbusergroups()">删除</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-tip" plain="true" onclick="add_dg_tbgaccess()">添加权限</a>
        </div>

    </div>
    
    
    <div id="dlg-tbusergroups"></div>
	<div id="dlg-tbgaccess"></div>  
	
<script>
$(function(){
	var dataUrl = "/rbac/group/getGroupList";
	var pagesize = $("#pagesize").val();
	var pname = $("#pname").val();
	$('#dg-tbusergroups').datagrid({
	    fitColumns:true,
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb-tbusergroups',
	    url:dataUrl ,
	    loadMsg:'数据加载中.....',
	    columns:[[
	        {field:'groupId',title:'用户ID',width:80},
	        {field:'name',title:'用户名',width:80}
	    ]],
        onDblClickRow:function(rowIndex, rowData){
    		opentabs("群组用户管理","/rbac/group/getUserList?groupId="+rowData.groupId);
        },
	    pagination:true //这里添加分页控件
	});
	
	var p = $('#dg-tbusergroups').datagrid('getPager');
	if (p){
		$(p).pagination({
	        pageSize: pagesize,//每页显示的记录条数，默认为10  
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '共 {total} 条记录',  
            onSelectPage:function(pageNumber, pageSize){
            	$.getJSON(dataUrl+"?"+pname+"="+pageNumber, function(json){
            		 $('#dg-tbusergroups').datagrid('loadData',json);   
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

function add_dg_tbusergroups(){
	$('#dlg-tbusergroups').dialog({  
	    title: '添加用户',  
	    width: 400,  
	    height: 200,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: '/rbac/group/addGroup',  
	    modal: true  
	});  
}


function edit_dg_tbusergroups(){
    var row = $('#dg-tbusergroups').datagrid('getSelected');
    $('#dlg-tbusergroups').dialog({  
	    title: '修改',
	    width: 400,
	    height: 200, 
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: "/rbac/group/modifyGroup?id="+row.groupId,
	    modal: true  
	});  
}

function remove_tbusergroups(){
    var row = $('#dg-tbusergroups').datagrid('getSelected');
    if (row){
        $.messager.confirm('操作','确定删除么?',function(r){
            if (r){
            	var url = "/rbac/group/deleteGroup?id="+row.groupId;
            	 $.post(url,function(obj){
            		 if(obj.code==0)
                        $('#dg-tbusergroups').datagrid('reload');
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

function add_dg_tbgaccess(){
    var row = $('#dg-tbusergroups').datagrid('getSelected');
    if(row){
    	var tagUrl = "/rbac/group/addGAccess?groupId="+row.groupId;
		$('#dlg-tbgaccess').dialog({  
		    title: '添加群组权限',  
		    width: 400,  
		    height: 400,  
		    closed: false,  
		    cache: false,  
		    resizable:true,
		    maximizable:true, 
		    minimizable:true,
		    href: tagUrl,  
		    modal: true  
		});  
    }
}
</script>

</body>  
</html>