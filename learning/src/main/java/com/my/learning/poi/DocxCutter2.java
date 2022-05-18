package com.my.learning.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.xwpf.usermodel.BodyType;
import org.apache.poi.xwpf.usermodel.IBody;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocxCutter2 {
    public static void main(String[] args) {
        String outputFolder = new String("C:/Users/guoenjing/Desktop/temp/doc-temp");
        File file = new File("C:/work/program/source_aunit/Help在线文档/SunwiseAUnit用户手册 v3.4.0.docx");
        try (FileInputStream in = new FileInputStream(file);) {
            XWPFDocument doc = new XWPFDocument(in);
            List<XWPFParagraph> paragraphs = doc.getParagraphs();
            
            for (int i = 0; i < paragraphs.size(); i++) {
                XWPFParagraph paragraph = paragraphs.get(i);
                XWPFDocument outputDocument = new XWPFDocument();
                outputDocument.createParagraph();
                outputDocument.setParagraph(paragraph, 0);
                String outputFile = "TestDocOutput" + (i) + ".docx";
                try (FileOutputStream outputStream = new FileOutputStream(new File(outputFolder, outputFile));) {
                    outputDocument.write(outputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
//            List<IBodyElement> bodyElements = doc.getBodyElements();
//            System.out.println(bodyElements.size());
//            for (int i = 0; i < bodyElements.size(); i++) {
//                System.out.println(i);
//                IBodyElement element = bodyElements.get(i);
//                if(element instanceof XWPFTable) {
//                    XWPFTable table = (XWPFTable) element;
//
//                    XWPFDocument outputDocument = new XWPFDocument();
//                    outputDocument.createTable();
//                    outputDocument.setTable(0, table);
//                    String outputFile = "TestDocOutput" + (i) + ".docx";
//                    try (FileOutputStream outputStream = new FileOutputStream(new File(outputFolder, outputFile));) {
//                        outputDocument.write(outputStream);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else if (element instanceof XWPFParagraph) {
//                    
//                      XWPFParagraph paragraph = (XWPFParagraph) element;
//                      
//                      XWPFDocument outputDocument = new XWPFDocument();
//                      outputDocument.createParagraph(); outputDocument.setParagraph(paragraph, 0);
//                      String outputFile = "TestDocOutput" + (i) + ".docx"; try (FileOutputStream
//                      outputStream = new FileOutputStream(new File(outputFolder, outputFile));) {
//                      outputDocument.write(outputStream); } catch (Exception e) {
//                      e.printStackTrace(); }
//                     }
//                
////                String text = element.getText();
////                System.out.println(text);
////                POIXMLDocumentPart part = element.getPart();
//
////                XWPFDocument outputDocument = new XWPFDocument();
//
//            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
    }
}
