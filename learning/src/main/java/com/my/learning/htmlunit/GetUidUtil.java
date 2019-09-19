package com.my.learning.htmlunit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class GetUidUtil {
	public static void main(String[] args) throws URISyntaxException, ClientProtocolException, IOException {
//		https://s.weibo.com/ajax/jsonp/gettopsug?uid=&ref=PC_topsug&url=https://weibo.com/liuyifeiofficial?is_all=1&Mozilla=Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:67.0) Gecko/20100101 Firefox/67.0&_cb=STK_155963818291212
		
		String mainPageUrl = "https://weibo.com/liuyifeiofficial";
		String url = "https://s.weibo.com/ajax/jsonp/gettopsug";
		URIBuilder uriBuilder = new URIBuilder(url);
		uriBuilder.addParameter("uid", "");
		uriBuilder.addParameter("ref", "PC_topsug");
		uriBuilder.addParameter("url", "mainPageUrl");
//		uriBuilder.addParameter(param, value)
		
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = builder.build();
		
		HttpPost httpPost = new HttpPost(url);
		HttpResponse response = httpClient.execute(httpPost);
		String responseStr = EntityUtils.toString(response.getEntity()); 
		System.out.println(responseStr);
		System.out.println(Arrays.toString(response.getAllHeaders()));
		
		
	}
}
