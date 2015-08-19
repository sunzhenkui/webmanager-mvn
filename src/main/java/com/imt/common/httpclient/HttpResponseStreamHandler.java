package com.imt.common.httpclient;

import java.io.InputStream;

/**
 * Created by sunzhenkui on 2015/8/12.
 */
public interface HttpResponseStreamHandler {
    public void doStream(InputStream in)  throws Exception;
}
