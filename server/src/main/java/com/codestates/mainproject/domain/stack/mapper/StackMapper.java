package com.codestates.mainproject.domain.stack.mapper;


import com.codestates.mainproject.domain.stack.dto.StackPostDto;
import com.codestates.mainproject.domain.stack.dto.StackResponseDto;
import com.codestates.mainproject.domain.stack.entity.Stack;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StackMapper {
    Stack stackPostDtoToStack(StackPostDto postDto);

    StackResponseDto stackToStackResponseDto(Stack stack);

    List<StackResponseDto> stacksToStackResponseDtos(List<Stack> stacks);
}
