package com.efun.core.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
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

    private static HttpClientBuilder httpClientBuilder;

    static {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = null;
        try {
            //信任任何链接
            X509TrustManager anyTrustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            SSLContext sslContext = SSLContext.getInstance("SSLv3");
            sslContext.init(null, new TrustManager[] {anyTrustManager}, null);
            // 设置协议http和https对应的处理socket链接工厂的对象
            socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslContext))
                    .build();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        requestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT_SECONDS * 1000)
                .setConnectTimeout(TIMEOUT_SECONDS * 1000).build();
        httpClientBuilder = HttpClients.custom().setConnectionManager(connManager).setMaxConnTotal(POOL_SIZE).setMaxConnPerRoute(POOL_SIZE)
                .setDefaultRequestConfig(requestConfig);
    }

    /**
     * 获取连接
     * @return
     */
    private static CloseableHttpClient createHttpClient() {
        return httpClientBuilder.build();
    }

    /**
     * get请求
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, new HashMap<String, String>(), DEFAULT_ENCODING);
    }

    /**
     * get请求
     * @param url
     * @param encoding
     * @return
     */
    public static String doGet(String url, String encoding) {
        return doGet(url, new HashMap<String, String>(), encoding);
    }

    /**
     * get请求
     * @param url 请求url
     * @param queryString 请求的参数字符串
     * @param encoding 编码类型
     * @return
     */
    public static String doGet(String url, String queryString, String encoding) {
        return doGet(url, queryStrTOMap(queryString), encoding);
    }

    /**
     * get请求
     * @param url 请求url
     * @param params 请求参数map
     * @param encoding 编码类型
     * @return
     */
    public static String doGet(String url, Map<String, String> params, String encoding) {
        if(StringUtils.isBlank(url)){
            return null;
        }

        try {

            if (params != null && params.size() != 0) {
                url += "?" + mapToQueryStr(params, encoding);
            }

            logger.debug("get request url : " + url);

            HttpGet httpGet = new HttpGet(url);
            CloseableHttpClient httpClient = createHttpClient();
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                httpGet.abort();
                logger.debug("http request failed");
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }


            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, StringUtils.isNotBlank(encoding) ? encoding : DEFAULT_ENCODING);
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
	public static String doPost(String url, Map<String, String> bodyParams, String encoding) {
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
			logger.error(e.getMessage(), e);
		}
		return null;
	}

    /**
     * post请求
     * @param url 请求url
     * @param reqeustBody 请求json格式的requestbody字符串
     * @param encoding 编码类型
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String doPost(String url, String reqeustBody, String encoding) throws UnsupportedEncodingException {
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
    public static String doPost(String url, HttpEntity httpEntity, String encoding) {
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            logger.debug("post request url : " + url);

            HttpPost httpPost = new HttpPost(url);

            logger.debug("post request body : " + httpEntity.toString());
            httpPost.setEntity(httpEntity);
            CloseableHttpClient httpClient = createHttpClient();
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                httpPost.abort();
                logger.debug("http request failed");
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, StringUtils.isNotBlank(encoding) ? encoding : DEFAULT_ENCODING);
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
