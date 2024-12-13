package com.my.learning.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;

public class DocCutter {

    public static void main(String[] args) {

        String docPath = "C:/work/program/source_aunit/Help在线文档/SunwiseAUnit用户手册 v3.4.0.doc";
        String outputFolder = new String("C:/Users/guoenjing/Desktop/temp/doc-temp");
        
        try (InputStream input = new FileInputStream(new File(docPath));) {
            HWPFDocument wordDocument = new HWPFDocument(input);
            Range range = wordDocument.getRange();
            int numSections = range.numSections();
            for (int i = 0; i < numSections; i++) {
                Section section = range.getSection(i);
                System.out.println(section.getEndOffset());
                final int paragraphs = section.numParagraphs();
                for (int p = 0; p < paragraphs; p++) {
                    System.err.println(i+"\t--------------------------");
                    Paragraph paragraph = section.getParagraph(p);
                    System.out.println(paragraph.text());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
