package com.sistema.solicitudes.sgs.repositories;

import com.sistema.solicitudes.sgs.entities.StatusRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StatusRequestRepository extends JpaRepository<StatusRequest, Integer> {

  
    Optional<StatusRequest> findByDescription(String name);
}