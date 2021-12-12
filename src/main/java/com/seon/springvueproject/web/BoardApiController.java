package com.seon.springvueproject.web;

import com.seon.springvueproject.config.auth.LoginUser;
import com.seon.springvueproject.config.auth.dto.SessionUser;
import com.seon.springvueproject.domain.board.Board;
import com.seon.springvueproject.service.board.BoardService;
import com.seon.springvueproject.web.dto.BoardResponseDto;
import com.seon.springvueproject.web.dto.BoardSaveRequestDto;
import com.seon.springvueproject.web.dto.BoardSearchDto;
import com.seon.springvueproject.web.dto.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BoardApiController {
    private final BoardService boardService;

    /**
     * 게시물 저장
     * @param boardSaveRequestDto
     * @return
     */
    @PostMapping("/api/board")
    public Long save(@RequestPart("key") BoardSaveRequestDto boardSaveRequestDto,
                     @RequestPart(value = "files", required = false) List<MultipartFile> files,
                     @LoginUser SessionUser sessionUser) throws Exception {
        return boardService.save(boardSaveRequestDto, files, sessionUser);
    }

    /**
     * 게시물 수정
     * @param id
     * @param requestDto
     * @return
     */
    @PutMapping("/api/board/{id}")
    public Long update(@PathVariable Long id, @RequestBody BoardUpdateRequestDto requestDto){
        return boardService.update(id, requestDto);
    }

    /**
     * 게시물 상세 조회
     * @param id
     * @return
     */
    @GetMapping("/api/board/{id}")
    public BoardResponseDto finById(@PathVariable Long id){
        boardService.updateHit(id);
        return boardService.findById(id);
    }

    /**
     * 게시물 전체 조회
     * @return
     */
    @GetMapping("/api/board/list")
    public Page<BoardResponseDto> findAll(){
        return boardService.findAllDesc();
    }

    /**
     * 게시물 검색
     * @param keyword
     * @return
     */
    @GetMapping("/api/board/search")
    public List<BoardSearchDto> search(@RequestParam(value = "keyword") String keyword){
        return boardService.searchBoard(keyword);
    }

    /**
     * 게시물 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/api/board/{id}")
    public Long delete(@PathVariable Long id){
        boardService.delete(id);
        return id;
    }

    /**
     * 게시물 좋아요 표시
     * @param boardId
     * @param sessionUser
     * @return
     */
    @GetMapping("/api/like/{boardId}")
    public int likeCheck(@PathVariable("boardId") Long boardId,
                         @LoginUser SessionUser sessionUser){
        return boardService.likeCheck(boardId, sessionUser);
    }

    /**
     * 게시물 좋아요 변화
     * @param boardId
     * @param sessionUser
     * @return
     */
    @PutMapping("/api/like/{boardId}")
    public int likeChange(@PathVariable("boardId") Long boardId,
                          @LoginUser SessionUser sessionUser){
        return boardService.likeChange(boardId, sessionUser);
    }
}
