package com._on1bet.authservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/v1/api/")
public class AuthController {

    @PostMapping("register")
    public String registerHandler() {
        return null;
    }
    
}
