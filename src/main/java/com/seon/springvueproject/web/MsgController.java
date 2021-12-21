package com.seon.springvueproject.web;

import com.seon.springvueproject.domain.messge.MsgRoom;
import com.seon.springvueproject.service.Message.MsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MsgController {
    private final MsgService msgService;

    @PostMapping("/chat")
    public MsgRoom createRoom(@RequestParam String name){
        return msgService.createRoom(name);
    }

    @GetMapping("/chat")
    public List<MsgRoom> findAllRoom(){
        return msgService.findAllRoom();
    }
}
