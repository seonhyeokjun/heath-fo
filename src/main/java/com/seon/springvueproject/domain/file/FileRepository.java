package com.seon.springvueproject.domain.file;

import antlr.collections.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<FileLoad, Long> {
    @Query("SELECT p FROM FileLoad p WHERE p.board.id = :id")
    FileLoad findByBoardId(Long id);
}
