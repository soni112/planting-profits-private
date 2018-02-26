package com.decipher.agriculture.service.util.impl;

import com.decipher.agriculture.service.util.HTTPService;
import com.decipher.util.PlantingProfitLogger;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

/**
 * Created by abhishek on 3/9/16.
 */
@Service
public class HTTPServiceImpl implements HTTPService {

    @Override
    public String sendPost(String url, List<NameValuePair> postParams) throws Exception {
        HttpClient httpclient = HttpClients.createDefault();
        PlantingProfitLogger.info("Hitting URL [POST] : " + url + ", with data : " + postParams);
        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(postParams));
//        httppost.getParams().setBooleanParameter("http.protocol.expect-continue", false);

        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        String s = EntityUtils.toString(response.getEntity());
        PlantingProfitLogger.debug("HttpPost response : \n" + s);
        return s;
    }

    @Override
    public String sendGet(String url, List<NameValuePair> getParams, Map<String, String> headers) throws Exception {
        final HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);

        if(getParams != null){
            URIBuilder builder = new URIBuilder(get.getURI());
            for (NameValuePair param : getParams) {
                builder.addParameter(param.getName(), param.getValue());
            }
            get = new HttpGet(builder.build());
        }

        if (headers != null && headers.size() != 0){
            for (String s : headers.keySet()) {
                get.setHeader(s, headers.get(s));
            }
        }

        get.getParams().setBooleanParameter("http.protocol.expect-continue", false);
        PlantingProfitLogger.info("Hitting URL [GET] : " + get.getURI());
        HttpResponse httpResponse = client.execute(get);
        String s = EntityUtils.toString(httpResponse.getEntity());
        PlantingProfitLogger.debug("HttpGet response : \n" + s);
        return s;
    }

    @Override
    public String sendHttpsPost(String url, List<NameValuePair> postParams){
        SSLContext sslContext = null;
        try {

            sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(null, new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                            return true;
                        }
                    }).build();


        CloseableHttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpPost httppost = new HttpPost(url);
        httppost.setHeader("Content-Type", "application/json");
        httppost.setHeader("Content-Security-Policy", "upgrade-insecure-requests");
        if(postParams != null){
            httppost.setEntity(new UrlEncodedFormEntity(postParams));
        }

        CloseableHttpResponse response = client.execute(httppost);
        return EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            return "{}";
        }
    }

    @Override
    public String sendHttpsGet(String url, List<NameValuePair> getParams, Map<String, String> headers){
        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(null, new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                            return true;
                        }
                    }).build();


        CloseableHttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpGet get = new HttpGet(url);
            if(getParams != null){
                URIBuilder builder = new URIBuilder(get.getURI());
                for (NameValuePair param : getParams) {
                    builder.addParameter(param.getName(), param.getValue());
                }
                get = new HttpGet(builder.build());
            }

            if (headers != null && headers.size() != 0){
                for (String s : headers.keySet()) {
                    get.setHeader(s, headers.get(s));
                }
            }

        CloseableHttpResponse response = client.execute(get);
        return EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            PlantingProfitLogger.error(e);
            return StringUtils.EMPTY;
        }
    }

    @Override
    public String sslPost(String urlOverHttps){
        CloseableHttpClient httpClient
                = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        ResponseEntity<String> response
                = new RestTemplate(requestFactory).exchange(urlOverHttps, HttpMethod.POST, null, String.class);


        return response.getBody();
    }

}
