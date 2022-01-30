package com.seon.springvueproject.web.dto;

import com.seon.springvueproject.domain.board.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate;
    private Integer hit;

    public BoardResponseDto(Board entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.createdDate = entity.getCreatedDate();
        this.hit = entity.getHit();
    }
}
