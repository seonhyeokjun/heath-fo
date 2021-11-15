package com.seon.springvueproject.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT p FROM Board p ORDER BY p.id DESC")
    Page<Board> findAllDesc(Pageable pageable);

    List<Board> findByTitleContaining(String search);
}
