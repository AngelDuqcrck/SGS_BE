package com.sistema.solicitudes.sgs.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.solicitudes.sgs.models.responses.RequestResponse;
import com.sistema.solicitudes.sgs.models.responses.Response;
import com.sistema.solicitudes.sgs.services.implementations.SolicitudService;
import com.sistema.solicitudes.sgs.shared.dto.SolicitudDTO;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {
    @Autowired
    SolicitudService solicitudService;

    @PostMapping("/create")
    public Response crearSolicitud(@RequestBody SolicitudDTO solicitudDTO, @RequestParam Integer usuarioId) {
        Response mensaje  = new Response();

        SolicitudDTO nuevaSolicitud = solicitudService.crearSolicitud(solicitudDTO, usuarioId);
        
        if (nuevaSolicitud != null) {
            mensaje.setMessage("Solicitud creada con exito");
        } else {
            mensaje.setMessage("Error al crear la solicitud");
        }

        return mensaje;
    }

    @GetMapping("/user")
    public List<RequestResponse> listRequestsPerUser(@RequestParam Integer usuarioId) {
        List<RequestResponse> requestsReturn = new ArrayList<>();
        List<SolicitudDTO> requests = solicitudService.listRequestPerEmployee(usuarioId);
    
        for (SolicitudDTO request : requests) {
            RequestResponse requestResponse = new RequestResponse();
            BeanUtils.copyProperties(request, requestResponse);
            requestsReturn.add(requestResponse);
        }
        return requestsReturn;
    }
}
