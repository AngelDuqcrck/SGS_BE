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
    //We'll use the status id
    private Integer statusId;
    // We'll use the user id
    private Integer userId;

}