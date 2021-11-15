package com.seon.springvueproject.web.dto;

import com.seon.springvueproject.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardSearchDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdDate;

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }

    @Builder
    public BoardSearchDto(Long id, String title, String content, String author, LocalDateTime createdDate){
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdDate = createdDate;
    }
}
