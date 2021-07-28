package com.ssh.sustain;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableCaching
@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(BootApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        // 이거 없으면 PasswordEncoder Bean injection 못함.
        return new BCryptPasswordEncoder();
    }

}
