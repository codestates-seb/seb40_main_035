package com.codestates.mainproject.domain.answer.controller;

import com.codestates.mainproject.domain.answer.dto.AnswerPatchDto;
import com.codestates.mainproject.domain.answer.dto.AnswerPostDto;
import com.codestates.mainproject.domain.answer.dto.AnswerResponseDto;
import com.codestates.mainproject.domain.answer.entity.Answer;
import com.codestates.mainproject.domain.answer.mapper.AnswerMapper;
import com.codestates.mainproject.domain.answer.service.AnswerService;
import com.codestates.mainproject.domain.comment.dto.CommentResponseDto;
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

import java.time.LocalDateTime;
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

@WebMvcTest(value = AnswerController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, OAuth2ClientAutoConfiguration.class})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class AnswerControllerTest {
    @MockBean
    private AnswerService answerService;

    @MockBean
    private AnswerMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;


    @Test
    void postAnswer() throws Exception {
        //given
        AnswerPostDto answerPostDto = new AnswerPostDto(1L, 1L, "??????1");
        String content = gson.toJson(answerPostDto);
        AnswerResponseDto ResponseDto =
                new AnswerResponseDto(1L, "??????1", 1L,"?????????", 1L,
                        LocalDateTime.now(), LocalDateTime.now(), List.of(new CommentResponseDto(
                                1L, "??????1", 2L, "?????????",1L,
                        LocalDateTime.now(), LocalDateTime.now()),
                        new CommentResponseDto(2L, "??????2", 1L, "?????????",
                                1L,LocalDateTime.now(), LocalDateTime.now())));

        given(mapper.answerPostDtoToAnswer(Mockito.any(AnswerPostDto.class)))
                .willReturn(new Answer());
        given(answerService.createAnswer(Mockito.any(Answer.class)))
                .willReturn(new Answer());
        given(mapper.answerToAnswerResponseDto(Mockito.any(Answer.class)))
                .willReturn(ResponseDto);

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/answers")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.articleId").value(answerPostDto.getArticleId()))
                .andExpect(jsonPath("$.data.memberId").value(answerPostDto.getMemberId()))
                .andExpect(jsonPath("$.data.body").value(answerPostDto.getBody()))
                .andDo(document("post-answer",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("articleId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("?????? ??????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????????"),
                                        fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.articleId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),

                                        fieldWithPath("data.comments").type(JsonFieldType.ARRAY).description("?????? ?????? ??????"),
                                        fieldWithPath("data.comments[].commentId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.comments[].body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.comments[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                       fieldWithPath("data.comments[].memberName").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("data.comments[].answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.comments[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.comments[].modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        )
                ));
    }

    @Test
    void patchAnswer() throws Exception {
        //given
        long answerId = 1L;
        AnswerPatchDto patchDto = new AnswerPatchDto();
        patchDto.setBody("??????2");
        String content = gson.toJson(patchDto);

        AnswerResponseDto responseDto = new AnswerResponseDto(1L, "??????2", 1L,"?????????",
                1L, LocalDateTime.now(), LocalDateTime.now(), List.of(
                        new CommentResponseDto(1L, "??????1", 2L, "?????????",1L,
                        LocalDateTime.now(), LocalDateTime.now()),
                        new CommentResponseDto(2L, "??????2", 1L, "?????????",
                        1L,LocalDateTime.now(), LocalDateTime.now())));

        given(mapper.answerPatchDtoToAnswer(Mockito.any(AnswerPatchDto.class)))
                .willReturn(new Answer());
        given(answerService.updateAnswer(Mockito.any(Answer.class)))
                .willReturn(new Answer());
        given(mapper.answerToAnswerResponseDto(Mockito.any(Answer.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/answers/{answer-id}", answerId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.answerId").value(answerId))
                .andExpect(jsonPath("$.data.body").value(patchDto.getBody()))
                .andDo(document("patch-answer",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("answer-id").description("?????? ?????????")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("?????? ?????????").ignored(),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("?????? ??????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????????"),
                                        fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.articleId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),

                                        fieldWithPath("data.comments").type(JsonFieldType.ARRAY).description("?????? ?????? ??????"),
                                        fieldWithPath("data.comments[].commentId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.comments[].body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.comments[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                        fieldWithPath("data.comments[].memberName").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("data.comments[].answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.comments[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.comments[].modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        )
                ));
    }

    @Test
    void getAnswer() throws Exception {
        //given
        long answerId = 1L;

        AnswerResponseDto responseDto =
                new AnswerResponseDto(1L, "??????1", 1L,"?????????", 1L,
                        LocalDateTime.now(), LocalDateTime.now(), List.of(new CommentResponseDto(
                                1L, "??????1", 2L, "?????????",1L,
                                LocalDateTime.now(), LocalDateTime.now()),
                        new CommentResponseDto(2L, "??????2", 1L, "?????????",
                                1L,LocalDateTime.now(), LocalDateTime.now())));

        given(answerService.findAnswer(Mockito.anyLong()))
                .willReturn(new Answer());
        given(mapper.answerToAnswerResponseDto(Mockito.any(Answer.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/answers/{answer-id}", answerId)
                                .accept(MediaType.APPLICATION_JSON)
                );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.answerId").value(answerId))
                .andDo(document("get-answer",
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("answer-id").description("?????? ?????????")),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????????"),
                                        fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.articleId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),

                                        fieldWithPath("data.comments").type(JsonFieldType.ARRAY).description("?????? ?????? ??????"),
                                        fieldWithPath("data.comments[].commentId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.comments[].body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.comments[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                        fieldWithPath("data.comments[].memberName").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("data.comments[].answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.comments[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.comments[].modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        )
                ));
    }

    @Test
    void getAnswers() throws Exception {
        //given
        List<Answer> answers = List.of(new Answer(), new Answer(), new Answer());

        given(answerService.findAnswers())
                .willReturn(answers);

        given(mapper.answersToAnswerResponseDtos(Mockito.anyList()))
                .willReturn(List.of(
                        new AnswerResponseDto(1L, "??????1", 1L,"?????????",
                                1L, LocalDateTime.now(), LocalDateTime.now(), List.of(
                                new CommentResponseDto(1L, "??????1", 2L, "?????????",1L,
                                        LocalDateTime.now(), LocalDateTime.now()),
                                new CommentResponseDto(2L, "??????2", 3L, "?????????",
                                        1L,LocalDateTime.now(), LocalDateTime.now()))),
                        new AnswerResponseDto(2L, "??????2", 2L,"?????????",
                                1L, LocalDateTime.now(), LocalDateTime.now(), List.of(
                                new CommentResponseDto(3L, "??????3", 1L, "?????????",2L,
                                        LocalDateTime.now(), LocalDateTime.now()),
                                new CommentResponseDto(4L, "??????4", 3L, "?????????",
                                        2L,LocalDateTime.now(), LocalDateTime.now()))),
                        new AnswerResponseDto(3L, "??????3", 3L,"?????????",
                                1L, LocalDateTime.now(), LocalDateTime.now(), List.of(
                                new CommentResponseDto(5L, "??????5", 1L, "?????????",3L,
                                        LocalDateTime.now(), LocalDateTime.now()),
                                new CommentResponseDto(6L, "??????6", 2L, "?????????",
                                        3L,LocalDateTime.now(), LocalDateTime.now())))
                ));

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/answers")
                                .accept(MediaType.APPLICATION_JSON)
                );

        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("get-answers",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                        fieldWithPath("data[].answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data[].body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????????"),
                                        fieldWithPath("data[].memberName").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data[].articleId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),

                                        fieldWithPath("data[].comments").type(JsonFieldType.ARRAY).description("?????? ?????? ??????"),
                                        fieldWithPath("data[].comments[].commentId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data[].comments[].body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data[].comments[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                        fieldWithPath("data[].comments[].memberName").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("data[].comments[].answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data[].comments[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data[].comments[].modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        )
                ));
    }

    @Test
    void deleteAnswer() throws Exception {
        //given
        long answerId = 1L;

        //when
        ResultActions actions =
                mockMvc.perform(
                        delete("/answers/{answer-id}", answerId)
                );

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-answer",
                        pathParameters(parameterWithName("answer-id").description("?????? ?????????"))
                ));
    }
}