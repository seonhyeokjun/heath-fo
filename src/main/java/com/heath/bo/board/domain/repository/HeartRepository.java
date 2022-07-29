package com.heath.bo.board.domain.repository;

import com.heath.bo.board.domain.model.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    @Query("SELECT h.likeCheck FROM Heart h WHERE h.boardId = :boardId and h.userId = :userId")
    int findByBoardIdUserId(Long boardId, Long userId);

    Heart findByBoardIdAndUserId(Long boardId, Long userId);
}
