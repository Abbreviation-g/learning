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
import com.gargoylesoftware.htmlunit.html.HtmlSpan;

import freemarker.template.TemplateException;

public class HtmlReader {
    public static void main(String[] args)
            throws FailingHttpStatusCodeException, MalformedURLException, IOException, URISyntaxException, TemplateException {
        // #_Toc
        // <a href="#_Toc61001585">
        String htmlPath = "C:/work/program/source_aunit/Help在线文档/doc3.htm";
        htmlPath = "C:/tmp/html/doc3.html";
        htmlPath = "C:/tmp/HTML2/AAAAAAAA.html";
        
        Collection<Catalog> firstCatalogs = readCatalog(htmlPath);
        System.out.println(firstCatalogs.size());
        for (Catalog catalog : firstCatalogs) {
            System.out.println(catalog);
        }
        final String tocHtmlPath = "html/doc.htm";
        final String outputXmlPath = "C:/Users/guoenjing/Desktop/temp/toc/toc.xml";
        TocXMLCreator.createToc(firstCatalogs, tocHtmlPath, outputXmlPath);
    }

    private static Collection<Catalog> readCatalog(String htmlFilePath)
            throws FailingHttpStatusCodeException, MalformedURLException, IOException, URISyntaxException {
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
        List<Object> WordSection2 = bodyEle.getByXPath("//div[@class='WordSection2']");// class="WordSection2"
        HtmlDivision WordSection2Div = (HtmlDivision) WordSection2.get(0);
        List<HtmlParagraph> pList = WordSection2Div.getByXPath("p") ;
        Collection<Catalog> firstCatalogs = readCatalog0(pList);

        webClient.close();
        
        return firstCatalogs;
    }

    private static Collection<Catalog>  readCatalog0(List<HtmlParagraph> paragraphs) {
        Set<Catalog> firCatalogs = new LinkedHashSet<>();
        Catalog firstCatalog = null;
        Catalog secendCatalog = null;
        for (int i = 1; i < paragraphs.size(); i++) {
            HtmlElement htmlElement = paragraphs.get(i);
            HtmlParagraph paragraph = (HtmlParagraph) htmlElement;
            String classAttr = paragraph.getAttribute("class");
            if(classAttr.equals("MsoToc1")) {
                firstCatalog = getCatalog(paragraph);
            } else if(classAttr.equals("MsoToc2")) {
                secendCatalog = getCatalog(paragraph);
                if(firstCatalog!=null) {
                    firstCatalog.addChildCatalog(secendCatalog);
                }
            } else if(classAttr.equals("MsoToc3")) {
                Catalog thirdCatalog = getCatalog(paragraph);
                if(secendCatalog != null) {
                    secendCatalog.addChildCatalog(thirdCatalog);
                }
            }
            firCatalogs.add(firstCatalog);
        }
        
        return firCatalogs;
    }
    
    private static Catalog getCatalog(HtmlParagraph paragraph) {
        List<Object> MsoHyperlink = paragraph.getByXPath("span[@class='MsoHyperlink']");// <span
        // class="MsoHyperlink">
        HtmlSpan MsoHyperlinkSpan = (HtmlSpan) MsoHyperlink.get(0);
        DomNodeList<HtmlElement> spans = MsoHyperlinkSpan.getElementsByTagName("span");
        List<Object> a = spans.get(0).getByXPath("a[@href]");// <a href=
        HtmlAnchor anchor = (HtmlAnchor) a.get(0);
        String toc = anchor.getAttribute("href");
        String tempTitle = anchor.asText();

        Catalog e = new Catalog(toc, splitTempTitle(tempTitle));
        return e;
    } 
    
    
    static String splitTempTitle(String text) {
        // 11    常见问题汇总... 141
        String[] splits = text.split("\\s+");
        text = splits[1];
        text = text.trim();
        text = text.replace("...", "");
        return text;
    }

}
