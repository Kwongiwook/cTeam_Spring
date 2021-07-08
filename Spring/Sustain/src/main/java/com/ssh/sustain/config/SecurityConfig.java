package com.ssh.sustain.config;

import com.ssh.sustain.domain.Role;
import com.ssh.sustain.security.oauth2.provider.CommonOAuth2Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Log4j
@RequiredArgsConstructor
@Configuration(value = "security")
@EnableWebSecurity(debug = true)
@PropertySource("classpath:application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //STATELESS for REST
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(header -> header
                        .frameOptions().disable())

                .authorizeRequests(request -> request
                        .antMatchers("/", "/resources/**").permitAll()
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                        .anyRequest().authenticated())

                .logout(logout -> logout
                        .logoutSuccessUrl("/"));
    }


    @Bean(name = "repository")
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = new ArrayList<>();

        registrations.add(CommonOAuth2Provider.NAVER.getBuilder("naver")
                .clientId(env.getProperty("spring.security.oauth2.client.registration.naver.client-id"))
                .clientSecret(env.getProperty("spring.security.oauth2.client.registration.naver.client-secret"))
                .jwkSetUri("temp").build());

        registrations.add(CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId(env.getProperty("spring.security.oauth2.client.registration.google.client-id"))
                .clientSecret(env.getProperty("spring.security.oauth2.client.registration.google.client-secret"))
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs").build());

        return new InMemoryClientRegistrationRepository(registrations);
    }

}