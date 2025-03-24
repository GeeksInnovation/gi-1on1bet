package com._on1bet.authservice.repo;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com._on1bet.authservice.projection.CountryCodeDetailsProj;

@Repository
public interface UtilRepo{

    @Query(value = "select iso_code FROM country_code where id=:id" , nativeQuery = true)
    String getIsoCodeFromId(@Param("id") Integer id);

    @Query(value = "select id AS id, iso_code AS isoCode, dial_code AS dialCode FROM country_code", nativeQuery = true)
    List<CountryCodeDetailsProj> fetchCountryCodeDetails();
    
}
