<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" type="text/css" href="${base}/css/uploadify.css">
<jsp:include page="/inc.jsp"></jsp:include>
	<script type="text/javascript" src="${base}/js/jquery.uploadify.min.js"></script>
</head>

<body>  
	    <!-- 上传测试-->
    <div>
			 <tr>
                        <th>
                            <label for="Attachment_GUID">附件上传：</label>
                        </th>
                        <td>                        
	                        <form>    
	                            <div>
	                                <input class="easyui-validatebox" type="hidden" id="Attachment_GUID" name="Attachment_GUID" />
	                                <input id="file_upload" name="file_upload"  type="file" multiple="multiple">
	                                <a href="javascript:void(0)" class="easyui-linkbutton" id="btnUpload" data-options="plain:true,iconCls:'icon-save'"
	                                    onclick="javascript: $('#file_upload').uploadify('upload', '*')">上传</a>
	                                <a href="javascript:void(0)" class="easyui-linkbutton" id="btnCancelUpload" data-options="plain:true,iconCls:'icon-cancel'"
	                                    onclick="javascript: $('#file_upload').uploadify('cancel', '*')">取消</a>
	
	                                <div id="fileQueue" class="fileQueue"></div>
	                                <div id="div_files"></div>
	                                <br />
	                            </div>
	                            </form>
                        </td>
                    </tr>
    </div>
    <div class="clear"></div>

    <!-- 添加层结束 -->
    
<script>
$(function () {
    //添加界面的附件管理
    $('#file_upload').uploadify({
         'swf': '/images/uploadify.swf',  //FLash文件路径
        'buttonText': '浏  览',                                 //按钮文本
        'uploader': '/test/upload/upload',                       //处理文件上传Action
        'queueID': 'fileQueue',                        //队列的ID
        'queueSizeLimit': 10,                          //队列最多可上传文件数量，默认为999
        'auto': false,                                 //选择文件后是否自动上传，默认为true
        'multi': true,                                 //是否为多选，默认为true
        'removeCompleted': true,                       //是否完成后移除序列，默认为true
        'fileSizeLimit': '10MB',                       //单个文件大小，0为无限制，可接受KB,MB,GB等单位的字符串值
        'fileTypeDesc': 'Image Files',                 //文件描述
        'fileTypeExts': '*.gif; *.jpg; *.png; *.bmp;*.tif;*.doc;*.xls;*.zip',  //上传的文件后缀过滤器
        'onQueueComplete': function (queueData) {                 //所有队列完成后事件
            //ShowUpFiles($("#Attachment_GUID").val(), "div_files");  //完成后更新已上传的文件列表

            $.messager.alert("提示", "上传完毕！");                                     //提示完成           
        },

        'onUploadStart' : function(file) {//当文件即将开始上传时立即触发
            $("#file_upload").uploadify("settings", 'formData', { 'name': '其他参数测试' }); //动态传参数
        },
        'onUploadError': function (event, queueId, fileObj, errorObj) {//文件上传出错时触发，参数由服务端程序返回。
            //alert(errorObj.type + "：" + errorObj.info);
        },
       'onUploadSuccess' : function(file,data,response) {//上传完成时触发（每个文件触发一次） 
    	   console.log( 'id: ' + file.id 
    	        	　　+ ' - 索引: ' + file.index 
    	        	　　+ ' - 文件名: ' + file.name 
    	        	　　+ ' - 文件大小: ' + file.size 
    	        	　　+ ' - 类型: ' + file.type 
    	        	　　+ ' - 创建日期: ' + file.creationdate 
    	        	　　+ ' - 修改日期: ' + file.modificationdate 
    	        	　　+ ' - 文件状态: ' + file.filestatus 
    	        	　　+ ' - 服务器端消息: ' + data 
    	        	　　+ ' - 是否上传成功: ' + response);
        	}

		});
    });

</script>
    
<style type="text/css">
@import url(/css/sys.css);
</style> 


</body>  
</html>