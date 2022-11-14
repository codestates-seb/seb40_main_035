package com.codestates.mainproject.domain.industry.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class IndustryPostDto {
    private long industryId;
    @NotBlank
    private String name;
}
