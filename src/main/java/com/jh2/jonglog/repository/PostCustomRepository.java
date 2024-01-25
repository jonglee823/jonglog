package com.jh2.jonglog.repository;

import com.jh2.jonglog.domain.Post;
import com.jh2.jonglog.request.PostSearch;

import java.util.List;

public interface PostCustomRepository {
    public Post getPost(Long postId);

    public List<Post> findAll();

}
