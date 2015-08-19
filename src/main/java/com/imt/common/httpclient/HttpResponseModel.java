package com.imt.common.httpclient;

import java.io.InputStream;
import java.util.HashMap;

public class HttpResponseModel {
	private String url;
	private InputStream stream;
	private byte[] b;
	private int status;
//	private List<Cookie> cookies;
	private HashMap<String,String> cookies;

	public HashMap<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(HashMap<String, String> cookies) {
		this.cookies = cookies;
	}

	public HttpResponseModel(String url,int status,byte[] b){
		this.url = url;
		this.status = status;
		this.b = b;
	}
	
	public HttpResponseModel(String url,int status,InputStream stream){
		this.url = url;
		this.status = status;
		this.stream = stream;
	}

	public HttpResponseModel(byte[] b,int status){
		this.b = b;
		this.status = status;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	 
//	public List<Cookie> getCookies() {
//		return cookies;
//	}
//
//	public void setCookies(List<Cookie> cookies) {
//		this.cookies = cookies;
//	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public byte[] getB() {
		return b;
	}
	public void setB(byte[] b) {
		this.b = b;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getMessage(String charset){
		String msg = "";
		try {
			msg = new  String(this.getB(),charset);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return msg;
	}
}
