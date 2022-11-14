package com.codestates.mainproject.domain.stack.controller;

import com.codestates.mainproject.domain.stack.dto.StackPostDto;
import com.codestates.mainproject.domain.stack.dto.StackResponseDto;
import com.codestates.mainproject.domain.stack.entity.Stack;
import com.codestates.mainproject.domain.stack.mapper.StackMapper;
import com.codestates.mainproject.domain.stack.service.StackService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = StackController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class StackControllerTest {
    @MockBean
    private StackService stackService;

    @MockBean
    private StackMapper mapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    Gson gson;

    @Test
    void postStack() throws Exception {
        //given
        StackPostDto postDto = new StackPostDto(1L,"기술스택1");
        String content = gson.toJson(postDto);
        StackResponseDto responseDto = new StackResponseDto(1L, "기술스택1");

        given(mapper.stackPostDtoToStack(Mockito.any(StackPostDto.class)))
                .willReturn(new Stack());

        given(stackService.createStack(Mockito.any(Stack.class)))
                .willReturn(new Stack());

        given(mapper.stackToStackResponseDto(Mockito.any(Stack.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                post("/stacks")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value(postDto.getName()))
                .andDo(document("post-stack",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("stackId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("기술스택 이름")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.stackId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("기술스택 이름")
                                )
                        )
                ));
    }

    @Test
    void getStack() throws Exception {
        //given
        long stackId = 1L;

        StackResponseDto responseDto = new StackResponseDto(1L, "기술스택1");

        given(stackService.findStack(Mockito.anyLong()))
                .willReturn(new Stack());

        given(mapper.stackToStackResponseDto(Mockito.any(Stack.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                get("/stacks/{stack-id}", stackId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.stackId").value(stackId))
                .andDo(document("get-stack",
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("stack-id").description("기술스택 식별자")),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.stackId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("기술스택 이름")
                                )
                        ))
                );
    }

    @Test
    void getStacks() throws Exception {
        List<StackResponseDto> responseDtos = new ArrayList<>(List.of(
                new StackResponseDto(1L, "기술스택1"),
                new StackResponseDto(2L, "기술스택2"),
                new StackResponseDto(3L, "기술스택3")
        ));

        given(stackService.findStacks())
                .willReturn(new ArrayList<>());

        given(mapper.stacksToStackResponseDtos(Mockito.anyList()))
                .willReturn(responseDtos);

        //when
        ResultActions actions = mockMvc.perform(
                get("/stacks")
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("get-stacks",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].stackId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("기술스택 이름")
                                )
                        ))
                );
    }

    @Test
    void deleteStack() throws Exception{
        long stackId = 1L;

        //when
        ResultActions actions = mockMvc.perform(
                delete("/stacks/{stack-id}", stackId)
        );

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-stack",
                                pathParameters(parameterWithName("stack-id").description("기술스택 식별자"))
                        )
                );
    }
}