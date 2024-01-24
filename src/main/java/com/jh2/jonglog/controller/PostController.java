package com.jh2.jonglog.controller;

import com.jh2.jonglog.request.PostRequest;
import com.jh2.jonglog.response.PostResponse;
import com.jh2.jonglog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping(value = "/posts")
    public List<PostResponse> getPostList(){
        return postService.postList();
    }

    @PostMapping(value = "/posts")
    public void createPost(@Valid @RequestBody PostRequest postRequest){
        postService.create(postRequest);
    }

    @GetMapping(value ="/posts/{postId}")
    public PostResponse getPost(@PathVariable(name="postId") Long postId){
        return postService.getPost(postId);
    }

    /**
     * id 게시글 삭제
     * TODO USER 정보 넘겨서 본인글 확인 로직 추가
     */
    @PostMapping(value = "/posts/{postId}")
    public void deletePost(@PathVariable(name="postId") @RequestBody Long postId){
        postService.remove(postId);
    }

}
