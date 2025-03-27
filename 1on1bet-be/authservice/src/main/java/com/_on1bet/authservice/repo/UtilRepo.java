package com._on1bet.authservice.repo;

import com._on1bet.authservice.projection.CountryCodeDetailsProj;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UtilRepo {

    Mono<String> getIsoCodeFromId(Integer id);
    Flux<CountryCodeDetailsProj> fetchCountryCodeDetails();
    
}
