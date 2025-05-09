package com._on1bet.authservice.model.request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveProfileDetailsRequest {
    
    private String userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
}
