package com.codestates.mainproject.domain.article.controller;

import com.codestates.mainproject.domain.answer.dto.AnswerResponseDto;
import com.codestates.mainproject.domain.article.dto.*;
import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.article.mapper.ArticleMapper;
import com.codestates.mainproject.domain.article.service.ArticleService;
import com.codestates.mainproject.domain.comment.dto.CommentResponseDto;
import com.codestates.mainproject.domain.hashtag.dto.HashtagResponseDto;
import com.codestates.mainproject.domain.interest.dto.InterestResponseDto;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ArticleController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, OAuth2ClientAutoConfiguration.class})
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class ArticleControllerTest {
    @MockBean
    private ArticleService articleService;

    @MockBean
    private ArticleMapper mapper;

    @MockBean
    private SkillService skillService;

    @MockBean
    private SkillMapper skillMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;


    @Test
    void postArticle() throws Exception{
        //given
        ArticlePostDto postDto = new ArticlePostDto(1L,"??????1","??????1","20221109","20221210",
                3, 3, List.of(new ArticleHashtagDto("???????????????1")),
                List.of(new ArticleInterestDto("??????")),List.of(new ArticleSkillDto("JAVA")));
        String content = gson.toJson(postDto);

        ArticleDetailResponseDto detailResponseDto = new ArticleDetailResponseDto(
                1L, "??????1", "??????1", 0, false,
                "20221109", "20221210", 3,3,
                1L, "?????????", "??????", LocalDateTime.now(),
                LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"???????????????1")),
                List.of(new InterestResponseDto(1L,"??????")),List.of(new SkillResponseDto(1L,"JAVA", Skill.SkillSort.BACKEND)),
                List.of(new AnswerResponseDto(1L,"??????1",1L,"?????????", 1L,
                        LocalDateTime.now(), LocalDateTime.now(),
                        List.of(new CommentResponseDto(1L, "??????1",1L, "?????????",
                                1L, LocalDateTime.now(),LocalDateTime.now())))));

        given(mapper.articlePostDtoToArticle(Mockito.any(ArticlePostDto.class)))
                .willReturn(new Article());

        given(articleService.createArticle(Mockito.any(Article.class)))
                .willReturn(new Article());

        given(mapper.articleToArticleDetailResponseDto(Mockito.any(Article.class)))
                .willReturn(detailResponseDto);

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
                .andExpect(jsonPath("$.data.memberId").value(postDto.getMemberId()))
                .andExpect(jsonPath("$.data.title").value(postDto.getTitle()))
                .andExpect(jsonPath("$.data.body").value(postDto.getBody()))
                .andExpect(jsonPath("$.data.startDay").value(postDto.getStartDay()))
                .andExpect(jsonPath("$.data.endDay").value(postDto.getEndDay()))
                .andExpect(jsonPath("$.data.backend").value(postDto.getBackend()))
                .andExpect(jsonPath("$.data.frontend").value(postDto.getFrontend()))
//                .andExpect(jsonPath("$.data.hashtags").value(articleHashtagDto1))
                .andDo(document("post-article",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("startDay").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                        fieldWithPath("endDay").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                        fieldWithPath("backend").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("frontend").type(JsonFieldType.NUMBER).description("????????? ?????????"),

                                        fieldWithPath("articleHashtags").type(JsonFieldType.ARRAY).description("????????? ??????"),
                                        fieldWithPath("articleHashtags[].hashtagName").type(JsonFieldType.STRING).description("????????? ?????? ??????"),

                                        fieldWithPath("articleInterests").type(JsonFieldType.ARRAY).description("????????? ????????????"),
                                        fieldWithPath("articleInterests[].interestName").type(JsonFieldType.STRING).description("????????? ???????????? ??????"),

                                        fieldWithPath("articleSkills").type(JsonFieldType.ARRAY).description("????????? ????????????"),
                                        fieldWithPath("articleSkills[].skillName").type(JsonFieldType.STRING).description("????????? ???????????? ??????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.articleId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("data.views").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("data.isCompleted").type(JsonFieldType.BOOLEAN).description("????????? ??????????????????"),
                                        fieldWithPath("data.startDay").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                        fieldWithPath("data.endDay").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                        fieldWithPath("data.backend").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("data.frontend").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.memberLevel").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.heartCount").type(JsonFieldType.NUMBER).description("????????? ????????????"),
                                        fieldWithPath("data.answerCount").type(JsonFieldType.NUMBER).description("????????? ?????????"),

                                        fieldWithPath("data.hashtags").type(JsonFieldType.ARRAY).description("????????? ?????? ??????"),
                                        fieldWithPath("data.hashtags[].hashtagId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.hashtags[].name").type(JsonFieldType.STRING).description("?????? ??????"),

                                        fieldWithPath("data.interests").type(JsonFieldType.ARRAY).description("????????? ????????????"),
                                        fieldWithPath("data.interests[].interestId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                        fieldWithPath("data.interests[].name").type(JsonFieldType.STRING).description("???????????? ??????"),

                                        fieldWithPath("data.skills").type(JsonFieldType.ARRAY).description("????????? ????????????"),
                                        fieldWithPath("data.skills[].skillId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                        fieldWithPath("data.skills[].name").type(JsonFieldType.STRING).description("???????????? ??????"),
                                        fieldWithPath("data.skills[].skillSort").type(JsonFieldType.STRING).description("???????????? ??????"),

                                        fieldWithPath("data.answers").type(JsonFieldType.ARRAY).description("????????? ?????? ??????"),
                                        fieldWithPath("data.answers[].answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.answers[].body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.answers[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                        fieldWithPath("data.answers[].memberName").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.answers[].articleId").type(JsonFieldType.NUMBER).description("?????? ?????? ????????? ?????????"),
                                        fieldWithPath("data.answers[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.answers[].modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),

                                        fieldWithPath("data.answers[].comments").type(JsonFieldType.ARRAY).description("?????? ?????? ??????"),
                                        fieldWithPath("data.answers[].comments[].commentId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.answers[].comments[].body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.answers[].comments[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                        fieldWithPath("data.answers[].comments[].memberName").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("data.answers[].comments[].answerId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                        fieldWithPath("data.answers[].comments[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.answers[].comments[].modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        )
                ));
    }

    @Test
    void patchArticle() throws Exception{
        //given
        long articleId = 1L;
        ArticlePatchDto patchDto = new ArticlePatchDto();
        patchDto.setTitle("??????2");
        patchDto.setBody("??????2");
        patchDto.setIsCompleted(true);
        patchDto.setStartDay("20221111");
        patchDto.setEndDay("20221130");
        patchDto.setBackend(2);
        patchDto.setFrontend(2);
        patchDto.setHearts(List.of(new HeartDto(1L)));
        patchDto.setArticleHashtags(List.of(new ArticleHashtagDto("???????????????1")));
        patchDto.setArticleInterests(List.of(new ArticleInterestDto("?????????")));
        patchDto.setArticleSkills(List.of(new ArticleSkillDto("Spring")));

        String content = gson.toJson(patchDto);

        ArticleDetailResponseDto detailResponseDto = new ArticleDetailResponseDto(articleId, "??????2", "??????2",
                0, true, "20221111","20221130",2,2,
                1L, "?????????", "??????", LocalDateTime.now(),LocalDateTime.now(),
                1,1, List.of(new HashtagResponseDto(1L,"????????????1")),
                List.of(new InterestResponseDto(1L,"?????????")),List.of(new SkillResponseDto(1L,"Spring", Skill.SkillSort.BACKEND)),
                List.of(new AnswerResponseDto(1L, "??????1",1L,"?????????", 1L,
                        LocalDateTime.now(), LocalDateTime.now(),
                        List.of(new CommentResponseDto(1L, "??????1",1L, "?????????",
                                1L, LocalDateTime.now(),LocalDateTime.now())))
                ));

        given(mapper.articlePatchDtoToArticle(Mockito.any(ArticlePatchDto.class)))
                .willReturn(new Article());

        given(articleService.updateArticle(Mockito.any(Article.class)))
                .willReturn(new Article());

        given(mapper.articleToArticleDetailResponseDto(Mockito.any(Article.class)))
                .willReturn(detailResponseDto);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/articles/{article-id}", articleId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)

        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.articleId").value(articleId))
                .andExpect(jsonPath("$.data.title").value(patchDto.getTitle()))
                .andExpect(jsonPath("$.data.body").value(patchDto.getBody()))
                .andExpect(jsonPath("$.data.isCompleted").value(patchDto.getIsCompleted()))
                .andExpect(jsonPath("$.data.startDay").value(patchDto.getStartDay()))
                .andExpect(jsonPath("$.data.endDay").value(patchDto.getEndDay()))
                .andExpect(jsonPath("$.data.backend").value(patchDto.getBackend()))
                .andExpect(jsonPath("$.data.frontend").value(patchDto.getFrontend()))
                .andDo(document("patch-article",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("article-id").description("????????? ?????????")),
                        requestFields(
                                List.of(
                                        fieldWithPath("articleId").type(JsonFieldType.NUMBER).description("????????? ?????????").ignored(),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("isCompleted").type(JsonFieldType.BOOLEAN).description("????????? ??????????????????"),
                                        fieldWithPath("startDay").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                        fieldWithPath("endDay").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                        fieldWithPath("backend").type(JsonFieldType.NUMBER).description("????????? ?????????").optional(),
                                        fieldWithPath("frontend").type(JsonFieldType.NUMBER).description("????????? ?????????").optional(),

                                        fieldWithPath("hearts").type(JsonFieldType.ARRAY).description("????????? ????????? ??????"),
                                        fieldWithPath("hearts[].memberId").type(JsonFieldType.NUMBER).description("????????? ????????? ?????? ?????????"),

                                        fieldWithPath("articleHashtags").type(JsonFieldType.ARRAY).description("????????? ?????? ??????"),
                                        fieldWithPath("articleHashtags[].hashtagName").type(JsonFieldType.STRING).description("????????? ?????? ??????"),

                                        fieldWithPath("articleInterests").type(JsonFieldType.ARRAY).description("????????? ???????????? ??????"),
                                        fieldWithPath("articleInterests[].interestName").type(JsonFieldType.STRING).description("????????? ???????????? ??????"),

                                        fieldWithPath("articleSkills").type(JsonFieldType.ARRAY).description("????????? ???????????? ??????"),
                                        fieldWithPath("articleSkills[].skillName").type(JsonFieldType.STRING).description("????????? ???????????? ??????")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.articleId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("data.views").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("data.isCompleted").type(JsonFieldType.BOOLEAN).description("????????? ??????????????????"),
                                        fieldWithPath("data.startDay").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                        fieldWithPath("data.endDay").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                        fieldWithPath("data.backend").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("data.frontend").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.memberLevel").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.heartCount").type(JsonFieldType.NUMBER).description("????????? ????????????"),
                                        fieldWithPath("data.answerCount").type(JsonFieldType.NUMBER).description("????????? ?????????"),

                                        fieldWithPath("data.hashtags").type(JsonFieldType.ARRAY).description("?????? ??????"),
                                        fieldWithPath("data.hashtags[].hashtagId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.hashtags[].name").type(JsonFieldType.STRING).description("?????? ??????"),

                                        fieldWithPath("data.interests").type(JsonFieldType.ARRAY).description("???????????? ??????"),
                                        fieldWithPath("data.interests[].interestId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                        fieldWithPath("data.interests[].name").type(JsonFieldType.STRING).description("???????????? ??????"),

                                        fieldWithPath("data.skills").type(JsonFieldType.ARRAY).description("???????????? ??????"),
                                        fieldWithPath("data.skills[].skillId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                        fieldWithPath("data.skills[].name").type(JsonFieldType.STRING).description("???????????? ??????"),
                                        fieldWithPath("data.skills[].skillSort").type(JsonFieldType.STRING).description("???????????? ??????"),

                                        fieldWithPath("data.answers").type(JsonFieldType.ARRAY).description("????????? ?????? ??????"),
                                        fieldWithPath("data.answers[].answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.answers[].body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.answers[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                        fieldWithPath("data.answers[].memberName").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.answers[].articleId").type(JsonFieldType.NUMBER).description("?????? ?????? ????????? ?????????"),
                                        fieldWithPath("data.answers[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.answers[].modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),

                                        fieldWithPath("data.answers[].comments").type(JsonFieldType.ARRAY).description("?????? ?????? ??????"),
                                        fieldWithPath("data.answers[].comments[].commentId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.answers[].comments[].body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.answers[].comments[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                        fieldWithPath("data.answers[].comments[].memberName").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("data.answers[].comments[].answerId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                        fieldWithPath("data.answers[].comments[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.answers[].comments[].modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        )
                ));
    }

    @Test
    void getArticle() throws Exception {
        //given
        long articleId = 1L;

        ArticleDetailResponseDto detailResponseDto = new ArticleDetailResponseDto(
                1L, "??????1", "??????1", 0, false,
                "20221109", "20221210", 3, 3,
                1L, "?????????", "??????", LocalDateTime.now(),
                LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"????????????1")),
                List.of(new InterestResponseDto(1L,"??????")),List.of(new SkillResponseDto(1L,"JAVA", Skill.SkillSort.BACKEND)),
                List.of(new AnswerResponseDto(1L,"??????1",1L,"?????????",
                        1L,LocalDateTime.now(), LocalDateTime.now(),
                        List.of(new CommentResponseDto(1L, "??????1",1L, "?????????",
                                1L, LocalDateTime.now(),LocalDateTime.now()))))
        );

        given(articleService.findArticle(Mockito.anyLong()))
                .willReturn(new Article());

        given(mapper.articleToArticleDetailResponseDto(Mockito.any(Article.class)))
                .willReturn(detailResponseDto);

        //when
        ResultActions actions = mockMvc.perform(
                get("/articles/{article-id}", articleId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.articleId").value(articleId))
                .andDo(document("get-article",
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("article-id").description("????????? ?????????")),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
                                        fieldWithPath("data.articleId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("data.views").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("data.isCompleted").type(JsonFieldType.BOOLEAN).description("????????? ??????????????????"),
                                        fieldWithPath("data.startDay").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                        fieldWithPath("data.endDay").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                        fieldWithPath("data.backend").type(JsonFieldType.NUMBER).description("????????? ?????????").optional(),
                                        fieldWithPath("data.frontend").type(JsonFieldType.NUMBER).description("????????? ?????????").optional(),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.memberLevel").type(JsonFieldType.STRING).description("?????? ?????????"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.heartCount").type(JsonFieldType.NUMBER).description("????????? ????????????"),
                                        fieldWithPath("data.answerCount").type(JsonFieldType.NUMBER).description("????????? ?????????"),

                                        fieldWithPath("data.hashtags").type(JsonFieldType.ARRAY).description("?????? ??????"),
                                        fieldWithPath("data.hashtags[].hashtagId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.hashtags[].name").type(JsonFieldType.STRING).description("?????? ??????"),

                                        fieldWithPath("data.interests").type(JsonFieldType.ARRAY).description("???????????? ??????"),
                                        fieldWithPath("data.interests[].interestId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                        fieldWithPath("data.interests[].name").type(JsonFieldType.STRING).description("???????????? ??????"),

                                        fieldWithPath("data.skills").type(JsonFieldType.ARRAY).description("???????????? ??????"),
                                        fieldWithPath("data.skills[].skillId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                        fieldWithPath("data.skills[].name").type(JsonFieldType.STRING).description("???????????? ??????"),
                                        fieldWithPath("data.skills[].skillSort").type(JsonFieldType.STRING).description("???????????? ??????"),

                                        fieldWithPath("data.answers").type(JsonFieldType.ARRAY).description("????????? ?????? ??????"),
                                        fieldWithPath("data.answers[].answerId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.answers[].body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.answers[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                        fieldWithPath("data.answers[].memberName").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.answers[].articleId").type(JsonFieldType.NUMBER).description("?????? ?????? ????????? ?????????"),
                                        fieldWithPath("data.answers[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.answers[].modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),

                                        fieldWithPath("data.answers[].comments").type(JsonFieldType.ARRAY).description("?????? ?????? ??????"),
                                        fieldWithPath("data.answers[].comments[].commentId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data.answers[].comments[].body").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data.answers[].comments[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                        fieldWithPath("data.answers[].comments[].memberName").type(JsonFieldType.STRING).description("?????? ?????? ?????? ??????"),
                                        fieldWithPath("data.answers[].comments[].answerId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ?????????"),
                                        fieldWithPath("data.answers[].comments[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                                        fieldWithPath("data.answers[].comments[].modifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                                )
                        )
                ));
    }

    @Test
    void getArticles() throws Exception {
        //given
        List<String> skill = new ArrayList<>(List.of("JAVA"));
        boolean status = false;
        String sort = "????????????";
        int page = 1;
        int size =5;

        List<Article> articles = List.of(new Article(), new Article(), new Article());

        List<ArticleResponseDto> responseDtos = List.of(
                new ArticleResponseDto(
                        1L, "??????1", 0, false,
                        "20221109", "20221210", 3, 3,
                        1L, "?????????", LocalDateTime.now(),
                        LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"????????????1")),
                        List.of(new InterestResponseDto(1L,"??????")),List.of(new SkillResponseDto(1L,"JAVA", Skill.SkillSort.BACKEND))
                ),
                new ArticleResponseDto(
                        2L, "??????2", 0, false,
                        "20221110", "20221211", 2, 2,
                        2L, "?????????", LocalDateTime.now(),
                        LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"????????????1")),
                        List.of(new InterestResponseDto(1L,"?????????")),List.of(new SkillResponseDto(2L,"spring", Skill.SkillSort.BACKEND))),
                new ArticleResponseDto(
                        3L, "??????3", 0, true,
                        "20221111", "20221212", 3, 4,
                        3L, "?????????", LocalDateTime.now(),
                        LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"????????????1")),
                        List.of(new InterestResponseDto(1L,"??????")),List.of(new SkillResponseDto(3L,"Nodejs", Skill.SkillSort.BACKEND)))
                );

        List<SkillResponseDto> skillResponseDtos = new ArrayList<>(List.of(new SkillResponseDto(1L,"JAVA", Skill.SkillSort.BACKEND), new SkillResponseDto(2L,"spring", Skill.SkillSort.BACKEND), new SkillResponseDto(3L,"Nodejs", Skill.SkillSort.BACKEND)));

        given(articleService.findArticles(Mockito.anyList(),Mockito.anyBoolean(),Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt()))
                .willReturn(new PageImpl<>(articles, PageRequest.of(page-1,size, Sort.by("articleId")
                        .descending()), articles.size()));

        given(mapper.articlesToArticleResponseDtos(Mockito.anyList()))
                .willReturn(responseDtos);

        given(skillService.findSkills())
                .willReturn(List.of(new Skill(), new Skill(), new Skill()));

        given(skillMapper.skillsToSkillResponseDtos(Mockito.anyList()))
                .willReturn(skillResponseDtos);

        //when
        ResultActions actions = mockMvc.perform(
                get("/articles?skill={skill}&status={status}&sort={sort}&page={page}&size={size}",skill,status, sort, page,size)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("get-articles",
                        preprocessResponse(prettyPrint()),
                        requestParameters(List.of(
                                parameterWithName("skill").description("?????? ??????"),
                                parameterWithName("status").description("????????? ??????????????????"),
                                parameterWithName("sort").description("????????? ????????????"),
                                parameterWithName("page").description("????????? ??????"),
                                parameterWithName("size").description("????????? ??????"))),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
                                        fieldWithPath("data[].articleId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("data[].title").type(JsonFieldType.STRING).description("????????? ??????"),
                                        fieldWithPath("data[].views").type(JsonFieldType.NUMBER).description("????????? ?????????"),
                                        fieldWithPath("data[].isCompleted").type(JsonFieldType.BOOLEAN).description("????????? ??????????????????"),
                                        fieldWithPath("data[].startDay").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                        fieldWithPath("data[].endDay").type(JsonFieldType.STRING).description("???????????? ?????????"),
                                        fieldWithPath("data[].backend").type(JsonFieldType.NUMBER).description("????????? ?????????").optional(),
                                        fieldWithPath("data[].frontend").type(JsonFieldType.NUMBER).description("????????? ?????????").optional(),
                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data[].memberName").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("?????? ??????"),
                                        fieldWithPath("data[].heartCount").type(JsonFieldType.NUMBER).description("????????? ????????????"),
                                        fieldWithPath("data[].answerCount").type(JsonFieldType.NUMBER).description("????????? ?????????"),

                                        fieldWithPath("data[].hashtags").type(JsonFieldType.ARRAY).description("?????? ??????"),
                                        fieldWithPath("data[].hashtags[].hashtagId").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                                        fieldWithPath("data[].hashtags[].name").type(JsonFieldType.STRING).description("?????? ??????"),

                                        fieldWithPath("data[].interests").type(JsonFieldType.ARRAY).description("???????????? ??????"),
                                        fieldWithPath("data[].interests[].interestId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                        fieldWithPath("data[].interests[].name").type(JsonFieldType.STRING).description("???????????? ??????"),

                                        fieldWithPath("data[].skills").type(JsonFieldType.ARRAY).description("???????????? ??????"),
                                        fieldWithPath("data[].skills[].skillId").type(JsonFieldType.NUMBER).description("???????????? ?????????"),
                                        fieldWithPath("data[].skills[].name").type(JsonFieldType.STRING).description("???????????? ??????"),
                                        fieldWithPath("data[].skills[].skillSort").type(JsonFieldType.STRING).description("???????????? ??????"),

                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("????????? ??????"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("????????? ??????"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("??? ??????"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("??? ????????? ???")
                                )
                        )
                ));

    }

    @Test
    void deleteArticle() throws Exception {
        //given
        long articleId = 1L;

        //when
        ResultActions actions = mockMvc.perform(
                delete("/articles/{article-id}", articleId)
        );

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-article",
                        pathParameters(parameterWithName("article-id").description("????????? ?????????"))
                ));
    }
}