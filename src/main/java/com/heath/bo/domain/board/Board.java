package com.heath.bo.domain.board;

import com.heath.bo.domain.BaseTimeEntity;
import com.heath.bo.domain.file.FileLoad;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    private Integer hit;

    private Long userId;

    @OneToMany(
            mappedBy = "board",
            cascade = ALL,
            orphanRemoval = true
    )
    private List<FileLoad> files = new ArrayList<>();

    @Builder
    public Board(String title, String content, String author, Integer hit){
        this.title = title;
        this.content = content;
        this.author = author;
        this.hit = hit;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    public void addFile(FileLoad file){
        this.files.add(file);
        if (file.getBoard() != this){
            file.setBoard(this);
        }
    }

    public void addUser(Long id){
        this.userId = id;
    }
}
