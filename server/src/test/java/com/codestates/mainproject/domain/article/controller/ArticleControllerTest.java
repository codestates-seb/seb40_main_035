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

@WebMvcTest(value = ArticleController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
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
        ArticlePostDto postDto = new ArticlePostDto(1L,"제목1","본문1","20221109","20221210",
                3, 3, List.of(new ArticleHashtagDto("게시글태그1")),
                List.of(new ArticleInterestDto("교육")),List.of(new ArticleSkillDto("JAVA")));
        String content = gson.toJson(postDto);

        ArticleDetailResponseDto detailResponseDto = new ArticleDetailResponseDto(
                1L, "제목1", "본문1", 0, false,
                "20221109", "20221210", 3,3,
                1L, "홍길동", LocalDateTime.now(),
                LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"게시글태그1")),
                List.of(new InterestResponseDto(1L,"교육")),List.of(new SkillResponseDto(1L,"JAVA", Skill.SkillSort.BACKEND)),
                List.of(new AnswerResponseDto(1L,"본문1",1L,"홍길동", 1L,
                        LocalDateTime.now(), LocalDateTime.now(),
                        List.of(new CommentResponseDto(1L, "댓글1",1L, "홍길동",
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
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("게시글 본문"),
                                        fieldWithPath("startDay").type(JsonFieldType.STRING).description("프로젝트 개시일"),
                                        fieldWithPath("endDay").type(JsonFieldType.STRING).description("프로젝트 마감일"),
                                        fieldWithPath("backend").type(JsonFieldType.NUMBER).description("백엔드 인원수"),
                                        fieldWithPath("frontend").type(JsonFieldType.NUMBER).description("프론트 인원수"),

                                        fieldWithPath("articleHashtags").type(JsonFieldType.ARRAY).description("게시글 태그"),
                                        fieldWithPath("articleHashtags[].hashtagName").type(JsonFieldType.STRING).description("게시글 태그 이름"),

                                        fieldWithPath("articleInterests").type(JsonFieldType.ARRAY).description("게시글 관심분야"),
                                        fieldWithPath("articleInterests[].interestName").type(JsonFieldType.STRING).description("게시글 관심분야 이름"),

                                        fieldWithPath("articleSkills").type(JsonFieldType.ARRAY).description("게시글 기술스택"),
                                        fieldWithPath("articleSkills[].skillName").type(JsonFieldType.STRING).description("게시글 기술스택 이름")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.articleId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("게시글 본문"),
                                        fieldWithPath("data.views").type(JsonFieldType.NUMBER).description("게시글 조회수"),
                                        fieldWithPath("data.isCompleted").type(JsonFieldType.BOOLEAN).description("게시글 모집종료여부"),
                                        fieldWithPath("data.startDay").type(JsonFieldType.STRING).description("프로젝트 개시일"),
                                        fieldWithPath("data.endDay").type(JsonFieldType.STRING).description("프로젝트 마감일"),
                                        fieldWithPath("data.backend").type(JsonFieldType.NUMBER).description("백엔드 인원수"),
                                        fieldWithPath("data.frontend").type(JsonFieldType.NUMBER).description("프론트 인원수"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정 날짜"),
                                        fieldWithPath("data.heartCount").type(JsonFieldType.NUMBER).description("게시글 좋아요수"),
                                        fieldWithPath("data.answerCount").type(JsonFieldType.NUMBER).description("게시글 답변수"),

                                        fieldWithPath("data.hashtags").type(JsonFieldType.ARRAY).description("게시글 태그 정보"),
                                        fieldWithPath("data.hashtags[].hashtagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data.hashtags[].name").type(JsonFieldType.STRING).description("태그 이름"),

                                        fieldWithPath("data.interests").type(JsonFieldType.ARRAY).description("게시글 관심분야"),
                                        fieldWithPath("data.interests[].interestId").type(JsonFieldType.NUMBER).description("관심분야 식별자"),
                                        fieldWithPath("data.interests[].name").type(JsonFieldType.STRING).description("관심분야 이름"),

                                        fieldWithPath("data.skills").type(JsonFieldType.ARRAY).description("게시글 기술스택"),
                                        fieldWithPath("data.skills[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.skills[].name").type(JsonFieldType.STRING).description("기술스택 이름"),
                                        fieldWithPath("data.skills[].skillSort").type(JsonFieldType.STRING).description("기술스택 종류"),

                                        fieldWithPath("data.answers").type(JsonFieldType.ARRAY).description("게시글 답변 정보"),
                                        fieldWithPath("data.answers[].answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                                        fieldWithPath("data.answers[].body").type(JsonFieldType.STRING).description("답변 본문"),
                                        fieldWithPath("data.answers[].memberId").type(JsonFieldType.NUMBER).description("답변 작성 회원 식별자"),
                                        fieldWithPath("data.answers[].memberName").type(JsonFieldType.STRING).description("답변 작성 회원"),
                                        fieldWithPath("data.answers[].articleId").type(JsonFieldType.NUMBER).description("답변 작성 게시글 식별자"),
                                        fieldWithPath("data.answers[].createdAt").type(JsonFieldType.STRING).description("답변 생성 시간"),
                                        fieldWithPath("data.answers[].modifiedAt").type(JsonFieldType.STRING).description("답변 수정 시간"),

                                        fieldWithPath("data.answers[].comments").type(JsonFieldType.ARRAY).description("답변 댓글 정보"),
                                        fieldWithPath("data.answers[].comments[].commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                                        fieldWithPath("data.answers[].comments[].body").type(JsonFieldType.STRING).description("댓글 내용"),
                                        fieldWithPath("data.answers[].comments[].memberId").type(JsonFieldType.NUMBER).description("댓글 작성 회원 식별자"),
                                        fieldWithPath("data.answers[].comments[].memberName").type(JsonFieldType.STRING).description("댓글 작성 회원 이름"),
                                        fieldWithPath("data.answers[].comments[].answerId").type(JsonFieldType.NUMBER).description("댓글 작성 답변 식별자"),
                                        fieldWithPath("data.answers[].comments[].createdAt").type(JsonFieldType.STRING).description("댓글 생성 시간"),
                                        fieldWithPath("data.answers[].comments[].modifiedAt").type(JsonFieldType.STRING).description("댓글 수정 시간")
                                )
                        )
                ));
    }

    @Test
    void patchArticle() throws Exception{
        //given
        long articleId = 1L;
        ArticlePatchDto patchDto = new ArticlePatchDto();
        patchDto.setTitle("제목2");
        patchDto.setBody("본문2");
        patchDto.setIsCompleted(true);
        patchDto.setStartDay("20221111");
        patchDto.setEndDay("20221130");
        patchDto.setBackend(2);
        patchDto.setFrontend(2);
        patchDto.setHearts(List.of(new HeartDto(1L)));
        patchDto.setArticleHashtags(List.of(new ArticleHashtagDto("게시글태그1")));
        patchDto.setArticleInterests(List.of(new ArticleInterestDto("미디어")));
        patchDto.setArticleSkills(List.of(new ArticleSkillDto("Spring")));

        String content = gson.toJson(patchDto);

        ArticleDetailResponseDto detailResponseDto = new ArticleDetailResponseDto(articleId, "제목2", "본문2",
                0, true, "20221111","20221130",2,2,
                1L, "홍길동", LocalDateTime.now(),LocalDateTime.now(),
                1,1, List.of(new HashtagResponseDto(1L,"해시태그1")),
                List.of(new InterestResponseDto(1L,"미디어")),List.of(new SkillResponseDto(1L,"Spring", Skill.SkillSort.BACKEND)),
                List.of(new AnswerResponseDto(1L, "본문1",1L,"홍길동", 1L,
                        LocalDateTime.now(), LocalDateTime.now(),
                        List.of(new CommentResponseDto(1L, "댓글1",1L, "홍길동",
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
                        pathParameters(parameterWithName("article-id").description("게시글 식별자")),
                        requestFields(
                                List.of(
                                        fieldWithPath("articleId").type(JsonFieldType.NUMBER).description("게시글 식별자").ignored(),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("게시글 본문"),
                                        fieldWithPath("isCompleted").type(JsonFieldType.BOOLEAN).description("게시글 모집종료여부"),
                                        fieldWithPath("startDay").type(JsonFieldType.STRING).description("프로젝트 개시일"),
                                        fieldWithPath("endDay").type(JsonFieldType.STRING).description("프로젝트 마감일"),
                                        fieldWithPath("backend").type(JsonFieldType.NUMBER).description("백엔드 인원수").optional(),
                                        fieldWithPath("frontend").type(JsonFieldType.NUMBER).description("프론트 인원수").optional(),

                                        fieldWithPath("hearts").type(JsonFieldType.ARRAY).description("게시글 좋아요 정보"),
                                        fieldWithPath("hearts[].memberId").type(JsonFieldType.NUMBER).description("게시글 좋아요 멤버 식별자"),

                                        fieldWithPath("articleHashtags").type(JsonFieldType.ARRAY).description("게시글 태그 정보"),
                                        fieldWithPath("articleHashtags[].hashtagName").type(JsonFieldType.STRING).description("게시글 태그 이름"),

                                        fieldWithPath("articleInterests").type(JsonFieldType.ARRAY).description("게시글 관심분야 정보"),
                                        fieldWithPath("articleInterests[].interestName").type(JsonFieldType.STRING).description("게시글 관심분야 이름"),

                                        fieldWithPath("articleSkills").type(JsonFieldType.ARRAY).description("게시글 기술스택 정보"),
                                        fieldWithPath("articleSkills[].skillName").type(JsonFieldType.STRING).description("게시글 기술스택 이름")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.articleId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("게시글 본문"),
                                        fieldWithPath("data.views").type(JsonFieldType.NUMBER).description("게시글 조회수"),
                                        fieldWithPath("data.isCompleted").type(JsonFieldType.BOOLEAN).description("게시글 모집종료여부"),
                                        fieldWithPath("data.startDay").type(JsonFieldType.STRING).description("프로젝트 개시일"),
                                        fieldWithPath("data.endDay").type(JsonFieldType.STRING).description("프로젝트 마감일"),
                                        fieldWithPath("data.backend").type(JsonFieldType.NUMBER).description("백엔드 인원수"),
                                        fieldWithPath("data.frontend").type(JsonFieldType.NUMBER).description("프론트 인원수"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정 날짜"),
                                        fieldWithPath("data.heartCount").type(JsonFieldType.NUMBER).description("게시글 좋아요수"),
                                        fieldWithPath("data.answerCount").type(JsonFieldType.NUMBER).description("게시글 답변수"),

                                        fieldWithPath("data.hashtags").type(JsonFieldType.ARRAY).description("태그 정보"),
                                        fieldWithPath("data.hashtags[].hashtagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data.hashtags[].name").type(JsonFieldType.STRING).description("태그 내용"),

                                        fieldWithPath("data.interests").type(JsonFieldType.ARRAY).description("관심분야 정보"),
                                        fieldWithPath("data.interests[].interestId").type(JsonFieldType.NUMBER).description("관심분야 식별자"),
                                        fieldWithPath("data.interests[].name").type(JsonFieldType.STRING).description("관심분야 내용"),

                                        fieldWithPath("data.skills").type(JsonFieldType.ARRAY).description("기술스택 정보"),
                                        fieldWithPath("data.skills[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.skills[].name").type(JsonFieldType.STRING).description("기술스택 내용"),
                                        fieldWithPath("data.skills[].skillSort").type(JsonFieldType.STRING).description("기술스택 종류"),

                                        fieldWithPath("data.answers").type(JsonFieldType.ARRAY).description("게시글 답변 정보"),
                                        fieldWithPath("data.answers[].answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                                        fieldWithPath("data.answers[].body").type(JsonFieldType.STRING).description("답변 본문"),
                                        fieldWithPath("data.answers[].memberId").type(JsonFieldType.NUMBER).description("답변 작성 회원 식별자"),
                                        fieldWithPath("data.answers[].memberName").type(JsonFieldType.STRING).description("답변 작성 회원"),
                                        fieldWithPath("data.answers[].articleId").type(JsonFieldType.NUMBER).description("답변 작성 게시글 식별자"),
                                        fieldWithPath("data.answers[].createdAt").type(JsonFieldType.STRING).description("답변 생성 시간"),
                                        fieldWithPath("data.answers[].modifiedAt").type(JsonFieldType.STRING).description("답변 수정 시간"),

                                        fieldWithPath("data.answers[].comments").type(JsonFieldType.ARRAY).description("답변 댓글 정보"),
                                        fieldWithPath("data.answers[].comments[].commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                                        fieldWithPath("data.answers[].comments[].body").type(JsonFieldType.STRING).description("댓글 내용"),
                                        fieldWithPath("data.answers[].comments[].memberId").type(JsonFieldType.NUMBER).description("댓글 작성 회원 식별자"),
                                        fieldWithPath("data.answers[].comments[].memberName").type(JsonFieldType.STRING).description("댓글 작성 회원 이름"),
                                        fieldWithPath("data.answers[].comments[].answerId").type(JsonFieldType.NUMBER).description("댓글 작성 답변 식별자"),
                                        fieldWithPath("data.answers[].comments[].createdAt").type(JsonFieldType.STRING).description("댓글 생성 시간"),
                                        fieldWithPath("data.answers[].comments[].modifiedAt").type(JsonFieldType.STRING).description("댓글 수정 시간")
                                )
                        )
                ));
    }

    @Test
    void getArticle() throws Exception {
        //given
        long articleId = 1L;

        ArticleDetailResponseDto detailResponseDto = new ArticleDetailResponseDto(
                1L, "제목1", "본문1", 0, false,
                "20221109", "20221210", 3, 3,
                1L, "홍길동", LocalDateTime.now(),
                LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"해쉬태그1")),
                List.of(new InterestResponseDto(1L,"교육")),List.of(new SkillResponseDto(1L,"JAVA", Skill.SkillSort.BACKEND)),
                List.of(new AnswerResponseDto(1L,"본문1",1L,"홍길동",
                        1L,LocalDateTime.now(), LocalDateTime.now(),
                        List.of(new CommentResponseDto(1L, "댓글1",1L, "홍길동",
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
                        pathParameters(parameterWithName("article-id").description("게시글 식별자")),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.articleId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("게시글 본문"),
                                        fieldWithPath("data.views").type(JsonFieldType.NUMBER).description("게시글 조회수"),
                                        fieldWithPath("data.isCompleted").type(JsonFieldType.BOOLEAN).description("게시글 모집종료여부"),
                                        fieldWithPath("data.startDay").type(JsonFieldType.STRING).description("프로젝트 개시일"),
                                        fieldWithPath("data.endDay").type(JsonFieldType.STRING).description("프로젝트 마감일"),
                                        fieldWithPath("data.backend").type(JsonFieldType.NUMBER).description("백엔드 인원수").optional(),
                                        fieldWithPath("data.frontend").type(JsonFieldType.NUMBER).description("프론트 인원수").optional(),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.memberName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정 날짜"),
                                        fieldWithPath("data.heartCount").type(JsonFieldType.NUMBER).description("게시글 좋아요수"),
                                        fieldWithPath("data.answerCount").type(JsonFieldType.NUMBER).description("게시글 답변수"),

                                        fieldWithPath("data.hashtags").type(JsonFieldType.ARRAY).description("태그 정보"),
                                        fieldWithPath("data.hashtags[].hashtagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data.hashtags[].name").type(JsonFieldType.STRING).description("태그 내용"),

                                        fieldWithPath("data.interests").type(JsonFieldType.ARRAY).description("관심분야 정보"),
                                        fieldWithPath("data.interests[].interestId").type(JsonFieldType.NUMBER).description("관심분야 식별자"),
                                        fieldWithPath("data.interests[].name").type(JsonFieldType.STRING).description("관심분야 내용"),

                                        fieldWithPath("data.skills").type(JsonFieldType.ARRAY).description("기술스택 정보"),
                                        fieldWithPath("data.skills[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.skills[].name").type(JsonFieldType.STRING).description("기술스택 내용"),
                                        fieldWithPath("data.skills[].skillSort").type(JsonFieldType.STRING).description("기술스택 종류"),

                                        fieldWithPath("data.answers").type(JsonFieldType.ARRAY).description("게시글 답변 정보"),
                                        fieldWithPath("data.answers[].answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                                        fieldWithPath("data.answers[].body").type(JsonFieldType.STRING).description("답변 본문"),
                                        fieldWithPath("data.answers[].memberId").type(JsonFieldType.NUMBER).description("답변 작성 회원 식별자"),
                                        fieldWithPath("data.answers[].memberName").type(JsonFieldType.STRING).description("답변 작성 회원"),
                                        fieldWithPath("data.answers[].articleId").type(JsonFieldType.NUMBER).description("답변 작성 게시글 식별자"),
                                        fieldWithPath("data.answers[].createdAt").type(JsonFieldType.STRING).description("답변 생성 시간"),
                                        fieldWithPath("data.answers[].modifiedAt").type(JsonFieldType.STRING).description("답변 수정 시간"),

                                        fieldWithPath("data.answers[].comments").type(JsonFieldType.ARRAY).description("답변 댓글 정보"),
                                        fieldWithPath("data.answers[].comments[].commentId").type(JsonFieldType.NUMBER).description("댓글 식별자"),
                                        fieldWithPath("data.answers[].comments[].body").type(JsonFieldType.STRING).description("댓글 내용"),
                                        fieldWithPath("data.answers[].comments[].memberId").type(JsonFieldType.NUMBER).description("댓글 작성 회원 식별자"),
                                        fieldWithPath("data.answers[].comments[].memberName").type(JsonFieldType.STRING).description("댓글 작성 회원 이름"),
                                        fieldWithPath("data.answers[].comments[].answerId").type(JsonFieldType.NUMBER).description("댓글 작성 답변 식별자"),
                                        fieldWithPath("data.answers[].comments[].createdAt").type(JsonFieldType.STRING).description("댓글 생성 시간"),
                                        fieldWithPath("data.answers[].comments[].modifiedAt").type(JsonFieldType.STRING).description("댓글 수정 시간")
                                )
                        )
                ));
    }

    @Test
    void getArticles() throws Exception {
        //given
        List<String> skill = new ArrayList<>(List.of("JAVA"));
        boolean status = false;
        String sort = "정렬기준";
        int page = 1;
        int size =5;

        List<Article> articles = List.of(new Article(), new Article(), new Article());

        List<ArticleResponseDto> responseDtos = List.of(
                new ArticleResponseDto(
                        1L, "제목1", "본문1", 0, false,
                        "20221109", "20221210", 3, 3,
                        1L, "홍길동", LocalDateTime.now(),
                        LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"해쉬태그1")),
                        List.of(new InterestResponseDto(1L,"교육")),List.of(new SkillResponseDto(1L,"JAVA", Skill.SkillSort.BACKEND))
                ),
                new ArticleResponseDto(
                        2L, "제목2", "본문2", 0, false,
                        "20221110", "20221211", 2, 2,
                        2L, "고길동", LocalDateTime.now(),
                        LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"해쉬태그1")),
                        List.of(new InterestResponseDto(1L,"미디어")),List.of(new SkillResponseDto(2L,"spring", Skill.SkillSort.BACKEND))),
                new ArticleResponseDto(
                        3L, "제목3", "본문3", 0, true,
                        "20221111", "20221212", 3, 4,
                        3L, "김길동", LocalDateTime.now(),
                        LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"해쉬태그1")),
                        List.of(new InterestResponseDto(1L,"제조")),List.of(new SkillResponseDto(3L,"Nodejs", Skill.SkillSort.BACKEND)))
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
                                parameterWithName("skill").description("기술 이름"),
                                parameterWithName("status").description("게시글 모집종료여부"),
                                parameterWithName("sort").description("페이지 정렬방식"),
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 크기"))),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].articleId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                        fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("data[].body").type(JsonFieldType.STRING).description("게시글 본문"),
                                        fieldWithPath("data[].views").type(JsonFieldType.NUMBER).description("게시글 조회수"),
                                        fieldWithPath("data[].isCompleted").type(JsonFieldType.BOOLEAN).description("게시글 모집종료여부"),
                                        fieldWithPath("data[].startDay").type(JsonFieldType.STRING).description("프로젝트 개시일"),
                                        fieldWithPath("data[].endDay").type(JsonFieldType.STRING).description("프로젝트 마감일"),
                                        fieldWithPath("data[].backend").type(JsonFieldType.NUMBER).description("백엔드 인원수").optional(),
                                        fieldWithPath("data[].frontend").type(JsonFieldType.NUMBER).description("프론트 인원수").optional(),
                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data[].memberName").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("수정 날짜"),
                                        fieldWithPath("data[].heartCount").type(JsonFieldType.NUMBER).description("게시글 좋아요수"),
                                        fieldWithPath("data[].answerCount").type(JsonFieldType.NUMBER).description("게시글 답변수"),

                                        fieldWithPath("data[].hashtags").type(JsonFieldType.ARRAY).description("태그 정보"),
                                        fieldWithPath("data[].hashtags[].hashtagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data[].hashtags[].name").type(JsonFieldType.STRING).description("태그 내용"),

                                        fieldWithPath("data[].interests").type(JsonFieldType.ARRAY).description("관심분야 정보"),
                                        fieldWithPath("data[].interests[].interestId").type(JsonFieldType.NUMBER).description("관심분야 식별자"),
                                        fieldWithPath("data[].interests[].name").type(JsonFieldType.STRING).description("관심분야 내용"),

                                        fieldWithPath("data[].skills").type(JsonFieldType.ARRAY).description("기술스택 정보"),
                                        fieldWithPath("data[].skills[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data[].skills[].name").type(JsonFieldType.STRING).description("기술스택 내용"),
                                        fieldWithPath("data[].skills[].skillSort").type(JsonFieldType.STRING).description("기술스택 종류"),

                                        fieldWithPath("additionalData").type(JsonFieldType.ARRAY).description("추가 데이터"),
                                        fieldWithPath("additionalData[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("additionalData[].name").type(JsonFieldType.STRING).description("기술스택 내용"),
                                        fieldWithPath("additionalData[].skillSort").type(JsonFieldType.STRING).description("기술스택 종류"),

                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 갯수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수")
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
                        pathParameters(parameterWithName("article-id").description("게시글 식별자"))
                ));
    }
}