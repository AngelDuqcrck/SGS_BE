package com.sistema.solicitudes.sgs.repositories;

import com.sistema.solicitudes.sgs.entities.StatusRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * Repository that define the methods to access to Status of a request data 
 */
@Repository
public interface StatusRequestRepository extends JpaRepository<StatusRequest, Integer> {

    /**
     * Find a status of a request by respective description
     * @param name that contains the name of status
     * @return an StatusRequest object containing the request if found, otherwise returns null 
     */
    Optional<StatusRequest> findByDescription(String name);
}