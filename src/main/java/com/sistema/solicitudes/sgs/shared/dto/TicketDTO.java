package com.sistema.solicitudes.sgs.shared.dto;

import lombok.Data;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

@Data
public class TicketDTO {
    private Integer id;

    @NotEmpty
    private String nombre;

    private String observacion;

    private Date fechaInicio;
    private Date fechaFinal;

    // Usamos el ID del empleado en lugar de la referencia al objeto Usuario
    private Integer empleadoId; 

    private Date fechaAsignacion;
    
    // Usamos el ID del estado en lugar de la referencia al objeto EstadoTicket
    private Integer estadoId; 

}
