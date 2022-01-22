package com.seon.springvueproject.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seon.springvueproject.config.auth.dto.SessionUser;
import com.seon.springvueproject.domain.board.Board;
import com.seon.springvueproject.domain.board.BoardRepository;
import com.seon.springvueproject.domain.user.Role;
import com.seon.springvueproject.domain.user.User;
import com.seon.springvueproject.web.dto.BoardResponseDto;
import com.seon.springvueproject.web.dto.BoardSaveRequestDto;
import com.seon.springvueproject.web.dto.BoardUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class BoardApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    protected MockHttpSession session;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        User user = User.builder()
                .name("test")
                .email("test@test.com")
                .picture("test")
                .role(Role.USER)
                .build();

        SessionUser sessionUser = new SessionUser(user);

        session = new MockHttpSession();
        session.setAttribute("user", sessionUser);
    }

    @AfterEach
    public void tearDown(){
        session.clearAttributes();
    }

    @Test
    @DisplayName("게시판글이 등록된다")
    @WithMockUser(roles="USER")
    void Board_등록된다() throws Exception {
        // given
        String title = "title";
        String content = "content";
        BoardSaveRequestDto requestDto = BoardSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .hit(0)
                .build();

        String url = "http://localhost:" + port + "/api/board";

        // when
        MockMultipartFile firstFile = new MockMultipartFile("files",
                "_i_icon_10247_icon_102470_256.png",
                "text/plain",
                new FileInputStream("/home/ec2-user/app/spring/springVueProject/src/main/resources/static/files/6acea158-6ba9-41a0-8b52-ac535e88883e_구직활동 확인서(2021-10-26_2021-11-25) (1).pdf"));
        MockMultipartFile secondFile = new MockMultipartFile("files",
                "_i_icon_16008_icon_160080_256.png",
                "text/plain",
                new FileInputStream("/home/ec2-user/app/spring/springVueProject/src/main/resources/static/files/a58ca201-1626-4023-81e7-f95863e5a80f_구직활동 확인서(2021-10-26_2021-11-25).pdf"));

        String contents = objectMapper.writeValueAsString(requestDto);
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("key", "jsondata",
                        "application/json", contents.getBytes(StandardCharsets.UTF_8));

        mvc.perform(multipart(url)
                .file(mockMultipartFile).file(firstFile).file(secondFile).session(session)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk());

        // then
        List<Board> all = boardRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles="USER")
    void Board_수정된다() throws Exception{
        // given
        Board savedBoard = boardRepository.save(Board.builder()
                .title("title")
                .content("content")
                .build());

        long updateId = savedBoard.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        BoardUpdateRequestDto requestDto = BoardUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/board/" + updateId;

        //when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto))).andDo(print())
                .andExpect(status().isOk());

        // then
        List<Board> all = boardRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    @WithMockUser(roles = "USER")
    void board_불러온다() throws Exception {
        // given
        Page<BoardResponseDto> boardList = boardRepository.findAllDesc(PageRequest.of(0, 2));
        String url = "http://localhost:" + port + "/api/board/";

        // when
        mvc.perform(get(url)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="USER")
    void board_삭제한다() throws Exception {
        //given
        Board savedBoard = boardRepository.save(Board.builder()
                .title("title")
                .content("content")
                .build());

        long deleteId = savedBoard.getId();

        String url = "http://localhost:" + port + "/api/board/" + deleteId;

        // when
        mvc.perform(delete(url)).andDo(print()).andExpect(status().isOk());

        // then
        List<Board> all = boardRepository.findAll();
        assertThat(all).isEmpty();
    }
}