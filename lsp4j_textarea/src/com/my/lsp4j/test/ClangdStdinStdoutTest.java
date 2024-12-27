package com.my.lsp4j.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClangdStdinStdoutTest {
    static String clangdPath = "C:/sw/clangd_15.0.0/bin/clangd.exe";

    public static void main(String[] args) throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        ProcessBuilder processBuilder = new ProcessBuilder(clangdPath, "--log=verbose", "--pretty");
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        InputStream inputStream = process.getInputStream();
        OutputStream outputStream = process.getOutputStream();

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        watch(executorService, inputStream, outputStream);

        executorService.shutdown();
        if (executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("线程执行完毕");
            process.waitFor();
        } else {
            System.out.println("线程超时");
            process.destroy();
        }

        long end = System.currentTimeMillis();
        System.out.println("-->>" + (end - start) + "<<--");
    }

    static final String CPP_FILE_PATH = "C:/temp/test_cpp/test.cpp";

    static void watch(ExecutorService executorService, InputStream inputStream, OutputStream outputStream) {
        executorService.submit(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executorService.submit(() -> {
            try {
                String message = String.format("{\"jsonrpc\":\"2.0\",\"id\":0,\"method\":\"initialize\",\"params\":{\"processId\":%s,\"rootPath\":\"%s\",\"capabilities\":{},\"trace\":\"on\"}}", 123, clangdPath);
                sendMsg(outputStream, message);

                String cppPath = new File(CPP_FILE_PATH).toURL().toString();
                message = String.format("{\"jsonrpc\":\"2.0\",\"method\":\"textDocument/didOpen\",\"params\":{\"textDocument\":{\"uri\":\"%s\",\"languageId\":\"cpp\",\"version\":1,\"text\":\"int x;\"}}}", cppPath);
                sendMsg(outputStream, message);

                message = String.format("{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"textDocument/ast\",\"params\":{\n" +
                        "  \"textDocument\":{\"uri\":\"%s\"},\n" +
                        "  \"range\": {\"start\": {\"line\":0, \"character\":0}, \"end\": {\"line\":0, \"character\":17}}\n" +
                        "}}", cppPath);
                sendMsg(outputStream, message);

                message = "{\"jsonrpc\":\"2.0\",\"id\":2,\"method\":\"shutdown\"}";
                sendMsg(outputStream, message);
                message = "{\"jsonrpc\":\"2.0\",\"method\":\"exit\"}";
                sendMsg(outputStream, message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    static void sendMsg(OutputStream writer, String message) throws IOException {
        byte[] data = message.getBytes("utf-8");
        String content = String.format("Content-Length: %d\r\n" + "\r\n", data.length);

        writer.write((content).getBytes("ascii"));
        writer.flush();
        writer.write(data);
        writer.flush();
    }
}
