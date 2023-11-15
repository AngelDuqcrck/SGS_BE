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
        // Obtener la solicitud por su ID
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        // Validar que la solicitud esté en estado "APPROVE"
        if (!request.getStatusRequest().getDescription().equals("APPROVE")) {
            throw new IllegalArgumentException("Cannot create ticket for a non-approved request.");
        }

        // Validar fechas y cambiar estado del ticket si es necesario
        validateDatesAndStatus(ticketDTO);

        // Crear un nuevo ticket
        Ticket ticket = new Ticket();
        BeanUtils.copyProperties(ticketDTO, ticket);

        // Asignar la solicitud al ticket
        ticket.setRequest(request);

        // Asignar el empleado al ticket (solo si es un empleado válido para asignar)
        assignEmployeeToTicket(ticketDTO.getEmployeeId(), ticket);

        // Establecer el estado inicial del ticket como "ASSIGNED"
        StatusTicket initialStatus = statusTicketRepository.findByDescription("ASSIGNED")
                .orElseThrow(() -> new IllegalArgumentException("Initial status not found"));
        ticket.setStatusTicket(initialStatus);

        // Guardar el ticket en la base de datos
        Ticket savedTicket = ticketRepository.save(ticket);

        // Actualizar la solicitud para hacer referencia al ticket
        request.setTicket(savedTicket);
        requestRepository.save(request);

        // Convertir el ticket guardado a un DTO y devolverlo
        TicketDTO savedTicketDTO = new TicketDTO();
        BeanUtils.copyProperties(savedTicket, savedTicketDTO);
        return savedTicketDTO;
    }

    private void assignEmployeeToTicket(Integer employeeId, Ticket ticket) {
        // Validar que el empleado existe y tiene el rol correcto
        Rol rol = rolRepository.findByDescription("ROLE_SERVICE_EMPLOYEE")
                .orElseThrow(() -> new IllegalArgumentException("Invalid role"));
        User employee = userRepository.findByIdAndRol(employeeId, rol)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID or role"));

        // Asignar el empleado al ticket
        ticket.setEmployee(employee);

        // Establecer la fecha de asignación como la fecha de creación del ticket
        ticket.setAssignmentDate(new Date());
    }

    private void validateDatesAndStatus(TicketDTO ticketDTO) {
        Date currentDate = new Date();

        // Validar que la fecha de inicio no sea anterior a la fecha actual
        if (ticketDTO.getStartDate() != null && ticketDTO.getStartDate().before(currentDate)) {
            throw new IllegalArgumentException("Start date cannot be in the past.");
        }

        // Validar que la fecha de fin sea posterior a la fecha de inicio (si está
        // presente)
        if (ticketDTO.getEndDate() != null && ticketDTO.getStartDate() != null
                && ticketDTO.getEndDate().before(ticketDTO.getStartDate())) {
            throw new IllegalArgumentException("End date must be after start date.");
        }

        // Cambiar el estado del ticket a "RUNNING" si la fecha actual es igual o
        // posterior a la fecha de inicio
        if (ticketDTO.getStartDate() != null && currentDate.after(ticketDTO.getStartDate())) {
            ticketDTO.setStatusId(statusTicketRepository.findByDescription("RUNNING")
                    .orElseThrow(() -> new IllegalArgumentException("Status not found")).getId());
        }

        // Cambiar el estado del ticket a "FINISHED" si la fecha actual es igual o
        // posterior a la fecha de fin
        if (ticketDTO.getEndDate() != null && currentDate.after(ticketDTO.getEndDate())) {
            ticketDTO.setStatusId(statusTicketRepository.findByDescription("FINISHED")
                    .orElseThrow(() -> new IllegalArgumentException("Status not found")).getId());
        }
    }
}
