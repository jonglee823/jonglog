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
public class CommentRequest {

    @NotBlank(message = "제목을 입력해 주세요.")
    @Length(min=2, max=250)
    private String title;

    @NotBlank(message = "내용을 입력해 주세요.")
    private String contents;

//    @Length(min=6, max=20, message = "비밀번호는 6~20 자리로 입력해 주세요.")
//    private String password;

    private LocalDateTime createTime;

    @NotBlank(message = "닉네임을 2~10자 입력해 주세요.")
    @Length(min=2, max=10)
    private String name;

    @NotNull(message = "회원 ID가 NULL 입니다.")
    private Long userId;

    @NotBlank(message = "비밀번호는 6 ~ 20자리로 입력해주세요.")
    private String password;

    @Builder

    public CommentRequest(String title, String contents, String password, LocalDateTime createTime, String name, Long userId) {
        this.title = title;
        this.contents = contents;
        this.password = password;
        this.createTime = createTime;
        this.name = name;
        this.userId = userId;
    }
}
