package com.sistema.solicitudes.sgs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.solicitudes.sgs.models.responses.Response;
import com.sistema.solicitudes.sgs.services.implementations.TicketService;
import com.sistema.solicitudes.sgs.shared.dto.TicketDTO;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

     @PostMapping("/create")
    public Response createTicket(@RequestBody TicketDTO ticketDTO, @RequestParam Integer requestId) {
        Response response = new Response();

        try {
            TicketDTO newTicket = ticketService.createTicket(requestId, ticketDTO);

            if (newTicket != null) {
                response.setMessage("Ticket created successfully");
            } else {
                response.setMessage("Unexpected error while ticket was created");
            }
        } catch (IllegalArgumentException e) {
            response.setMessage("Error: " + e.getMessage());
        }

        return response;
    }
}
