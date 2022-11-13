package com.codestates.mainproject.domain.industry.mapper;


import com.codestates.mainproject.domain.industry.dto.IndustryPostDto;
import com.codestates.mainproject.domain.industry.dto.IndustryResponseDto;
import com.codestates.mainproject.domain.industry.entity.Industry;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IndustryMapper {
    Industry industryPostDtoToIndustry(IndustryPostDto postDto);

    IndustryResponseDto industryToIndustryResponseDto(Industry industry);

    List<IndustryResponseDto> industriesToIndustryResponseDtos(List<Industry> industries);
}
