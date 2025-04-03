package com._on1bet.authservice.entity;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
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
public class UserDetails implements Persistable<String> {

    @Column("mobile_no")
    private String mobileNo;

    @Id
    @Column("user_id")
    private String userId;

    @Column("country_code_details")
    private Integer countryCodeDetails;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Override
    @Nullable
    public String getId() {
        return userId;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
