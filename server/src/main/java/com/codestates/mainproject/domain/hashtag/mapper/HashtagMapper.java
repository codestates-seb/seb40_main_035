package com.codestates.mainproject.domain.hashtag.mapper;

import com.codestates.mainproject.domain.hashtag.dto.HashtagPatchDto;
import com.codestates.mainproject.domain.hashtag.dto.HashtagPostDto;
import com.codestates.mainproject.domain.hashtag.dto.HashtagResponseDto;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
    Hashtag hashtagPostDtoToHashtag(HashtagPostDto postDto);

    Hashtag hashtagPatchDtoToHashtag(HashtagPatchDto patchDto);

    HashtagResponseDto hashtagToHashtagResponseDto(Hashtag hashtag);

    List<HashtagResponseDto> hashtagsToHashtagResponseDtos(List<Hashtag> hashtags);
}
