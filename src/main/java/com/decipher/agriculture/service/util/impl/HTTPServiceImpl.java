package com.decipher.agriculture.service.util.impl;

import com.decipher.agriculture.service.util.HTTPService;
import com.decipher.util.PlantingProfitLogger;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by abhishek on 3/9/16.
 */
@Service
public class HTTPServiceImpl implements HTTPService {
    @Override
    public void sendPost(String url, List<NameValuePair> postParams) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        PlantingProfitLogger.info("Hitting URL [POST] : " + url + ", with data : " + postParams);
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(postParams));
//        httppost.getParams().setBooleanParameter("http.protocol.expect-continue", false);

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        String s = EntityUtils.toString(response.getEntity());
        PlantingProfitLogger.debug("HttpPost response : \n" + s);

    }
}
