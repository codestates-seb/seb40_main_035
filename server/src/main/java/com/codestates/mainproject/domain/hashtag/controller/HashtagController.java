package com.codestates.mainproject.domain.hashtag.controller;

import com.codestates.mainproject.domain.hashtag.dto.HashtagPatchDto;
import com.codestates.mainproject.domain.hashtag.dto.HashtagPostDto;
import com.codestates.mainproject.domain.hashtag.dto.HashtagResponseDto;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import com.codestates.mainproject.domain.hashtag.mapper.HashtagMapper;
import com.codestates.mainproject.domain.hashtag.service.HashtagService;
import com.codestates.mainproject.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/hashtags")
@Validated
@RequiredArgsConstructor
public class HashtagController {
    private final HashtagService hashtagService;
    private final HashtagMapper mapper;

    @PostMapping
    public ResponseEntity postHashtag(@Valid @RequestBody HashtagPostDto postDto) {

        Hashtag hashtag = mapper.hashtagPostDtoToHashtag(postDto);
        Hashtag createdHashtag = hashtagService.createHashtag(hashtag);
        HashtagResponseDto responseDto = mapper.hashtagToHashtagResponseDto(createdHashtag);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{hashtag-id}")
    public ResponseEntity patchHashtag(@PathVariable("hashtag-id") @Positive long hashtagId,
                                       @Valid @RequestBody HashtagPatchDto patchDto) {
        patchDto.setHashtagId(hashtagId);

        Hashtag hashtag = mapper.hashtagPatchDtoToHashtag(patchDto);
        Hashtag updatedHashtag = hashtagService.updateHashtag(hashtag);
        HashtagResponseDto responseDto = mapper.hashtagToHashtagResponseDto(updatedHashtag);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping("/{hashtag-id}")
    public ResponseEntity getHashtag(@PathVariable("hashtag-id") @Positive long hashtagId) {

        Hashtag findHashtag = hashtagService.findHashtag(hashtagId);
        HashtagResponseDto responseDto = mapper.hashtagToHashtagResponseDto(findHashtag);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getHashtags() {

        List<Hashtag> hashtags = hashtagService.findHashtags();
        List<HashtagResponseDto> responseDtos = mapper.hashtagsToHashtagResponseDtos(hashtags);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDtos), HttpStatus.OK);
    }

    @DeleteMapping("/{hashtag-id}")
    public ResponseEntity deleteHashtag(@PathVariable("hashtag-id") @Positive long hashtagId) {

        hashtagService.deleteHashtag(hashtagId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

