package com.codestates.mainproject.domain.stack.controller;

import com.codestates.mainproject.domain.stack.dto.StackPostDto;
import com.codestates.mainproject.domain.stack.dto.StackResponseDto;
import com.codestates.mainproject.domain.stack.entity.Stack;
import com.codestates.mainproject.domain.stack.mapper.StackMapper;
import com.codestates.mainproject.domain.stack.service.StackService;
import com.codestates.mainproject.dto.SingleResponseDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/stacks")
@Validated
@RequiredArgsConstructor
public class StackController {
    private final StackService stackService;
    private final StackMapper mapper;

    @PostMapping
    public ResponseEntity postStack(@Valid @RequestBody StackPostDto postDto) {

        Stack stack = mapper.stackPostDtoToStack(postDto);
        Stack createdStack = stackService.createStack(stack);
        StackResponseDto responseDto = mapper.stackToStackResponseDto(createdStack);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.CREATED);
    }

    @GetMapping("/{stack-id}")
    public ResponseEntity getStack(@PathVariable("stack-id") @Positive long stackId) {

        Stack foundStack = stackService.findStack(stackId);
        StackResponseDto responseDto = mapper.stackToStackResponseDto(foundStack);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getStacks() {

        List<Stack> stacks = stackService.findStacks();
        List<StackResponseDto> responseDtos = mapper.stacksToStackResponseDtos(stacks);

        return new ResponseEntity<>(new SingleResponseDto<>(responseDtos), HttpStatus.OK);
    }

    @DeleteMapping("/{stack-id}")
    public ResponseEntity deleteStack(@PathVariable("stack-id") @Positive long stackId) {

        stackService.deleteStack(stackId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

