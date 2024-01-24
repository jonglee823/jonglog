package com.jh2.jonglog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh2.jonglog.domain.User;
import com.jh2.jonglog.repository.PostRepository;
import com.jh2.jonglog.repository.UserRepository;
import com.jh2.jonglog.request.PostRequest;
import com.jh2.jonglog.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = {"local"})
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void claer(){
        userRepository.deleteAll();
        postRepository.deleteAll();
    }
    @Test
    @DisplayName("(정상) 게시글 등록")
    @Transactional(readOnly = false)
    void createPost() throws Exception{

        User user = User.builder()
                .email("jh2@kakao.com")
                .name("이종혁")
                .password("tkfkd12dl!")
                .build();

        userRepository.save(user);

        PostRequest postRequest = PostRequest.builder()
                .title("테스트 제목")
                .contents("테스트 내용")
                .userId(user.getId())
                .build();

        String value = objectMapper.writeValueAsString(postRequest);

        mockMvc.perform(post("/posts")
                        .content(value)
                        .contentType(MediaType.APPLICATION_JSON)
                    )
                .andExpect(status().isOk())
                .andDo(print());


    }

    @Test
    @DisplayName("(실패) 게시글 등록")
    @Transactional(readOnly = false)
    void createInvalidPost() throws Exception{
        PostRequest postRequest = PostRequest.builder()
                .title("테스트 제목")
                .contents("테스트 내용")
                .userId(2L)
                .build();

        String value = objectMapper.writeValueAsString(postRequest);

        mockMvc.perform(post("/posts")
                        .content(value)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                ;


    }
}