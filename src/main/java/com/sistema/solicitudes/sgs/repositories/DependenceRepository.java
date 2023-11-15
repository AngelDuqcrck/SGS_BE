package com.sistema.solicitudes.sgs.repositories;

import com.sistema.solicitudes.sgs.entities.Dependence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/*
 * Repository that define the methods to access to Dependence's data 
 */

import java.util.Optional;


@Repository
public interface DependenceRepository extends JpaRepository<Dependence, Integer> {
    Optional<Dependence> findByDescription(String description);
}