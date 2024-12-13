package com.my.learning.htmlreader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.client.utils.URIBuilder;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;

public class HtmlReader2 {
    public static void main(String[] args) throws URISyntaxException, FailingHttpStatusCodeException, MalformedURLException, IOException {
        String htmlFilePath = "C:/work/program/source_aunit/Help在线文档/html/doc.html";
        
        Collection<Catalog> firstCatalogs = readCatalog(htmlFilePath);
        System.out.println(firstCatalogs.size());
        for (Catalog catalog : firstCatalogs) {
            System.out.println(catalog);
        }
    }
    
    public static Collection<Catalog> readCatalog(String htmlFilePath) throws URISyntaxException, FailingHttpStatusCodeException, MalformedURLException, IOException{
        WebClient webClient = new WebClient();
        // 启动 js 解释器
        webClient.getOptions().setJavaScriptEnabled(true);
        // 禁用 css 支持
        webClient.getOptions().setCssEnabled(false);
        URI uri = new URIBuilder().setScheme("file").setPath(htmlFilePath).build();
        System.out.println("uri: " + uri);
        HtmlPage page = webClient.getPage(uri.toURL());

        DomNodeList<DomElement> body = page.getElementsByTagName("body");
        DomElement bodyEle = body.get(0);
        DomNodeList<HtmlElement> divs = bodyEle.getElementsByTagName("div");
        HtmlDivision division = (HtmlDivision) divs.get(1);
        DomNodeList<HtmlElement> pEles = division.getElementsByTagName("p");
        
        Catalog firstCatalog = null;
        Catalog secendCatalog = null;
        Set<Catalog> firCatalogs = new LinkedHashSet<>();
        for (int i = 0; i < pEles.size(); i++) {
            HtmlElement pElement = pEles.get(i);
            HtmlParagraph paragraph = (HtmlParagraph) pElement;
            String classAttr = paragraph.getAttribute("class");
            if(classAttr.equals("p1")) {
                break;
            }
            if(classAttr.equals("p13")) {
                firstCatalog = getCatalog(paragraph);
                firCatalogs.add(firstCatalog);
            } else if(classAttr.equals("p14")) {
                secendCatalog = getCatalog(paragraph);
                if(firstCatalog!=null) {
                    firstCatalog.addChildCatalog(secendCatalog);
                }
            } else if(classAttr.equals("p15")) {
                Catalog thirdCatalog = getCatalog(paragraph);
                if(secendCatalog != null) {
                    secendCatalog.addChildCatalog(thirdCatalog);
                }
            }
        }
        webClient.close();
        return firCatalogs;
    }
    
    private static Catalog getCatalog(HtmlParagraph paragraph) {
        DomNodeList<HtmlElement> anchors = paragraph.getElementsByTagName("a");
        if(anchors.isEmpty()) {
            return null;
        }
        HtmlAnchor anchor = (HtmlAnchor) anchors.get(0);
        String toc = anchor.getAttribute("href");
        String title = paragraph.asText();
        String[] split = title.split("\\s+");
        title = split[1];
        
        Catalog catalog = new Catalog();
        catalog.setToc(toc);
        catalog.setTitle(title);
        return catalog;
    }
    
    
}
