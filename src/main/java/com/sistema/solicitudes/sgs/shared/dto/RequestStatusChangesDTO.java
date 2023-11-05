package com.sistema.solicitudes.sgs.shared.dto;

import java.util.Date;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestStatusChangesDTO {

    private Integer id;
    //We'll use the request id
    private Integer requestId;
    //We'll use the request status id
    private Integer statusId;
    private Date changeDate;
}
