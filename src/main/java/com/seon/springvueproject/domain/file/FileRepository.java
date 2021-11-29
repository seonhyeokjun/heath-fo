package com.seon.springvueproject.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FileRepository extends JpaRepository<FileLoad, Long> {
    @Query
    FileLoad findByBoardId(Long id);
}
