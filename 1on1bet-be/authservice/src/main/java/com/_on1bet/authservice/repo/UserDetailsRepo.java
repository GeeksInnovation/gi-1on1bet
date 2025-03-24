package com._on1bet.authservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com._on1bet.authservice.entity.UserDetails;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails, Long>{

}
