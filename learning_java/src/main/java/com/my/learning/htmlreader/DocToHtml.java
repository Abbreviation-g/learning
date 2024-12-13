package com.my.learning.htmlreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;

public class DocToHtml {
    public static Charset charset = Charset.forName("GB2312");
    
    public static void main(String[] args) {
        String docPath ="C:/work/program/source_aunit/Help在线文档/SunwiseAUnit用户手册 v3.4.0.doc";
        String outputFolder= "C:/work/program/source_aunit/Help在线文档/html";
        String outputFileName = "doc.html";
        try {
            docToHtml(docPath, outputFolder, outputFileName);
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
        }
    }
    
    public static File docToHtml(String docPath, String outputFolder, String outputFileName) throws FileNotFoundException, TransformerConfigurationException, IllegalArgumentException, IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        InputStream input = new FileInputStream(new File(docPath));
        HWPFDocument wordDocument = new HWPFDocument(input);
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        //设置图片存放的位置
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                File imgPath = new File(outputFolder, "images");
                if(!imgPath.exists()){//图片目录不存在则创建
                    imgPath.mkdirs();
                }
                File file = new File(imgPath , suggestedName);
                try {
                    OutputStream os = new FileOutputStream(file);
                    os.write(content);
                    os.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return file.getPath();
            }
        });
        
        //解析word文档
        wordToHtmlConverter.processDocument(wordDocument);
        Document htmlDocument = wordToHtmlConverter.getDocument();
        
        File htmlFile = new File(outputFolder, outputFileName);
        OutputStream outStream = new FileOutputStream(htmlFile);
        
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer serializer = factory.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, charset.name());
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        
        serializer.transform(domSource, streamResult);
        outStream.close();
        
        replaceDisorderlyStr(htmlFile);
        return htmlFile;
    }
    
    private static void replaceDisorderlyStr(File htmlFile) throws IOException {
        /*
         * TOC \o "1-3" \h \z \\u
         * */
        String disorderLyStr = "TOC \\o \"1-3\" \\h \\z \\u";
        List<String> allLines = Files.readAllLines(htmlFile.toPath(), charset);
        for (String line : allLines) {
            line = line.replace(disorderLyStr, "");
        }
        for (int i = 0; i < allLines.size(); i++) {
            String newLine = allLines.get(i);
            newLine = newLine.replace(disorderLyStr, "");
            allLines.set(i, newLine);
        }
       Files.write(htmlFile.toPath(), allLines, charset);
    }
}   
