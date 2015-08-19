package com.imt.rbac.controller.rbac.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.imt.rbac.controller.helper.Interceptor.NeedNotLogon;
import com.imt.rbac.model.UploadFileModel;

/**
 * 测试使用的action
 */

@Controller
public class UploadAction {
    @NeedNotLogon
    @RequestMapping(value="/test/upload/upload",method = RequestMethod.GET)   
    public String upload(Model model) {
        return "/test/upload/upload";
    }
    
    @RequestMapping(value="/test/upload/uploadOk",method = RequestMethod.GET)   
    public String uploadOk(Model model) {
        return "/test/upload/uploadOk";
    }
    
    @RequestMapping(value="/test/upload/upload",method = RequestMethod.POST)  
    @ResponseBody
    public  List<UploadFileModel> upload(HttpServletRequest request, HttpServletResponse response) {
		List<UploadFileModel> array = new ArrayList<>();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			//String name = request.getParameter("name");
			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();  
			for(Entry<String, MultipartFile> entry:fileMap.entrySet()){
				MultipartFile documentFile = entry.getValue();
				if (!documentFile.isEmpty()) {
/*					byte[] bytes = documentFile.getBytes();
					FileOutputStream fos = new FileOutputStream("d:/temp/"
							+ documentFile.getOriginalFilename());
					fos.write(bytes);*/
					System.out.println("name: " + documentFile.getOriginalFilename() + "    size: " + documentFile.getSize()); // 打印文件大小和文件名称
					UploadFileModel model = new UploadFileModel(documentFile.getOriginalFilename(),"http://ww.www.ww",documentFile.getSize());
					array.add(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
		
    }

}
