package com.heath.bo.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class HeartDto {
    private Long boardId;
    private Long userId;
    private int likeCheck;

    @Builder
    public HeartDto(Long boardId, Long userId, int likeCheck){
        this.boardId = boardId;
        this.userId = userId;
        this.likeCheck = likeCheck;
    }
}
