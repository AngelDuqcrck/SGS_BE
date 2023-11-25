package com.sistema.solicitudes.sgs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistema.solicitudes.sgs.entities.StatusChangeTicket;

public interface StatusChangeTicketRepository extends JpaRepository<StatusChangeTicket, Integer> {
    
}
