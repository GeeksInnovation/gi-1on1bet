package com._on1bet.authservice.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com._on1bet.authservice.entity.UserDetails;
import reactor.core.publisher.Mono;

@Repository
public interface UserDetailsRepo extends ReactiveCrudRepository<UserDetails, String>{

    public Mono<Boolean> existsByMobileNo(String mobileNo);
}
