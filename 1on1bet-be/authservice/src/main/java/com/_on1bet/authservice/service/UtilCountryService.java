package com._on1bet.authservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com._on1bet.authservice.model.response.CountryCodeListResponse;
import com._on1bet.authservice.projection.CountryCodeDetailsProj;
import com._on1bet.authservice.repo.UtilRepo;
import com._on1betutils.utils1on1bet._on1BetResponse;
import com._on1betutils.utils1on1bet._on1BetResponseBuilder;

@Service
public class UtilCountryService {

    private final UtilRepo utilRepo;
    private final _on1BetResponseBuilder _on1betResponseBuilder;

    public UtilCountryService(UtilRepo utilRepo, _on1BetResponseBuilder _on1betResponseBuilder) {
        this.utilRepo = utilRepo;
        this._on1betResponseBuilder = _on1betResponseBuilder;
    }

    public _on1BetResponse<CountryCodeListResponse> getAllCountryCode() {
        List<CountryCodeDetailsProj> listOfCountryCodes = utilRepo.fetchCountryCodeDetails();
        return _on1betResponseBuilder.buildSuccessResponse(CountryCodeListResponse.builder()
        .countryCodeList(listOfCountryCodes).build());
    }

}
