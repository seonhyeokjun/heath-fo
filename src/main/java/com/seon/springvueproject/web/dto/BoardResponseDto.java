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

    @Builder
    public BoardResponseDto(Long id, String title, String content, String author, LocalDateTime createdDate,
                            Integer hit){
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdDate = createdDate;
        this.hit = hit;
    }

    public BoardResponseDto(Board entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
        this.createdDate = entity.getCreatedDate();
        this.hit = entity.getHit();
    }
}
