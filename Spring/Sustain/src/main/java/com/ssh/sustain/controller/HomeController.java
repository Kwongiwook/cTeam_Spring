package com.ssh.sustain.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j
public class HomeController {

    @GetMapping("/")
    public String toHome(Model model) {
        model.addAttribute("greeting", "hello");
        return "index.tiles";
    }
}
