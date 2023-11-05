package com.sistema.solicitudes.sgs.models.responses;

import java.util.Date;

import org.hibernate.mapping.List;

import com.sistema.solicitudes.sgs.entities.StatusChangeRequest;

import lombok.Data;

@Data
public class RequestDetails {
    

    private String title;
    
    private String description;

    private Date requestDate;
    //We'll use the status id
    private Integer statusId;
    // We'll use the user id
    private Integer userId;

    private StatusChangeRequest statusChangeRequest;
}
