package com.my.learning.htmlunit;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {
	public static void main(String[] args) throws ClientProtocolException, IOException {
		HttpClientBuilder builder = HttpClientBuilder.create();
		HttpClient httpClient = builder.build();
		HttpGet httpGet = new HttpGet("https://weibo.com/liuyifeiofficial");
		String cookie = "Cookie: SUB=_2AkMrqqa0dcPxrABXmPsXyW3kZYhH-jyYf89CAn7uJhMyOhh77nAEqSVutBF-XLs4BpoTFbCHjEPWR-CSGnbaiijw; SUBP=0033WrSXqPxfM72wWs9jqgMF55529P9D9WFOy.hqXqwQGpGTla2erusc5JpV2K201Knpehqp1KL5MP2Vqcv_; UOR=mo.techweb.com.cn,widget.weibo.com,www.jianshu.com; SINAGLOBAL=9885357410804.312.1530079419322; ULV=1558585509777:14:1:1:6388741570869.145.1558585509666:1550485697979; UM_distinctid=1683c05f4079-0f2687bf6b0b6e-4c312e7e-1fa400-1683c05f408333; YF-V5-G0=4e19e5a0c5563f06026c6591dbc8029f; login_sid_t=e26e3617255f1d237caeb8364cdfc3cb; cross_origin_proto=SSL; Ugrow-G0=9ec894e3c5cc0435786b4ee8ec8a55cc; _s_tentry=-; Apache=6388741570869.145.1558585509666; YF-Page-G0=753ea17f0c76317e0e3d9670fa168584|1559636344|1559636325; WBtopGlobal_register_version=5c10f3128cf400c5; TC-V5-G0=841d8e04c4761f733a87c822f72195f3; TC-Page-G0=7a922a70806a77294c00d51d22d0a6b7|1559634129|1559634129; SCF=AqR_RFFNEYHp19RNK8nyKsp1V8SMTo2HDGoP3IyYi863uJA-K3bFr2C3v0NXNzIFqubFqlsvxJpXB35-T52-CmY.; SUHB=0FQ7FOEwVgXw_y; un=1398106199@qq.com; _T_WM=b673e0b2537f99094bb5ccf44a49f83f; wb_view_log=1920*10801";
		String Referer = "https://passport.weibo.com/visitor/visitor?entry=miniblog&a=enter&url=https%3A%2F%2Fweibo.com%2Fliuyifeiofficial%3Fis_all%3D1&domain=.weibo.com&ua=php-sso_sdk_client-0.6.28&_rand=1559636275.9232";
		httpGet.setHeader("Referer", Referer);
		httpGet.setHeader("Cookie", cookie);
		
		HttpResponse response = httpClient.execute(httpGet);
		String responseStr = EntityUtils.toString(response.getEntity());
		System.out.println(responseStr);
	}
}
