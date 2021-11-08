package com.seon.springvueproject.service.posts;

import com.seon.springvueproject.domain.posts.Board;
import com.seon.springvueproject.domain.posts.BoardRepository;
import com.seon.springvueproject.web.dto.BoardResponseDto;
import com.seon.springvueproject.web.dto.BoardSaveRequestDto;
import com.seon.springvueproject.web.dto.BoardSearchDto;
import com.seon.springvueproject.web.dto.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    /**
     * 게시물 저장
     * @param boardSaveRequestDto
     * @return
     */
    @Transactional
    public Long save(BoardSaveRequestDto boardSaveRequestDto) {
        return boardRepository.save(boardSaveRequestDto.toEntity()).getId();
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
    public List<Board> findAllDesc(){
        return boardRepository.findAllDesc();
    }

    /**
     * 특정 게시물 검색
     * @param keyword
     * @return
     */
    @Transactional
    public List<BoardSearchDto> searchBoard(String keyword){
        List<Board> postsList = boardRepository.findByTitleContaining(keyword);
        List<BoardSearchDto> boardSearchDtoList = new ArrayList<>();

        if (postsList.isEmpty()) return boardSearchDtoList;

        for (Board posts : postsList){
            boardSearchDtoList.add(this.convertEntityToDto(posts));
        }

        return boardSearchDtoList;
    }

    private BoardSearchDto convertEntityToDto(Board posts) {
        return BoardSearchDto.builder()
                .id(posts.getId())
                .title(posts.getTitle())
                .content(posts.getContent())
                .author(posts.getAuthor())
                .createdDate(posts.getCreatedDate())
                .build();
    }
}
