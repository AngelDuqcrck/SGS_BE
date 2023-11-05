package com.sistema.solicitudes.sgs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.solicitudes.sgs.entities.StatusChangeRequest;

/*
 * Repository that define the methods to access to Status history of a request data 
 */
public interface StatusChageRequestRepository extends JpaRepository<StatusChangeRequest, Integer> {
    
}
