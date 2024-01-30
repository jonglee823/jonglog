package com.jh2.jonglog.controller;

import com.jh2.jonglog.request.PostEdit;
import com.jh2.jonglog.request.PostRequest;
import com.jh2.jonglog.request.PostSearch;
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

    /**
     * TODO : 게시글 등록, 삭제, 수정에 대한 권한체크는 공통으로 처리
     */

    /**
     * 게시글 전체 조회
     * @return
     */
    @GetMapping(value = "/posts")
    public List<PostResponse> getPostList(@Valid PostSearch postSearch){
        log.info(postSearch.toString());
        return postService.postList(postSearch);
    }

    /**
     * 게시글 등록
     * @param postRequest
     */
    @PostMapping(value = "/posts")
    public void createPost(@Valid @RequestBody PostRequest postRequest){
        postService.create(postRequest);
    }

    /**
     * 특정 게시글 조회
     * @param Long postId
     * @return PostResponse
     */
    @GetMapping(value ="/posts/{postId}")
    public PostResponse getPost(@PathVariable(name="postId") Long postId){
        return postService.getPost(postId);
    }

    /**
     * id 게시글 삭제
     * @param Long postId
     */
    @PostMapping(value = "/posts/delete/{postId}")
    public void deletePost(@PathVariable(name="postId") @RequestBody Long postId){
        postService.remove(postId);
    }

    /**
     * 게시글 수정
     * HTTP METHOD : PATCH
     * @param Long postId, PostEdit postEdit
     */
    @PatchMapping(value ="/posts/{postId}")
    public void updatePost(@PathVariable(name="postId") Long postId, @RequestBody @Valid PostEdit postEdit){
        postService.updatePost(postId, postEdit);
    }
}
