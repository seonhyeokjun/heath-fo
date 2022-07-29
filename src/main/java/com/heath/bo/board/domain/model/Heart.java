package com.heath.bo.board.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Heart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long boardId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int likeCheck;

    @Builder
    public Heart(Long boardId, Long userId, int likeCheck){
        this.boardId = boardId;
        this.userId = userId;
        this.likeCheck = likeCheck;
    }

    public void update(int likeCheck){
        this.likeCheck = likeCheck;
    }
}
