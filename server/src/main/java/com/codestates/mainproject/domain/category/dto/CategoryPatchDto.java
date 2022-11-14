package com.codestates.mainproject.domain.category.dto;

import com.codestates.mainproject.validator.NotSpace;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryPatchDto {
    private long categoryId;

    @NotSpace
    private String name;
}
