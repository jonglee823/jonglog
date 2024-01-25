package com.jh2.jonglog.repository;

import com.jh2.jonglog.domain.Post;
import com.jh2.jonglog.domain.QPost;
import com.jh2.jonglog.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository{

    public final JPAQueryFactory queryFactory;


    @Override
    public Post getPost(Long postId){
        return queryFactory
                .selectFrom(QPost.post)
                .where(QPost.post.deleteYn.eq(false).and(QPost.post.id.eq(postId)))
                .fetchOne();
    }

    @Override
    public List<Post> findAll() {
        return queryFactory
                .selectFrom(QPost.post)
                .where(QPost.post.deleteYn.eq(false))
                .orderBy(QPost.post.id.desc())
                .fetch();
    }


}
