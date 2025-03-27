package com._on1bet.authservice.repo;

import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;

import com._on1bet.authservice.projection.CountryCodeDetailsProj;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UtilRepoImpl implements UtilRepo {

    private final DatabaseClient databaseClient;

    public UtilRepoImpl(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<String> getIsoCodeFromId(Integer id) {
        String sql = "SELECT iso_code FROM country_code WHERE id = :id";
        return databaseClient.sql(sql)
                .bind("id", id)
                .map((row, metadata) -> row.get("iso_code", String.class))
                .one();
    }

    @Override
    public Flux<CountryCodeDetailsProj> fetchCountryCodeDetails() {
        String sql = "SELECT id, name, dial_code FROM country_code";
        return databaseClient.sql(sql)
                .map((row, metadata) -> new CountryCodeDetailsProj(
                        row.get("id", Integer.class),
                        row.get("name", String.class),
                        row.get("dial_code", String.class)))
                .all();
    }
}