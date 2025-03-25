package com._on1bet.authservice.model.response;

import java.util.List;

import com._on1bet.authservice.projection.CountryCodeDetailsProj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryCodeListResponse {

    private List<CountryCodeDetailsProj> countryCodeList;

}
