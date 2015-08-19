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

	 <table id="dg-tbgroupanduser" >
    </table>
    
    <div id="tb-tbgroupanduser" style="padding:5px;height:auto">
        <div style="margin-bottom:5px">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add_dg_tbgroupanduser()">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove_tbgroupanduser()">删除</a>
        </div>
        <div>
            Date From: <input class="easyui-datebox" style="width:80px">
            To: <input class="easyui-datebox" style="width:80px">
            Language: 
            <select class="easyui-combobox" panelHeight="auto" style="width:100px">
                <option value="java">Java</option>
                <option value="c">C</option>
                <option value="basic">Basic</option>
                <option value="perl">Perl</option>
                <option value="python">Python</option>
            </select>
            <a href="#" class="easyui-linkbutton" iconCls="icon-search">Search</a>
        </div>
    </div>
    
    
    <div id="dlg-tbgroupanduser"></div>  
    
<script>
$(function(){
	var dataUrl = "/rbac/group/getUserList?groupId=${groupId}";
	var pagesize = $("#pagesize").val();
	var pname = $("#pname").val();
	$('#dg-tbgroupanduser').datagrid({
	    fitColumns:true,
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb-tbgroupanduser',
	    url:dataUrl ,
	    loadMsg:'数据加载中.....',
	    columns:[[
	        {field:'group',title:'群组名',width:80,
	        	formatter:function(value,val,rec){
	        		return value.name;
	        	}
	        },
	        {field:'user',title:'用户名',width:80,
	        	formatter:function(value,val,rec){
	        		return value.name;
	        	}
	        }
	    ]],
	    pagination:true //这里添加分页控件
	});
	
	var p = $('#dg-tbgroupanduser').datagrid('getPager');
	if (p){
		$(p).pagination({
	        pageSize: pagesize,//每页显示的记录条数，默认为10  
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '共 {total} 条记录',  
            onSelectPage:function(pageNumber, pageSize){
            	$.getJSON(dataUrl+"?"+pname+"="+pageNumber + "&groupId=${groupId}", function(json){
            		 $('#dg-tbgroupanduser').datagrid('loadData',json);   
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

function add_dg_tbgroupanduser(){
	$('#dlg-tbgroupanduser').dialog({  
	    title: '添加用户',  
	    width: 400,  
	    height: 200,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: '/rbac/group/addUser?groupId=${groupId}',
	    modal: true  
	});  
}

function remove_tbgroupanduser(){
    var row = $('#dg-tbgroupanduser').datagrid('getSelected');
    if (row){
        $.messager.confirm('操作','确定删除么?',function(r){
            if (r){
            	var url = "/rbac/group/deleteUser?id="+row.id;
            	 $.post(url,function(obj){
            		 if(obj.code==0)
                        $('#dg-tbgroupanduser').datagrid('reload');
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