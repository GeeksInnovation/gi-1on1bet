package com._on1bet.authservice.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com._on1bet.authservice.entity.ProfileDetails;

@Repository
public interface ProfileDetailsRepo extends ReactiveCrudRepository<ProfileDetails, Integer>{
    
}
