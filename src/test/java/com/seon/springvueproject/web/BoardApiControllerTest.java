package com.seon.springvueproject.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seon.springvueproject.config.WebConfig;
import com.seon.springvueproject.config.auth.LoginUser;
import com.seon.springvueproject.config.auth.dto.SessionUser;
import com.seon.springvueproject.domain.board.Board;
import com.seon.springvueproject.domain.board.BoardRepository;
import com.seon.springvueproject.domain.user.Role;
import com.seon.springvueproject.domain.user.User;
import com.seon.springvueproject.web.dto.BoardResponseDto;
import com.seon.springvueproject.web.dto.BoardSaveRequestDto;
import com.seon.springvueproject.web.dto.BoardUpdateRequestDto;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardApiControllerTest {
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

    @Before
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

    @After
    public void tearDown(){
        session.clearAttributes();
    }

    @Test
    @WithMockUser(roles="USER")
    public void Board_등록된다() throws Exception {
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

        MockMultipartFile firstFile = new MockMultipartFile("files", "_i_icon_10247_icon_102470_256.png", "text/plain", new FileInputStream("/Users/seonhyeogjun/springVueProject/src/main/resources/static/files/a8de20c3-d967-4471-85cd-20307473c2b0__i_icon_10247_icon_102470_256.png"));
        MockMultipartFile secondFile = new MockMultipartFile("files", "_i_icon_16008_icon_160080_256.png", "text/plain", new FileInputStream("/Users/seonhyeogjun/springVueProject/src/main/resources/static/files/75785461-ed0c-4323-9b63-532f0d5cc22e__i_icon_16008_icon_160080_256.png"));
        String contents = objectMapper.writeValueAsString(requestDto);
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("key", "", "application/json", contents.getBytes(StandardCharsets.UTF_8));

        mvc.perform(multipart(url)
                .file(mockMultipartFile).file(firstFile).file(secondFile).session(session)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk());

        List<Board> all = boardRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles="USER")
    public void Board_수정된다() throws Exception{
        // given
        Board savedPosts = boardRepository.save(Board.builder()
                .title("title")
                .content("content")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        BoardUpdateRequestDto requestDto = BoardUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/board/" + updateId;

        //when
        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(requestDto))).andDo(print())
                .andExpect(status().isOk());

        // then
        List<Board> all = boardRepository.findAll();
        assertThat(all.get(1).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(1).getContent()).isEqualTo(expectedContent);
    }

    @Test
    public void Posts_불러온다(){
        // given
        Page<BoardResponseDto> postsList = boardRepository.findAllDesc(PageRequest.of(0, 2));
        String url = "http://localhost:" + port + "/api/v1/posts/list";
    }
}