package com.jh2.jonglog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class MainController {

    @GetMapping("/main")
    public String jongMain() {
        log.info("in main Controller main method");
        return "index";
    }
}