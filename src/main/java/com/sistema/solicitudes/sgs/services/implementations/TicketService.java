package com.sistema.solicitudes.sgs.services.implementations;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.solicitudes.sgs.entities.Request;
import com.sistema.solicitudes.sgs.entities.Rol;
import com.sistema.solicitudes.sgs.entities.StatusTicket;
import com.sistema.solicitudes.sgs.entities.Ticket;
import com.sistema.solicitudes.sgs.entities.User;
import com.sistema.solicitudes.sgs.repositories.RequestRepository;
import com.sistema.solicitudes.sgs.repositories.RolRepository;
import com.sistema.solicitudes.sgs.repositories.StatusTicketRepository;
import com.sistema.solicitudes.sgs.repositories.TicketRepository;
import com.sistema.solicitudes.sgs.repositories.UserRepository;
import com.sistema.solicitudes.sgs.services.interfaces.TicketServiceInterface;
import com.sistema.solicitudes.sgs.shared.dto.TicketDTO;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService implements TicketServiceInterface {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusTicketRepository statusTicketRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public TicketDTO createTicket(Integer requestId, TicketDTO ticketDTO) {

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (!request.getStatusRequest().getDescription().equals("APPROVE")) {
            throw new IllegalArgumentException("Cannot create ticket for a non-approved request.");
        }

        validateDatesAndStatus(ticketDTO);

        Ticket ticket = new Ticket();
        BeanUtils.copyProperties(ticketDTO, ticket);

        ticket.setRequest(request);

        assignEmployeeToTicket(ticketDTO.getEmployeeId(), ticket);

        StatusTicket initialStatus = statusTicketRepository.findByDescription("ASSIGNED")
                .orElseThrow(() -> new IllegalArgumentException("Initial status not found"));
        ticket.setStatusTicket(initialStatus);

        Ticket savedTicket = ticketRepository.save(ticket);

        TicketDTO savedTicketDTO = new TicketDTO();
        BeanUtils.copyProperties(savedTicket, savedTicketDTO);
        return savedTicketDTO;
    }

    @Override
    public List<TicketDTO> listAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());

    }

    @Override
    public TicketDTO lookTicketDetails(Integer ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not Found"));
        
        TicketDTO ticketDTO = new TicketDTO();
        BeanUtils.copyProperties(ticket, ticketDTO);

        return ticketDTO;

    }

    // -----------------------class methods----------------------------------

    private TicketDTO convertEntityToDTO(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        BeanUtils.copyProperties(ticket, ticketDTO);
        return ticketDTO;
    }

    private void assignEmployeeToTicket(Integer employeeId, Ticket ticket) {

        Rol rol = rolRepository.findByDescription("ROLE_SERVICE_EMPLOYEE")
                .orElseThrow(() -> new IllegalArgumentException("Invalid role"));
        User employee = userRepository.findByIdAndRol(employeeId, rol)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID or role"));

        ticket.setEmployee(employee);

        ticket.setAssignmentDate(new Date());
    }

    private void validateDatesAndStatus(TicketDTO ticketDTO) {
        Date currentDate = new Date();

        if (ticketDTO.getStartDate() != null && ticketDTO.getStartDate().before(currentDate)) {
            throw new IllegalArgumentException("Start date cannot be in the past.");
        }

        if (ticketDTO.getEndDate() != null && ticketDTO.getStartDate() != null
                && ticketDTO.getEndDate().before(ticketDTO.getStartDate())) {
            throw new IllegalArgumentException("End date must be after start date.");
        }

        if (ticketDTO.getStartDate() != null && currentDate.after(ticketDTO.getStartDate())) {
            ticketDTO.setStatusId(statusTicketRepository.findByDescription("RUNNING")
                    .orElseThrow(() -> new IllegalArgumentException("Status not found")).getId());
        }

        if (ticketDTO.getEndDate() != null && currentDate.after(ticketDTO.getEndDate())) {
            ticketDTO.setStatusId(statusTicketRepository.findByDescription("FINISHED")
                    .orElseThrow(() -> new IllegalArgumentException("Status not found")).getId());
        }
    }
}
