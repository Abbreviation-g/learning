package com.my.learning.htmlunit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class WebClientTest {
	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true); 
		webClient.getOptions().setRedirectEnabled(true);
		
		String username = "刘亦菲";
		String url = "https://weibo.com/";
		Page page = webClient.getPage(url);
		Thread.sleep(5*1000);
		System.out.println(page.getClass().getName());
		
		System.out.println(page.getUrl());
		Set<Cookie> cookies = webClient.getCookies(page.getUrl());
		System.out.println(cookies);
		
		Page page2 = webClient.getPage("https://passport.weibo.com/visitor/visitor?entry=miniblog&a=enter&url=https%3A%2F%2Fweibo.com%2F&domain=.weibo.com&ua=php-sso_sdk_client-0.6.28&_rand=1559639771.0646" );
		System.out.println(page2.getUrl());
		
		webClient.close();
	}
}
