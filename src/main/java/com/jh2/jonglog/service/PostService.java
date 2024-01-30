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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    /**
     * 게시글 전체 조회
     * Post.deleteYn이 false인 값만 리턴
     * @return List<PostResponse>
     */
    public List<PostResponse> postList(PostSearch postSearch){
        return postRepository.findAll().stream()
                .map(post -> new PostResponse(post))
                .collect(Collectors.toList());
    }

    /**
     * 게시글 등록
     * @param postRequest
     */
    @Transactional(readOnly = false)
    public void create(PostRequest postRequest){

//        User user = userRepository.findById(postRequest.getUserId())
//                                    .orElseThrow(UserNotFound::new);
        User user = userRepository.findById(1L)
                                  .orElseThrow(UserNotFound::new);

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .user(user)
                .build();

        postRepository.save(post);
    }

    /**
     * 특정 게시글 리턴
     * Post.deleteYn이 false인 값만 리턴
     * @return List<PostResponse>
     */
    public PostResponse getPost(Long postId){
        Post findPost = Optional.ofNullable(postRepository.getPost(postId))
                .orElseThrow(PostNotFound::new);

        return new PostResponse(findPost);
    }

    /**
     * 게시글 삭제
     * @param postId
     */
    @Transactional(readOnly = false)
    public void remove(Long postId){
        Post findPost = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        findPost.delete();
    }

    /**
     * 게시글 수정
     * @param postId
     * @param postEdit
     */
    @Transactional(readOnly = false)
    public void updatePost(Long postId, PostEdit postEdit){
        Post findPost = postRepository.findById((postId))
                .orElseThrow(PostNotFound::new);

        findPost.update(postEdit);
    }
}
