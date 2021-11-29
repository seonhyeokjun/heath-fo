package com.seon.springvueproject.domain.board;

import com.seon.springvueproject.web.dto.BoardResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT p FROM Board p ORDER BY p.id DESC")
    Page<BoardResponseDto> findAllDesc(Pageable pageable);

    List<Board> findByTitleContaining(String search);

    @Modifying
    @Query("UPDATE Board p set p.hit = p.hit + 1 WHERE p.id = :id")
    void updateHit(@Param("id") Long id);
}
