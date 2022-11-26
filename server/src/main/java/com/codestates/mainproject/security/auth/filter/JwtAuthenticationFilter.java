package com.codestates.mainproject.security.auth.filter;

import com.codestates.mainproject.security.auth.jwt.MemberDetailsService;
import com.codestates.mainproject.security.auth.jwt.JwtTokenizer;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenizer jwtTokenizer;
    private final MemberDetailsService memberDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String access = request.getHeader("Authorization");
        String refresh = request.getHeader("Refresh");
        String requestURI = request.getRequestURI();

        //accee토큰이 null이고 토큰에 Bearer를 포함지않거나
        if ((!Objects.isNull(access) && access.startsWith("Bearer ")) || requestURI.equals("/members/reissue")) {
            try {

                if (requestURI.equals("/members/reissue")) {
                    String rtkSubject = jwtTokenizer.getSubject(refresh);
                    UserDetails userDetails = memberDetailsService.loadUserByUsername(rtkSubject);
                    Authentication token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
                else {
                    String atk = access.substring(7);
                    String atkSubject = jwtTokenizer.getSubject(atk);

                    if (jwtTokenizer.isBlackList(atk)) {
                        throw new JwtException("유효하지 않은 AccessToken 입니다.");
                    }

                    UserDetails userDetails = memberDetailsService.loadUserByUsername(atkSubject);
                    Authentication token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            } catch (JwtException e) {
                request.setAttribute("exception", e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }

}
