package com.codestates.mainproject.domain.member.controller;

import com.codestates.mainproject.domain.article.dto.ArticleSimpleResponseDto;
import com.codestates.mainproject.domain.article.entity.Article;
import com.codestates.mainproject.domain.heart.entity.Heart;
import com.codestates.mainproject.domain.member.dto.MemberDetailResponseDto;
import com.codestates.mainproject.domain.member.dto.MemberPatchDto;
import com.codestates.mainproject.domain.member.dto.MemberPostDto;
import com.codestates.mainproject.domain.member.dto.MemberResponseDto;
import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.domain.member.mapper.MemberMapper;
import com.codestates.mainproject.domain.member.service.MemberService;
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

import static org.junit.jupiter.api.Assertions.*;
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

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    void postMember() throws Exception{
        //given
        MemberPostDto postDto = new MemberPostDto(
                "hgd@gmail.com", "홍길동", "hgd1234@","2ndPass");
        String content = gson.toJson(postDto);
        MemberDetailResponseDto detailResponseDto= new MemberDetailResponseDto(
                1L,"hgd@gmail.com", "hgd1234!", "홍길동","길동이입니다","학생",
                List.of("Java", "SpringMVC", "SpringJPA"),
                List.of("미디어"),
                "github.com/honggildong",
                LocalDateTime.of(2022,11,10,11,30),
                LocalDateTime.now(),
                List.of(new ArticleSimpleResponseDto(1L,"제목1")),
                List.of(new ArticleSimpleResponseDto(2L,"제목2"))
        );

        given(mapper.memberPostDtoToMember(Mockito.any(MemberPostDto.class)))
                .willReturn(new Member());
        given(memberService.createMember(Mockito.any(Member.class)))
                .willReturn(new Member());
        given(mapper.memberToMemberDetailResponseDto(Mockito.any(Member.class)))
                .willReturn(detailResponseDto);

        //when
        ResultActions actions =
                mockMvc.perform(
                        post("/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value(postDto.getEmail()))
                .andExpect(jsonPath("$.data.name").value(postDto.getName()))
                .andExpect(jsonPath("$.data.password").value(postDto.getPassword()))
                .andExpect(jsonPath("$.data.passwordCheck").value(postDto.getPasswordCheck()))
                .andDo(document("post-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("회원 비밀번호"),
                                        fieldWithPath("passwordCheck").type(JsonFieldType.STRING).description("2차 비밀번호")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data.password").type(JsonFieldType.STRING).description("회원 비밀번호"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.description").type(JsonFieldType.STRING).description("회원 소개"),
                                        fieldWithPath("data.level").type(JsonFieldType.STRING).description("회원 티어"),
                                        fieldWithPath("data.stack").type(JsonFieldType.ARRAY).description("회원 기술스택"),
                                        fieldWithPath("data.field").type(JsonFieldType.ARRAY).description("회원 산업분야"),
                                        fieldWithPath("data.github").type(JsonFieldType.STRING).description("회원 깃허브"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정 날짜"),
                                        fieldWithPath("data.article").type(JsonFieldType.ARRAY).description("회원 작성글"),
                                        fieldWithPath("data.hearts").type(JsonFieldType.ARRAY).description("회원 좋아요")
                                )
                        )
                ));
    }

    @Test
    void patchMember() throws Exception{
        //given
        long memberId = 1L;
        MemberPatchDto patchDto = new MemberPatchDto();
        patchDto.setPassword("hgd4321@");
        patchDto.setPasswordCheck("pass2nd");
        patchDto.setName("고길동");
        patchDto.setDescription("홍길동아닙니다");
        patchDto.setLevel("시니어");
        patchDto.setStack(List.of("Python","R","Pytorch"));
        patchDto.setField(List.of("헬스케어"));
        patchDto.setGithub("github.com/gogildong");

        String content = gson.toJson(patchDto);

        MemberDetailResponseDto detailResponseDto =
                new MemberDetailResponseDto(1L, "hgd@gmail.com","hgd1234@","고길동",
                        "홍길동아닙니다", "시니어", List.of("Python","R","Pytorch"),List.of("헬스케어"),
                        "github.com/gogildong",LocalDateTime.of(2022,11,10,11,30),
                        LocalDateTime.now(),List.of(new ArticleSimpleResponseDto(2L,"제목2")),
                        List.of(new ArticleSimpleResponseDto(1L,"제목1")));

        given(mapper.memberPatchDtoToMember(Mockito.any(MemberPatchDto.class)))
                .willReturn(new Member());

        given(memberService.updateMember(Mockito.any(Member.class)))
                .willReturn(new Member());

        given(mapper.memberToMemberDetailResponseDto(Mockito.any(Member.class)))
                .willReturn(detailResponseDto);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/member/{member-id", memberId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(memberId))
                .andExpect(jsonPath("$.data.password").value(patchDto.getPassword()))
                .andExpect(jsonPath("$.data.passwordCheck").value(patchDto.getPasswordCheck()))
                .andExpect(jsonPath("$.data.name").value(patchDto.getName()))
                .andExpect(jsonPath("$.data.description").value(patchDto.getDescription()))
                .andExpect(jsonPath("$.data.level").value(patchDto.getLevel()))
                .andExpect(jsonPath("$.data.stack").value(patchDto.getStack()))
                .andExpect(jsonPath("$.data.field").value(patchDto.getField()))
                .andExpect(jsonPath("$.data.github").value(patchDto.getGithub()))
                .andDo(document("patch-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("member-id").description("회원 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored(),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("회원 비밀번호"),
                                        fieldWithPath("passwordCheck").type(JsonFieldType.STRING).description("2차 비밀번호"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("회원 소개"),
                                        fieldWithPath("level").type(JsonFieldType.STRING).description("회원 티어"),
                                        fieldWithPath("stack").type(JsonFieldType.ARRAY).description("회원 기술스택"),
                                        fieldWithPath("field").type(JsonFieldType.ARRAY).description("회원 산업분야"),
                                        fieldWithPath("github").type(JsonFieldType.STRING).description("회원 깃허브")
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("회원 이메일"),
                                        fieldWithPath("data.password").type(JsonFieldType.STRING).description("회원 비밀번호"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.description").type(JsonFieldType.STRING).description("회원 소개"),
                                        fieldWithPath("data.level").type(JsonFieldType.STRING).description("회원 티어"),
                                        fieldWithPath("data.stack").type(JsonFieldType.ARRAY).description("회원 기술스택"),
                                        fieldWithPath("data.field").type(JsonFieldType.ARRAY).description("회원 산업분야"),
                                        fieldWithPath("data.github").type(JsonFieldType.STRING).description("회원 깃허브"),
                                        fieldWithPath("data.article").type(JsonFieldType.ARRAY).description("회원 작성글"),
                                        fieldWithPath("data.hearts").type(JsonFieldType.ARRAY).description("회원 좋아요"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정 날짜")
                                )
                        )
                ));

    }

    @Test
    void getMember() throws Exception{
        //given
        long memberId = 1;

        MemberDetailResponseDto detailResponseDto = new MemberDetailResponseDto(memberId, "hgd@gmail.com", "hgd1234!", "홍길동","길동이입니다","학생",
                List.of("Java", "SpringMVC", "SpringJPA"),
                List.of("미디어"),"github.com/honggildong",
                LocalDateTime.of(2022,11,10,11,30),
                LocalDateTime.now(),List.of(new ArticleSimpleResponseDto(1L,"제목1")),
                List.of(new ArticleSimpleResponseDto(2L,"제목2")));

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
                                        fieldWithPath("data.password").type(JsonFieldType.STRING).description("회원 비밀번호"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data.description").type(JsonFieldType.STRING).description("회원 소개"),
                                        fieldWithPath("data.level").type(JsonFieldType.STRING).description("회원 티어"),
                                        fieldWithPath("data.stack").type(JsonFieldType.ARRAY).description("회원 기술스택"),
                                        fieldWithPath("data.field").type(JsonFieldType.ARRAY).description("회원 산업분야"),
                                        fieldWithPath("data.github").type(JsonFieldType.STRING).description("회원 깃허브"),
                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정 날짜"),
                                        fieldWithPath("data.article").type(JsonFieldType.ARRAY).description("회원 작성글"),
                                        fieldWithPath("data.hearts").type(JsonFieldType.ARRAY).description("회원 좋아요")
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
                        new MemberResponseDto(1L, "hgd@gmail.com", "hgd1234!", "홍길동","길동이입니다","학생",
                                List.of("Java", "SpringMVC", "SpringJPA"),
                                List.of("미디어"),"github.com/honggildong",
                                LocalDateTime.of(2022,11,10,11,30),
                                LocalDateTime.now()),
                        new MemberResponseDto(2L, "ggd@gmail.com", "ggd1234@", "고길동","길동이아닙니다","시니어",
                                List.of("Python","R","Pytorch"),
                                List.of("헬스케어"),"github.com/gogildong",
                                LocalDateTime.of(2022,11,10,11,30),
                                LocalDateTime.now()),
                       new MemberResponseDto(3L, "kgd@gmail.com", "hgd1234#", "김길동","길동","주니어",
                               List.of("Python", "SpringMVC", "Pytorch"),
                               List.of("제조"),"github.com/kimgildong",
                               LocalDateTime.of(2022,11,10,11,30),
                               LocalDateTime.now())
                    )
                );

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
                                        fieldWithPath("data[].password").type(JsonFieldType.STRING).description("회원 비밀번호"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("회원 이름"),
                                        fieldWithPath("data[].description").type(JsonFieldType.STRING).description("회원 소개"),
                                        fieldWithPath("data[].level").type(JsonFieldType.STRING).description("회원 티어"),
                                        fieldWithPath("data[].stack").type(JsonFieldType.ARRAY).description("회원 기술스택"),
                                        fieldWithPath("data[].field").type(JsonFieldType.ARRAY).description("회원 산업분야"),
                                        fieldWithPath("data[].github").type(JsonFieldType.STRING).description("회원 깃허브"),
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