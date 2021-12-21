package com.seon.springvueproject.service.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seon.springvueproject.domain.messge.MsgRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsgService {
    private final ObjectMapper objectMapper;
    private Map<String, MsgRoom> msgRooms;

    @PostConstruct
    private void init(){
        msgRooms = new LinkedHashMap<>();
    }

    public List<MsgRoom> findAllRoom(){
        return new ArrayList<>(msgRooms.values());
    }

    public MsgRoom findById(String roomId){
        return msgRooms.get(roomId);
    }

    public MsgRoom createRoom(String name){
        String roomId = name;
        return MsgRoom.builder()
                .roomId(roomId)
                .build();
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
