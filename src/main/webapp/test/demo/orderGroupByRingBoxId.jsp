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


	 <table id="dg-order" >
    </table>
    
    <div id="tb-order" style="padding:5px;height:auto">
        <div style="margin-bottom:5px">

        </div>
        
        <div>
         	<form  id="fm-order-query" method="post" novalidate>
         		渠道ID: <input class="easyui-validatebox" name="channelId"  style="width:80px">
				铃音盒ID: <input class="easyui-validatebox" name="ringBoxId"  style="width:80px">
				批次号: <input class="easyui-validatebox" name="batchNo"   style="width:80px">
				省份: 
				     <select class="easyui-combobox"   name="province"  id="province" panelHeight="auto" style="width:100px" >
	            		<option value="1" >云南</option>
					</select>
				
				查询日期：<input class="easyui-datebox" name="beginTime" />~
				<input class="easyui-datebox"  name="endTime"  />
	            <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="query_order()">查询</a>
              </form>
        </div>
    </div>
    
<script>
$(function(){
	var dataUrl = "/ringbox/report/getOrderGroupByRingBoxId";
	var pagesize = '${pagesize}';
	var pname = '${pname}';
	$('#dg-order').datagrid({
	    fitColumns:true,
	    rownumbers:true,
	    singleSelect:true,
	    toolbar:'#tb-order',
	    url:dataUrl ,
	    loadMsg:'数据加载中.....',
	    columns:[[
	  	        {field:'ringBoxId',title:'铃音盒ID',width:80},
	  	        {field:'name',title:'铃音盒名称',width:80,
	  	        	formatter: function(value,row,index){
		                if (row.ringBox){
		                    return row.ringBox.name;
		                } else {
		                    return value;
		                }
		             }
	  	        },
		        {field:'isOkDesc',title:'是否成功',width:80},
		        {field:'itemNum',title:'数量',width:80}
	    ]],
	    pagination:false //这里添加分页控件
	});
	
	var p = $('#dg-order').datagrid('getPager');
	if (p){
		$(p).pagination({
	        pageSize: pagesize,//每页显示的记录条数，默认为10  
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '共 {total} 条记录',  
            onSelectPage:function(pageNumber, pageSize){
            	$.getJSON(dataUrl+"?"+pname+"="+pageNumber, function(json){
            		 $('#dg-order').datagrid('loadData',json);   
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


function query_order(){
    var url = '/ringbox/report/getOrderGroupByRingBoxId';
    $('#fm-order-query').form('submit',{
        url: url,
        onSubmit: function(){
        	var isValid = $(this).form('validate');
        	if (isValid)
        		$('#dg-order').datagrid('loading');   
            return isValid;
        },
        success: function(result){
       		var obj = jQuery.parseJSON(result);
       		 $('#dg-order').datagrid('loadData',obj);   
     		 $('#dg-order').datagrid('loaded');   
        }
    });
}


</script>

</body>  
</html>