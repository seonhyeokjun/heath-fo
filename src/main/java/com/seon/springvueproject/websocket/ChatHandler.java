package com.seon.springvueproject.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seon.springvueproject.domain.messge.Message;
import com.seon.springvueproject.domain.messge.MsgRoom;
import com.seon.springvueproject.service.Message.MsgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {
    private final MsgService msgService;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : {}", payload);

        Message msg = objectMapper.readValue(payload, Message.class);
        MsgRoom room = msgService.findById(msg.getRoomId());
    }
}
