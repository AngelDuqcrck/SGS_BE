package com.sistema.solicitudes.sgs.shared.dto;

import lombok.Data;

import java.util.Date;

@Data
public class SolicitudDTO {
    
    private Integer id;

    private String titulo;
    
    private String descripcion;

    private Date fechaSolicitud;
    // Usamos el ID del estado en lugar de la referencia al objeto EstadoSolicitud
    private Integer estadoId;
    // Usamos el ID del usuario en lugar de la referencia al objeto Usuario
    private Integer usuarioId; 
}