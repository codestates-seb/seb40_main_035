package com.codestates.mainproject.domain.article.dto;

import com.codestates.mainproject.validator.NotSpace;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

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

    private Optional<@Min(0) Integer> backend  = Optional.empty();

    private Optional<@Min(0) Integer> frontend  = Optional.empty();


    public int getBackend() {return backend.orElse(-1);
    }

    public int getFrontend() {
        return frontend.orElse(-1);
    }
}
