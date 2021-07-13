package com.ssh.sustain;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(BootApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
