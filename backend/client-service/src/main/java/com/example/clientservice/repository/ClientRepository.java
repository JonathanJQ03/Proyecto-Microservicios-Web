package com.example.clientservice.repository;

import com.example.clientservice.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    
    Optional<Client> findByEmail(String email);
    
    Optional<Client> findByAuthUserId(String authUserId);
    
    boolean existsByEmail(String email);
    
    boolean existsByAuthUserId(String authUserId);
}
