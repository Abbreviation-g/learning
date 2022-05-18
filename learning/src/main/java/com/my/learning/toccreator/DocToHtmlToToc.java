package com.my.learning.toccreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;

import freemarker.template.TemplateException;

public class DocToHtmlToToc {
    public static void main(String[] args) {
//        String docPath = "C:/work/program/source_aunit/Help在线文档/SunwiseAUnit用户手册 v3.4.0.doc";
//        String htmlOuputFolder = "C:/work/program/source_aunit/Help在线文档/html";
//        String htmlOuputName = "doc.html";
//        String htmlOuputPath = "html" +"/" +htmlOuputName;
//        final String outputXmlPath = "C:/Users/guoenjing/Desktop/temp/toc/toc.xml";
//
//        docToHtmlToToc(docPath, htmlOuputFolder, htmlOuputName, htmlOuputPath, outputXmlPath);
        
        String docPath = "C:/work/program/source_aunit/Help在线文档/SunwiseAUnit用户手册 v3.4.0.doc";
        String helpPluginPath = "C:/work/program/source_aunit/com.sunwiseinfo.aunit.ui.help";
        docToHtmlToToc(docPath, helpPluginPath);
        
    }
    
    public static void docToHtmlToToc(String docPath, String helpPluginPath) {
        String htmlOuputFolder = helpPluginPath +"/" + "html";
        String htmlOuputName = "doc.html";
        String htmlOuputPath = "html" +"/"+ htmlOuputName;
        String outputXmlPath = helpPluginPath +"/" +"toc.xml";
        docToHtmlToToc(docPath, htmlOuputFolder, htmlOuputName, htmlOuputPath, outputXmlPath);
    }

    public static void docToHtmlToToc(String docPath, String htmlOuputFolder, String htmlOuputName, String htmlOuputPath, final String outputXmlPath) {
        try {
            File htmlFile = DocToHtml.docToHtml(docPath, htmlOuputFolder, htmlOuputName);
            Collection<Catalog> firstCatalogs = HtmlReader.readCatalog(htmlFile);
            HtmlToToc.createToc(firstCatalogs, htmlOuputPath, outputXmlPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerFactoryConfigurationError e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FailingHttpStatusCodeException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw e;
        }
    }
}
