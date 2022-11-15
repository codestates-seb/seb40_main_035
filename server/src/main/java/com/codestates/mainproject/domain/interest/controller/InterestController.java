package com.codestates.mainproject.domain.interest.controller;

import com.codestates.mainproject.domain.hashtag.dto.HashtagPostDto;
import com.codestates.mainproject.domain.hashtag.dto.HashtagResponseDto;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import com.codestates.mainproject.domain.hashtag.mapper.HashtagMapper;
import com.codestates.mainproject.domain.hashtag.service.HashtagService;
import com.codestates.mainproject.domain.interest.dto.InterestPatchDto;
import com.codestates.mainproject.domain.interest.dto.InterestPostDto;
import com.codestates.mainproject.domain.interest.dto.InterestResponseDto;
import com.codestates.mainproject.domain.interest.entity.Interest;
import com.codestates.mainproject.domain.interest.mapper.InterestMapper;
import com.codestates.mainproject.domain.interest.service.InterestService;
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
@RequestMapping("/interests")
@Validated
@RequiredArgsConstructor
public class InterestController {
    private final InterestService service;
    private final InterestMapper mapper;

    @PostMapping
    public ResponseEntity postInterest(@Valid @RequestBody InterestPostDto postDto) {

        Interest interest = mapper.interestPostDtoToInterest(postDto);
        Interest createdInterest = service.createInterest(interest);
        InterestResponseDto responseDto = mapper.interestToInterestResponseDto(createdInterest);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{interest-id}")
    public ResponseEntity patchInterest(@PathVariable("interest-id") @Positive long interestId,
                                        @Valid @RequestBody InterestPatchDto patchDto) {
        patchDto.setInterestId(interestId);

        Interest interest = mapper.interestPatchDtoToInterest(patchDto);
        Interest updatedInterest = service.updateInterest(interest);
        InterestResponseDto responseDto = mapper.interestToInterestResponseDto(updatedInterest);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping("/{interest-id}")
    public ResponseEntity getInterest(@PathVariable("interest-id") @Positive long interestId) {

        Interest foundInterest = service.findInterest(interestId);
        InterestResponseDto responseDto = mapper.interestToInterestResponseDto(foundInterest);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getInterests() {

        List<Interest> interests = service.findInterests();
        List<InterestResponseDto> responseDtos = mapper.interestsToInterestResponseDtos(interests);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDtos), HttpStatus.OK);
    }

    @DeleteMapping("/{hashtag-id}")
    public ResponseEntity deleteInterest(@PathVariable("interest-id") @Positive long interestId) {

        service.deleteInterest(interestId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

