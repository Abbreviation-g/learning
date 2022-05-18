package com.my.learning.poi;

import org.apache.poi.hwpf.HWPFDocumentCore;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Section;
import org.w3c.dom.Document;

public class SectionToHtmlConverter extends WordToHtmlConverter{

    public SectionToHtmlConverter(Document document) {
        super(document);
    }

    @Override
    public void processSection(HWPFDocumentCore wordDocument, Section section, int sectionCounter) {
        super.processSection(wordDocument, section, sectionCounter);
    }
}
