package com.jh2.jonglog.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostRequest {

    @NotBlank(message = "제목을 입력해 주세요.")
    @Length(min=2, max=250)
    private String title;

    @NotBlank(message = "내용을 입력해 주세요.")
    private String content;

//    @Length(min=6, max=20, message = "비밀번호는 6~20 자리로 입력해 주세요.")
//    private String password;

    private LocalDateTime createTime;

    @NotNull(message = "회원 ID가 NULL 입니다.")
    private Long userId;

    @Builder
    public PostRequest(String title, String contents, LocalDateTime createTime, Long userId) {
        this.title = title;
        this.content = contents;
        this.createTime = createTime;
        this.userId = userId;
    }
}
