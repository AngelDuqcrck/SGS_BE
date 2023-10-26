package com.sistema.solicitudes.sgs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.solicitudes.sgs.models.responses.Response;
import com.sistema.solicitudes.sgs.services.implementations.SolicitudService;
import com.sistema.solicitudes.sgs.shared.dto.SolicitudDTO;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {
    @Autowired
    SolicitudService solicitudService;

    @PostMapping("/crear")

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
}
