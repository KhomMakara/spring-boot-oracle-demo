package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "forward:/index.html";
    }
    
    @GetMapping("/websocket")
    public String websocketTest() {
        return "forward:/websocket-test.html";
    }
}
