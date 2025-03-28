package com._on1bet.authservice.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegsiterUserRequest {
    
    private Long mobileNo;
    private Integer countryCode;
    private String enteredOTP;
}
