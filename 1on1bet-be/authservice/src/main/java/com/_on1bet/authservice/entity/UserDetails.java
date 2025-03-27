package com._on1bet.authservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("user_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetails {

    @Id
    private Long mobileNo;
    private String userId;
    private Integer countryCodeDetails;
}
