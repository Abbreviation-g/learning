package com.my.learning.freemarker;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerTest1 {
    public static void main(String[] args) throws URISyntaxException, IOException, TemplateException {
        URL ftlUrl = FreeMarkerTest1.class.getResource("/freemarker/Test1.ftl");
        File ftlFile = new File(ftlUrl.toURI());
        System.out.println(ftlUrl);
        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setDirectoryForTemplateLoading(ftlFile.getParentFile());
        
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("arr", new int[] {11,22,33,44,55,66,77,88,99,110});
        Template template = configuration.getTemplate(ftlFile.getName());
        
        StringWriter writer =new StringWriter();
        template.process(dataModel, writer);
        writer.close();
        System.out.println(writer.toString());
    }
    
}
