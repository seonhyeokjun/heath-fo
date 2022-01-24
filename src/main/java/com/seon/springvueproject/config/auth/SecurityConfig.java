package com.seon.springvueproject.config.auth;

import com.seon.springvueproject.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v3/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().headers().frameOptions().disable().and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/board/{\\d+}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/board").permitAll()
                .antMatchers("/", "/h2-console/**", "/auth/client", "/api/like/**", "/api/file/**",
                        "/ws/chat", "/chat/rooms", "/api/google", "/api/naver", "/profile").permitAll()
                .antMatchers("/api/**", "/chat/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}
