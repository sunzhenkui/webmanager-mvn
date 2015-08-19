package com.imt.common.httpclient;

public interface HttpResponseHandler {
	public void onResponse(HttpResponseModel model)  throws Exception;
	public void onHttpsFailed(HttpResponseModel model);
	public void onResponseFailed(HttpResponseModel model);
}
