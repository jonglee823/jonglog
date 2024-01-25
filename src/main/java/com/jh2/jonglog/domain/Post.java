package com.jh2.jonglog.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn
    private User user;

    @OneToMany(mappedBy ="post")
    private List<Comment> commentList = new ArrayList<>();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @CreatedDate
    private LocalDateTime createTime;

    @LastModifiedDate
    private LocalDateTime updateTime;

    private boolean deleteYn = false;

    @Builder
    public Post(String title, String content, User user, LocalDateTime createTime) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.createTime = createTime;
    }

    public Long getUserId(){
        return this.user.getId();
    }

    public void addUser(User user){
        this.user = user;
        user.getPostList().add(this);
    }

    public void addCategory(Category category){
        this.category = category;
        category.getPostList().add(this);
    }

    public void delete(){
        this.deleteYn = true;
    }
}