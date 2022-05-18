package com.my.learning.poi;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
 
public class DocxTransHtml {
    
    /**
     * 
     * @Title: docxTransHtml   
     * @Description: docx 转换 HTML  
     * @param: @param inputStream: docx 文件输入流
     * @param: @param outputStream: html 文件输出流
     * @param: @param imageSaveDir：图片路径 
     * @return: void      
     * @throws
     */
    public static void docxTransHtml(InputStream inputStream, OutputStream outputStream, String imageSaveDir){
        try{
            XWPFDocument document = new XWPFDocument(inputStream);
            
            XHTMLOptions options = XHTMLOptions.create();
            options.setExtractor(new FileImageExtractor(new File(imageSaveDir)));
            options.URIResolver(new BasicURIResolver(imageSaveDir));
            options.setIgnoreStylesIfUnused(true);
            
            org.apache.poi.xwpf.converter.xhtml.XHTMLConverter.getInstance().convert(document, outputStream, options);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
 
}
