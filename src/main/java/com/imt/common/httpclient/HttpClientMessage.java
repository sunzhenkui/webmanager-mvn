package com.imt.common.httpclient;

import org.apache.http.HttpHost;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class HttpClientMessage {
	private boolean isPost = false;
	private  int reqTimeout = 30000;// 连接超时时间（请求超时）
	private int soTimeout = 30000;// 数据传输超时（读取超时）
	private int connTimeout = 60000;// 连接超时
//	private HttpHeaderMessage httpHeaderMessag;
	private int poolMaxNum = 500;  //连接池里的最大连接数
	private int poolMaxPerRouteNum = 100;  //每个路由的默认最大连接数,
	private int poolMaxTargetConnNum = 100; // 设置对目标主机的最大连接数
	private int maxRetryConnNum = 2; //最大重练次数

	private HttpHost proxy;//代理
	private HashMap<String,String> headers;
	HttpRequestRetryHandler httpRequestRetryHandler = null;

	public HashMap<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(HashMap<String, String> headers) {
		this.headers = headers;
	}

	

	
	public HttpClientMessage(int maxRetryConnNum){
		this.maxRetryConnNum = maxRetryConnNum;
		init();
	}
	
	public HttpClientMessage(String proxyIp,int proxyPort){
		this.proxy = new HttpHost(proxyIp,proxyPort);
		init();
	}
	
	public HttpClientMessage(){
		init();
	}
	
	private void init(){
//		httpHeaderMessag  = new HttpHeaderMessage();
		headers = new HashMap<>();
		//重试类
		httpRequestRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception,
					int executionCount, HttpContext context) {
				if (executionCount >= maxRetryConnNum) {
					// 如果超过最大重试次数，那么就不要继续了
					return false;
				}
				if (exception instanceof NoHttpResponseException) {
					// 如果服务器丢掉了连接，那么就重试
					return true;
				}
				if (exception instanceof SSLHandshakeException) {
					// 不要重试SSL握手异常
					return false;
				}
/*				HttpRequestBase request = (HttpRequestBase) context.getAttribute(ExecutionContext.HTTP_REQUEST);
				boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
				if (idempotent) {
					// 如果请求被认为是幂等的，那么就重试
					return true;
				}*/
				return true;
			}
		};
	}
	
	public boolean isPost() {
		return isPost;
	}

	public void setPost(boolean isPost) {
		this.isPost = isPost;
	}
	
	public HttpHost getHost(String httpUrl){
		HttpHost host = null;
		try {
			URL url  = new URL(httpUrl);
			host = new HttpHost(url.getHost(),url.getPort());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return host;
	}
	
/*
	
	public HttpHeaderMessage getHttpHeaderMessag() {
		return httpHeaderMessag;
	}

	public void setHttpHeaderMessag(HttpHeaderMessage httpHeaderMessag) {
		this.httpHeaderMessag = httpHeaderMessag;
	}*/

	public int getReqTimeout() {
		return reqTimeout;
	}

	public void setReqTimeout(int reqTimeout) {
		this.reqTimeout = reqTimeout;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	public int getConnTimeout() {
		return connTimeout;
	}

	public void setConnTimeout(int connTimeout) {
		this.connTimeout = connTimeout;
	}
	
	public int getPoolMaxNum() {
		return poolMaxNum;
	}

	public void setPoolMaxNum(int poolMaxNum) {
		this.poolMaxNum = poolMaxNum;
	}

	public int getPoolMaxPerRouteNum() {
		return poolMaxPerRouteNum;
	}

	public void setPoolMaxPerRouteNum(int poolMaxPerRouteNum) {
		this.poolMaxPerRouteNum = poolMaxPerRouteNum;
	}

	public int getPoolMaxTargetConnNum() {
		return poolMaxTargetConnNum;
	}

	public void setPoolMaxTargetConnNum(int poolMaxTargetConnNum) {
		this.poolMaxTargetConnNum = poolMaxTargetConnNum;
	}

	public int getMaxRetryConnNum() {
		return maxRetryConnNum;
	}

	public void setMaxRetryConnNum(int maxRetryConnNum) {
		this.maxRetryConnNum = maxRetryConnNum;
	}

	public HttpRequestRetryHandler getHttpRequestRetryHandler() {
		return httpRequestRetryHandler;
	}

	public void setHttpRequestRetryHandler(
			HttpRequestRetryHandler httpRequestRetryHandler) {
		this.httpRequestRetryHandler = httpRequestRetryHandler;
	}
	
	public HttpHost getProxy() {
		return proxy;
	}

	public void setProxy(HttpHost proxy) {
		this.proxy = proxy;
	}

}
