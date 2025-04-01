package com._on1bet.authservice.entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("profile_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDetails {
    
    @Id
    private Integer id;
    private String userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;

}