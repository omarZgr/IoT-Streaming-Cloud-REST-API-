package com.example.application2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("open")
public class openController {

    @GetMapping("/welcome")
    public String welcome()
    {
        return "Welcome" ;

    } ;
}
