package com.my.learning.jdk;

import java.io.*;
import java.util.concurrent.*;

/**
 * 执行本地命令，等待命令执行完毕，获取执行结果和异常信息
 */
public class LocalCommandExecutor2 {
    private final String[] cmd;
    private final boolean isUnix;

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String command = "echo Hello, World!";
//        command = "xxxx";
        command = "explorer C:\\Users\\enjing.guo\\Downloads";
        LocalCommandExecutor2 localCommandExecutor = new LocalCommandExecutor2(false, command);
        CommandResult commandResult = localCommandExecutor.execute();
        System.out.println("执行结果：");
        System.out.println(printBytes(commandResult.output()));
        System.out.println("异常信息：");
        System.out.println(printBytes(commandResult.error()));
        System.out.println("退出值：");
        System.out.println(commandResult.exitCode());
    }

    private static String printBytes(byte[] bytes) {
        try {
            return new String(bytes, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "GBK编码异常";
        }
    }

    public LocalCommandExecutor2(boolean isUnix, String... cmd) {
        this.isUnix = isUnix;
        this.cmd = cmd;
    }

    public CommandResult execute() throws IOException, InterruptedException, ExecutionException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (isUnix) {
            processBuilder.command("bash", "-c", String.join(" ", cmd));
        } else {
            processBuilder.command("cmd", "/c", String.join(" ", cmd));
        }

        Process process = processBuilder.start();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<byte[]> outputFuture = executorService.submit(() -> readStream(process.getInputStream()));
        Future<byte[]> errorFuture = executorService.submit(() -> readStream(process.getErrorStream()));

        executorService.shutdown();
        if (executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            System.out.println("线程执行完毕");
            int exitCode = process.waitFor();
            executorService.close();

            return new CommandResult(outputFuture.get(), errorFuture.get(), exitCode);
        } else {
            System.out.println("线程超时");
            process.destroy();
            executorService.close();

            return new CommandResult(null, null, -1);
        }
    }

    private byte[] readStream(InputStream inputStream) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buff = new byte[128];
            int length = -1;
            while ((length = inputStream.read(buff)) != -1) {
                baos.write(buff, 0, length);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public record CommandResult(byte[] output, byte[] error, int exitCode) {
    }
}
