package com.ssh.sustain.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 작업을 위해 모든 시큐리티 설정을 제거하였습니다.
        http
                .authorizeRequests().antMatchers("/", "/css/**", "/js/**").permitAll()
                .anyRequest().permitAll();
    }
}
