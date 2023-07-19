package com.win.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class Test {
    @GetMapping("")
    ResponseEntity<String> test(@RequestBody String data) {
        return ResponseEntity.status(200).body("Hello "+data);
    }
}
