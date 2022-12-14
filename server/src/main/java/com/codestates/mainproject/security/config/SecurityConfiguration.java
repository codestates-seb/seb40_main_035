package com.codestates.mainproject.security.config;

import com.codestates.mainproject.security.auth.handler.MemberAuthenticationEntryPoint;
import com.codestates.mainproject.security.auth.jwt.MemberDetailsService;
import com.codestates.mainproject.security.auth.filter.JwtAuthenticationFilter;
import com.codestates.mainproject.security.auth.jwt.JwtTokenizer;
import com.codestates.mainproject.security.oauth2.OAuth2MemberSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration {

    private final JwtTokenizer jwtTokenizer;
    private final MemberDetailsService memberDetailsService;
    private final MemberAuthenticationEntryPoint memberAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()

                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .exceptionHandling()
                .authenticationEntryPoint(memberAuthenticationEntryPoint)
                .and()

                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/oauth2/authorization/github", "/members/login", "/members/signup", "/members/signup/**", "/members/find-password", "/docs/*", "/h2/**", "/h2").permitAll()
                .antMatchers(HttpMethod.GET, "/login/oauth2/code/github", "/articles/**", "/articles", "/answers/**", "/comments/**", "/members/{member-id}").permitAll()
                .anyRequest().authenticated()
                .and()

                .oauth2Login(oauth2 -> oauth2.successHandler(new OAuth2MemberSuccessHandler(jwtTokenizer, memberDetailsService)))
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenizer, memberDetailsService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Refresh", "memberId"));
        configuration.setAllowedHeaders(Arrays.asList("*")); // ?????? ????????? ?????? HTTP ????????? ??????
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedOrigins(Arrays.asList("http://seb-main-035-client.s3-website.ap-northeast-2.amazonaws.com/")); // ?????? ????????? ?????? HTTP ????????? ??????
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS")); //??????????????? ????????? HTTP Method??? ?????? HTTP ????????? ??????

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // ?????? URL ????????? ????????? CORS ????????? ??????
        return source;
    }
}
