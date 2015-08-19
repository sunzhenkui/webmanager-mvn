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
        <div class="easyui-panel" title="" style="width:max;height:800px;padding:10px;">
        <div class="easyui-layout" data-options="fit:true">
            <div data-options="region:'west',split:true" style="width:250px;padding:10px">
                    <ul class="easyui-tree" id="tree-tbmenu" data-options="url:'${base}/rbac/menu/getUserMenus',animate:true" style="float:left;margin: 10px 10px"></ul>
            </div>
            <div data-options="region:'center'" style="padding:10px">
					 <table id="dg-tbmenu"  style="float:left;margin: 10px 10px"></table>
    
				    <div id="tb-tbmenu" style="padding:5px;height:auto;">
				        <div style="margin-bottom:5px">
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="get_root_dg_tbmenu()">根节点</a>
				            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add_dg_tbmenu()">添加</a>
				            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="edit_dg_tbmenu()">修改</a>
				            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="remove_dg_tbmenu()">删除</a>
				        </div>
						<form  id="fm-tbmenu-find" method="post" action="">
							<input type="hidden"  name="${pname}"  value="" />
							<input name="parentId"  id="parentId" type="hidden" value="">
						</form>
				    </div>
				    <div id="dlg-tbmenu"></div>  
            </div>
        </div>
    </div>
    

	
    
<script>
$('#tree-tbmenu').tree({
	onClick: function(node){
		var url = '${base}/rbac/menu/getMenuList';
		$("#fm-tbmenu-find input[name='parentId']").val(node.id);
	    $('#fm-tbmenu-find').form('submit',{
	        url: url,
	        onSubmit: function(){
	            return $(this).form('validate');
	        },
	        success: function(result){
		        var obj = jQuery.parseJSON(result);
		        $('#dg-tbmenu').datagrid('loadData',obj);  
	        }
	    });
	}
});

$(function(){
	var dataUrl = "/rbac/menu/getMenuList";
	var pagesize = '${pagesize}';
	var pname = '${pname}';
	$('#dg-tbmenu').datagrid({
	    fitColumns:true,
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb-tbmenu',
	   // url:dataUrl ,
	    loadMsg:'数据加载中.....',
	    columns:[[
	        {field:'menuId',title:'ID',width:80},
	        {field:'name',title:'菜单名称',width:80},
	        {field:'nodeType',title:'节点类型',width:80},
	        {field:'url',title:'链接地址',width:80}
	    ]],
	    loader:function(){
            $('#fm-tbmenu-find').form('submit',{
                url: dataUrl,
                success: function(result){
               		var obj = jQuery.parseJSON(result);
               		$('#dg-tbmenu').datagrid('loadData',obj);
               		$('#dg-tbmenu').datagrid('loaded');
                }
            });
	    },
	    pagination:true //这里添加分页控件
	});
	
	var p = $('#dg-tbmenu').datagrid('getPager');
	if (p){
		$(p).pagination({
	        pageSize: pagesize,//每页显示的记录条数，默认为10  
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '共 {total} 条记录',  
            onSelectPage:function(pageNumber, pageSize){
            	$("#fm-tbmenu-query :hidden[name='"+pname+"']").val(pageNumber);
                $('#fm-tbmenu-query').form('submit',{
                    url: dataUrl,
                    onSubmit: function(){
                        return $(this).form('validate');
                    },
                    success: function(result){
                   		var obj = jQuery.parseJSON(result);
                   		 $('#dg-tbmenu').datagrid('loadData',obj);   
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

function add_dg_tbmenu(){
	var url = '/rbac/menu/addMenu';
	$('#dlg-tbmenu').dialog({  
	    title: '添加',  
	    width: 400,  
	    height: 300,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href:url , 
	    modal: true  
	});  
}

function edit_dg_tbmenu(){
    var row = $('#dg-tbmenu').datagrid('getSelected');
	$('#dlg-tbmenu').dialog({  
	    title: '修改1',  
	    width: 400,  
	    height: 300,  
	    closed: false,  
	    cache: false,  
	    resizable:true,
	    maximizable:true, 
	    minimizable:true,
	    href: "/rbac/menu/modifyMenu?id="+row.menuId,  
	    modal: true  
	});  
}

function remove_dg_tbmenu(){	
    var row = $('#dg-tbmenu').datagrid('getSelected');
    if (row){
        $.messager.confirm('操作','确定删除么?',function(r){
            if (r){
            	var url = "/rbac/menu/deleteMenu?id="+row.menuId;
            	 $.post(url,function(obj){
            		 if(obj.code==0)
                        $('#dg-tbmenu').datagrid('reload');
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

function get_root_dg_tbmenu(){
	var url = '${base}/rbac/menu/getMenuList';
	$("#fm-tbmenu-find input[name='parentId']").val("0");
    $('#fm-tbmenu-find').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
	        var obj = jQuery.parseJSON(result);
	        $('#dg-tbmenu').datagrid('loadData',obj);  
        }
    });
}
</script>

</body>  
</html>