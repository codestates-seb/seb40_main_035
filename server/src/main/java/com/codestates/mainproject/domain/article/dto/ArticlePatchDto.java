package com.codestates.mainproject.domain.article.dto;

import com.codestates.mainproject.validator.NotSpace;
import com.codestates.mainproject.validator.NotSpaceInteger;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class ArticlePatchDto {
    private long articleId;

    @NotSpace
    private String title;

    @NotSpace
    private String body;

    private Boolean isCompleted;

    @NotSpace
    private String startDay;

    @NotSpace
    private String endDay;

    @NotSpaceInteger
    private Integer backend;

    @NotSpaceInteger
    private Integer frontend;

    public int getBackend() {
        if (backend == null) return -1;
        else return backend;
    }

    public int getFrontend() {
        if (frontend == null) return -1;
        else return frontend;
    }

    @Nullable
    private List<@Valid HeartDto> hearts;

    @Nullable
    private List<@Valid ArticleHashtagDto> articleHashtags;
}
