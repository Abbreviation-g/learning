package com.my.learning.aspose.word;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JSExecution {
    private ScriptEngine jScriptEngine ;
    public JSExecution(String context) throws IOException, ScriptException{
        init(context);
    }
    
    public JSExecution(File jsFile) throws IOException, ScriptException{
        byte[] jsBytes = Files.readAllBytes(Paths.get(jsFile.toURI()));
        String context = new String(jsBytes);
        init(context);
    }
    
    public void init(String context) throws ScriptException {
        jScriptEngine = new ScriptEngineManager().getEngineByName("js");
        jScriptEngine.eval(context);
    }
    
    public String invokeFunction(String name, Object... args) throws NoSuchMethodException, ScriptException {
        Invocable invocable = (Invocable) jScriptEngine;
        Object result = invocable.invokeFunction(name, args);
        return result.toString();
    }
    
    public static void main(String[] args) throws IOException, ScriptException, NoSuchMethodException {
       File jsFile =  new File("C:/tmp/aspose/js.conf");
        JSExecution jsExecution = new JSExecution(jsFile);
        
//        Object result = jsExecution.invokeFunction("sum", new int[] {1,2,3,4,5,6});
        Object result = jsExecution.invokeFunction("joinStr", (Object)new String[] {"2","3"});
        System.out.println(result);
    }
}
