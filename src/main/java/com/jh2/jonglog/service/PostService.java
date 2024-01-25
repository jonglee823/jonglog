package com.jh2.jonglog.service;

import com.jh2.jonglog.domain.Post;
import com.jh2.jonglog.domain.User;
import com.jh2.jonglog.exception.PostNotFound;
import com.jh2.jonglog.exception.UserNotFound;
import com.jh2.jonglog.repository.PostRepository;
import com.jh2.jonglog.repository.UserRepository;
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
     * 게시글 전체 리턴
     * @return List<PostResponse>
     */
    public List<PostResponse> postList(){
        return postRepository.findAll().stream()
                .map(post -> new PostResponse(post))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = false)
    public void create(PostRequest postRequest){

        User user = userRepository.findById(postRequest.getUserId())
                                    .orElseThrow(UserNotFound::new);

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContents())
                .user(user)
                .build();

        postRepository.save(post);
    }

    public PostResponse getPost(Long postId){
        Post findPost = Optional.ofNullable(postRepository.getPost(postId))
                .orElseThrow(PostNotFound::new);

        return new PostResponse(findPost);
    }

    @Transactional(readOnly = false)
    public void remove(Long postId){
        Post findPost = postRepository.findById(postId)
                .orElseThrow(PostNotFound::new);

        findPost.delete();
    }

}
