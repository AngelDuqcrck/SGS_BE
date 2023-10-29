package com.sistema.solicitudes.sgs.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    
    private Integer id;

    private String title;
    
    private String description;

    private Date requestDate;
    // Usamos el ID del estado en lugar de la referencia al objeto EstadoSolicitud
    private Integer statusId;
    // Usamos el ID del usuario en lugar de la referencia al objeto Usuario
    private Integer userId;

}