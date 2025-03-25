package com._on1bet.authservice.repo;

import java.util.List;
import com._on1bet.authservice.projection.CountryCodeDetailsProj;

public interface UtilRepo {

    String getIsoCodeFromId(Integer id);
    List<CountryCodeDetailsProj> fetchCountryCodeDetails();
    
}
