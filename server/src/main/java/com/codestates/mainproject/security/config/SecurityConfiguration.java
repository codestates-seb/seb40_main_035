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
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Refresh"));
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 출처에 대해 HTTP 통신을 허용
        configuration.setAllowedOrigins(Arrays.asList("http://seb-main-035-client.s3-website.ap-northeast-2.amazonaws.com/")); // 모든 출처에 대해 HTTP 통신을 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS")); //파라미터로 지정한 HTTP Method에 대한 HTTP 통신을 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 URL 앞에서 구성한 CORS 정책을 적용
        return source;
    }
}
