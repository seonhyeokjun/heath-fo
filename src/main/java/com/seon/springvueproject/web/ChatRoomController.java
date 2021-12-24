package com.seon.springvueproject.web;

import com.seon.springvueproject.domain.messge.MsgRoom;
import com.seon.springvueproject.service.Message.MsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
    private final MsgService msgService;

    /**
     * 채팅방 전체 조회
     * @return
     */
    @GetMapping("/rooms")
    public List<MsgRoom> rooms(){
        return msgService.findAllRoom();
    }

    /**
     * 채팅방 상세 조회
     * @param id
     */
    @GetMapping("/room")
    public void findRoom(Long id){
        msgService.findById(id);
    }

    /**
     * 채팅방 생성
     * @param name
     * @return
     */
    @PostMapping("/room")
    public MsgRoom createRoom(@RequestParam String name){
        return msgService.createRoom(name);
    }
}
