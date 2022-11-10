package com.codestates.mainproject.domain.heart.controller;

import com.codestates.mainproject.domain.heart.dto.HeartPostDto;
import com.codestates.mainproject.domain.heart.dto.HeartResponseDto;
import com.codestates.mainproject.domain.heart.entity.Heart;
import com.codestates.mainproject.domain.heart.mapper.HeartMapper;
import com.codestates.mainproject.domain.heart.repository.HeartRepository;
import com.codestates.mainproject.domain.heart.service.HeartService;
import com.codestates.mainproject.dto.SingleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/hearts")
@Validated
@RequiredArgsConstructor
public class HeartController {
    private final HeartService heartService;
    private final HeartMapper mapper;

    @PostMapping
    public ResponseEntity postHeart(HeartPostDto postDto) {

        Heart heart = mapper.heartPostDtoToHeart(postDto);
        Heart createdHeart = heartService.createHeart(heart);
        HeartResponseDto responseDto = mapper.heartToHeartResponseDto(createdHeart);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @GetMapping("/{heart-id}")
    public ResponseEntity getHeart(@PathVariable("heart-id") @Positive long heartId) {

        Heart foundHeart = heartService.findHeart(heartId);
        HeartResponseDto responseDto = mapper.heartToHeartResponseDto(foundHeart);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getHearts() {

        List<Heart> hearts = heartService.findHearts();
        List<HeartResponseDto> responseDtos = mapper.heartsToHeartResponseDtos(hearts);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDtos), HttpStatus.OK);
    }

    @DeleteMapping("/{heart-id}")
    public ResponseEntity deleteHeart(@PathVariable("heart-id") @Positive long heartId) {

        heartService.deleteHeart(heartId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
