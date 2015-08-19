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
	 <table id="dg-tbuser" >
    </table>
    
    <div  id="tb-tbuser" style="padding:5px;height:auto">
        <div style="margin-bottom:5px">
<c:if test="${sys:permission('/rbac/user/addUser')}">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add_dg_tbuser()">添加</a>
</c:if>
<c:if test="${sys:permission('/rbac/user/modifyUser')}">
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit_dg_tbuser()">修改</a>
</c:if>
<c:if test="${sys:permission('/rbac/user/deleteUser')}">
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove_tbuser()">删除</a>
</c:if>
<c:if test="${sys:permission('/rbac/user/addUAccess')}">
            <a href="#" class="easyui-linkbutton" iconCls="icon-tip" plain="true" onclick="add_dg_tbuaccess()">添加权限</a>
</c:if>
        </div>
        <div>
         	<form  id="fm-tbuser-query" method="post" novalidate>
         	         	<input type="hidden"  name="${pname}"  value="" />
            用户名: <input class="easyui-validatebox" name="name"  style="width:80px" required="true">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query_tbuser()">查询</a>
              </form>
        </div>
    </div>
     
    
    <div id="dlg-tbuser"></div>  
	<div id="dlg-tbuaccess"></div>  
    
<script>
$(function(){
	var dataUrl = "/rbac/user/getUserList";
	var pagesize = $("#pagesize").val();
	var pname = $("#pname").val();
	$('#dg-tbuser').datagrid({
	    fitColumns:true,
	    rownumbers:true,
	    singleSelect:false,
	    toolbar:'#tb-tbuser',
	    url:dataUrl ,
	    loadMsg:'数据加载中.....',
	    frozenColumns: [[
	                     { field: 'ck', checkbox: true }
	     ]],
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
            	$("#fm-tbuser-query :hidden[name='"+pname+"']").val(pageNumber);
                $('#fm-tbuser-query').form('submit',{
                    url: dataUrl,
                    onSubmit: function(){
                        return $(this).form('validate');
                    },
                    success: function(result){
                   		var obj = jQuery.parseJSON(result);
                   		 $('#dg-tbuser').datagrid('loadData',obj);   
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
	    href: '/rbac/user/addUser',  
	    modal: true  
	});  
}

function add_dg_tbuaccess(){
    var row = $('#dg-tbuser').datagrid('getSelected');
    if(row){
    	var tagUrl = "/rbac/user/addUAccess?userId="+row.userId;
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
    var url = '/rbac/user/getUserList';
    $.post(url,$('#fm-tbuser-query').serialize(),function(data) {
    	var isValid = $('#fm-tbuser-query').form('validate');
    	if (isValid)
    		$('#dg-tbuser').datagrid('loading');   
    	if(!isValid) return;
   		//var obj = jQuery.parseJSON(data);
  		 $('#dg-tbuser').datagrid('loadData',data);   
		 $('#dg-tbuser').datagrid('loaded');   
    });

}


function edit_dg_tbuser(){
    var row = $('#dg-tbuser').datagrid('getSelected');
	$('#dlg-tbuser').dialog({  
	    title: '修改',  
	    width: 400,  
	    height: 200,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: "/rbac/user/modifyUser?id="+row.userId,  
	    modal: true  
	});  
}

function remove_tbuser(){
    var rows = $('#dg-tbuser').datagrid('getSelections');
    if (rows){
    	var ids = "";
    	for(var i=0;i<rows.length;i++){
    			ids = ids + rows[i].userId + ",";
    	}
        $.messager.confirm('操作','确定删除么?',function(r){
            if (r){
            	var url = "/rbac/user/deleteUser?ids="+ids;
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
    else{
    	$.messager.alert("提示", "请至少选择一行数据！");
    }
}
</script>

</body>  
</html>