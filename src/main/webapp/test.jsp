<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="${base}/ui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/ui/themes/icon.css">
	<script type="text/javascript" src="${base}/ui/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="${base}/ui/jquery.easyui.min.js"></script>
	

</head>

<body >  
<input type="hidden" value="${pagesize}" id="pagesize">
<input type="hidden" value="${pname}" id="pname">
	 <table id="dg-tbuser" >
    </table>
    
    <div id="tb-tbuser" style="padding:5px;height:auto">
        <div style="margin-bottom:5px">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add_dg_tbuser()">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove_tbuser()">删除</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-tip" plain="true" onclick="add_dg_tbuaccess()">添加权限</a>
        </div>
        <div>
         	<form  id="fm-tbuser-query" method="post" novalidate>
            用户名: <input class="easyui-validatebox" name="name" required="true" style="width:80px">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query_tbuser()">查询</a>
              </form>
        </div>
    </div>
     
    
    <div id="dlg-tbuser"></div>  
	<div id="dlg-tbuaccess"></div>  
    
<script>
$(function(){
	var dataUrl = "/mapper.rbac/user/userList";
	var pagesize = $("#pagesize").val();
	var pname = $("#pname").val();
	$('#dg-tbuser').datagrid({
	    fitColumns:true,
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb-tbuser',
	    url:dataUrl ,
	    columns:[[
	        {field:'userId',title:'用户ID',width:80},
	        {field:'name',title:'用户名',width:80}
	    ]],
	    pagination:true //这里添加分页控件
	});
	
	var p = $('#dg-tbuser').datagrid('getPager');
	if (p){
		$(p).pagination({
	        pageSize: pagesize,//每页显示的记录条数，默认为10  
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '共 {total} 条记录',  
            onSelectPage:function(pageNumber, pageSize){
            	$.getJSON(dataUrl+"?"+pname+"="+pageNumber, function(json){
            		 $('#dg-tbuser').datagrid('loadData',json);   
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

function add_dg_tbuser(){
	$('#dlg-tbuser').dialog({  
	    title: '添加用户',  
	    width: 400,  
	    height: 200,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: '/mapper.rbac/user/addUser',
	    modal: true  
	});  
}

function add_dg_tbuaccess(){
    var row = $('#dg-tbuser').datagrid('getSelected');
    if(row){
    	var tagUrl = "/mapper.rbac/user/addUAccess?userId="+row.userId;
		$('#dlg-tbuaccess').dialog({  
		    title: '添加用户权限',  
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

function query_tbuser(){
    var url = '/mapper.rbac/user/userList';
    $('#fm-tbuser-query').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
       		var obj = jQuery.parseJSON(result);
       		 $('#dg-tbuser').datagrid('loadData',obj);   
        }
    });
}

function remove_tbuser(){
    var row = $('#dg-tbuser').datagrid('getSelected');
    if (row){
        $.messager.confirm('操作','确定删除么?',function(r){
            if (r){
            	var url = "/mapper.rbac/user/deleteUser/"+row.userId;
            	 $.post(url,function(obj){
            		 if(obj.code==0)
                        $('#dg-tbuser').datagrid('reload');
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