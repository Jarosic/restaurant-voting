package com.myproject.restaurantvoting.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    public String hello = "hello";

    @GetMapping("/hello")
    public String hello() {
        return "<H1>Hello!<H1>";
    }
}
