package com._on1bet.authservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("user_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetails implements Persistable<Long> {

    @Id
    private Long mobileNo;
    private String userId;
    private Integer countryCodeDetails;

    @Override
    @Nullable
    public Long getId() {
        return mobileNo;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
