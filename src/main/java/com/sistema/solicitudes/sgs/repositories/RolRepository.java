package com.sistema.solicitudes.sgs.repositories;

import com.sistema.solicitudes.sgs.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Repository that define the methods to access to Rol's data 
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
}