package com.sistema.solicitudes.sgs.repositories;

import com.sistema.solicitudes.sgs.entities.StatusTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


/*
 * Repository that define the methods to access to Status of a ticket data 
 */
@Repository
public interface StatusTicketRepository extends JpaRepository<StatusTicket, Integer> {
    
    Optional<StatusTicket> findByDescription(String description);
}