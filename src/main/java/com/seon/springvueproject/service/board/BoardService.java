package com.seon.springvueproject.service.board;

import com.seon.springvueproject.config.auth.LoginUser;
import com.seon.springvueproject.config.auth.dto.SessionUser;
import com.seon.springvueproject.domain.board.Board;
import com.seon.springvueproject.domain.board.BoardRepository;
import com.seon.springvueproject.domain.file.FileLoad;
import com.seon.springvueproject.domain.heart.Heart;
import com.seon.springvueproject.domain.heart.HeartRepository;
import com.seon.springvueproject.service.file.FileService;
import com.seon.springvueproject.web.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final HeartRepository heartRepository;
    private final FileService fileService;

    /**
     * 게시물 저장
     * @param boardSaveRequestDto
     * @return
     */
    @Transactional
    public Long save(BoardSaveRequestDto boardSaveRequestDto, List<MultipartFile> files,
                     SessionUser sessionUser) throws Exception {
        Board board = boardSaveRequestDto.toEntity();
        if (files != null){
            for (MultipartFile file : files){
                if (Objects.equals(file.getOriginalFilename(), "")) continue;
                String realFilename = file.getOriginalFilename();
                UUID uuid = UUID.randomUUID();
                String filename = uuid + "_" + file.getOriginalFilename();
                String savePath = System.getProperty("user.dir") + "/src/main/resources/static/files";
                String filePath = savePath + "/" + filename;
                file.transferTo(new File(filePath));
                FileLoad fileLoad = new FileLoad(realFilename, filename, filePath);
                board.addFile(fileLoad);
            }
        }
        board.addUser(sessionUser.getId());

        return boardRepository.save(board).getId();
    }

    /**
     * 게시물 수정
     * @param id
     * @param requestDto
     * @return
     */
    @Transactional
    public Long update(Long id, BoardUpdateRequestDto requestDto){
        Board posts = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. ID=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    /**
     * 게시물 검색
     * @param id
     * @return
     */
    public BoardResponseDto findById(Long id) {
        Board entity = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        return new BoardResponseDto(entity);
    }

    /**
     * 게시물 전체 불러오기
     * @return
     */
    public Page<BoardResponseDto> findAllDesc(){
        int size = boardRepository.findAll().size();
        if (size == 0) size = 1;
        return boardRepository.findAllDesc(PageRequest.of(0, size));
    }

    /**
     * 특정 게시물 검색
     * @param keyword
     * @return
     */
    @Transactional
    public List<BoardSearchDto> searchBoard(String keyword){
        List<Board> boardList = boardRepository.findByTitleContaining(keyword);
        List<BoardSearchDto> boardSearchDtoList = new ArrayList<>();

        if (boardList.isEmpty()) return boardSearchDtoList;

        for (Board board : boardList){
            boardSearchDtoList.add(this.convertEntityToDto(board));
        }

        return boardSearchDtoList;
    }

    private BoardSearchDto convertEntityToDto(Board board) {
        return BoardSearchDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .createdDate(board.getCreatedDate())
                .build();
    }

    /**
     * 게시물 삭제
     * @param id
     */
    @Transactional
    public void delete(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        boardRepository.delete(board);
    }

    /**
     * 조회수 증가
     * @param id
     */
    @Transactional
    public void updateHit(Long id) {
        boardRepository.updateHit(id);
    }

    /**
     * 게시물 좋아요 표시
     * @param boardId
     * @param sessionUser
     * @return
     */
    @Transactional
    public int likeCheck(Long boardId, SessionUser sessionUser){
        /* 비회원일 경우 */
        if (sessionUser == null){
            return 0;
        }

        /* 처음 게시물을 방문했을때 */
        if (heartRepository.findByBoardIdAndUserId(boardId, sessionUser.getId()) == null){
            Heart heart = Heart.builder()
                    .boardId(boardId)
                    .userId(sessionUser.getId())
                    .likeCheck(0)
                    .build();

            heartRepository.save(heart);
        }
        return heartRepository.findByBoardIdUserId(boardId, sessionUser.getId());
    }

    /**
     * 게시물 좋아요 변화
     * @param boardId
     * @param sessionUser
     * @return
     */
    @Transactional
    public int likeChange(Long boardId, SessionUser sessionUser) {
        Heart heart = heartRepository.findByBoardIdAndUserId(boardId, sessionUser.getId());

        /* 좋아요 표시 X */
        if (heart.getLikeCheck() == 0){
            heart.update(1);
        } else if (heart.getLikeCheck() == 1){ /* 좋아요 표시 O */
            heart.update(0);
        }

        return heart.getLikeCheck();
    }
}
