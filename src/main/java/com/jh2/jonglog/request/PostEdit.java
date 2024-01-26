package com.jh2.jonglog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostEdit {

    /**
     * 게시글 수정에 대한 고민
     * 1안) 수정될 파라미터만 요청
     *  - 중복으로 데이터 요청안해도 되니까, 부하가 줄지 않을까?
     *  - 그럼 valid 누락 여부 체크 어떻게 하지?
     * 2안) 전체 데이터 요청
     *  - 전체 param에 대한 valid check 가능..
     */

    @NotBlank(message = "제목을 입력해주세요.")
    private final String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private final String content;

    @Builder
    public PostEdit(String title, String cotent) {
        this.title = title;
        this.content = cotent;
    }
}
