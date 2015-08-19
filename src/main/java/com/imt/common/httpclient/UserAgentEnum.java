package com.imt.common.httpclient;

public enum UserAgentEnum {
	   IPAD("Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A403 Safari/8536.25"),
	   IE7("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)"),
	   IE8("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)"),
	   IE9("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)"),
	   Chrome("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11"),
	   Sougou("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0"),
	   IE360("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)"),
	   ;
	   
	    private String value;
		 
	    private UserAgentEnum(String value) {
	        this.value = value;
	    }
	 
	    public String getValue() {
	        return value;
	    }
}
