package com._on1bet.authservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com._on1bet.authservice.service.RegisterService;

import jakarta.annotation.Nonnull;

@RestController
@RequestMapping("auth/v1/register/")
public class RegisterController {

    private  final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService)
    {
        this.registerService = registerService;
    }

    @PostMapping("verify/mobileno")
    public String firstTimeMobileNoRegisterHandler(@RequestParam("mobileno")  @Nonnull Long mobileNo,
     @RequestParam("countrycode") @Nonnull Integer countrycodeid) {
        return "Working fine!";
    }
    
}
