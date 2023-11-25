package com.sistema.solicitudes.sgs.services.interfaces;

import java.util.List;

import com.sistema.solicitudes.sgs.shared.dto.TicketDTO;

public interface TicketServiceInterface {
    
     public TicketDTO createTicket(Integer requestId, TicketDTO ticketDTO);
     public List<TicketDTO> listAllTickets();
     public TicketDTO lookTicketDetails(Integer ticketId);
     public TicketDTO updateTicket(Integer ticketId, TicketDTO ticketDTO);
     public void ChangeTicketStatus(Integer ticketId, Integer newTicketStateId);
     public TicketDTO registerObservations(Integer ticketId, String observation);
     public List<TicketDTO> listTicketsAssignedToEmployee(Integer userId);


}
