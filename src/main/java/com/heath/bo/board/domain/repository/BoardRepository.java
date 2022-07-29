package com.heath.bo.board.domain.repository;

import com.heath.bo.board.domain.model.Board;
import com.heath.bo.board.domain.dto.BoardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b ORDER BY b.id DESC")
    Page<BoardResponseDto> findAllDesc(Pageable pageable);

    List<Board> findByTitleContaining(String search);

    @Modifying
    @Query("UPDATE Board b set b.hit = b.hit + 1 WHERE b.id = :id")
    void updateHit(@Param("id") Long id);
}
