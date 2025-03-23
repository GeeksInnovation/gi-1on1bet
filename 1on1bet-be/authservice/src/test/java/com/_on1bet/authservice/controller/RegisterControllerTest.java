package com._on1bet.authservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com._on1bet.authservice.service.RegisterService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegisterController.class) 
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @MockitoBean
    RegisterService registerService;
    
    @Test
    void invalidEndpoint() throws Exception {
        mockMvc.perform(post("/auth/invalid/endpoint")
        .param("mobileno", "")
        .param("countrycode", ""))
        .andExpect(status().isNotFound());
    }

    @Test
    void testFirstTimeMobileNoRegisterHandlerWithInValidValues() throws Exception {
        mockMvc.perform(post("/auth/v1/register/verify/mobileno")
                .param("mobileno", "")
                .param("countrycode", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFirstTimeMobileNoRegisterHandlerWithValidValues() throws Exception {
        mockMvc.perform(post("/auth/v1/register/verify/mobileno")
        .param("mobileno", "9876543210")
        .param("countrycode", "5"))
        .andExpect(status().isOk());
    }
    
}
