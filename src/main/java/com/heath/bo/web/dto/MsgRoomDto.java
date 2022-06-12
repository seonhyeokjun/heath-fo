package com.heath.bo.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MsgRoomDto {
    private String roomName;

    @Builder
    public MsgRoomDto(String roomName){
        this.roomName = roomName;
    }
}
