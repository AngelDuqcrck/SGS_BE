package com.sistema.solicitudes.sgs.services.interfaces;

import com.sistema.solicitudes.sgs.shared.dto.TicketDTO;

public interface TicketServiceInterface {
    
     public TicketDTO createTicket(Integer requestId, TicketDTO ticketDTO);
}
