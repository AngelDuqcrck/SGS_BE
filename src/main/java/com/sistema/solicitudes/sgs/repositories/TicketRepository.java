package com.sistema.solicitudes.sgs.repositories;

import com.sistema.solicitudes.sgs.entities.Ticket;
import com.sistema.solicitudes.sgs.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


/*
 * Repository that define the methods to access to Tickets' data 
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    
    List<Ticket> findByEmployee(User user);
}
