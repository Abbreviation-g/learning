package com.my.learning.aspose.word;

import java.io.File;
import java.io.FileOutputStream;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import com.aspose.words.net.System.Data.DataColumn;
import com.aspose.words.net.System.Data.DataRow;
import com.aspose.words.net.System.Data.DataTable;

public class WordWriting {
    public static void main(String[] args) throws Exception {
        JSExecution jsExecution = new JSExecution(new File("C:/tmp/aspose/js.conf"));
        
        String inputFilePath = "C:/work/sorce_code/learning/learning/src/main/java/com/my/learning/aspose/word/域计算_template.docx";
        String outputFilePath = "C:/tmp/aspose/域计算.docx";
        FileOutputStream os = new FileOutputStream(new File(outputFilePath));
        Document doc = new Document(inputFilePath); // Address是将要被转化的word文档

        DataTable table1 = new DataTable("同行求和");
        table1.getColumns().add(new DataColumn("序号", Integer.class));
        table1.getColumns().add("a");
        table1.getColumns().add("b");
        table1.getColumns().add("c");
        table1.getColumns().add("d");
        table1.getColumns().add("sum");
        
        int[] arr1 = new int[] {1,2,3,4};
        DataRow row1 = table1.newRow();
        row1.set(0, 1);
        for (int i = 0; i < arr1.length; i++) {
            row1.set(i+1, arr1[i]);
        }
        String sum1 = jsExecution.invokeFunction("sum", arr1);
        row1.set(5, sum1);
        table1.getRows().add(row1);
        
        DataTable table2 = new DataTable("同行拼接");
        table2.getColumns().add(new DataColumn("序号", Integer.class));
        table2.getColumns().add("a");
        table2.getColumns().add("b");
        table2.getColumns().add("c");
        table2.getColumns().add("d");
        table2.getColumns().add("joinStr");
        String[] arr2 = new String[] {"a","b","c","d"};
        DataRow row2 = table2.newRow();
        row2.set(0, 2);
        for (int i = 0; i < arr2.length; i++) {
            row2.set(i+1, arr2[i]);
        }
        String joinStr = jsExecution.invokeFunction("joinStr",(Object) arr2);
        row2.set(5, joinStr);
        table2.getRows().add(row2);
        
        doc.getMailMerge().execute(new String[] {"标题域"}, new String[] {"一级标题"});
        doc.getMailMerge().executeWithRegions(table1);
        doc.getMailMerge().executeWithRegions(table2);
        
        doc.save(os, SaveFormat.DOCX);

    }
}
