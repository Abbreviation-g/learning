package com.my.learning.htmlunit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("deprecation")
public class WeiboUtils {
	/** UA */
	static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36";

	/**
	 * uidתcontianerId
	 * 
	 * @author yanximin
	 */
	static String uidToContainerId(String uid) {
		if (uid == null)
			throw new IllegalArgumentException("uid is null");
		return 107603 + uid;
	}

	/**
	 * 昵称转ContainerId
	 * 
	 * @author yanximin
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	static String nicknameToContainerId(String nickname) throws ClientProtocolException, IOException {
		String url = "http://m.weibo.com/n/" + nickname;
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.setHeader("User-Agent", USER_AGENT);
		HttpResponse response = httpClient.execute(post);
		post.abort();
		if (response.getStatusLine().getStatusCode() == 302) {
			String cid = response.getLastHeader("Location").getValue().substring(27);
			return "107603" + cid;
		}
		return null;
	}

	/**
	 * 用户名转contianerId
	 * 
	 * @author yanximin
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	static String usernameToContainerId(String name) throws ClientProtocolException, IOException {
		String url = "https://weibo.cn/" + name;
		HttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		get.setHeader("User-Agent", USER_AGENT);
		HttpResponse response = httpClient.execute(get);
		String ret = EntityUtils.toString(response.getEntity(), "utf-8");
		Pattern pattern = Pattern.compile("href=\"/([\\d]*?)/info\"");
		Matcher matcher = pattern.matcher(ret);
		while (matcher.find()) {
			return "107603" + matcher.group(1);
		}
		return null;
	}

	/**
	 * 初始HttpClient
	 * 
	 * @author yanximin
	 */
	public static HttpClient getHttpClient() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
		return httpClient;
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		String username = "刘亦菲";
		String url = "http://m.weibo.com/n/" + username;
		HttpClientBuilder builder = HttpClientBuilder.create();
		CloseableHttpClient httpClient = builder.build();
		
		HttpPost httpPost = new HttpPost(url);
		HttpResponse response = httpClient.execute(httpPost);
		String responseStr = EntityUtils.toString(response.getEntity()); 
		System.out.println(responseStr);
		System.out.println(Arrays.toString(response.getAllHeaders()));
	}
}
