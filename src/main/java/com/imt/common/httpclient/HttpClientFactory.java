package com.imt.common.httpclient;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HttpClientFactory {
	private HttpClientMessage message = null;
	private CloseableHttpClient httpclient = null;

	private HttpClientBuilder httpClientBuilder = null;
	public static ThreadPoolExecutor threadPool = null;
	private  HttpHost targetHost =null;
	private boolean isProxy = false;
	static{
		threadPool = new ThreadPoolExecutor(15, 40, 0L,TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1000),new ThreadPoolExecutor.CallerRunsPolicy());
	}
	
	public HttpClientFactory(){
		this.message = new HttpClientMessage(); 
		httpclient = initHttpClient();
	}
	
	//使用代理主机构造
	public HttpClientFactory(String proxHost,int proxyPort){
	    HttpHost proxy = new HttpHost(proxHost, proxyPort);
	    this.message = new HttpClientMessage(); 
	    this.message.setProxy(proxy);
		httpclient = initHttpClient();
		this.isProxy = true;
	}
	
	public HttpClientMessage getMessage() {
		return message;
	}

	public HttpClientFactory(HttpClientMessage message){
		this.message = message; 
		httpclient = initHttpClient();
	}
	
	public HttpClientFactory(HttpClientMessage message,String host){
		this.message = message; 
		httpclient = initHttpClient();
		targetHost = new HttpHost(host, 80);
	}
	


	
	private CloseableHttpClient initHttpClient(){
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(message.getPoolMaxNum());
		cm.setDefaultMaxPerRoute(message.getPoolMaxPerRouteNum());

		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setSocketTimeout(message.getSoTimeout())
				.setConnectTimeout(message.getConnTimeout())
				.setConnectionRequestTimeout(message.getReqTimeout())
				.build();

/*		ArrayList<Header> headers = new ArrayList<Header>();
		Header header = new BasicHeader(HttpHeaders.USER_AGENT, message.getHttpHeaderMessag().getRandomUserAgent());
		headers.add(header);*/

		httpClientBuilder = HttpClients.custom()
				.setDefaultRequestConfig(defaultRequestConfig)
				.setRetryHandler(message.getHttpRequestRetryHandler())
//				.setDefaultHeaders(headers)
				.setConnectionManager(cm);

		//设置代理
		if(message.getProxy()!=null) {
			DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(message.getProxy());
			httpClientBuilder.setRoutePlanner(routePlanner);
		}


		CloseableHttpClient httpclient = httpClientBuilder.build();


		return httpclient;
	}


	/**
	 * https的方法生成
	 * @return
	 */
	public CloseableHttpClient initHttpsClient(){
		try {
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
			cm.setMaxTotal(message.getPoolMaxNum());
			cm.setDefaultMaxPerRoute(message.getPoolMaxPerRouteNum());

			RequestConfig defaultRequestConfig = RequestConfig.custom()
					.setSocketTimeout(message.getSoTimeout())
					.setConnectTimeout(message.getConnTimeout())
					.setConnectionRequestTimeout(message.getReqTimeout())
					.build();

			ArrayList<Header> headers = new ArrayList<Header>();
			//Header header = new BasicHeader(HttpHeaders.USER_AGENT, message.getHttpHeaderMessag().getRandomUserAgent());
			Header header = new BasicHeader(HttpHeaders.USER_AGENT, UserAgentEnum.Chrome.getValue());
			headers.add(header);


			X509TrustManager x509mgr = new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

				}

				@Override
				public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					//return new X509Certificate[0];
					return null;
				}
			};

			SSLContext sslContext = SSLContext.getInstance("TLS");
				sslContext.init(null, new TrustManager[] { x509mgr }, null);
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

			httpClientBuilder = HttpClients.custom()
					.setDefaultRequestConfig(defaultRequestConfig)
					.setRetryHandler(message.getHttpRequestRetryHandler())
					.setDefaultHeaders(headers)
					.setSSLSocketFactory(sslsf)
					.setConnectionManager(cm);


			return httpClientBuilder.build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return  HttpClients.createDefault();
	}


	public void shutdown() {
		try {
			httpclient.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//httpclient.getConnectionManager().shutdown();
	}
	
	/**
	 * 校验代理
	 * @return
	 */
	public boolean validateProxy() {
		if(!this.isProxy) return true;
		boolean tag = false;
		Socket socket = null;
		try {
			socket = new Socket(this.getMessage().getProxy().getHostName(),this.getMessage().getProxy().getPort());
			socket.setSoTimeout(  1000*2 );
			socket.close();
			tag = true;
		} catch (Exception e) {
			return false;
		}
		finally{
			try {
				if(socket!=null)
					socket.close();
			} catch (IOException e) {
			}
		}
		return tag;
	}

	public CloseableHttpClient getHttpclient() {
		return httpclient;
	}

}
