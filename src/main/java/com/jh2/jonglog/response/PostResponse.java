package com.jh2.jonglog.response;


import com.jh2.jonglog.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {

    private Long id;

    private String title;

    private String content;

    private boolean deleteYn;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.deleteYn = post.isDeleteYn();
        this.createTime = post.getCreateTime();
        this.updateTime = post.getUpdateTime();
    }
}
