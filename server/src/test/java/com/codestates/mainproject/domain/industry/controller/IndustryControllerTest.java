package com.codestates.mainproject.domain.industry.controller;

import com.codestates.mainproject.domain.industry.dto.IndustryResponseDto;
import com.codestates.mainproject.domain.industry.entity.Industry;
import com.codestates.mainproject.domain.industry.dto.IndustryPostDto;
import com.codestates.mainproject.domain.industry.mapper.IndustryMapper;
import com.codestates.mainproject.domain.industry.repository.IndustryRepository;
import com.codestates.mainproject.domain.industry.service.IndustryService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
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

@WebMvcTest(value = IndustryController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class IndustryControllerTest {
    @MockBean
    private IndustryService industryService;

    @MockBean
    private IndustryMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    Gson gson;

    @Test
    void postIndustry() throws Exception {
        //given
        IndustryPostDto postDto = new IndustryPostDto(1L,"산업군1");
        String content = gson.toJson(postDto);
        IndustryResponseDto responseDto = new IndustryResponseDto(1L, "산업군1");

        given(mapper.industryPostDtoToIndustry(any(IndustryPostDto.class)))
                .willReturn(new Industry());

        given(industryService.createIndustry(any(Industry.class)))
                .willReturn(new Industry());

        given(mapper.industryToIndustryResponseDto(any(Industry.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                post("/industries")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value(postDto.getName()))
                .andDo(document("post-industry",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("industryId").type(JsonFieldType.NUMBER).description("산업군 식별자"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("산업군 이름")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.industryId").type(JsonFieldType.NUMBER).description("산업군 식별자"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("산업군 이름")
                                )
                        )
                ));

    }

    @Test
    void getIndustry() throws Exception {
        //given
        long industryId = 1L;

        IndustryResponseDto responseDto = new IndustryResponseDto(1L, "산업군1");

        Industry foundIndustry = new Industry();
        given(industryService.findIndustry(Mockito.anyLong()))
                .willReturn(foundIndustry);

        given(mapper.industryToIndustryResponseDto(any(Industry.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                get("/industries/{industry-id}", industryId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.industryId").value(industryId))
                .andDo(document("get-industry",
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("industry-id").description("산업군 식별자")),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.industryId").type(JsonFieldType.NUMBER).description("산업군 식별자"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("산업군 이름")
                                )
                        ))
                );
    }

    @Test
    void getIndustries() throws Exception {
        List<IndustryResponseDto> responseDtos = new ArrayList<>(List.of(
                new IndustryResponseDto(1L, "산업군1"),
                new IndustryResponseDto(2L, "산업군2"),
                new IndustryResponseDto(3L, "산업군3")
        ));

        given(industryService.findIndustries())
                .willReturn(new ArrayList<>());

        given(mapper.industriesToIndustryResponseDtos(Mockito.anyList()))
                .willReturn(responseDtos);

        //when
        ResultActions actions = mockMvc.perform(
                get("/industries")
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("get-industries",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].industryId").type(JsonFieldType.NUMBER).description("산업군 식별자"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("산업군 이름")
                                )
                        ))
                );
    }

    @Test
    void deleteIndustry() throws Exception{
        long industryId = 1L;

        //when
        ResultActions actions = mockMvc.perform(
                delete("/industries/{industry-id}", industryId)
        );

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-industry",
                                pathParameters(parameterWithName("industry-id").description("산업군 식별자"))
                        )
                );
    }
}
