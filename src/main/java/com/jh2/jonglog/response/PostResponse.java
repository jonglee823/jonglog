package com.jh2.jonglog.response;


import com.jh2.jonglog.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {

    private Long id;

    private String title;

    private String contents;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContent();
    }
}
