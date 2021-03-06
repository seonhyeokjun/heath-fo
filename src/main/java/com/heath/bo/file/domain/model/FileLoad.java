package com.heath.bo.file.domain.model;

import com.heath.bo.board.domain.model.Board;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileLoad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(nullable = false)
    private String realFilename;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String filePath;

    @Builder
    public FileLoad(String realFilename, String filename, String filePath){
        this.realFilename = realFilename;
        this.filename = filename;
        this.filePath = filePath;
    }

    public void setBoard(Board board){
        this.board = board;
        if (!board.getFiles().contains(this)){
            board.getFiles().add(this);
        }
    }
}