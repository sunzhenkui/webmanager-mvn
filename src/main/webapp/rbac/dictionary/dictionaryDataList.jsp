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


	 <table id="dg-dicdata" >
    </table>
    
    <div id="tb-dicdata" style="padding:5px;height:auto">
        <div style="margin-bottom:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add_dicdata()">添加</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove_dicdata()">删除</a>
        </div>
						<form  id="fm" method="post" action="">
						<input type="hidden"  name="${pname}"  value="" />
						<input type="hidden"  name="dicValue"  value="${dicValue}" />
							<input name="parentId"  id="parentId" type="hidden" value="">
						</form>
    </div>
    <div id="dlg-dicdata"></div>  
<script>
$(function(){
	var dataUrl = "/rbac/dictionary/getDictionaryDataList";
	var pagesize = '${pagesize}';
	var pname = '${pname}';
	$('#dg-dicdata').datagrid({
	    fitColumns:true,
	    rownumbers:true,
	    singleSelect:false,
	    toolbar:'#tb-dicdata',
	    //url:dataUrl ,
	    loadMsg:'数据加载中.....',
	    frozenColumns: [[
	                     { field: 'ck', checkbox: true }
	     ]],
	    columns:[[
	        {field:'dicDataName',title:'键名',width:80,editor:'text'},
	        {field:'dicDataValue',title:'键值',width:80,editor:'text'}
	    ]],
	    loader:function(){
            $('#fm').form('submit',{
                url: dataUrl,
                success: function(result){
               		var obj = jQuery.parseJSON(result);
               		$('#dg-dicdata').datagrid('loadData',obj);
               		$('#dg-dicdata').datagrid('loaded');
                }
            });
	    },
	    pagination:false //这里添加分页控件
	});

});

function add_dicdata(){
	var url = '/rbac/dictionary/addDictionaryData?dicValue=${dicValue}';
	$('#dlg-dicdata').dialog({  
	    title: '添加',  
	    width: 400,  
	    height: 200,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: url,  
	    modal: true  
	});  
}

function remove_dicdata(){
    var rows = $('#dg-dicdata').datagrid('getSelections');
    if (rows){
    	var ids = "";
    	for(var i=0;i<rows.length;i++){
    			ids = ids + rows[i].id + ",";
    	}
        $.messager.confirm('操作','确定删除么?',function(r){
            if (r){
            	$('#dg-dicdata').datagrid('loading');   
            	var url = "/rbac/dictionary/deleteDictionaryData?ids="+ids;
            	 $.post(url,function(obj){
            		 if(obj.code==0){
                        $('#dg-dicdata').datagrid('reload');
            		 }
            		 else{
            			 $.messager.show({
                             title: '提示信息',
                             msg: obj.msg
                         });
            		 }
            		 $('#dg-tbuser').datagrid('loaded');   
                 },'json');
            }
        });
    }
}



</script>

</body>  
</html>