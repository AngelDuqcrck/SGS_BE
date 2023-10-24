package com.sistema.solicitudes.sgs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.solicitudes.sgs.entities.Dependencia;

@Repository
public interface DependenciaRepository extends JpaRepository<Dependencia, Integer> {
    
}