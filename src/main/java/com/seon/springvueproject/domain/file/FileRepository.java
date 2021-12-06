package com.seon.springvueproject.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<FileLoad, Long> {
    @Query("SELECT p FROM FileLoad p WHERE p.board.id = :id")
    List<FileLoad> findByBoardId(Long id);
}
