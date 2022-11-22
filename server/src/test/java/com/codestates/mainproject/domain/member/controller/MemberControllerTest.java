package com.codestates.mainproject.domain.member.controller;

import com.codestates.mainproject.domain.article.dto.ArticleResponseDto;
import com.codestates.mainproject.domain.hashtag.dto.HashtagResponseDto;
import com.codestates.mainproject.domain.interest.dto.InterestResponseDto;
import com.codestates.mainproject.domain.member.dto.*;
import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.domain.member.mapper.MemberMapper;
import com.codestates.mainproject.domain.member.service.MemberService;
import com.codestates.mainproject.domain.skill.dto.SkillResponseDto;
import com.codestates.mainproject.domain.skill.entity.Skill;
import com.codestates.mainproject.email.service.EmailService;
import com.codestates.mainproject.security.auth.jwt.JwtTokenizer;
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
import org.springframework.test.context.ActiveProfiles;
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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = MemberController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@ActiveProfiles
class MemberControllerTest {
    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;

    @MockBean
    private JwtTokenizer jwtTokenizer;

    @MockBean
    private EmailService emailService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    void postMember() throws Exception{
        //given
        MemberPostDto postDto = new MemberPostDto(
                "hgd@gmail.com", "홍길동", "hgd1234!","hgd1234!",
                List.of(new MemberInterestDto("교육")),List.of(new MemberSkillDto("JAVA")));

        String content = gson.toJson(postDto);

        MemberDetailResponseDto detailResponseDto= new MemberDetailResponseDto(
                1L,"hgd@gmail.com", "홍길동", "길동이입니다","학생",
                "github.com/honggildong", List.of(new InterestResponseDto(1L, "교육")),
                List.of(new SkillResponseDto(1L, "JAVA", Skill.SkillSort.BACKEND)), LocalDateTime.now(), LocalDateTime.now(),
                List.of(new ArticleResponseDto(
                        1L, "제목1", 0, false,
                        "20221109", "20221210", 3, 3,
                        1L, "홍길동", LocalDateTime.now(),
                        LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"해쉬태그1")),
                        List.of(new InterestResponseDto(1L,"교육")),List.of(new SkillResponseDto(1L,"JAVA", Skill.SkillSort.BACKEND))
                )), List.of(new ArticleResponseDto(1L, "제목1", 0, false,
                "20221109", "20221210", 3, 3,
                1L, "홍길동", LocalDateTime.now(),
                LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"해쉬태그1")),
                List.of(new InterestResponseDto(1L,"교육")),List.of(new SkillResponseDto(1L,"JAVA", Skill.SkillSort.BACKEND)))));

        given(mapper.memberPostDtoToMember(Mockito.any(MemberPostDto.class)))
                .willReturn(new Member());
        given(memberService.createMember(Mockito.any(Member.class)))
                .willReturn(new Member());
        given(mapper.memberToMemberDetailResponseDto(Mockito.any(Member.class)))
                .willReturn(detailResponseDto);

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/members/signup")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value(postDto.getEmail()))
                .andExpect(jsonPath("$.data.name").value(postDto.getName()))
//                .andExpect(jsonPath("$.data.passwordCheck").value(postDto.getPasswordCheck()))

                .andDo(document("post-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("회원 비밀번호"),
                                        fieldWithPath("passwordCheck").type(JsonFieldType.STRING).description("비밀번호 재확인"),

                                        fieldWithPath("memberInterests").type(JsonFieldType.ARRAY).description("회원 관심분야"),
                                        fieldWithPath("memberInterests[].interestName").type(JsonFieldType.STRING).description("회원 관심분야 이름"),

                                        fieldWithPath("memberSkills").type(JsonFieldType.ARRAY).description("회원 기술스택"),
                                        fieldWithPath("memberSkills[].skillName").type(JsonFieldType.STRING).description("회원 기술스택 이름")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.description").type(JsonFieldType.STRING).description("회원 소개"),
                                        fieldWithPath("data.level").type(JsonFieldType.STRING).description("회원 티어"),
                                        fieldWithPath("data.github").type(JsonFieldType.STRING).description("회원 깃허브"),

                                        fieldWithPath("data.interests").type(JsonFieldType.ARRAY).description("회원 관심분야 정보"),
                                        fieldWithPath("data.interests[].interestId").type(JsonFieldType.NUMBER).description("관심분야 식별자"),
                                        fieldWithPath("data.interests[].name").type(JsonFieldType.STRING).description("관심분야 이름"),

                                        fieldWithPath("data.skills").type(JsonFieldType.ARRAY).description("회원 기술스택 정보"),
                                        fieldWithPath("data.skills[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.skills[].name").type(JsonFieldType.STRING).description("기술스택 이름"),
                                        fieldWithPath("data.skills[].skillSort").type(JsonFieldType.STRING).description("회원 기술스택 종류"),

                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정 날짜"),

                                        fieldWithPath("data.articles").type(JsonFieldType.ARRAY).description("회원 작성글 정보"),
                                        fieldWithPath("data.articles[].articleId").type(JsonFieldType.NUMBER).description("작성글 식별자"),
                                        fieldWithPath("data.articles[].title").type(JsonFieldType.STRING).description("작성글 제목"),
                                        fieldWithPath("data.articles[].views").type(JsonFieldType.NUMBER).description("작성글 조회수"),
                                        fieldWithPath("data.articles[].isCompleted").type(JsonFieldType.BOOLEAN).description("작성글 모집종료여부"),
                                        fieldWithPath("data.articles[].startDay").type(JsonFieldType.STRING).description("작성글 프로젝트 개시일"),
                                        fieldWithPath("data.articles[].endDay").type(JsonFieldType.STRING).description("작성글 프로젝트 마감일"),
                                        fieldWithPath("data.articles[].backend").type(JsonFieldType.NUMBER).description("작성글 백엔드 인원수").optional(),
                                        fieldWithPath("data.articles[].frontend").type(JsonFieldType.NUMBER).description("작성글 프론트 인원수").optional(),
                                        fieldWithPath("data.articles[].memberId").type(JsonFieldType.NUMBER).description("작성글 회원 식별자"),
                                        fieldWithPath("data.articles[].memberName").type(JsonFieldType.STRING).description("작성글 회원 이름"),
                                        fieldWithPath("data.articles[].createdAt").type(JsonFieldType.STRING).description("작성글 생성 날짜"),
                                        fieldWithPath("data.articles[].modifiedAt").type(JsonFieldType.STRING).description("작성글 수정 날짜"),
                                        fieldWithPath("data.articles[].heartCount").type(JsonFieldType.NUMBER).description("작성글 좋아요수"),
                                        fieldWithPath("data.articles[].answerCount").type(JsonFieldType.NUMBER).description("작성글 답변수"),

                                        fieldWithPath("data.articles[].hashtags").type(JsonFieldType.ARRAY).description("태그 정보"),
                                        fieldWithPath("data.articles[].hashtags[].hashtagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data.articles[].hashtags[].name").type(JsonFieldType.STRING).description("태그 내용"),

                                        fieldWithPath("data.articles[].interests").type(JsonFieldType.ARRAY).description("관심분야 정보"),
                                        fieldWithPath("data.articles[].interests[].interestId").type(JsonFieldType.NUMBER).description("관심분야 식별자"),
                                        fieldWithPath("data.articles[].interests[].name").type(JsonFieldType.STRING).description("관심분야 내용"),

                                        fieldWithPath("data.articles[].skills").type(JsonFieldType.ARRAY).description("기술스택 정보"),
                                        fieldWithPath("data.articles[].skills[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.articles[].skills[].name").type(JsonFieldType.STRING).description("기술스택 내용"),
                                        fieldWithPath("data.articles[].skills[].skillSort").type(JsonFieldType.STRING).description("기술스택 종류"),

                                        fieldWithPath("data.heartArticles").type(JsonFieldType.ARRAY).description("회원 좋아요 게시글 정보"),
                                        fieldWithPath("data.heartArticles[].articleId").type(JsonFieldType.NUMBER).description("좋아요 게시글 식별자"),
                                        fieldWithPath("data.heartArticles[].title").type(JsonFieldType.STRING).description("좋아요 게시글 제목"),
                                        fieldWithPath("data.heartArticles[].views").type(JsonFieldType.NUMBER).description("좋아요 게시글 조회수"),
                                        fieldWithPath("data.heartArticles[].isCompleted").type(JsonFieldType.BOOLEAN).description("좋아요 게시글 모집종료여부"),
                                        fieldWithPath("data.heartArticles[].startDay").type(JsonFieldType.STRING).description("좋아요 게시글 프로젝트 개시일"),
                                        fieldWithPath("data.heartArticles[].endDay").type(JsonFieldType.STRING).description("좋아요 게시글 프로젝트 마감일"),
                                        fieldWithPath("data.heartArticles[].backend").type(JsonFieldType.NUMBER).description("좋아요 게시글 백엔드 인원수").optional(),
                                        fieldWithPath("data.heartArticles[].frontend").type(JsonFieldType.NUMBER).description("좋아요 게시글 프론트 인원수").optional(),
                                        fieldWithPath("data.heartArticles[].memberId").type(JsonFieldType.NUMBER).description("좋아요 게시글 회원 식별자"),
                                        fieldWithPath("data.heartArticles[].memberName").type(JsonFieldType.STRING).description("좋아요 게시글 회원 이름"),
                                        fieldWithPath("data.heartArticles[].createdAt").type(JsonFieldType.STRING).description("좋아요 게시글 생성 날짜"),
                                        fieldWithPath("data.heartArticles[].modifiedAt").type(JsonFieldType.STRING).description("좋아요 게시글 수정 날짜"),
                                        fieldWithPath("data.heartArticles[].heartCount").type(JsonFieldType.NUMBER).description("좋아요 게시글 좋아요수"),
                                        fieldWithPath("data.heartArticles[].answerCount").type(JsonFieldType.NUMBER).description("좋아요 게시글 답변수"),

                                        fieldWithPath("data.heartArticles[].hashtags").type(JsonFieldType.ARRAY).description("태그 정보"),
                                        fieldWithPath("data.heartArticles[].hashtags[].hashtagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data.heartArticles[].hashtags[].name").type(JsonFieldType.STRING).description("태그 내용"),

                                        fieldWithPath("data.heartArticles[].interests").type(JsonFieldType.ARRAY).description("관심분야 정보"),
                                        fieldWithPath("data.heartArticles[].interests[].interestId").type(JsonFieldType.NUMBER).description("관심분야 식별자"),
                                        fieldWithPath("data.heartArticles[].interests[].name").type(JsonFieldType.STRING).description("관심분야 내용"),

                                        fieldWithPath("data.heartArticles[].skills").type(JsonFieldType.ARRAY).description("기술스택 정보"),
                                        fieldWithPath("data.heartArticles[].skills[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.heartArticles[].skills[].name").type(JsonFieldType.STRING).description("기술스택 내용"),
                                        fieldWithPath("data.heartArticles[].skills[].skillSort").type(JsonFieldType.STRING).description("기술스택 종류")
                                )
                        )
                ));
    }

    @Test
    void patchMember() throws Exception{
        //given
        long memberId = 1L;
        MemberPatchDto patchDto = new MemberPatchDto();
        patchDto.setPassword("hgd1234@");
        patchDto.setPasswordCheck("hgd1234@");
        patchDto.setName("고길동");
        patchDto.setDescription("홍길동아닙니다");
        patchDto.setLevel("시니어");
        patchDto.setGithub("github.com/gogildong");
        patchDto.setMemberInterests(List.of(new MemberInterestDto( "미디어")));
        patchDto.setMemberSkills(List.of(new MemberSkillDto( "Spring")));

        String content = gson.toJson(patchDto);

        MemberDetailResponseDto detailResponseDto =
                new MemberDetailResponseDto(1L, "hgd@gmail.com","고길동", "홍길동아닙니다",
                        "시니어", "github.com/gogildong", List.of(new InterestResponseDto(1L, "미디어")),
                        List.of(new SkillResponseDto(1L, "Spring", Skill.SkillSort.BACKEND)), LocalDateTime.now(), LocalDateTime.now(),
                        List.of(new ArticleResponseDto(
                                1L, "제목1", 0, false,
                                "20221109", "20221210", 3, 3,
                                1L, "고길동", LocalDateTime.now(),
                                LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"해쉬태그1")),
                                List.of(new InterestResponseDto(1L,"교육")),List.of(new SkillResponseDto(1L,"JAVA", Skill.SkillSort.BACKEND))
                        )), List.of(new ArticleResponseDto(
                        1L, "제목1", 0, false,
                        "20221109", "20221210", 3, 3,
                        1L, "고길동", LocalDateTime.now(),
                        LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"해쉬태그1")),
                        List.of(new InterestResponseDto(1L,"교육")),List.of(new SkillResponseDto(1L,"JAVA", Skill.SkillSort.BACKEND))
                )));

        given(mapper.memberPatchDtoToMember(Mockito.any(MemberPatchDto.class)))
                .willReturn(new Member());

        given(memberService.updateMember(Mockito.any(Member.class)))
                .willReturn(new Member());

        given(mapper.memberToMemberDetailResponseDto(Mockito.any(Member.class)))
                .willReturn(detailResponseDto);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/members/{member-id}", memberId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(memberId))
//                .andExpect(jsonPath("$.data.passwordCheck").value(patchDto.getPasswordCheck()))
                .andExpect(jsonPath("$.data.name").value(patchDto.getName()))
                .andExpect(jsonPath("$.data.description").value(patchDto.getDescription()))
                .andExpect(jsonPath("$.data.level").value(patchDto.getLevel()))
                .andExpect(jsonPath("$.data.github").value(patchDto.getGithub()))
                .andDo(document("patch-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("member-id").description("회원 식별자")),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored(),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("회원 비밀번호"),
                                        fieldWithPath("passwordCheck").type(JsonFieldType.STRING).description("비밀번호 재확인"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("회원 소개"),
                                        fieldWithPath("level").type(JsonFieldType.STRING).description("회원 티어"),
                                        fieldWithPath("github").type(JsonFieldType.STRING).description("회원 깃허브"),

                                        fieldWithPath("memberInterests").type(JsonFieldType.ARRAY).description("회원 관심분야"),
                                        fieldWithPath("memberInterests[].interestName").type(JsonFieldType.STRING).description("회원 관심분야 이름"),

                                        fieldWithPath("memberSkills").type(JsonFieldType.ARRAY).description("회원 기술스택"),
                                        fieldWithPath("memberSkills[].skillName").type(JsonFieldType.STRING).description("회원 기술스택 이름")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.description").type(JsonFieldType.STRING).description("회원 소개"),
                                        fieldWithPath("data.level").type(JsonFieldType.STRING).description("회원 티어"),
                                        fieldWithPath("data.github").type(JsonFieldType.STRING).description("회원 깃허브"),

                                        fieldWithPath("data.interests").type(JsonFieldType.ARRAY).description("회원 관심분야 정보"),
                                        fieldWithPath("data.interests[].interestId").type(JsonFieldType.NUMBER).description("관심분야 식별자"),
                                        fieldWithPath("data.interests[].name").type(JsonFieldType.STRING).description("관심분야 이름"),

                                        fieldWithPath("data.skills").type(JsonFieldType.ARRAY).description("회원 기술스택 정보"),
                                        fieldWithPath("data.skills[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.skills[].name").type(JsonFieldType.STRING).description("기술스택 이름"),
                                        fieldWithPath("data.skills[].skillSort").type(JsonFieldType.STRING).description("회원 기술스택 종류"),

                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정 날짜"),

                                        fieldWithPath("data.articles").type(JsonFieldType.ARRAY).description("회원 작성글 정보"),
                                        fieldWithPath("data.articles[].articleId").type(JsonFieldType.NUMBER).description("작성글 식별자"),
                                        fieldWithPath("data.articles[].title").type(JsonFieldType.STRING).description("작성글 제목"),
                                        fieldWithPath("data.articles[].views").type(JsonFieldType.NUMBER).description("작성글 조회수"),
                                        fieldWithPath("data.articles[].isCompleted").type(JsonFieldType.BOOLEAN).description("작성글 모집종료여부"),
                                        fieldWithPath("data.articles[].startDay").type(JsonFieldType.STRING).description("작성글 프로젝트 개시일"),
                                        fieldWithPath("data.articles[].endDay").type(JsonFieldType.STRING).description("작성글 프로젝트 마감일"),
                                        fieldWithPath("data.articles[].backend").type(JsonFieldType.NUMBER).description("작성글 백엔드 인원수").optional(),
                                        fieldWithPath("data.articles[].frontend").type(JsonFieldType.NUMBER).description("작성글 프론트 인원수").optional(),
                                        fieldWithPath("data.articles[].memberId").type(JsonFieldType.NUMBER).description("작성글 회원 식별자"),
                                        fieldWithPath("data.articles[].memberName").type(JsonFieldType.STRING).description("작성글 회원 이름"),
                                        fieldWithPath("data.articles[].createdAt").type(JsonFieldType.STRING).description("작성글 생성 날짜"),
                                        fieldWithPath("data.articles[].modifiedAt").type(JsonFieldType.STRING).description("작성글 수정 날짜"),
                                        fieldWithPath("data.articles[].heartCount").type(JsonFieldType.NUMBER).description("작성글 좋아요수"),
                                        fieldWithPath("data.articles[].answerCount").type(JsonFieldType.NUMBER).description("작성글 답변수"),

                                        fieldWithPath("data.articles[].hashtags").type(JsonFieldType.ARRAY).description("태그 정보"),
                                        fieldWithPath("data.articles[].hashtags[].hashtagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data.articles[].hashtags[].name").type(JsonFieldType.STRING).description("태그 내용"),

                                        fieldWithPath("data.articles[].interests").type(JsonFieldType.ARRAY).description("관심분야 정보"),
                                        fieldWithPath("data.articles[].interests[].interestId").type(JsonFieldType.NUMBER).description("관심분야 식별자"),
                                        fieldWithPath("data.articles[].interests[].name").type(JsonFieldType.STRING).description("관심분야 내용"),

                                        fieldWithPath("data.articles[].skills").type(JsonFieldType.ARRAY).description("기술스택 정보"),
                                        fieldWithPath("data.articles[].skills[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.articles[].skills[].name").type(JsonFieldType.STRING).description("기술스택 내용"),
                                        fieldWithPath("data.articles[].skills[].skillSort").type(JsonFieldType.STRING).description("기술스택 종류"),

                                        fieldWithPath("data.heartArticles").type(JsonFieldType.ARRAY).description("회원 좋아요 게시글 정보"),
                                        fieldWithPath("data.heartArticles[].articleId").type(JsonFieldType.NUMBER).description("좋아요 게시글 식별자"),
                                        fieldWithPath("data.heartArticles[].title").type(JsonFieldType.STRING).description("좋아요 게시글 제목"),
                                        fieldWithPath("data.heartArticles[].views").type(JsonFieldType.NUMBER).description("좋아요 게시글 조회수"),
                                        fieldWithPath("data.heartArticles[].isCompleted").type(JsonFieldType.BOOLEAN).description("좋아요 게시글 모집종료여부"),
                                        fieldWithPath("data.heartArticles[].startDay").type(JsonFieldType.STRING).description("좋아요 게시글 프로젝트 개시일"),
                                        fieldWithPath("data.heartArticles[].endDay").type(JsonFieldType.STRING).description("좋아요 게시글 프로젝트 마감일"),
                                        fieldWithPath("data.heartArticles[].backend").type(JsonFieldType.NUMBER).description("좋아요 게시글 백엔드 인원수").optional(),
                                        fieldWithPath("data.heartArticles[].frontend").type(JsonFieldType.NUMBER).description("좋아요 게시글 프론트 인원수").optional(),
                                        fieldWithPath("data.heartArticles[].memberId").type(JsonFieldType.NUMBER).description("좋아요 게시글 회원 식별자"),
                                        fieldWithPath("data.heartArticles[].memberName").type(JsonFieldType.STRING).description("좋아요 게시글 회원 이름"),
                                        fieldWithPath("data.heartArticles[].createdAt").type(JsonFieldType.STRING).description("좋아요 게시글 생성 날짜"),
                                        fieldWithPath("data.heartArticles[].modifiedAt").type(JsonFieldType.STRING).description("좋아요 게시글 수정 날짜"),
                                        fieldWithPath("data.heartArticles[].heartCount").type(JsonFieldType.NUMBER).description("좋아요 게시글 좋아요수"),
                                        fieldWithPath("data.heartArticles[].answerCount").type(JsonFieldType.NUMBER).description("좋아요 게시글 답변수"),

                                        fieldWithPath("data.heartArticles[].hashtags").type(JsonFieldType.ARRAY).description("태그 정보"),
                                        fieldWithPath("data.heartArticles[].hashtags[].hashtagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data.heartArticles[].hashtags[].name").type(JsonFieldType.STRING).description("태그 내용"),

                                        fieldWithPath("data.heartArticles[].interests").type(JsonFieldType.ARRAY).description("관심분야 정보"),
                                        fieldWithPath("data.heartArticles[].interests[].interestId").type(JsonFieldType.NUMBER).description("관심분야 식별자"),
                                        fieldWithPath("data.heartArticles[].interests[].name").type(JsonFieldType.STRING).description("관심분야 내용"),

                                        fieldWithPath("data.heartArticles[].skills").type(JsonFieldType.ARRAY).description("기술스택 정보"),
                                        fieldWithPath("data.heartArticles[].skills[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.heartArticles[].skills[].name").type(JsonFieldType.STRING).description("기술스택 내용"),
                                        fieldWithPath("data.heartArticles[].skills[].skillSort").type(JsonFieldType.STRING).description("기술스택 종류")
                                )
                        )
                ));

    }

    @Test
    void getMember() throws Exception{
        //given
        long memberId = 1;

        MemberDetailResponseDto detailResponseDto = new MemberDetailResponseDto(memberId, "hgd@gmail.com",
                "홍길동","길동이입니다","학생", "github.com/honggildong",
                List.of(new InterestResponseDto(1L, "교육")), List.of(new SkillResponseDto(1L, "JAVA", Skill.SkillSort.BACKEND)),
                LocalDateTime.now(), LocalDateTime.now(),List.of(new ArticleResponseDto(
                1L, "제목1", 0, false,
                "20221109", "20221210", 3, 3,
                1L, "고길동", LocalDateTime.now(),
                LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"해쉬태그1")),
                List.of(new InterestResponseDto(1L,"교육")),List.of(new SkillResponseDto(1L,"JAVA", Skill.SkillSort.BACKEND))
        )),
                List.of(new ArticleResponseDto(
                        1L, "제목1", 0, false,
                        "20221109", "20221210", 3, 3,
                        1L, "고길동", LocalDateTime.now(),
                        LocalDateTime.now(),0, 1, List.of(new HashtagResponseDto(1L,"해쉬태그1")),
                        List.of(new InterestResponseDto(1L,"교육")),List.of(new SkillResponseDto(1L,"JAVA", Skill.SkillSort.BACKEND))
                )));

        given(memberService.findMember(Mockito.anyLong()))
                .willReturn(new Member());

        given(mapper.memberToMemberDetailResponseDto(Mockito.any(Member.class)))
                .willReturn(detailResponseDto);

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/members/{member-id}", memberId)
                                .accept(MediaType.APPLICATION_JSON)
                );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(memberId))
                .andDo(document("get-member",
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("member-id").description("회원 식별자")),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.description").type(JsonFieldType.STRING).description("회원 소개"),
                                        fieldWithPath("data.level").type(JsonFieldType.STRING).description("회원 티어"),
                                        fieldWithPath("data.github").type(JsonFieldType.STRING).description("회원 깃허브"),

                                        fieldWithPath("data.interests").type(JsonFieldType.ARRAY).description("회원 관심분야 정보"),
                                        fieldWithPath("data.interests[].interestId").type(JsonFieldType.NUMBER).description("관심분야 식별자"),
                                        fieldWithPath("data.interests[].name").type(JsonFieldType.STRING).description("관심분야 이름"),

                                        fieldWithPath("data.skills").type(JsonFieldType.ARRAY).description("회원 기술스택 정보"),
                                        fieldWithPath("data.skills[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.skills[].name").type(JsonFieldType.STRING).description("기술스택 이름"),
                                        fieldWithPath("data.skills[].skillSort").type(JsonFieldType.STRING).description("회원 기술스택 종류"),

                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정 날짜"),

                                        fieldWithPath("data.articles").type(JsonFieldType.ARRAY).description("회원 작성글 정보"),
                                        fieldWithPath("data.articles[].articleId").type(JsonFieldType.NUMBER).description("작성글 식별자"),
                                        fieldWithPath("data.articles[].title").type(JsonFieldType.STRING).description("작성글 제목"),
                                        fieldWithPath("data.articles[].views").type(JsonFieldType.NUMBER).description("작성글 조회수"),
                                        fieldWithPath("data.articles[].isCompleted").type(JsonFieldType.BOOLEAN).description("작성글 모집종료여부"),
                                        fieldWithPath("data.articles[].startDay").type(JsonFieldType.STRING).description("작성글 프로젝트 개시일"),
                                        fieldWithPath("data.articles[].endDay").type(JsonFieldType.STRING).description("작성글 프로젝트 마감일"),
                                        fieldWithPath("data.articles[].backend").type(JsonFieldType.NUMBER).description("작성글 백엔드 인원수").optional(),
                                        fieldWithPath("data.articles[].frontend").type(JsonFieldType.NUMBER).description("작성글 프론트 인원수").optional(),
                                        fieldWithPath("data.articles[].memberId").type(JsonFieldType.NUMBER).description("작성글 회원 식별자"),
                                        fieldWithPath("data.articles[].memberName").type(JsonFieldType.STRING).description("작성글 회원 이름"),
                                        fieldWithPath("data.articles[].createdAt").type(JsonFieldType.STRING).description("작성글 생성 날짜"),
                                        fieldWithPath("data.articles[].modifiedAt").type(JsonFieldType.STRING).description("작성글 수정 날짜"),
                                        fieldWithPath("data.articles[].heartCount").type(JsonFieldType.NUMBER).description("작성글 좋아요수"),
                                        fieldWithPath("data.articles[].answerCount").type(JsonFieldType.NUMBER).description("작성글 답변수"),

                                        fieldWithPath("data.articles[].hashtags").type(JsonFieldType.ARRAY).description("태그 정보"),
                                        fieldWithPath("data.articles[].hashtags[].hashtagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data.articles[].hashtags[].name").type(JsonFieldType.STRING).description("태그 내용"),

                                        fieldWithPath("data.articles[].interests").type(JsonFieldType.ARRAY).description("관심분야 정보"),
                                        fieldWithPath("data.articles[].interests[].interestId").type(JsonFieldType.NUMBER).description("관심분야 식별자"),
                                        fieldWithPath("data.articles[].interests[].name").type(JsonFieldType.STRING).description("관심분야 내용"),

                                        fieldWithPath("data.articles[].skills").type(JsonFieldType.ARRAY).description("기술스택 정보"),
                                        fieldWithPath("data.articles[].skills[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.articles[].skills[].name").type(JsonFieldType.STRING).description("기술스택 내용"),
                                        fieldWithPath("data.articles[].skills[].skillSort").type(JsonFieldType.STRING).description("기술스택 종류"),

                                        fieldWithPath("data.heartArticles").type(JsonFieldType.ARRAY).description("회원 좋아요 게시글 정보"),
                                        fieldWithPath("data.heartArticles[].articleId").type(JsonFieldType.NUMBER).description("좋아요 게시글 식별자"),
                                        fieldWithPath("data.heartArticles[].title").type(JsonFieldType.STRING).description("좋아요 게시글 제목"),
                                        fieldWithPath("data.heartArticles[].views").type(JsonFieldType.NUMBER).description("좋아요 게시글 조회수"),
                                        fieldWithPath("data.heartArticles[].isCompleted").type(JsonFieldType.BOOLEAN).description("좋아요 게시글 모집종료여부"),
                                        fieldWithPath("data.heartArticles[].startDay").type(JsonFieldType.STRING).description("좋아요 게시글 프로젝트 개시일"),
                                        fieldWithPath("data.heartArticles[].endDay").type(JsonFieldType.STRING).description("좋아요 게시글 프로젝트 마감일"),
                                        fieldWithPath("data.heartArticles[].backend").type(JsonFieldType.NUMBER).description("좋아요 게시글 백엔드 인원수").optional(),
                                        fieldWithPath("data.heartArticles[].frontend").type(JsonFieldType.NUMBER).description("좋아요 게시글 프론트 인원수").optional(),
                                        fieldWithPath("data.heartArticles[].memberId").type(JsonFieldType.NUMBER).description("좋아요 게시글 회원 식별자"),
                                        fieldWithPath("data.heartArticles[].memberName").type(JsonFieldType.STRING).description("좋아요 게시글 회원 이름"),
                                        fieldWithPath("data.heartArticles[].createdAt").type(JsonFieldType.STRING).description("좋아요 게시글 생성 날짜"),
                                        fieldWithPath("data.heartArticles[].modifiedAt").type(JsonFieldType.STRING).description("좋아요 게시글 수정 날짜"),
                                        fieldWithPath("data.heartArticles[].heartCount").type(JsonFieldType.NUMBER).description("좋아요 게시글 좋아요수"),
                                        fieldWithPath("data.heartArticles[].answerCount").type(JsonFieldType.NUMBER).description("좋아요 게시글 답변수"),

                                        fieldWithPath("data.heartArticles[].hashtags").type(JsonFieldType.ARRAY).description("태그 정보"),
                                        fieldWithPath("data.heartArticles[].hashtags[].hashtagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data.heartArticles[].hashtags[].name").type(JsonFieldType.STRING).description("태그 내용"),

                                        fieldWithPath("data.heartArticles[].interests").type(JsonFieldType.ARRAY).description("관심분야 정보"),
                                        fieldWithPath("data.heartArticles[].interests[].interestId").type(JsonFieldType.NUMBER).description("관심분야 식별자"),
                                        fieldWithPath("data.heartArticles[].interests[].name").type(JsonFieldType.STRING).description("관심분야 내용"),

                                        fieldWithPath("data.heartArticles[].skills").type(JsonFieldType.ARRAY).description("기술스택 정보"),
                                        fieldWithPath("data.heartArticles[].skills[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data.heartArticles[].skills[].name").type(JsonFieldType.STRING).description("기술스택 내용"),
                                        fieldWithPath("data.heartArticles[].skills[].skillSort").type(JsonFieldType.STRING).description("기술스택 종류")
                                )
                        )
                ));
    }

    @Test
    void getMembers() throws Exception{
        //given
        int page = 1;
        int size = 5;

        List<Member> members = List.of(new Member(), new Member(), new Member());

        given(memberService.findMembers(Mockito.anyInt(), Mockito.anyInt()))
                .willReturn(new PageImpl<>(members, PageRequest
                                .of(page-1, size, Sort.by("memberId")
                                        .descending()), members.size()));

        given(mapper.membersToMemberResponseDtos(Mockito.anyList()))
                .willReturn(List.of(
                        new MemberResponseDto(
                                1L,"hgd@gmail.com", "홍길동", "길동이입니다","학생",
                                "github.com/honggildong", List.of(new InterestResponseDto(1L, "교육")),
                                List.of(new SkillResponseDto(1L, "JAVA", Skill.SkillSort.BACKEND)), LocalDateTime.now(), LocalDateTime.now()),
                        new MemberResponseDto(2L, "ggd@gmail.com", "고길동", "길동이아닙니다",
                                "시니어", "github.com/gogildong", List.of(new InterestResponseDto(1L, "미디어")),
                                List.of(new SkillResponseDto(1L, "Spring", Skill.SkillSort.BACKEND)), LocalDateTime.now(), LocalDateTime.now()),
                       new MemberResponseDto(3L, "kgd@gmail.com", "김길동", "길동",
                               "주니어", "github.com/kimgildong",List.of(new InterestResponseDto(1L, "제조")),
                               List.of(new SkillResponseDto(1L, "Nodejs", Skill.SkillSort.BACKEND)), LocalDateTime.now(), LocalDateTime.now())
                        ));

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/members/?page={page}&size={size}", page, size)
                                .accept(MediaType.APPLICATION_JSON)
                );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andDo(document("get-members",
                        preprocessResponse(prettyPrint()),
                        requestParameters(List.of(parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 크기"))),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data[].email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data[].description").type(JsonFieldType.STRING).description("회원 소개"),
                                        fieldWithPath("data[].level").type(JsonFieldType.STRING).description("회원 티어"),
                                        fieldWithPath("data[].github").type(JsonFieldType.STRING).description("회원 깃허브"),

                                        fieldWithPath("data[].interests").type(JsonFieldType.ARRAY).description("회원 관심분야 정보"),
                                        fieldWithPath("data[].interests[].interestId").type(JsonFieldType.NUMBER).description("관심분야 식별자"),
                                        fieldWithPath("data[].interests[].name").type(JsonFieldType.STRING).description("관심분야 이름"),

                                        fieldWithPath("data[].skills").type(JsonFieldType.ARRAY).description("회원 기술스택 정보"),
                                        fieldWithPath("data[].skills[].skillId").type(JsonFieldType.NUMBER).description("기술스택 식별자"),
                                        fieldWithPath("data[].skills[].name").type(JsonFieldType.STRING).description("기술스택 이름"),
                                        fieldWithPath("data[].skills[].skillSort").type(JsonFieldType.STRING).description("기술스택 종류"),

                                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("수정 날짜"),

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
    void deleteMember() throws Exception{
        //given
        long memberId = 1;

        //when
        ResultActions actions =
                mockMvc.perform(
                        delete("/members/{member-id}",memberId)
                );

        //then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-member",
                        pathParameters(parameterWithName("member-id").description("회원 식별자"))
                ));
    }
}