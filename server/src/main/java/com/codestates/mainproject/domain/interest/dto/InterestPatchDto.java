package com.codestates.mainproject.domain.interest.dto;

import com.codestates.mainproject.validator.NotSpace;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InterestPatchDto {
    private long interestId;

    @NotSpace
    private String name;
}
