package com.codestates.mainproject.security.oauth2;

import com.codestates.mainproject.domain.member.entity.Member;
import com.codestates.mainproject.exception.BusinessLogicException;
import com.codestates.mainproject.security.auth.jwt.JwtTokenizer;
import com.codestates.mainproject.security.auth.jwt.MemberDetails;
import com.codestates.mainproject.security.auth.jwt.MemberDetailsService;
import com.codestates.mainproject.security.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@RequiredArgsConstructor
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final MemberDetailsService memberDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String html_url = oAuth2User.getAttributes().get("html_url").toString();

        try {
            Member member = memberDetailsService.findByGithub(html_url);
            TokenResponse tokenResponse = jwtTokenizer.createTokensByLogin(member);
            MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
            queryParams.add("Authorization", "Bearer " + tokenResponse.getAcToken());
            queryParams.add("Refresh", tokenResponse.getRfToken());
            queryParams.add("memberId", String.valueOf(member.getMemberId()));
            getRedirectStrategy().sendRedirect(request, response, createURI(queryParams).toString());
        } catch (BusinessLogicException e) {
            MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
            queryParams.add("github", html_url);
            getRedirectStrategy().sendRedirect(request, response, createURI(queryParams).toString());
        }
    }

    private URI createURI(MultiValueMap<String, String> queryParams) {
        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("seb-main-035-client.s3-website.ap-northeast-2.amazonaws.com")
//                .port(3000)
                .path("/receive-token")
                .queryParams(queryParams)
                .build()
                .toUri();
    }
}
