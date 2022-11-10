package com.codestates.mainproject.domain.article.controller;

import com.codestates.mainproject.domain.answer.entity.Answer;
import com.codestates.mainproject.domain.article.dto.ArticlePostDto;
import com.codestates.mainproject.domain.article.dto.ArticleResponseDto;
import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.article.entity.ArticleHashtag;
import com.codestates.mainproject.domain.article.mapper.ArticleMapper;
import com.codestates.mainproject.domain.article.service.ArticleService;
import com.codestates.mainproject.domain.category.entity.Category;
import com.codestates.mainproject.domain.heart.entity.Heart;
import com.codestates.mainproject.domain.member.entity.Member;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ArticleController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class ArticleControllerTest {
    @MockBean
    private ArticleService articleService;

    @MockBean
    private ArticleMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;


    @Test
    void postArticle() throws Exception{
        //given
        ArticlePostDto postDto = new ArticlePostDto("제목1","본문1",
                7,false, "20221109"
                ,"20221210",
                3,3, List.of("금융"),new Member(), new Category(), List.of(new ArticleHashtag()),
                List.of(new Heart()),List.of(new Answer()));
        String content = gson.toJson(postDto);

        ArticleResponseDto responseDto = new ArticleResponseDto();

        given(mapper.articlePostDtoToArticle(Mockito.any(ArticlePostDto.class)))
                .willReturn(new Article());

//        given(ArticleService.createArticle(Mockito.any(Article.class)))
//                .willReturn(new Article());

        given(mapper.articleToArticleResponseDto(Mockito.any(Article.class)))
                .willReturn(responseDto);

        //when
        ResultActions actions = mockMvc.perform(
                post("/articles")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.title").value(postDto.getTitle()))
                .andExpect(jsonPath("$.data.body").value(postDto.getBody()))

                .andDo(document("post-question",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("질문 제목"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("질문 본문"),
                                        fieldWithPath("tags").type(JsonFieldType.ARRAY).description("질문 태그").optional(),
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("질문 제목"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("질문 본문"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.tags").type(JsonFieldType.ARRAY).description("질문 태그"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정 날짜"),
                                        fieldWithPath("data.answerCount").type(JsonFieldType.NUMBER).description("답변 갯수")
                                )
                        )
                ));
    }

    @Test
    void patchArticle() {
    }

    @Test
    void getArticle() {
    }

    @Test
    void getArticles() {
    }

    @Test
    void deleteArticle() {
    }
}