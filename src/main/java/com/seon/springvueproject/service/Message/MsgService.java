package com.seon.springvueproject.service.Message;

import com.seon.springvueproject.domain.messge.MsgRoom;
import com.seon.springvueproject.domain.messge.MsgRoomRepository;
import com.seon.springvueproject.web.dto.MsgRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsgService {
    private final MsgRoomRepository msgRoomRepository;

    /**
     * 채팅 방 전체 조회
     * @return
     */
    public List<MsgRoom> findAllRoom(){
        return msgRoomRepository.findAll();
    }

    /**
     * 채팅방 상세 조회
     * @param id
     * @return
     */
    public MsgRoom findById(Long id){
        return msgRoomRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. ID=" + id));
    }

    /**
     * 채티방 생성
     * @param msgRoomDto
     * @return
     */
    public Long createRoom(MsgRoomDto msgRoomDto){
        return msgRoomRepository.save(MsgRoom.builder()
                .roomName(msgRoomDto.getRoomName())
                .build()).getId();
    }
}
