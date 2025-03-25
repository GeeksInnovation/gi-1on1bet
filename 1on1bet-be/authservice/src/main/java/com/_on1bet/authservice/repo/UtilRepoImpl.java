package com._on1bet.authservice.repo;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com._on1bet.authservice.projection.CountryCodeDetailsProj;

@Repository
public class UtilRepoImpl implements UtilRepo {

    private final JdbcTemplate jdbcTemplate;

    public UtilRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String getIsoCodeFromId(Integer id) {
        String sql = "SELECT iso_code FROM country_code WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<CountryCodeDetailsProj> fetchCountryCodeDetails() {
        String sql = "SELECT id, iso_code, dial_code FROM country_code";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new CountryCodeDetailsProj(
                rs.getInt("id"),
                rs.getString("iso_code"),
                rs.getString("dial_code")));
    }

}
