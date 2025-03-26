package com._on1bet.authservice.repo;

import java.util.List;
import com._on1bet.authservice.projection.CountryCodeDetailsProj;

import reactor.core.publisher.Mono;

public interface UtilRepo {

    Mono<String> getIsoCodeFromId(Integer id);
    Mono<List<CountryCodeDetailsProj>> fetchCountryCodeDetails();
    
}
