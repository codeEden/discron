 package com.nt.open.proc.util;

 import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.common.base.Strings;




 /**
  * 
  * @author bjfulianqiu
  *
  */
public class HTTPUtils {
    private final static Logger logger = LogManager.getLogger(HTTPUtils.class.getName());

    public static Object[] sendHttpOrHttps(String url, String method, List<BasicNameValuePair> nvps,String contentType) throws Exception{
    	Object[] result=new Object[2];
    	HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
    	//信任所有
        configureHttpsClient(httpClientBuilder);  
        CloseableHttpClient httpClient = httpClientBuilder.build();
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).build();//设置请求超时时间
        CloseableHttpResponse response=null;
        String UserAgent="Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)";
        String ContentType="application/x-www-form-urlencoded;UTF-8";
        if(!Strings.isNullOrEmpty(contentType)){
        	ContentType = contentType;
        }
        // 创建本地的HTTP内容  
        if ("GET".equals(method) ) {
        	HttpGet httpGet = new HttpGet();  
        	httpGet.addHeader("User-Agent", UserAgent);
        	httpGet.addHeader("Content-Type", ContentType);
        	httpGet.setURI(new URI(url));  
        	httpGet.setConfig(requestConfig);
        	
            response = httpClient.execute(httpGet);
        }else{
        	HttpPost httpPost = new HttpPost();
        	if(nvps!=null){
        		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        	}
        	httpPost.addHeader("User-Agent", UserAgent);
        	httpPost.setURI(new URI(url));  
        	httpPost.setConfig(requestConfig);
            response = httpClient.execute(httpPost);
        }
        result[0]=response;
        return result;
    }
    
    
    public static void configureHttpsClient(HttpClientBuilder clientBuilder) {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();

			clientBuilder.setSslcontext(sslContext);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}
    
}
