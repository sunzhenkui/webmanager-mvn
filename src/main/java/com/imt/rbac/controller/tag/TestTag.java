package com.imt.rbac.controller.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class TestTag  extends TagSupport{
    private String msg;  
    
    public void setMsg(String msg){  
        this.msg = msg;  
    }  
      
      
    public TestTag(){  
        System.out.println("MyTag构造方法：一个myTag类的对象被构建了....");  
    }  
      
    public int doStartTag() {  
        JspWriter out = this.pageContext.getOut();  
          
        try {  
            out.print(msg);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
        System.out.println("对象正在处理开始标记.....");  
        return EVAL_BODY_INCLUDE;  
    }  
    public int doAfterBody() throws JspException{  
        System.out.println("处理标签体（after body）....");  
        return SKIP_BODY;  
    }  
    public int doEndTag() throws JspException{  
        System.out.println("对象正在处理结束标记.....");  
        return EVAL_PAGE;  
    }  
}
