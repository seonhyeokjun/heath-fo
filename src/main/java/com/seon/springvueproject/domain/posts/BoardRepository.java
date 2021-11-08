package com.seon.springvueproject.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT p FROM Board p ORDER BY p.id DESC")
    List<Board> findAllDesc();

    List<Board> findByTitleContaining(String search);
}
