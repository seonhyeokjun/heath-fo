package com.seon.springvueproject.domain.messge;

import com.seon.springvueproject.service.Message.MsgService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class MsgRoom {
    private String roomId;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public MsgRoom(String roomId){
        this.roomId = roomId;
    }

    public void handleActions(WebSocketSession session, Message message, MsgService msgService){
        if (message.getMessageType().equals(Message.MessageType.ENTER)){
            sessions.add(session);
            message.setMessage(message.getSender() + "님이 입장했습니다.");
        }
        sendMessage(message, msgService);
    }

    private <T> void sendMessage(T message, MsgService msgService) {
        sessions.parallelStream().forEach(session -> msgService.sendMessage(session, message));
    }
}
