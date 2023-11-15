package com.sistema.solicitudes.sgs.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDTO {

    private Integer id;

    private String tittle;

    private String description;

    private String observation;

    private Date startDate;

    private Date endDate;

    // Usamos el ID del empleado en lugar de la referencia al objeto Usuario
    private Integer employeeId;

    private Date assignmentDate;
    
    // Usamos el ID del estado en lugar de la referencia al objeto EstadoTicket
    private Integer statusId;

}
