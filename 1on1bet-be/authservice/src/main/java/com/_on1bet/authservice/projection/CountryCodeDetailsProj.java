package com._on1bet.authservice.projection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryCodeDetailsProj {
    private Integer id;
    private String name;
    private String dialCode;
}
