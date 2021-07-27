package com.ssh.sustain.config;

import com.ssh.sustain.security.auth.user.oauth2.OAuth2AuthenticationSuccessHandler;
import com.ssh.sustain.security.auth.user.oauth2.CustomOAuth2UserService;
import com.ssh.sustain.security.auth.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler OAuth2AuthenticationSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                    .and()

                .httpBasic()
                    .disable()

                .formLogin()
                    .disable()

                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()

                .authorizeRequests()
                    .antMatchers("/", "/static/**").permitAll()
                    .antMatchers("/login", "/register").permitAll();

        http
                .logout()
                    .deleteCookies("JSESSIONID", "accessToken");

        http
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .oauth2Login()
                    .loginPage("/login")
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .and()
                .successHandler(OAuth2AuthenticationSuccessHandler);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        // 이거 없으면 PasswordEncoder Bean injection 못함.
        return new BCryptPasswordEncoder();
    }

}