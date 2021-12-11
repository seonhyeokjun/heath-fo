package com.seon.springvueproject.domain.heart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    @Query("SELECT p.likeCheck FROM Heart p WHERE p.boardId = :boardId and p.userId = :userId")
    int findByBoardIdUserId(Long boardId, Long userId);

    Heart findByBoardId(Long boardId);

    Heart findByUserId(Long userId);
}
