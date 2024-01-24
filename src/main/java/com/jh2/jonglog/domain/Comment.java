package com.jh2.jonglog.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @CreatedDate
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    @Builder
    public Comment(String name, String password, LocalDateTime createTime, LocalDateTime updateTime) {
        this.name = name;
        this.password = password;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public void addPost(Post post){
        this.post = post;
        post.getCommentList().add(this);
    }
}