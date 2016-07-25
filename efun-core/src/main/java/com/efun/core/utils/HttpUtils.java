package com.efun.core.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HttpUtils
 *
 * @author Galen
 * @since 2016/05/30.
 */
public class HttpUtils {

    protected final static Logger logger = LogManager.getLogger(HttpUtils.class);

    private static final int TIMEOUT_SECONDS = 20;

    private static final int POOL_SIZE = 20;

    private static final String DEFAULT_ENCODING = "UTF-8";

    private static RequestConfig requestConfig;

    private static CloseableHttpClient httpClient;

    static {
        httpClient = createHttpClient();
    }

    /**
     * 待优化
     * @return
     */
    private static CloseableHttpClient createHttpClient() {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        //指定信任密钥存储对象和连接套接字工厂
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //信任任何链接
            TrustStrategy anyTrustStrategy = new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            };
            SSLContext sslContext = SSLContexts.custom().useTLS().loadTrustMaterial(trustStore, anyTrustStrategy).build();
            LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            registryBuilder.register("https", sslSF);
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();
        //设置连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(registry);
        requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT_SECONDS * 1000)
                .setConnectTimeout(TIMEOUT_SECONDS * 1000).build();
        return HttpClientBuilder.create().setConnectionManager(connManager).setMaxConnTotal(POOL_SIZE).setMaxConnPerRoute(POOL_SIZE)
                .setDefaultRequestConfig(requestConfig).build();
    }

    /**
     * get请求
     * @param url 请求url
     * @param queryString 请求的参数字符串
     * @param encoding 编码类型
     * @return
     */
    public static Response doGet(String url, String queryString, String encoding) {
        return doGet(url, queryStrTOMap(queryString), encoding);
    }

    /**
     * get请求
     * @param url 请求url
     * @param params 请求参数map
     * @param encoding 编码类型
     * @return
     */
    public static Response doGet(String url, Map<String, String> params, String encoding) {
        if(StringUtils.isBlank(url)){
            return null;
        }

        try {

            url += "?" + mapToQueryStr(params, encoding);

            logger.debug("get request url : " + url);

            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                httpGet.abort();
                logger.debug("http request failed");
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }


            HttpEntity entity = response.getEntity();
            Response result = new Response();
            if (entity != null){
                result.setBody(EntityUtils.toString(entity, StringUtils.isNotBlank(encoding) ? encoding : DEFAULT_ENCODING));
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            logger.error("error", e);
        }
        return null;
    }

    /**
     * post请求
     * @param url 请求url
     * @param bodyParams requestbody的内容map
     * @param encoding 编码类型
     * @return
     */
	/*public static HttpResult doPost(String url, Map<String, String> bodyParams, String encoding) {
		List<NameValuePair> pairs = null;
        if(bodyParams != null && !bodyParams.isEmpty()){
            pairs = new ArrayList<NameValuePair>(bodyParams.size());
            for(Map.Entry<String,String> entry : bodyParams.entrySet()){
                String value = entry.getValue();
                if(value != null){
                    pairs.add(new BasicNameValuePair(entry.getKey(),value));
                }
            }
        }
        try {
			return doPost(url, new UrlEncodedFormEntity(pairs, StringUtils.isNotBlank(encoding) ? encoding : DEFAULT_ENCODING), encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}*/

    /**
     * post请求
     * @param url 请求url
     * @param reqeustBody 请求requestbody字符串
     * @param encoding 编码类型
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Response doPost(String url, String reqeustBody, String encoding) throws UnsupportedEncodingException {
        if(StringUtils.isNotBlank(reqeustBody)) {
            StringEntity stringEntity = new StringEntity(reqeustBody, ContentType.APPLICATION_JSON);
            stringEntity.setChunked(true);
            return doPost(url, stringEntity, encoding);
        } else {
            return doPost(url, new StringEntity("{}", ContentType.APPLICATION_JSON), StringUtils.isNotBlank(encoding)
                    ? encoding : DEFAULT_ENCODING);
        }
    }

    /**
     * post请求
     * @param url 请求url
     * @param httpEntity 请求实体
     * @param encoding 编码类型
     * @return
     */
    public static Response doPost(String url, HttpEntity httpEntity, String encoding) {
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            logger.debug("post request url : " + url);

            HttpPost httpPost = new HttpPost(url);

            logger.debug("post request body : " + httpEntity.toString());
            httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");
            httpPost.setEntity(httpEntity);

            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                httpPost.abort();
                logger.debug("http request failed");
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            Response result = new Response();
            if (entity != null){
                result.setBody(EntityUtils.toString(entity, StringUtils.isNotBlank(encoding) ? encoding : DEFAULT_ENCODING));
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            logger.error("error", e);
        }
        return null;
    }

    /**
     * 请求参数字符串转化为map
     * @param queryString
     * @return
     */
    public static Map<String, String> queryStrTOMap(String queryString) {
        String[] subQueryStrs = null;
        if(StringUtils.isNotBlank(queryString)){
            subQueryStrs = queryString.split("&");
        }
        Map<String, String> params = new HashMap<String, String>(subQueryStrs.length);
        for(String item : subQueryStrs){
            if(StringUtils.isNotBlank(item)){
                String[] keyValue = item.split("=");
                if(keyValue.length == 2){
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }

    /**
     * 请求参数map转化为字符串，并且按照编码转换
     * @param params
     * @param encoding
     * @return
     */
    public static String mapToQueryStr(Map<String, String> params, String encoding) {
        if(params != null && !params.isEmpty()){
            List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
            for(Map.Entry<String,String> entry : params.entrySet()){
                String value = entry.getValue();
                if(value != null){
                    pairs.add(new BasicNameValuePair(entry.getKey(),value));
                }
            }
            try {
                return EntityUtils.toString(new UrlEncodedFormEntity(pairs, StringUtils.isNotBlank(encoding) ? encoding : DEFAULT_ENCODING));
            } catch (Exception e) {
                logger.debug("map to queryStr failed !");
                logger.error("error", e);
            }
        }
        return null;
    }

    public static class Response {

        private String body;

        /**
         * 获取 body
         * @return body body
         */
        public String getBody() {
            return body;
        }

        /**
         * 设置 body
         * @param body body
         */
        public void setBody(String body) {
            this.body = body;
        }
    }

}
