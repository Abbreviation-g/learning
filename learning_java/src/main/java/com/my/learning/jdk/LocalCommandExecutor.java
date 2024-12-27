package com.my.learning.jdk;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

/**
 * 执行本地命令，等待命令执行完毕，获取执行结果和异常信息
 */
public class LocalCommandExecutor {
    private final String[] cmd;
    private final boolean isUnix;

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        String command = "echo Hello, World!";
        command = "xxxx";
        LocalCommandExecutor localCommandExecutor = new LocalCommandExecutor(false, command);
        CommandResult commandResult = localCommandExecutor.execute();
        System.out.println("执行结果：");
        System.out.println(commandResult.getOutput());
        System.out.println("异常信息：");
        System.out.println(commandResult.getError());
        System.out.println("退出值：");
        System.out.println(commandResult.getExitCode());
    }

    public LocalCommandExecutor(boolean isUnix, String... cmd) {
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

        Future<String> outputFuture = executorService.submit(() -> readStream(process.getInputStream()));
        Future<String> errorFuture = executorService.submit(() -> readStream(process.getErrorStream()));

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

            return new CommandResult("", "", -1);
        }
    }

    private static String readStream(InputStream inputStream) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "gbk"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
                result.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static class CommandResult {
        private final String output;
        private final String error;
        private final int exitCode;

        public CommandResult(String output, String error, int exitCode) {
            this.output = output;
            this.error = error;
            this.exitCode = exitCode;
        }

        public String getOutput() {
            return output;
        }

        public String getError() {
            return error;
        }

        public int getExitCode() {
            return exitCode;
        }
    }
}
