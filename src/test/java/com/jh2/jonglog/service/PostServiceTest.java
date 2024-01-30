package com.jh2.jonglog.service;

import com.jh2.jonglog.domain.Post;
import com.jh2.jonglog.domain.User;
import com.jh2.jonglog.exception.PostNotFound;
import com.jh2.jonglog.exception.UserNotFound;
import com.jh2.jonglog.repository.PostRepository;
import com.jh2.jonglog.repository.UserRepository;
import com.jh2.jonglog.request.PostEdit;
import com.jh2.jonglog.request.PostRequest;
import com.jh2.jonglog.request.PostSearch;
import com.jh2.jonglog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("(정상) 게시글 등록")
    void createPost() throws Exception{

        //given
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

        //then
        Post findPost = postRepository.findById(post.getId())
                .orElseThrow(PostNotFound::new);
        Assertions.assertEquals(post.getTitle(), findPost.getTitle());
        Assertions.assertEquals(post.getContent(), findPost.getContent());
        Assertions.assertEquals(post.getUserId(), findPost.getUserId());
    }

    @Test
    @DisplayName("(실패) 존재하지 않는 사용자의 게시글 등록")
    void createInvalidPost() throws Exception{

        PostRequest postRequest = PostRequest.builder()
                .title("테스트 제목")
                .contents("테스트 내용")
                .userId(1L)
                .build();

        Assertions.assertThrows(UserNotFound.class,
                ()-> postService.create(postRequest));
    }

    @Test
    @DisplayName("(정상) 게시글 전체 조회")
    void findAllPostList(){
        //given
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
        post.delete();
        postRepository.save(post);

        Post post2 = Post.builder()
                .title("테스트 제목2")
                .content("테스트 내용2")
                .user(user)
                .build();
        postRepository.save(post2);
//
//        PostSearch postSearch = PostSearch.builder()
//                                            .page(1)
//                                            .size(10)
//                                            .build();


        //List<PostResponse> postResponseList = postService.postList(postSearch);

       // Assertions.assertEquals(1, postResponseList.size());
    }

    @Test
    @DisplayName("(정상) 게시글 삭제")
    @Transactional(readOnly = false)
    void deletePost(){
        //given
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

        postService.remove(post.getId());

        Post findPost = postRepository.findById(post.getId())
                .orElseThrow(PostNotFound::new);

        Assertions.assertEquals(true, findPost.isDeleteYn());
    }

    @Test
    @DisplayName("(정상) 게시글 제목, 내용 수정")
    @Transactional(readOnly = false)
    void updateTitleAndContentPost() throws Exception{

        //given
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

        //then
        PostEdit postEdit = PostEdit.builder()
                .title("수정된 테스트 제목")
                .cotent("수정된 테스트 내용")
                .build();

        postService.updatePost(post.getId(), postEdit);

        Post findPost = postRepository.findById(post.getId())
                .orElseThrow(PostNotFound::new);

        Assertions.assertEquals(postEdit.getTitle(), findPost.getTitle());
        Assertions.assertEquals(postEdit.getContent(), findPost.getContent());
    }

    @Test
    @DisplayName("(정상) 게시글 제목 수정")
    @Transactional(readOnly = false)
    void updatePost() throws Exception{

        //given
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

        //then
        PostEdit postEdit = PostEdit.builder()
                .title("수정된 테스트 제목")
                .build();

        postService.updatePost(post.getId(), postEdit);

        Post findPost = postRepository.findById(post.getId())
                .orElseThrow(PostNotFound::new);

        Assertions.assertEquals(postEdit.getTitle(), findPost.getTitle());
        Assertions.assertEquals(post.getContent(), findPost.getContent());
    }
}