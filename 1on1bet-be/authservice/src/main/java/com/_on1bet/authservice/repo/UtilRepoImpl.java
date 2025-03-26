package com._on1bet.authservice.repo;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com._on1bet.authservice.projection.CountryCodeDetailsProj;

import reactor.core.publisher.Mono;

@Repository
public class UtilRepoImpl implements UtilRepo {

    private final JdbcTemplate jdbcTemplate;

    public UtilRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mono<String> getIsoCodeFromId(Integer id) {
        String sql = "SELECT iso_code FROM country_code WHERE id = ?";
        try {
            return Mono.just(jdbcTemplate.queryForObject(sql, String.class, id));
        } catch (EmptyResultDataAccessException e) {
            return Mono.just(null);
        }
    }

    @Override
    public Mono<List<CountryCodeDetailsProj>> fetchCountryCodeDetails() {
        String sql = "SELECT id, name, dial_code FROM country_code";
        return Mono.just(jdbcTemplate.query(sql, (rs, rowNum) -> new CountryCodeDetailsProj(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("dial_code"))));
    }

}