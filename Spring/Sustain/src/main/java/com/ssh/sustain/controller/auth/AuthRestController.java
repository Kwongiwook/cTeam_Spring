package com.ssh.sustain.controller.auth;

import com.ssh.sustain.dto.user.VerifyDto;
import com.ssh.sustain.model.user.Verify;
import com.ssh.sustain.repository.VerifyRepository;
import com.ssh.sustain.service.user.EmailService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RequestMapping(value = "/auth")
@RestController
public class AuthRestController {

    private final EmailService emailService;
    private final VerifyRepository verifyRepository;

    public AuthRestController(EmailService emailService, VerifyRepository verifyRepository) {
        this.emailService = emailService;
        this.verifyRepository = verifyRepository;
    }

    @GetMapping(value = "{email}")
    public ResponseEntity<HttpHeaders> getVerifyCode(@PathVariable @NotNull String email) throws Exception {
        verifyRepository.save(new Verify(email, emailService.sendEmail(email), 300L));
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<HttpHeaders> verifyCode(@RequestBody @Valid VerifyDto dto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        Verify verify = verifyRepository.findById(dto.getEmail()).get();

        return verify.getCode().equals(dto.getCode()) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

}
