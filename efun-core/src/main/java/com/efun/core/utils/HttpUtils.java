package com.efun.core.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.impl.io.DefaultHttpResponseParserFactory;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Http
 *
 * @author Galen
 * @since 2017/8/6
 */
public class HttpUtils {

    protected final static Logger logger = LogManager.getLogger(HttpUtils.class);

    static PoolingHttpClientConnectionManager manager = null;

    static volatile CloseableHttpClient httpClient = null;

    public static CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            synchronized (HttpUtils.class) {
                if (httpClient == null) {
                    //注册访问协相关的socket工厂
                    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                            .<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.INSTANCE)
                            .register("https", SSLConnectionSocketFactory.getSystemSocketFactory()).build();

                    //HttpConnection工厂：配置写请求/解析响应处理器
                    HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connectionFactory
                            = new ManagedHttpClientConnectionFactory(DefaultHttpRequestWriterFactory.INSTANCE,
                            DefaultHttpResponseParserFactory.INSTANCE);

                    //DSN解析器
                    DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;

                    //创建池化连接管理器
                    manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connectionFactory, dnsResolver);

                    //默认为socket配置
                    SocketConfig defaultSocketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
                    manager.setDefaultSocketConfig(defaultSocketConfig);

                    //设置连接池整体最大连接数
                    manager.setMaxTotal(300);
                    //设置每个路由的最大连接数
                    manager.setDefaultMaxPerRoute(200);
                    //从连接池获取连接时，连接不活跃长时间后需要进行一次验证，默认为2s
                    manager.setValidateAfterInactivity(5 * 1000);
                    RequestConfig defaultRequestConfig = RequestConfig.custom()
                            .setConnectTimeout(2 * 1000)//设置连接超时时间，2s
                            .setSocketTimeout(5 * 1000)//设置等待数据超时时间，5s
                            .setConnectionRequestTimeout(2 * 1000)//设置从连接池获取连接的等待超时时间
                            .build();

                    //穿件HttpClient
                    httpClient = HttpClients.custom()
                            .setConnectionManager(manager)
                            //连接池不是共享模式，共享模式下，多个httpclient对象共享一个连接池，回收线程也只能一个。
                            .setConnectionManagerShared(false)
                            //定义回收空闲连接
                            .evictIdleConnections(60, TimeUnit.SECONDS)
                            //定期回收过期线程
                            .evictExpiredConnections()
                            //连接存货时间，如果不设置，则根据长连接信息决定
                            .setConnectionTimeToLive(60, TimeUnit.SECONDS)
                            //设置默认请求配置
                            .setDefaultRequestConfig(defaultRequestConfig)
                            //连接重用策略，是否能keepalive
                            .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)
                            //长连接配置，即获取长连接生产多长的时间
                            .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                            //重试次数，默认是3次；当前禁用掉，根据需要开启
                            .setRetryHandler(new DefaultHttpRequestRetryHandler(0, false))
                            .build();

                    //JVM停止或者重启时，关闭连接池释放掉连接
                    Runtime.getRuntime().addShutdownHook(new Thread() {
                        @Override
                        public void run() {
                            try {
                                httpClient.close();
                            } catch (Exception ex) {
                                logger.error(ex.getMessage(), ex);
                            }
                        }
                    });
                }
            }
        }
        return httpClient;
    }

    /**
     * 建立请求builder
     * @param url
     * @return
     */
    public static HttpBuilder url(String url) {
        return HttpBuilder.createHttpBuilder(url);
    }

    /**
     * builder模式，满足http请求的多参数的组合。
     */
    public static class HttpBuilder {

        private static final String DEFAULT_CHARSET = "UTF-8";

        private static final ContentType DEFAULT_CONTENTTYPE = ContentType.APPLICATION_FORM_URLENCODED;

        private CloseableHttpClient httpClient = null;

        //默认编码
        private String charset = DEFAULT_CHARSET;

        //默认内容格式
        private ContentType contentType = DEFAULT_CONTENTTYPE;

        //请求头
        private Map<String, String> headers = new HashMap<String, String>();

        //请求参数
        private Map<String, Object> params = new HashMap<String, Object>();

        //请求内容，用于post
        private String content = null;

        //请求url
        private String url = null;

        private HttpBuilder () {
        }

        private HttpBuilder(String url) {
            AssertUtils.notNull(url, "url can not be null");
            this.url = url;
        }

        public static HttpBuilder createHttpBuilder(String url) {
            HttpBuilder builder = new HttpBuilder(url);
            return builder;
        }

        public HttpBuilder addHeader(String name, String value) {
            this.headers.put(name, value);
            return this;
        }

        public HttpBuilder addHeaders(Map<String, String> headers) {
            this.headers.putAll(headers);
            return this;
        }

        public HttpBuilder addParam(String key, Object value) {
            this.params.put(key, value);
            return this;
        }

        public HttpBuilder addParams(Map<String, Object> params) {
            this.params.putAll(params);
            return this;
        }

        /**
         * 设置请求内容的格式
         * @param contentType
         * @return
         */
        public HttpBuilder contentType(ContentType contentType) {
            this.contentType = contentType;
            return this;
        }

        /**
         * 设置请求内容
         * @param content
         * @return
         */
        public HttpBuilder content(String content) {
            this.content = content;
            return this;
        }

        private Header[] getHeaders() {
            this.headers.put("Content-Type", contentType.toString());
            Header[] headers = new Header[this.headers.size()];
            int i = 0;
            for (Map.Entry<String, String> item : this.headers.entrySet()) {
                headers[i++] = new BasicHeader(item.getKey(), item.getValue());
            }
            return headers;
        }

        private List<NameValuePair> getParams() {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> item : this.params.entrySet()) {
                params.add(new BasicNameValuePair(item.getKey(), String.valueOf(item.getValue())));
            }
            return params;
        }

        /**
         * get请求
         * @return
         */
        public String get() {
            return get(String.class);
        }

        /**
         * get请求，支持返回String或者byte[]类型
         * @return
         */
        public <T> T get(Class<T> clz) {
            HttpResponse response = null;
            T result = null;
            try {
                if (this.params.size() > 0) {
                    String queryStr = EntityUtils.toString(new UrlEncodedFormEntity(getParams(), charset));
                    this.url = this.url + "?" + queryStr;
                }
                HttpGet get = new HttpGet(this.url);
                if (this.headers.size() > 0) {
                    get.setHeaders(getHeaders());
                }
                response = getHttpClient().execute(get);
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    EntityUtils.consume(response.getEntity());
                    logger.warn("http get failed, code: {}, url:{}", response.getStatusLine().getStatusCode(), url);
                }
                if (byte[].class.equals(clz)) {
                    result = (T) EntityUtils.toByteArray(response.getEntity());
                } else {
                    result = (T) EntityUtils.toString(response.getEntity(), charset);
                }
            } catch (Exception ex) {
                logger.warn("http get failed, url: {}", url);
                logger.warn(ex.getMessage(), ex);
                if (response != null) {
                    try {
                        EntityUtils.consume(response.getEntity());
                    } catch (IOException e) {
                        logger.warn(e.getMessage(), e);
                    }
                }
            }
            return result;
        }

        /**
         * post请求
         * @return
         */
        public String post() {
           return post(String.class);
        }
        /**
         * post请求，默认支持form_urlencoded, 可以选择json。返回值支持String或byte[]类型
         * @return
         */
        public <T> T post(Class<T> clz) {
            HttpResponse response = null;
            T result = null;
            try {
                HttpEntity entity = null;
                if (StringUtils.isNotBlank(content)) {
                    entity = new StringEntity(content, contentType);
                } else if (params.size() > 0) {
                    if (contentType.equals(ContentType.APPLICATION_FORM_URLENCODED)) {
                        entity = new UrlEncodedFormEntity(getParams(), charset);
                    } else if (contentType.equals(ContentType.APPLICATION_JSON)) {
                        entity = new StringEntity(JSON.toJSONString(params), charset);
                    }
                }
                HttpPost post = new HttpPost(this.url);
                if (this.headers.size() > 0) {
                    post.setHeaders(getHeaders());
                }
                if (entity != null) {
                    post.setEntity(entity);
                }
                response = getHttpClient().execute(post);
                if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    EntityUtils.consume(response.getEntity());
                    logger.warn("http post failed, code: {}, url:{} content:{}", response.getStatusLine().getStatusCode(), url, content);
                }
                if (byte[].class.equals(clz)) {
                    result = (T) EntityUtils.toByteArray(response.getEntity());
                } else {
                    result = (T) EntityUtils.toString(response.getEntity(), charset);
                }
            } catch (Exception ex) {
                logger.warn("http post failed, url: {}", url);
                logger.warn(ex.getMessage(), ex);
                if (response != null) {
                    try {
                        EntityUtils.consume(response.getEntity());
                    } catch (IOException e) {
                        logger.warn(e.getMessage(), e);
                    }
                }
            }
            return result;
        }

    }


    public static void main(String[] args) {
        System.out.println(HttpUtils.url("http://assist.efuntw.com/ip/welcome.shtml").addHeader("User-Agent", "java8").get());
        System.out.println(HttpUtils.url("https://assist.efuntw.com/ip/welcome.shtml").post());
        System.out.println(new String(HttpUtils.url("http://assist.efuntw.com/ip/welcome.shtml").get(byte[].class)));
        System.out.println(HttpUtils.url("https://assist.efuntw.com/ip/welcome.shtml").post(String.class));
    }
}
