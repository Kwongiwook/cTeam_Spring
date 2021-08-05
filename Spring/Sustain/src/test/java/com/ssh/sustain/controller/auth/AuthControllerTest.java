package com.ssh.sustain.controller.auth;

import com.ssh.sustain.commom.BaseControllerTest;
import com.ssh.sustain.dto.user.VerifyDto;
import com.ssh.sustain.model.user.Verify;
import com.ssh.sustain.repository.VerifyRepository;
import com.ssh.sustain.service.user.EmailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends BaseControllerTest {

    @Autowired
    VerifyRepository verifyRepository;

    @Test
    public void getEmailCode() throws Exception {
        mockMvc.perform(get("/auth/{email}", "gitmiu@gmail.com"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void verifyCode() throws Exception {
        Optional<Verify> verify = verifyRepository.findById("gitmiu@gmail.com");
        String code = verify.get().getCode();

        VerifyDto verifyDto = VerifyDto
                .builder()
                .email("gitmiu@gmail.com")
                .code(code)
                .build();

        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(verifyDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void verifyCodeFail() throws Exception {
        mockMvc.perform(post("/auth")
                        .content("tjsltjejsljdljsa"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}