package com.sistema.solicitudes.sgs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.solicitudes.sgs.entities.StatusChangeTicket;

/*
 * Repository that define the methods to access to Status history of a ticket data 
 */
public interface StatusChangeTicketRepository extends JpaRepository<StatusChangeTicket, Integer> {
    
}
