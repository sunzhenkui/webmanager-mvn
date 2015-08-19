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
	 <table id="dg-tbdep" >
    </table>
    
    <div  id="tb-tbdep" style="padding:5px;height:auto">
        <div style="margin-bottom:5px">
<c:if test="${sys:permission('/rbac/department/addDep')}">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add()">添加</a>
</c:if>
<c:if test="${sys:permission('/rbac/department/modifyDep')}">
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit()">修改</a>
</c:if>
<c:if test="${sys:permission('/rbac/department/deleteDep')}">
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove()">删除</a>
</c:if>
        </div>
        <div>
         	<form  id="fm-tbdep-query" method="post" novalidate>
         	         	<input type="hidden"  name="${pname}"  value="" />
            用户名: <input class="easyui-validatebox" name="name"  style="width:80px" required="true">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query_tbuser()">查询</a>
              </form>
        </div>
    </div>
     
    
    <div id="dlg-tbdep"></div>  
    
<script>
$(function(){
	var dataUrl = "/rbac/department/getDepList";
	var pagesize = $("#pagesize").val();
	var pname = $("#pname").val();
	$('#dg-tbdep').datagrid({
	    fitColumns:true,
	    rownumbers:true,
	    singleSelect:false,
	    toolbar:'#tb-tbdep',
	    url:dataUrl ,
	    loadMsg:'数据加载中.....',
	    frozenColumns: [[
	                     { field: 'ck', checkbox: true }
	     ]],
	    columns:[[
	        {field:'depId',title:'部门ID',width:80},
	        {field:'name',title:'用户名',width:80}
	    ]],
	    pagination:true //这里添加分页控件
	});
	
	var p = $('#dg-tbdep').datagrid('getPager');
	if (p){
		$(p).pagination({
	        pageSize: pagesize,//每页显示的记录条数，默认为10  
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '共 {total} 条记录',  
            onSelectPage:function(pageNumber, pageSize){
            	$("#fm-tbdep-query :hidden[name='"+pname+"']").val(pageNumber);
                $('#fm-tbdep-query').form('submit',{
                    url: dataUrl,
                    onSubmit: function(){
                        return $(this).form('validate');
                    },
                    success: function(result){
                   		var obj = jQuery.parseJSON(result);
                   		 $('#dg-tbdep').datagrid('loadData',obj);   
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

function add(){
	$('#dlg-tbdep').dialog({  
	    title: '添加用户',  
	    width: 400,  
	    height: 200,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: '/rbac/department/addDep',  
	    modal: true  
	});  
}


function query(){
    var url = '/rbac/department/getDepList';
    $.post(url,$('#fm-tbdep-query').serialize(),function(data) {
    	var isValid = $('#fm-tbdep-query').form('validate');
    	if (isValid)
    		$('#dg-tbdep').datagrid('loading');   
    	if(!isValid) return;
   		//var obj = jQuery.parseJSON(data);
  		 $('#dg-tbdep').datagrid('loadData',data);   
		 $('#dg-tbdep').datagrid('loaded');   
    });

}


function edit(){
    var row = $('#dg-tbdep').datagrid('getSelected');
	$('#dlg-tbdep').dialog({  
	    title: '修改',  
	    width: 400,  
	    height: 200,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: "/rbac/department/modifyDep?id="+row.depId,  
	    modal: true  
	});  
}

function remove(){
    var rows = $('#dg-tbdep').datagrid('getSelections');
    if (rows){
    	var ids = "";
    	for(var i=0;i<rows.length;i++){
    			ids = ids + rows[i].depId + ",";
    	}
        $.messager.confirm('操作','确定删除么?',function(r){
            if (r){
            	var url = "/rbac/department/deleteDep?ids="+ids;
            	 $.post(url,function(obj){
            		 if(obj.code==0)
                        $('#dg-tbdep').datagrid('reload');
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