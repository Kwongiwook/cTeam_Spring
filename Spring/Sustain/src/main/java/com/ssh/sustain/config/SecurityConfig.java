package com.ssh.sustain.config;

import com.ssh.sustain.security.auth.user.normal.CustomUserDetailsService;
import com.ssh.sustain.security.auth.user.CustomAuthenticationSuccessHandler;
import com.ssh.sustain.security.auth.user.oauth2.CustomOAuth2UserService;
import com.ssh.sustain.security.auth.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()
                    .and()

                .csrf()
                    .disable()

                .httpBasic()
                    .disable()

                .formLogin()
                    .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("pwd")
                    .successHandler(customAuthenticationSuccessHandler)
                    .and()

                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()

                .authorizeRequests()
                    .antMatchers("/", "/static/**", "/webjars/**").permitAll()
                    .antMatchers("/login", "/register").permitAll()
                    .anyRequest().authenticated();

        http
                .logout()
                    .deleteCookies("JSESSIONID", "accessToken");

        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .oauth2Login()
                    .loginPage("/login")
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                        .and()
                .successHandler(customAuthenticationSuccessHandler);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

}