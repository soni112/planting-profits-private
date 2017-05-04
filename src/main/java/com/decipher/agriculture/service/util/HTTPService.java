package com.decipher.agriculture.service.util;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by abhishek on 3/9/16.
 */
public interface HTTPService {

    void sendPost(String url, List<NameValuePair> postParams) throws Exception;

}
