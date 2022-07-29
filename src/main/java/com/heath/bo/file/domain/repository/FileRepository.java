package com.heath.bo.file.domain.repository;

import com.heath.bo.file.domain.model.FileLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<FileLoad, Long> {
    @Query("SELECT f FROM FileLoad f WHERE f.board.id = :id")
    List<FileLoad> findByBoardId(Long id);
}
