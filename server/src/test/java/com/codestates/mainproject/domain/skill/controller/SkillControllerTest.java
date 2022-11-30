package com.codestates.mainproject.domain.skill.controller;

import com.codestates.mainproject.domain.skill.dto.SkillPatchDto;
import com.codestates.mainproject.domain.skill.dto.SkillPostDto;
import com.codestates.mainproject.domain.skill.dto.SkillResponseDto;
import com.codestates.mainproject.domain.skill.entity.Skill;
import com.codestates.mainproject.domain.skill.mapper.SkillMapper;
import com.codestates.mainproject.domain.skill.service.SkillService;
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

@WebMvcTest(value = SkillController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class SkillControllerTest {
    @MockBean
    private SkillService skillService;

    @MockBean
    private SkillMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    void postSkill() throws Exception {
        //given
        SkillPostDto postDto = new SkillPostDto("JAVA", Skill.SkillSort.BACKEND);
        String content = gson.toJson(postDto);
        SkillResponseDto responseDto = new SkillResponseDto(1L, "JAVA", Skill.SkillSort.BACKEND);

        given(mapper.skillPostDtoToSkill(Mockito.any(SkillPostDto.class)))
                .willReturn(new Skill());

        given(skillService.createSkill(Mockito.any(Skill.class)))
                .willReturn(new Skill());

        given(mapper.skillToSkillResponseDto(Mockito.any(Skill.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                post("/skills")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value(postDto.getName()))
                .andDo(document("post-skill",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("기술스택 이름"),
                                        fieldWithPath("skillSort").type(JsonFieldType.STRING).description("기술스택 종류")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("기술스택 이름"),
                                        fieldWithPath("data.skillSort").type(JsonFieldType.STRING).description("기술스택 종류")
                                )
                        )
                ));
    }

    @Test
    void patchSkill() throws Exception {
        long skillId = 1L;

        SkillPatchDto patchDto = new SkillPatchDto();
        patchDto.setName("Spring");

        String content = gson.toJson(patchDto);

        SkillResponseDto responseDto = new SkillResponseDto(skillId, "Spring", Skill.SkillSort.BACKEND);

        given(mapper.skillPatchDtoToSkill(Mockito.any(SkillPatchDto.class)))
                .willReturn(new Skill());

        given(skillService.updateSkill(Mockito.any(Skill.class)))
                .willReturn(new Skill());

        given(mapper.skillToSkillResponseDto(Mockito.any(Skill.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/skills/{skill-id}", skillId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.skillId").value(skillId))
                .andDo(document("patch-skill",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("skill-id").description("기술스택 식별자")),
                        requestFields(
                                List.of(
                                        fieldWithPath("skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자").ignored(),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("기술스택 이름").optional(),
                                        fieldWithPath("skillSort").type(JsonFieldType.STRING).description("기술스택 종류").optional()
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("기술스택 이름"),
                                        fieldWithPath("data.skillSort").type(JsonFieldType.STRING).description("기술스택 종류")
                                )
                        )
                ));
    }

    @Test
    void getSkill() throws Exception {
        //given
        long skillId = 1L;

        SkillResponseDto responseDto = new SkillResponseDto(1L, "JAVA", Skill.SkillSort.BACKEND);

        given(skillService.findSkill(Mockito.anyLong()))
                .willReturn(new Skill());

        given(mapper.skillToSkillResponseDto(Mockito.any(Skill.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                get("/skills/{skill-id}", skillId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.skillId").value(skillId))
                .andDo(document("get-skill",
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("skill-id").description("기술스택 식별자")),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("기술스택 이름"),
                                        fieldWithPath("data.skillSort").type(JsonFieldType.STRING).description("기술스택 종류")
                                )
                        ))
                );
    }

    @Test
    void getSkills() throws Exception {
        List<SkillResponseDto> responseDtos = new ArrayList<>(List.of(
                new SkillResponseDto(1L, "JAVA", Skill.SkillSort.BACKEND),
                new SkillResponseDto(2L, "Spring", Skill.SkillSort.BACKEND),
                new SkillResponseDto(3L, "Nodejs", Skill.SkillSort.BACKEND)
        ));

        given(skillService.findSkills())
                .willReturn(new ArrayList<>());

        given(mapper.skillsToSkillResponseDtos(Mockito.anyList()))
                .willReturn(responseDtos);

        //when
        ResultActions actions = mockMvc.perform(
                get("/skills")
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("get-skills",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("기술스택 이름"),
                                        fieldWithPath("data[].skillSort").type(JsonFieldType.STRING).description("기술스택 종류")
                                )
                        ))
                );
    }

    @Test
    void deleteSkill() throws Exception {
        long skillId = 1L;

        //when
        ResultActions actions = mockMvc.perform(
                delete("/skills/{skill-id}", skillId)
        );

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-skill",
                                pathParameters(parameterWithName("skill-id").description("기술스택 식별자"))
                        )
                );
    }
}