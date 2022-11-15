package com.codestates.mainproject.domain.interest.mapper;


import com.codestates.mainproject.domain.interest.dto.InterestPatchDto;
import com.codestates.mainproject.domain.interest.dto.InterestPostDto;
import com.codestates.mainproject.domain.interest.dto.InterestResponseDto;
import com.codestates.mainproject.domain.interest.entity.Interest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InterestMapper {
    Interest interestPostDtoToInterest(InterestPostDto postDto);

    Interest interestPatchDtoToInterest(InterestPatchDto patchDto);

    InterestResponseDto interestToInterestResponseDto(Interest interest);

    List<InterestResponseDto> interestsToInterestResponseDtos(List<Interest> interests);
}
