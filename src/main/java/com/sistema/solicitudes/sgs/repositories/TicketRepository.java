package com.sistema.solicitudes.sgs.repositories;

import com.sistema.solicitudes.sgs.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    
}
