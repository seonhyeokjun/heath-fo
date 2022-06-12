package com.heath.bo.web.dto;

import com.heath.bo.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private Integer hit;

    @Builder
    public BoardSaveRequestDto(String title, String content, String author, Integer hit){
        this.title = title;
        this.content = content;
        this.author = author;
        this.hit = hit;
    }

    public Board toEntity(){
        return Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .hit(hit)
                .build();
    }
}
