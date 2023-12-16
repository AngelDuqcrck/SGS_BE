package com.sistema.solicitudes.sgs.services.implementations;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.solicitudes.sgs.entities.Request;
import com.sistema.solicitudes.sgs.entities.Rol;
import com.sistema.solicitudes.sgs.entities.StatusChangeRequest;
import com.sistema.solicitudes.sgs.entities.StatusChangeTicket;
import com.sistema.solicitudes.sgs.entities.StatusRequest;
import com.sistema.solicitudes.sgs.entities.StatusTicket;
import com.sistema.solicitudes.sgs.entities.Ticket;
import com.sistema.solicitudes.sgs.entities.User;
import com.sistema.solicitudes.sgs.repositories.RequestRepository;
import com.sistema.solicitudes.sgs.repositories.RolRepository;
import com.sistema.solicitudes.sgs.repositories.StatusChangeTicketRepository;
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

    @Autowired
    private StatusChangeTicketRepository statusChangeTicketRepository;

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
        createStatusChange(ticket, initialStatus);
        TicketDTO savedTicketDTO = new TicketDTO();
        savedTicketDTO.setEmployeeId(savedTicket.getEmployee().getId());
        savedTicketDTO.setStatusId(savedTicket.getStatusTicket().getId());
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

    @Override
    public TicketDTO updateTicket(Integer ticketId, TicketDTO ticketDTO) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not Found"));

        if (!ticket.getStatusTicket().getDescription().equals("ASSIGNED")) {
            throw new IllegalArgumentException("You can't edit the ticket because it isn't in ASSIGNED status.");
        }

        ticket.setTittle(ticketDTO.getTittle());
        ticket.setDescription(ticketDTO.getDescription());
        ticket.setObservation(ticketDTO.getObservation());
        ticket.setEndDate(ticketDTO.getEndDate());

        User user = userRepository.findById(ticketDTO.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("User not Found"));
        ticket.setEmployee(user);

        ticketRepository.save(ticket);
        TicketDTO updatedTicketDTO = new TicketDTO();
        BeanUtils.copyProperties(ticket, updatedTicketDTO);
        return updatedTicketDTO;
    }

    @Override
    public void ChangeTicketStatus(Integer ticketId, Integer newTicketStateId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not Found"));

        StatusTicket statusTicket = statusTicketRepository.findById(newTicketStateId)
                .orElseThrow(() -> new IllegalArgumentException("Status Request not found"));

        switch (newTicketStateId) {
            case 3:
                if(!ticket.getStatusTicket().getDescription().equals("RUNNING")){
                    throw new IllegalArgumentException("Youc can't finish the ticket because, this isn't in RUNNING status");
                }
                break;
            
            case 4:
                if(ticket.getStatusTicket().getDescription().equals("FINISHED")|| ticket.getStatusTicket().getDescription().equals("CANCELLED")){
                    throw new IllegalArgumentException("Youc can't cancel the ticket because, this is in FINISHED OR RUNNING status");
                }
                break;

            default:
                throw new IllegalArgumentException("Something is wrong with states");
        }
        ticket.setStatusTicket(statusTicket);
        createStatusChange(ticket, statusTicket);
        ticketRepository.save(ticket);

    }

    @Override
    public TicketDTO registerObservations(Integer ticketId, String observation){
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not Found"));
        
        ticket.setObservation(observation);

        Ticket ticketWithObservation = ticketRepository.save(ticket);
        TicketDTO ticketDTO = new TicketDTO();
        BeanUtils.copyProperties(ticketWithObservation, ticketDTO);
        return ticketDTO;
    }

    @Override
    public List<TicketDTO> listTicketsAssignedToEmployee(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not Found"));

        List<Ticket> tickets = ticketRepository.findByEmployee(user);

        return tickets.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    // -----------------------class methods----------------------------------

    private TicketDTO convertEntityToDTO(Ticket ticket) {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setStatusId(ticket.getStatusTicket().getId());
        ticketDTO.setEmployeeId(ticket.getEmployee().getId());
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

        
    }

    private void createStatusChange(Ticket ticket, StatusTicket statusTicket) {
        StatusChangeTicket statusChangeTicket = new StatusChangeTicket();
        statusChangeTicket.setTicket(ticket);
        statusChangeTicket.setStatusTicket(statusTicket);
        statusChangeTicket.setChangeDate(new Date());

        statusChangeTicketRepository.save(statusChangeTicket);

    }

    

}
