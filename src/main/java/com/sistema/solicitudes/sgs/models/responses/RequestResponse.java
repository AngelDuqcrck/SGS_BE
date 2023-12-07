package com.sistema.solicitudes.sgs.models.responses;

import lombok.Data;

import java.util.Date;

@Data
public class RequestResponse {

    private Integer id;
    private String title;
    private Date requestDate;

}
