package com.sistema.solicitudes.sgs.repositories;

import com.sistema.solicitudes.sgs.entities.StatusTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusTicketRepository extends JpaRepository<StatusTicket, Integer> {
    
}