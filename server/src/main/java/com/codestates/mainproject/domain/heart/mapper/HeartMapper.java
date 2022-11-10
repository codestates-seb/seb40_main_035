package com.codestates.mainproject.domain.heart.mapper;

import com.codestates.mainproject.domain.heart.dto.HeartPostDto;
import com.codestates.mainproject.domain.heart.dto.HeartResponseDto;
import com.codestates.mainproject.domain.heart.entity.Heart;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HeartMapper {
    Heart heartPostDtoToHeart(HeartPostDto postDto);

    HeartResponseDto heartToHeartResponseDto(Heart heart);

    List<HeartResponseDto> heartsToHeartResponseDtos(List<Heart> hearts);
}
