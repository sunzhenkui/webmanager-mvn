package com.imt.common.httpclient;

import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunzhenkui on 2015/8/12.
 */
public class HttpClientRequest {
    private HttpClientFactory httpclient = null;
    private HashMap<String,String> headers = null;
    private String url;
    public HttpClientRequest(HttpClientFactory httpclient,String url){
        this.headers = headers;
        this.url  = url;
    }

    public HttpClientRequest(HttpClientFactory httpclient,String url,HashMap<String,String> headers){
        this.headers = headers;
        this.url  = url;
    }

    private void setHeaders(HttpRequestBase request){
        if(headers!=null || headers.size()>0){
            for(Map.Entry<String,String> entry:headers.entrySet()){
                request.setHeader(entry.getKey(),entry.getValue());
            }
        }
    }


    public HttpResponseModel doPostResponse(HashMap<String,String> paraMap) {
        if(!StringUtils.isNotBlank(url)) return null;
        HttpPost request = null;
        request = new HttpPost(url);
        try {
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for(Map.Entry<String, String> entry:paraMap.entrySet()){
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            ((HttpPost) request).setEntity(new UrlEncodedFormEntity(nvps));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return getResponseBytes(request);
    }

    public HttpResponseModel doPostResponse() {
        if(!StringUtils.isNotBlank(url)) return null;
        HttpPost request = null;
        request = new HttpPost(url);
        return getResponseBytes(request);
    }

    public HttpResponseModel doPostResponse(final HttpResponseHandler rh) {
        if(!StringUtils.isNotBlank(url)) return null;
        HttpPost request = null;
        request = new HttpPost(url);
        return getResponseBytes(request);
    }

    public HttpResponseModel doPostResponse(String content) {
        if(!StringUtils.isNotBlank(url)) return null;
        HttpPost request = null;
        request = new HttpPost(url);
        try {
            ByteArrayEntity entity = new ByteArrayEntity(content.getBytes("utf-8"));
            request.setEntity(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getResponseBytes(request);
    }



    public HttpResponseModel doGetResponse(boolean isAjax){
        if(!StringUtils.isNotBlank(url)) return null;
        HttpRequestBase request = null;
        request = new HttpGet(url);
        if(isAjax){
            request.removeHeaders("Content-Type");
            request.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            request.removeHeaders("X-Requested-With");
            request.addHeader("X-Requested-With", "XMLHttpRequest");
        }
        return getResponseBytes(request);
    }


    /**
     * 获取文件流
     * @return
     */
    public HttpResponseModel doGetFileResponse(HttpResponseStreamHandler handler){
        if(!StringUtils.isNotBlank(url)) return null;
        HttpRequestBase request = null;
        request = new HttpGet(url);
        return getStreamResponse(request,handler);
    }

    /**
     * 获取http连接后的返回状态
     * @return
     */
    public HttpResponseModel doGetResponse(){
        HttpRequestBase request = new HttpGet(url);
        return getResponseBytes(request);
    }

    /**
     * 执行简单的response，状态和cookie等，不返回字节流
     * @return
     */
    public HttpResponseModel doGetSimpleResponse(){
        HttpRequestBase request = new HttpGet(url);
        return getSimpleResponse(request);
    }

    private HttpResponseModel getSimpleResponse(HttpRequestBase request){
        HttpResponseModel model = null;
        InputStream in = null;
        CloseableHttpResponse response;
        response = null;
        int statusCode = -1;
        String url = "";
        try {
            setHeaders(request);
            url = request.getURI().toString();
            response = httpclient.getHttpclient().execute(request);
            statusCode = response.getStatusLine().getStatusCode();

            HashMap<String,String> cookies = getResponseCookies(response);
            model = new HttpResponseModel(url,statusCode,in);
            model.setCookies(cookies);
        } catch (Exception e) {
        }
        finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return model;
    }


    private HttpResponseModel getStreamResponse(HttpRequestBase request,HttpResponseStreamHandler handler){
        HttpResponseModel model = null;
        InputStream in = null;
        CloseableHttpResponse response = null;
        int statusCode = -1;
        String url = "";
        try {
            setHeaders(request);
            url = request.getURI().toString();
            response = httpclient.getHttpclient().execute(request);
            HttpEntity entity = response.getEntity();
            in = entity.getContent();
            statusCode = response.getStatusLine().getStatusCode();

            HashMap<String,String> cookies = getResponseCookies(response);
            model = new HttpResponseModel(url,statusCode,in);
            model.setCookies(cookies);

            if(handler!=null)
                handler.doStream(in);

            EntityUtils.consume(entity);

        } catch (Exception e) {

        }
        finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return model;
    }

    /**
     * 获取http连接后的返回字节
     * @param request
     * @return
     */
    private HttpResponseModel getResponseBytes(HttpRequestBase request){
        HttpResponseModel model = null;
        byte[] b = null;
        CloseableHttpResponse response = null;
        int statusCode = -1;
        String url = "";
        HttpEntity entity = null;
        try {
            setHeaders(request);
            url = request.getURI().toString();
            response = httpclient.getHttpclient().execute(request);
            entity = response.getEntity();
            b = EntityUtils.toByteArray(entity);
            statusCode = response.getStatusLine().getStatusCode();
            HashMap<String,String> cookies = getResponseCookies(response);
            model = new HttpResponseModel(b,statusCode);
            model.setCookies(cookies);

            EntityUtils.consume(entity);
        } catch (Exception e) {
        }
        finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return model;
    }

    /**
     * 响应中获取cookie
     *
     * @param response
     */
    private HashMap<String,String> getResponseCookies(HttpResponse response) {
        HashMap<String,String> map = new HashMap<String,String>();
/*		//法1
		HeaderIterator headerIter = response.headerIterator("Set-Cookie");
		while (headerIter.hasNext()) {
			Object obj = headerIter.next();
			if (obj != null) {
				String cookie = ((Header) headerIter.next()).getValue();

				String cookieRecord = cookie.substring(0, cookie.indexOf(';'));
				System.out.println(cookieRecord);
			}

		}*/

        //法2
        HeaderElementIterator headerElementIter = new BasicHeaderElementIterator(response.headerIterator("Set-Cookie"));
        while (headerElementIter.hasNext()) {
            HeaderElement elem = headerElementIter.nextElement();
            System.out.println("Element : " + elem.getName() + " = " + elem.getValue());
            NameValuePair[] params = elem.getParameters();
            for (int i = 0; i < params.length; i++) {
                map.put(params[i].getName(),params[i].getValue());
//				System.out.println(" Params : " + params[i].getName() + " | " + params[i].getValue());
            }
        }
        return map;
    }
}
