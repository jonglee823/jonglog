package com.jh2.jonglog.request;

import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
public class PostSearch {

    private String searchWord;

    private Integer page;

    @Max(value = 100, message = "page, size는 100을 초과할 수 없습니다.")
    private Integer size;

    public PostSearch() {
        page = 0;
        size = 10;
    }

    @Builder
    public PostSearch(String searchWord, Integer page, Integer size) {
        this.searchWord = searchWord;
        this.page = page;
        this.size = size;
    }
}
