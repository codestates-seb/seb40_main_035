package com.codestates.mainproject.domain.heart.controller;

import com.codestates.mainproject.domain.heart.dto.HeartPostDto;
import com.codestates.mainproject.domain.heart.dto.HeartResponseDto;
import com.codestates.mainproject.domain.heart.entity.Heart;
import com.codestates.mainproject.domain.heart.mapper.HeartMapper;
import com.codestates.mainproject.domain.heart.service.HeartService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = HeartController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class HeartControllerTest {
    @MockBean
    private HeartService heartService;
    @MockBean
    private HeartMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    void postHeart() throws Exception {
        //given
        HeartPostDto postDto = new HeartPostDto(1L, 1L);
        String content = gson.toJson(postDto);

        HeartResponseDto responseDto = new HeartResponseDto(1L, 1L, 1L);

        given(mapper.heartPostDtoToHeart(Mockito.any(HeartPostDto.class)))
                .willReturn(new Heart());

        given(heartService.createHeart(Mockito.any(Heart.class)))
                .willReturn(new Heart());

        given(mapper.heartToHeartResponseDto(Mockito.any(Heart.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/hearts")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.memberId").value(postDto.getMemberId()))
                .andExpect(jsonPath("$.data.articleId").value(postDto.getArticleId()))
                .andDo(document("post-heart",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("답변 식별자")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.heartId").type(JsonFieldType.NUMBER).description("좋아요 식별자"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.STRING).description("좋아요 작성 멤버 식별자"),
                                        fieldWithPath("data.articleId").type(JsonFieldType.NUMBER).description("좋아요 작성 게시글 식별자")
                                )
                        )
                ));
    }

    @Test
    void getHeart() throws Exception {
        //given
        long heartId = 1L;

        HeartResponseDto responseDto = new HeartResponseDto(1L,1L, 1L);

        given(heartService.findHeart(Mockito.anyLong()))
                .willReturn(new Heart());

        given(mapper.heartToHeartResponseDto(Mockito.any(Heart.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                get("/hearts/{heart-id}", heartId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.heartId").value(heartId))
                .andDo(document("get-heart",
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("heart-id").description("댓글 식별자")),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.heartId").type(JsonFieldType.NUMBER).description("좋아요 식별자"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.STRING).description("좋아요 작성 멤버 식별자"),
                                        fieldWithPath("data.articleId").type(JsonFieldType.NUMBER).description("좋아요 작성 게시글 식별자")
                                )
                        ))
                );
    }

    @Test
    void getHearts() throws Exception {
        //given
        List<HeartResponseDto> responseDtos = new ArrayList<>(List.of(
                new HeartResponseDto(1L, 1L, 1L),
                new HeartResponseDto(2L, 1L, 2L),
                new HeartResponseDto(3L, 1L, 3L)
        ));

        given(heartService.findHearts())
                .willReturn(new ArrayList<>());

        given(mapper.heartsToHeartResponseDtos(Mockito.anyList()))
                .willReturn(responseDtos);

        //when
        ResultActions actions = mockMvc.perform(
                get("/hearts")
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("get-hearts",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.heartId").type(JsonFieldType.NUMBER).description("좋아요 식별자"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.STRING).description("좋아요 작성 멤버 식별자"),
                                        fieldWithPath("data.articleId").type(JsonFieldType.NUMBER).description("좋아요 작성 게시글 식별자")
                                )
                        ))
                );
    }

    @Test
    void deleteHeart() throws Exception {
        //given
        long heartId = 1L;

        //when
        ResultActions actions = mockMvc.perform(
                delete("/hearts/{heart-id}", heartId)
        );

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-heart",
                                pathParameters(parameterWithName("heart-id").description("좋아요 식별자"))
                        )
                );
    }
}