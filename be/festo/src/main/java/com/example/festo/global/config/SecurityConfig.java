package com.example.festo.global.config;

import com.example.festo.global.auth.JwtAuthenticationFilter;
import com.example.festo.global.auth.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final WebConfig webMvcConfig;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(webMvcConfig.corsConfigurationSource())
                .and()
                .httpBasic().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                                 .requestMatchers(AntPathRequestMatcher.antMatcher("/error/**")).permitAll()
                                 .requestMatchers(AntPathRequestMatcher.antMatcher("/mock/**")).permitAll()
                                 .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/v1/login")).permitAll()
                                 .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin().disable()
                .logout().disable()
                .headers(headers -> headers.frameOptions().disable())
                .csrf().disable()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);
        return http.build();
    }
}
