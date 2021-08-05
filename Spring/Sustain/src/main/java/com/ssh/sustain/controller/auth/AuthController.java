package com.ssh.sustain.controller.auth;

import com.ssh.sustain.dto.user.NormalUserDTO;
import com.ssh.sustain.dto.user.RegisterUserDTO;
import com.ssh.sustain.repository.TokenRepository;
import com.ssh.sustain.security.auth.cookie.CookieUtil;
import com.ssh.sustain.security.auth.jwt.JwtUtil;
import com.ssh.sustain.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RequiredArgsConstructor
@Controller
public class AuthController {

    private final UserService userService;

    @GetMapping(value = "/register")
    public String toRegister() throws Exception {
        return "register";
    }

    @PostMapping(value = "/register")
    public ModelAndView register(@RequestBody RegisterUserDTO userDTO) throws Exception {
        userService.saveNormal(userDTO);
        return new ModelAndView("index");
    }

    @GetMapping(value = "/login")
    public String toLogin() {
        return "login";
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public void login(@RequestBody NormalUserDTO user) {

    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping("/testing")
    public String test() {
        return "register";
    }

}
