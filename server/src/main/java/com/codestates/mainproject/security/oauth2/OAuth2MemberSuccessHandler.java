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
            response.addHeader("Authorization", "Bearer " + tokenResponse.getAcToken());
            response.addHeader("Refresh", tokenResponse.getRfToken());
            response.addHeader("memberId", String.valueOf(member.getMemberId()));
        } catch (BusinessLogicException e) {
            response.addHeader("github", html_url);
        }
    }
}
