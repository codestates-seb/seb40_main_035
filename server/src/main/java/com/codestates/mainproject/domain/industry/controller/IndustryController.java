package com.codestates.mainproject.domain.industry.controller;

import com.codestates.mainproject.domain.industry.dto.IndustryPostDto;
import com.codestates.mainproject.domain.industry.dto.IndustryResponseDto;
import com.codestates.mainproject.domain.industry.entity.Industry;
import com.codestates.mainproject.domain.industry.mapper.IndustryMapper;
import com.codestates.mainproject.domain.industry.service.IndustryService;
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
@RequestMapping("/industries")
@Validated
@RequiredArgsConstructor
public class IndustryController {
    private final IndustryService industryService;
    private final IndustryMapper mapper;

    @PostMapping
    public ResponseEntity postIndustry(@Valid @RequestBody IndustryPostDto postDto) {

        Industry industry = mapper.industryPostDtoToIndustry(postDto);
        Industry createdIndustry = industryService.createIndustry(industry);
        IndustryResponseDto responseDto = mapper.industryToIndustryResponseDto(createdIndustry);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @GetMapping("/{industry-id}")
    public ResponseEntity getIndustry(@PathVariable("industry-id") @Positive long industryId) {

        Industry foundIndustry = industryService.findIndustry(industryId);
        IndustryResponseDto responseDto = mapper.industryToIndustryResponseDto(foundIndustry);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getIndustries() {

        List<Industry> industries = industryService.findIndustries();
        List<IndustryResponseDto> responseDtos = mapper.industriesToIndustryResponseDtos(industries);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDtos), HttpStatus.OK);
    }

    @DeleteMapping("/{industry-id}")
    public ResponseEntity deleteIndustry(@PathVariable("industry-id") @Positive long industryId) {

        industryService.deleteIndustry(industryId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

