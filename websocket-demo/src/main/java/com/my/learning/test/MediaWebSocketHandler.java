package com.my.learning.test;

import com.alibaba.fastjson.JSONObject;
import com.streamcomputing.ytj.inference.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.NativeWebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class MediaWebSocketHandler extends BinaryWebSocketHandler {
    final String baseUrl = "http://172.16.30.26:8125";
    final String startSessionUrl = baseUrl + "/api/start";
    final String sendAudioUrl = baseUrl + "/api/chunk";
    final String finishSessionUrl = baseUrl + "/api/finish";

    private static final int THRESHOLD_SIZE = 4096;

    private static final Map<String, String> REQUEST_SESSION_MAP = new ConcurrentHashMap<>();
    private static final Map<String, ByteBuffer> REQUEST_AUDIO_MAP = new ConcurrentHashMap<>();

    private String getRequestId(WebSocketSession session) {
        URI uri = session.getUri();
        if (ObjectUtils.isEmpty(uri)) {
            return null;
        }
        return getRequestId(session.getUri());
    }

    private String getRequestId(URI uri) {
        String path = uri.getPath();
        String[] split = path.split("/");
        return split[split.length - 1];
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (!(session instanceof NativeWebSocketSession)) {
            return;
        }
        String requestId = getRequestId(session);
        if (ObjectUtils.isEmpty(requestId)) {
            return;
        }
        log.info("连接建立成功, ==> 开始监听新连接：session_id = {}， requestId = {},。", session.getId(), requestId);
        REQUEST_AUDIO_MAP.put(requestId, ByteBuffer.allocate(THRESHOLD_SIZE * 10));
        startSession(requestId);
    }

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        byte[] array = message.getPayload().array();
        String requestId = getRequestId(session);
        if (ObjectUtils.isEmpty(requestId)) {
            return;
        }
        String sessionId = REQUEST_SESSION_MAP.get(requestId);
        if (ObjectUtils.isEmpty(sessionId)) {
            return;
        }
        log.info("接收到二进制数据, requestId={}, sessionId={}, message.size={}", requestId, sessionId, array.length);

        sendDataToSession(requestId, sessionId, session, array);
    }

    private void startSession(String requestId) {
        JSONObject result = HttpUtil.postForJSON("", startSessionUrl, null);
        if (ObjectUtils.isEmpty(result)) {
            return;
        }
        log.info("startSession, http result->{}", result);
        String sessionId = result.getString("session_id");
        if (ObjectUtils.isEmpty(sessionId)) {
            return;
        }
        REQUEST_SESSION_MAP.put(requestId, sessionId);
    }

    private void stopSession(String requestId, WebSocketSession session) {
        ByteBuffer buffer = REQUEST_AUDIO_MAP.get(requestId);
        String sessionId = REQUEST_SESSION_MAP.get(requestId);
        if (ObjectUtils.isEmpty(buffer) || ObjectUtils.isEmpty(sessionId)) {
            return;
        }
        // 如果buffer内存有数据，则发送剩余数据
        if (buffer.position() > 0) {
            // 1. 准备读取数据：将 limit 设置为 position，position 设置为 0
            buffer.flip();
            // 从buffer内取出剩余数据，放到一个4096字节的数组中
            byte[] remainingData = new byte[buffer.limit()];
            buffer.get(remainingData);
            sendData(sessionId, remainingData, session);
            buffer.compact();
        }

        String url = finishSessionUrl + "?" + "session_id=" + sessionId;
        JSONObject result = HttpUtil.postForJSON("", url, null);
        log.info("finishSession, http result->{}", result);

        REQUEST_SESSION_MAP.remove(requestId);
        REQUEST_AUDIO_MAP.remove(requestId);
    }

    private void sendData(String sessionId, byte[] data4096, WebSocketSession session) {
        try {
            if (data4096.length < THRESHOLD_SIZE) {
                byte[] sendData = new byte[THRESHOLD_SIZE];
                System.arraycopy(data4096, 0, sendData, 0, data4096.length);
                data4096 = sendData;
            }

            // 4. 打印或处理提取的数据
            String url = sendAudioUrl + "?" + "session_id=" + sessionId;
            JSONObject result = HttpUtil.postForStream(data4096, url, MediaType.APPLICATION_OCTET_STREAM, null);
            log.info("sendAudio, http result->{}", result);
            if (!ObjectUtils.isEmpty(result)) {
                session.sendMessage(new TextMessage(result.toJSONString()));
            }
        } catch (IOException ioe) {
            log.error("发送音频数据失败", ioe);
        }
    }

    private void sendDataToSession(String requestId, String sessionId, WebSocketSession session, byte[] array) {
        ByteBuffer buffer = REQUEST_AUDIO_MAP.get(requestId);
        buffer.put(array);

        while (buffer.position() >= THRESHOLD_SIZE) {
            buffer.flip();
            byte[] extractedData = new byte[THRESHOLD_SIZE];
            buffer.get(extractedData);

            sendData(sessionId, extractedData, session);

            buffer.compact();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String requestId = getRequestId(session);
        log.info("连接已关闭, ==> requestId={}, status = {}", requestId, status);

        stopSession(requestId, session);
    }
}