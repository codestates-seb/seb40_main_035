package com.codestates.mainproject.domain.comment.controller;

import com.codestates.mainproject.domain.comment.dto.CommentPatchDto;
import com.codestates.mainproject.domain.comment.dto.CommentPostDto;
import com.codestates.mainproject.domain.comment.dto.CommentResponseDto;
import com.codestates.mainproject.domain.comment.entity.Comment;
import com.codestates.mainproject.domain.comment.mapper.CommentMapper;
import com.codestates.mainproject.domain.comment.service.CommentService;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CommentController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, OAuth2ClientAutoConfiguration.class})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class CommentControllerTest {
    @MockBean
    private CommentService commentService;

    @MockBean
    private CommentMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    void postComment() throws Exception {
        //given
        CommentPostDto postDto = new CommentPostDto(1L, 1L,"??????1");
        String content = gson.toJson(postDto);

        CommentResponseDto responseDto = new CommentResponseDto(1L, "??????1",1L, "?????????",
                1L,LocalDateTime.now(), LocalDateTime.now());

        given(mapper.commentPostDtoToComment(Mockito.any(CommentPostDto.class)))
                .willReturn(new Comment());

        given(commentService.createComment(Mockito.any(Comment.class)))
                .willReturn(new Comment());

        given(mapper.commentToCommentResponseDto(Mockito.any(Comment.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/comments")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.body").value(postDto.getBody()))
                .andDo(document("post-comment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("?????? ??????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.commentId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        )
                ));
    }

    @Test
    void patchComment() throws Exception{
        //given
        LocalDateTime time = LocalDateTime.now();
        long commentId = 1L;

        CommentPatchDto patchDto = new CommentPatchDto();
        patchDto.setBody("??????2");

        String content = gson.toJson(patchDto);

        CommentResponseDto responseDto = new CommentResponseDto(1L, "??????2",1L, "?????????",
                 1L,LocalDateTime.now(), LocalDateTime.now());

        given(mapper.commentPatchDtoToComment(Mockito.any(CommentPatchDto.class)))
                .willReturn(new Comment());

        given(commentService.updateComment(Mockito.any(Comment.class)))
                .willReturn(new Comment());

        given(mapper.commentToCommentResponseDto(Mockito.any(Comment.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/comments/{comment-id}", commentId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.commentId").value(commentId))
                .andExpect(jsonPath("$.data.body").value(patchDto.getBody()))
                .andDo(document("patch-comment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("comment-id").description("?????? ?????????")),
                        requestFields(
                                List.of(
                                        fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("?????? ?????????").ignored(),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("?????? ??????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.commentId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        ))
                );
    }

    @Test
    void getComment() throws Exception{
        //given
        long commentId = 1L;

        CommentResponseDto responseDto = new CommentResponseDto(1L, "??????1", 1L,"?????????",
                1L,LocalDateTime.now(), LocalDateTime.now());

        given(commentService.findComment(Mockito.anyLong()))
                .willReturn(new Comment());

        given(mapper.commentToCommentResponseDto(Mockito.any(Comment.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                get("/comments/{comment-id}", commentId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.commentId").value(commentId))
                .andDo(document("get-comment",
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("comment-id").description("?????? ?????????")),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.commentId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        ))
                );
    }

    @Test
    void getComments() throws Exception {
        //given
        List<CommentResponseDto> responseDtos = new ArrayList<>(List.of(
                new CommentResponseDto(1L, "??????1", 1L,"?????????",
                        1L,LocalDateTime.now(), LocalDateTime.now()),
                new CommentResponseDto(2L, "??????2", 2L,"?????????",
                        1L,LocalDateTime.now(), LocalDateTime.now()),
                new CommentResponseDto(3L, "??????3", 3L,"?????????",
                        1L, LocalDateTime.now(), LocalDateTime.now())
        ));

        given(commentService.findComments())
                .willReturn(new ArrayList<>());

        given(mapper.commentsToCommentResponseDtos(Mockito.anyList()))
                .willReturn(responseDtos);

        //when
        ResultActions actions = mockMvc.perform(
                get("/comments")
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("get-comments",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                        fieldWithPath("data[].commentId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data[].body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data[].memberName").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("data[].answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        ))
                );
    }

    @Test
    void deleteComment() throws Exception{
        //given
        long commentId = 1L;

        //when
        ResultActions actions = mockMvc.perform(
                delete("/comments/{comment-id}", commentId)
        );

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-comment",
                                pathParameters(parameterWithName("comment-id").description("?????? ?????????"))
                        )
                );
    }
}