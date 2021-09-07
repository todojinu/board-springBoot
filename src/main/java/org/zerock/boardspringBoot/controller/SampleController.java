package org.zerock.boardspringBoot.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample")
@Log4j2
public class SampleController {
    @GetMapping("/exSidebar")
    public void ex1() {
        log.info("exSidebar......");
    }

    @GetMapping("/index")
    public void index() {
        log.info("index......");
    }

    @GetMapping("/basic")
    public void basic() {
        log.info("basic......");
    }
}
