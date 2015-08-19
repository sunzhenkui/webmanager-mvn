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


	 <table id="dg-sysdictionary" >
    </table>
    
    <div id="tb-sysdictionary" style="padding:5px;height:auto">
        <div style="margin-bottom:5px">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add_dg_sysdictionary()">添加</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove_sysdictionary()">删除</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="time" plain="true" onclick="flush_sysdictionary()">刷新字典</a>
        </div>
        <div>
         	<form  id="fm-sysdictionary-query" method="post" novalidate>
         	<input type="hidden"  name="${pname}"  value="" />
            类型: <input class="easyui-validatebox" name="type"  style="width:80px">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query_sysdictionary()">查询</a>
              </form>
        </div>
    </div>
   <div id="dlg-sysdictionary"></div>
   <div id="dlg-sysdictionarydata"></div>    
<script>
$(function(){
	var dataUrl = "/rbac/dictionary/getDictionaryList";
	var pagesize = '${pagesize}';
	var pname = '${pname}';
	$('#dg-sysdictionary').datagrid({
	    fitColumns:true,
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb-sysdictionary',
	    url:dataUrl ,
	    loadMsg:'数据加载中.....',
	    columns:[[
	        {field:'dicName',title:'键名',width:80},
	        {field:'dicValue',title:'键值',width:80}
	    ]],
		onDblClickCell: function(index,field,value){
			getDetail(index,field,value);
		},
	    pagination:true //这里添加分页控件
	});
	
	var p = $('#dg-sysdictionary').datagrid('getPager');
	if (p){
		$(p).pagination({
	        pageSize: pagesize,//每页显示的记录条数，默认为10  
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '共 {total} 条记录',  
            onSelectPage:function(pageNumber, pageSize){
            	$("#fm-sysdictionary-query :hidden[name='"+pname+"']").val(pageNumber);
                $('#fm-sysdictionary-query').form('submit',{
                    url: dataUrl,
                    onSubmit: function(){
                        return $(this).form('validate');
                    },
                    success: function(result){
                   		var obj = jQuery.parseJSON(result);
                   		 $('#dg-sysdictionary').datagrid('loadData',obj);   
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

function add_dg_sysdictionary(){
	$('#dlg-sysdictionary').dialog({  
	    title: '添加',  
	    width: 400,  
	    height: 200,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: '/rbac/dictionary/addDictionary',  
	    modal: true  
	});  
}

function remove_sysdictionary(){
    var row = $('#dg-sysdictionary').datagrid('getSelected');
    if (row){
        $.messager.confirm('操作','确定删除么?',function(r){
            if (r){
            	var url = "/rbac/dictionary/deleteDictionary?dicValue="+row.dicValue;
            	 $.post(url,function(obj){
            		 if(obj.code==0)
                        $('#dg-sysdictionary').datagrid('reload');
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

function getDetail(index,field,value){
	var row = $('#dg-sysdictionary').datagrid('getSelected');
	var url = '/rbac/dictionary/dictionaryDataList?dicValue='+row.dicValue;
	alert(url);
	$('#dlg-sysdictionarydata').dialog({  
	    title: '添加',  
	    width: 500,  
	    height: 400,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: url,  
	    modal: true  
	});  
}

function query_sysdictionary(){
    var url = '/rbac/dictionary/getDictionaryList';
    $('#fm-sysdictionary-query').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
       		var obj = jQuery.parseJSON(result);
       		 $('#dg-sysdictionary').datagrid('loadData',obj);   
        }
    });
}

function flush_sysdictionary(){
	var url = '/rbac/dictionary/flushDictionary';
	$.post(url,  function(data){
		 $.messager.show({
             title: '提示信息',
             msg: data.msg
         });
	});
}
</script>

</body>  
</html>