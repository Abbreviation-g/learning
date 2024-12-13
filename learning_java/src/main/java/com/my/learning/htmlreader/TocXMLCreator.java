package com.my.learning.htmlreader;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TocXMLCreator {
    public static void main(String[] args){
        final String outputXmlPath = "C:/Users/guoenjing/Desktop/temp/toc/toc.xml";
        
        
    }
    
    public static void createToc(Collection<Catalog> catalogs,String htmlPath, String outputXmlPath)  throws IOException, URISyntaxException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_21);
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        URL ftlUrl = TocXMLCreator.class.getResource("toc_xml.ftl");
        String ftlString = new String(Files.readAllBytes(Paths.get(ftlUrl.toURI())));
        templateLoader.putTemplate("toc_xml", ftlString);
        configuration.setTemplateLoader(templateLoader);

        Template template = configuration.getTemplate("toc_xml");
        
        FileWriter out = new FileWriter(outputXmlPath);
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("firstCatalogs", catalogs);
        dataModel.put("htmlPath", htmlPath);
        template.process(dataModel, out);
        out.close();
        
    }
}
