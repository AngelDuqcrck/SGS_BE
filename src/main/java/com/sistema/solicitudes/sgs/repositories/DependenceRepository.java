package com.sistema.solicitudes.sgs.repositories;

import com.sistema.solicitudes.sgs.entities.Dependence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DependenceRepository extends JpaRepository<Dependence, Integer> {
    
}