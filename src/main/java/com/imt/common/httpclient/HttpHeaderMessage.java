package com.imt.common.httpclient;

import java.util.ArrayList;
import java.util.List;

public class HttpHeaderMessage {
	private String headerUserAgent = "Mozilla/5.0 (iPad; CPU OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3";
	private String headerAccept = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
	private String headerContentType = "text/xml; charset=utf-8";
	private String headerAcceptLanguage = "zh-cn,zh,en-US,en;q=0.5";
	private String headerAcceptEncoding = "gzip, deflate";
	private List<String> headerUserAgents = null;

	public HttpHeaderMessage(){
		headerUserAgents = new ArrayList<String>();
		headerUserAgents.add(UserAgentEnum.Chrome.getValue());
		headerUserAgents.add(UserAgentEnum.IE360.getValue());
		headerUserAgents.add(UserAgentEnum.IE7.getValue());
		headerUserAgents.add(UserAgentEnum.IE8.getValue());
		headerUserAgents.add(UserAgentEnum.IE9.getValue());
	}
	

	public HttpHeaderMessage(String type){
		if(type.equals("ipad")){
			headerUserAgents = new ArrayList<String>();
			headerUserAgents.add(UserAgentEnum.IPAD.getValue());
		}
	}

	
	public List<String> getHeaderUserAgents() {
		return headerUserAgents;
	}
	public void setHeaderUserAgents(List<String> headerUserAgents) {
		this.headerUserAgents = headerUserAgents;
	}
	public String getHeaderUserAgent() {
		return headerUserAgent;
	}

	public String getRandomUserAgent() {
		java.util.Random r = new java.util.Random();
		int randomNum = r.nextInt(headerUserAgents.size());
		return headerUserAgents.get(randomNum);
	}

	public void setHeaderUserAgent(String headerUserAgent) {
		this.headerUserAgent = headerUserAgent;
	}
	public String getHeaderAccept() {
		return headerAccept;
	}
	public void setHeaderAccept(String headerAccept) {
		this.headerAccept = headerAccept;
	}
	public String getHeaderContentType() {
		return headerContentType;
	}
	public void setHeaderContentType(String headerContentType) {
		this.headerContentType = headerContentType;
	}
	public String getHeaderAcceptLanguage() {
		return headerAcceptLanguage;
	}
	public void setHeaderAcceptLanguage(String headerAcceptLanguage) {
		this.headerAcceptLanguage = headerAcceptLanguage;
	}
	public String getHeaderAcceptEncoding() {
		return headerAcceptEncoding;
	}
	public void setHeaderAcceptEncoding(String headerAcceptEncoding) {
		this.headerAcceptEncoding = headerAcceptEncoding;
	}
}
