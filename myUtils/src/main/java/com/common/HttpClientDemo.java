package com.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * HttpClient 工具类
 * @author afen
 *
 */
public class HttpClientDemo {

	@Test
	public void doGet() throws ClientProtocolException, IOException{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet("http://sogou.com");
		CloseableHttpResponse response = client.execute(get);
		System.out.println(response.getStatusLine().getStatusCode());
		
		HttpEntity entity = response.getEntity();
		String str = EntityUtils.toString(entity, "utf-8");
		System.out.println(str);
		response.close();
		client.close();
	}
	
	@Test
	public void doGetWithParam() throws Exception{
		CloseableHttpClient client = HttpClients.createDefault();
		URIBuilder urib = new URIBuilder("http://www.sogou.com/web");
		urib.addParameter("query", "蜡笔小新");
		HttpGet get = new HttpGet(urib.build());
		CloseableHttpResponse response = client.execute(get);
		System.out.println(response.getStatusLine().getStatusCode());
		
		HttpEntity entity = response.getEntity();
		String str = EntityUtils.toString(entity,"utf-8");
		System.out.println(str);
		response.close();
		client.close();
	}
	
	@Test
	public void doPost() throws ClientProtocolException, IOException{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost("http://localhost:8083/getResDate");
		CloseableHttpResponse response = client.execute(post);
		String str = EntityUtils.toString(response.getEntity());
		System.out.println(str);
		response.close();
		client.close();
	}
	
	@Test
	public void doPostWithParam() throws ClientProtocolException, IOException{
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost("http://localhost:8083/getResDateWithParam");
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("username", "zhangshan"));
		list.add(new BasicNameValuePair("password", "123456"));
		StringEntity eitity = new UrlEncodedFormEntity(list,"utf-8");
		post.setEntity(eitity);
		CloseableHttpResponse response = client.execute(post);
		String str = EntityUtils.toString(response.getEntity());
		System.out.println(str);
	}
}
