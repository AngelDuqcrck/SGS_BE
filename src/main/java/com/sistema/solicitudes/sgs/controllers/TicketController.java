package com.sistema.solicitudes.sgs.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

     @GetMapping("/all")
    public List<TicketDTO> listAllTickets() {
        return ticketService.listAllTickets();
    }

    @GetMapping
    public TicketDTO lookTicketDetails(@RequestParam Integer ticketId) {
        return ticketService.lookTicketDetails(ticketId);
    }

    @PostMapping("/update")
    public Response updateTicket(@RequestBody TicketDTO ticketDTO) {
        Response response = new Response();
        try {
            ticketService.updateTicket(ticketDTO.getId(), ticketDTO);
            response.setMessage("Ticket updated successfully");
        } catch (IllegalArgumentException e) {
            response.setMessage("Error updating ticket: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/cancel")
    public Response cancelTicket(@RequestParam Integer ticketId) {
        Response response = new Response();
        try {
            ticketService.ChangeTicketStatus(ticketId, 4);
            response.setMessage("Ticket status changed successfully");
        } catch (IllegalArgumentException e) {
            response.setMessage("Error changing ticket status: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/finish")
    public Response finishTicket(@RequestParam Integer ticketId) {
        Response response = new Response();
        try {
            ticketService.ChangeTicketStatus(ticketId, 3);
            response.setMessage("Ticket status changed successfully");
        } catch (IllegalArgumentException e) {
            response.setMessage("Error changing ticket status: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/registerObservations")
    public Response registerObservations(@RequestParam Integer ticketId, @RequestParam String observation) {
        Response response = new Response();
        try {
            ticketService.registerObservations(ticketId, observation);
            response.setMessage("Observations registered successfully");
        } catch (IllegalArgumentException e) {
            response.setMessage("Error registering observations: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/employee")
    public List<TicketDTO> listTicketsAssignedToEmployee(@RequestParam Integer userId) {
        return ticketService.listTicketsAssignedToEmployee(userId);
    }
}
