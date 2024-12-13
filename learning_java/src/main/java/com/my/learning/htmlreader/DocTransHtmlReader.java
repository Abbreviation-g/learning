package com.my.learning.htmlreader;

import java.io.IOException;
import java.net.MalformedURLException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlBody;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class DocTransHtmlReader {
    public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        String htmlPath = "C:/work/program/source_aunit/Help在线文档/html/doc.html";

        WebClient webClient = new WebClient();
        // 启动 js 解释器
        webClient.getOptions().setJavaScriptEnabled(true);
        // 禁用 css 支持
        webClient.getOptions().setCssEnabled(false);
        HtmlPage page = webClient.getPage("file:///"+htmlPath);
        System.out.println(page.toString());
        DomNodeList<DomElement> bodyEles = page.getElementsByTagName("body");
        HtmlBody body = (HtmlBody)(bodyEles.get(0));
        System.out.println(body);
    }
}
