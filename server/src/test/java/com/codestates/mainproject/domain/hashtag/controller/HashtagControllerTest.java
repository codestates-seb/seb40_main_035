package com.codestates.mainproject.domain.hashtag.controller;

import com.codestates.mainproject.domain.hashtag.dto.HashtagPostDto;
import com.codestates.mainproject.domain.hashtag.dto.HashtagResponseDto;
import com.codestates.mainproject.domain.hashtag.entity.Hashtag;
import com.codestates.mainproject.domain.hashtag.mapper.HashtagMapper;
import com.codestates.mainproject.domain.hashtag.service.HashtagService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = HashtagController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class HashtagControllerTest {
    @MockBean
    private HashtagService hashtagService;

    @MockBean
    private HashtagMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    void postHashtag() throws Exception {
        //given
        HashtagPostDto postDto = new HashtagPostDto("해시태그1");
        String content = gson.toJson(postDto);
        HashtagResponseDto responseDto = new HashtagResponseDto(1L, "해시태그1");

        given(mapper.hashtagPostDtoToHashtag(Mockito.any(HashtagPostDto.class)))
                .willReturn(new Hashtag());

        given(hashtagService.createHashtag(Mockito.any(Hashtag.class)))
                .willReturn(new Hashtag());

        given(mapper.hashtagToHashtagResponseDto(Mockito.any(Hashtag.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                post("/hashtags")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value(postDto.getName()))
                .andDo(document("post-hashtag",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("태그 이름")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.hashtagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("태그 이름")
                                )
                        )
                ));
    }

//    @Test
//    void patchHashtag() throws Exception {
//        long hashtagId = 1L;
//
//        HashtagPatchDto patchDto = new HashtagPatchDto();
//        patchDto.setName("React");
//
//        String content = gson.toJson(patchDto);
//
//        HashtagResponseDto responseDto = new HashtagResponseDto(hashtagId, "해시태그2");
//
//        given(mapper.hashtagPatchDtoToHashtag(Mockito.any(HashtagPatchDto.class)))
//                .willReturn(new Hashtag());
//
//        given(hashtagService.updateHashtag(Mockito.any(Hashtag.class)))
//                .willReturn(new Hashtag());
//
//        given(mapper.hashtagToHashtagResponseDto(Mockito.any(Hashtag.class)))
//                .willReturn(responseDto);
//
//        //when
//        ResultActions actions = mockMvc.perform(
//                patch("/hashtags/{hashtag-id}", hashtagId)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(content)
//        );
//
//        //then
//        actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.hashtagId").value(hashtagId))
//                .andExpect(jsonPath("$.data.name").value(patchDto.getName()))
//                .andDo(document("patch-hashtag",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
//                        pathParameters(parameterWithName("hashtag-id").description("태그 식별자")),
//                        requestFields(
//                                List.of(
//                                        fieldWithPath("hashtagId").type(JsonFieldType.NUMBER).description("태그 식별자").ignored(),
//                                        fieldWithPath("name").type(JsonFieldType.STRING).description("태그 이름")
//                                )
//                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
//                                        fieldWithPath("data.hashtagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
//                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("태그 이름")
//                                )
//                        )
//                ));
//    }

    @Test
    void getHashtag() throws Exception {
        //given
        long hashtagId = 1L;

        HashtagResponseDto responseDto = new HashtagResponseDto(1L, "해시태그1");

        given(hashtagService.findHashtag(Mockito.anyLong()))
                .willReturn(new Hashtag());

        given(mapper.hashtagToHashtagResponseDto(Mockito.any(Hashtag.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                get("/hashtags/{hashtag-id}", hashtagId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.hashtagId").value(hashtagId))
                .andDo(document("get-hashtag",
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("hashtag-id").description("태그 식별자")),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.hashtagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("태그 이름")
                                )
                        ))
                );
    }

    @Test
    void getHashtags() throws Exception {
        List<HashtagResponseDto> responseDtos = new ArrayList<>(List.of(
                new HashtagResponseDto(1L, "해시태그1"),
                new HashtagResponseDto(2L, "해시태그2"),
                new HashtagResponseDto(3L, "해시태그3")
        ));

        given(hashtagService.findHashtags())
                .willReturn(new ArrayList<>());

        given(mapper.hashtagsToHashtagResponseDtos(Mockito.anyList()))
                .willReturn(responseDtos);

        //when
        ResultActions actions = mockMvc.perform(
                get("/hashtags")
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("get-hashtags",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].hashtagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("태그 이름")
                                )
                        ))
                );
    }

    @Test
    void deleteHashtag() throws Exception {
        long hashtagId = 1L;

        //when
        ResultActions actions = mockMvc.perform(
                delete("/hashtags/{hashtag-id}", hashtagId)
        );

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-hashtag",
                                pathParameters(parameterWithName("hashtag-id").description("태그 식별자"))
                        )
                );
    }
}