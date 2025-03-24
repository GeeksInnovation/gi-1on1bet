package com._on1bet.authservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserDetails {

    @Id
    private Long mobileNo;
    private String userId;
    private Integer countryCodeDetails;
}
