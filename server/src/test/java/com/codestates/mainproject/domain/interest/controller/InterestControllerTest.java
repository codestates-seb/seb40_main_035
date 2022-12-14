package com.codestates.mainproject.domain.interest.controller;


import com.codestates.mainproject.domain.interest.dto.InterestPatchDto;
import com.codestates.mainproject.domain.interest.dto.InterestPostDto;
import com.codestates.mainproject.domain.interest.dto.InterestResponseDto;
import com.codestates.mainproject.domain.interest.entity.Interest;
import com.codestates.mainproject.domain.interest.mapper.InterestMapper;
import com.codestates.mainproject.domain.interest.service.InterestService;
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

@WebMvcTest(value = InterestController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, OAuth2ClientAutoConfiguration.class})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class InterestControllerTest {
    @MockBean
    private InterestService interestService;

    @MockBean
    private InterestMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    void postInterest() throws Exception {
        //given
        InterestPostDto postDto = new InterestPostDto("??????");
        String content = gson.toJson(postDto);
        InterestResponseDto responseDto = new InterestResponseDto(1L, "??????");

        given(mapper.interestPostDtoToInterest(Mockito.any(InterestPostDto.class)))
                .willReturn(new Interest());

        given(interestService.createInterest(Mockito.any(Interest.class)))
                .willReturn(new Interest());

        given(mapper.interestToInterestResponseDto(Mockito.any(Interest.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                post("/interests")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value(postDto.getName()))
                .andDo(document("post-interest",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("???????????? ??????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.interestId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("???????????? ??????")
                                )
                        )
                ));
    }

    @Test
    void patchInterest() throws Exception {
        long interestId = 1L;

        InterestPatchDto patchDto = new InterestPatchDto();
        patchDto.setName("?????????");

        String content = gson.toJson(patchDto);

        InterestResponseDto responseDto = new InterestResponseDto(interestId, "?????????");

        given(mapper.interestPatchDtoToInterest(Mockito.any(InterestPatchDto.class)))
                .willReturn(new Interest());

        given(interestService.updateInterest(Mockito.any(Interest.class)))
                .willReturn(new Interest());

        given(mapper.interestToInterestResponseDto(Mockito.any(Interest.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/interests/{interest-id}", interestId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.interestId").value(interestId))
                .andDo(document("patch-interest",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("interest-id").description("???????????? ?????????")),
                        requestFields(
                                List.of(
                                        fieldWithPath("interestId").type(JsonFieldType.NUMBER).description("???????????? ?????????").ignored(),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("???????????? ??????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.interestId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("???????????? ??????")
                                )
                        )
                ));
    }

    @Test
    void getInterest() throws Exception {
        //given
        long interestId = 1L;

        InterestResponseDto responseDto = new InterestResponseDto(1L, "??????");

        given(interestService.findInterest(Mockito.anyLong()))
                .willReturn(new Interest());

        given(mapper.interestToInterestResponseDto(Mockito.any(Interest.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                get("/interests/{interest-id}", interestId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.interestId").value(interestId))
                .andDo(document("get-interest",
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("interest-id").description("???????????? ?????????")),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.interestId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("???????????? ??????")
                                )
                        ))
                );
    }

    @Test
    void getInterests() throws Exception {
        List<InterestResponseDto> responseDtos = new ArrayList<>(List.of(
                new InterestResponseDto(1L, "??????"),
                new InterestResponseDto(2L, "?????????"),
                new InterestResponseDto(3L, "??????")
        ));

        given(interestService.findInterests())
                .willReturn(new ArrayList<>());

        given(mapper.interestsToInterestResponseDtos(Mockito.anyList()))
                .willReturn(responseDtos);

        //when
        ResultActions actions = mockMvc.perform(
                get("/interests")
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("get-interests",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                        fieldWithPath("data[].interestId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("???????????? ??????")
                                )
                        ))
                );
    }

    @Test
    void deleteInterest() throws Exception {
        long interestId = 1L;

        //when
        ResultActions actions = mockMvc.perform(
                delete("/interests/{interest-id}", interestId)
        );

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-interest",
                                pathParameters(parameterWithName("interest-id").description("???????????? ?????????"))
                        )
                );
    }
}