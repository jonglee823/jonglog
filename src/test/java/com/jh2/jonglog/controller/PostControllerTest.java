package com.jh2.jonglog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh2.jonglog.domain.Post;
import com.jh2.jonglog.domain.User;
import com.jh2.jonglog.exception.PostNotFound;
import com.jh2.jonglog.repository.PostRepository;
import com.jh2.jonglog.repository.UserRepository;
import com.jh2.jonglog.request.PostEdit;
import com.jh2.jonglog.request.PostRequest;
import com.jh2.jonglog.service.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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
    @DisplayName("(정상) 특정 ID 조회")
    @Transactional(readOnly = false)
    void getPost() throws Exception{

        User user = User.builder()
                .email("jh2@kakao.com")
                .name("이종혁")
                .password("tkfkd12dl!")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .user(user)
                .build();

        postRepository.save(post);

        mockMvc.perform(get("/posts/{postId}", post.getId())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
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

    @Test
    @DisplayName("(정상) 게시글 삭제")
    @Transactional(readOnly = false)
    void deletePost() throws Exception{

        User user = User.builder()
                .email("jh2@kakao.com")
                .name("이종혁")
                .password("tkfkd12dl!")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("테스트 게시글 제목")
                .content("테스트 게시글 내용")
                .user(user)
                .build();

        postRepository.save(post);

        mockMvc.perform(post("/posts/delete/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print());

        Assertions.assertThrows(PostNotFound.class
                , ()-> postService.getPost(post.getId()));
    }

    @Test
    @DisplayName("(정상) 게시글 제목, 내용 수정")
    @Transactional(readOnly = false)
    void sucUpdatePost() throws Exception{
        User user = User.builder()
                .email("jh2@kakao.com")
                .name("이종혁")
                .password("tkfkd12dl!")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("테스트 게시글 제목")
                .content("테스트 게시글 내용")
                .user(user)
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("수정 테스트 제목")
                .cotent("수정 테스트 내용")
                .build();

        String value = objectMapper.writeValueAsString(postEdit);

        mockMvc.perform(patch("/posts/{postId}", post.getId())
                        .content(value)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;

        Post findPost = postRepository.getPost(post.getId());

        Assertions.assertEquals(postEdit.getTitle(), findPost.getTitle());
        Assertions.assertEquals(postEdit.getContent(), findPost.getContent());
    }

    @Test
    @DisplayName("(실패) 게시글 제목만 수정")
    @Transactional(readOnly = false)
    void failUpdatePost() throws Exception{
        User user = User.builder()
                .email("jh2@kakao.com")
                .name("이종혁")
                .password("tkfkd12dl!")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .title("테스트 게시글 제목")
                .content("테스트 게시글 내용")
                .user(user)
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("수정 테스트 제목")
                .build();

        String value = objectMapper.writeValueAsString(postEdit);

        mockMvc.perform(patch("/posts/{postId}", post.getId())
                        .content(value)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
               // .andExpect(status().isOk())
        ;

        Post findPost = postRepository.getPost(post.getId());

        Assertions.assertEquals(postEdit.getTitle(), findPost.getTitle());
        Assertions.assertEquals(post.getContent(), findPost.getContent());
    }
}