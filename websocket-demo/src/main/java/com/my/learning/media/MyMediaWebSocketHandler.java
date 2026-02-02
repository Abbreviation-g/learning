package com.my.learning.media;

import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.NativeWebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class MyMediaWebSocketHandler extends BinaryWebSocketHandler {

    /**
     * 静态变量，用来记录当前在线连接数，线程安全的类。
     */
    private static AtomicInteger onlineSessionClientCount = new AtomicInteger(0);

    /**
     * 存放所有在线的客户端
     */
    private static Map<String, Session> onlineSessionClientMap = new ConcurrentHashMap<>();

    /**
     * 连接sid和连接会话
     */
    private String sid;

    private ByteArrayOutputStream bos = new ByteArrayOutputStream();

    private String getSid(URI uri) {
        String path = uri.getPath();
        String[] split = path.split("/");
        return split[split.length - 1];
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (!(session instanceof NativeWebSocketSession)) {
            return;
        }
        NativeWebSocketSession nativeWebSocketSession = (NativeWebSocketSession) session;
        sid = getSid(session.getUri());
        Session nativeSession
                = (Session) nativeWebSocketSession.getNativeSession();
        /**
         * session.getId()：当前session会话会自动生成一个id，从0开始累加的。
         */
        log.info("连接建立中 ==> session_id = {}， sid = {}", session.getId(), sid);
        //加入 Map中。将页面的sid和session绑定或者session.getId()与session
        onlineSessionClientMap.put(sid, nativeSession);

        //在线数加1
        onlineSessionClientCount.incrementAndGet();
        log.info("连接建立成功，当前在线数为：{} ==> 开始监听新连接：session_id = {}， sid = {},。", onlineSessionClientCount, session.getId(), sid);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        log.info("接收到二进制消息：{}", message);
        // java.nio.HeapByteBuffer
        byte[] bytes = message.getPayload().array();
        bos.write(bytes);
//        ByteBuffer buffer = message.getPayload();
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 从 Map中移除
        onlineSessionClientMap.remove(sid);

        //在线数减1
        onlineSessionClientCount.decrementAndGet();
        log.info("连接关闭成功，当前在线数为：{} ==> 关闭该连接信息：session_id = {}， sid = {},。", onlineSessionClientCount, session.getId(), sid);

        // 保存音频文件
        try {
            File file = new File("C:\\temp\\audio\\" + sid + ".webm");
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

            Files.write(file.toPath(), bis.readAllBytes());
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}