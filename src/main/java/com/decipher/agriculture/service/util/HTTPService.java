package com.decipher.agriculture.service.util;

import org.apache.http.NameValuePair;

import java.util.List;
import java.util.Map;

/**
 * Created by abhishek on 3/9/16.
 */
public interface HTTPService {

    String sendGet(String url, List<NameValuePair> getParams, Map<String, String> headers) throws Exception;

    String sendPost(String url, List<NameValuePair> postParams) throws Exception;

    String sendHttpsPost(String url, List<NameValuePair> postParams);

    String sendHttpsGet(String url, List<NameValuePair> getParams, Map<String, String> headers);

    String sslPost(String urlOverHttps);
}
