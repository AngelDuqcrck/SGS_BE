package com.sistema.solicitudes.sgs.controllers;

import java.util.ArrayList;
import java.util.List;

import com.sistema.solicitudes.sgs.entities.Solicitud;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    SolicitudService requestService;

    @PostMapping("/create")
    public Response createRequest(@RequestBody SolicitudDTO solicitudDTO, @RequestParam Integer usuarioId) {
        Response response  = new Response();

        SolicitudDTO newRequest = requestService.createRequest(solicitudDTO, usuarioId);
        
        if (newRequest != null) response.setMessage("Solicitud creada con exito");
        else   response.setMessage("Error al crear la solicitud");
        return response;
    }

    @GetMapping("/user")
    public List<RequestResponse> listRequestsPerUser(@RequestParam Integer usuarioId) {
        List<RequestResponse> requestsReturn = new ArrayList<>();
        List<SolicitudDTO> requests = requestService.listRequestPerEmployee(usuarioId);
    
        for (SolicitudDTO request : requests) {
            RequestResponse requestResponse = new RequestResponse();
            BeanUtils.copyProperties(request, requestResponse);
            requestsReturn.add(requestResponse);
        }
        return requestsReturn;
    }

    @GetMapping
    public SolicitudDTO lookRequestDetails(@RequestParam Integer requestId){
        SolicitudDTO requestToReturn = requestService.lookRequestDetails(requestId);
        return requestToReturn;
    }
    
    @PostMapping("/update")
    public Response updateRequest(@RequestParam Integer requestId, @RequestBody SolicitudDTO requestDTO) {
        Response response = new Response();
        SolicitudDTO updatedRequest = requestService.updateRequest(requestDTO, requestId);
        if (updatedRequest != null) 
            response.setMessage("Request updated successfully");
         else  response.setMessage("Error updating the request");
        
        return response;
    }

    @PostMapping("/send")
    public Response sendRequest(@RequestParam Integer requestId) {
        Response response = new Response();
        try {
            requestService.changeRequestState(requestId, 5);
            response.setMessage("Request sent successfully");
        } catch (IllegalArgumentException e) {
            response.setMessage("Error changing request state: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("/cancel")
    public Response cancelRequest(@RequestParam Integer requestId) {
        Response response = new Response();
        try {
            requestService.changeRequestState(requestId, 6);
            response.setMessage("Request was cancelled successfully");
        } catch (IllegalArgumentException e) {
            response.setMessage("Error cancelling request : " + e.getMessage());
        }

        return response;
    }

    @DeleteMapping("/delete")
    public Response deleteRequest(@RequestParam Integer requestId){
        Response response = new Response();
        try {
            requestService.deleteRequest(requestId);
            response.setMessage("Request was deleted succesfully");
        }  catch (IllegalArgumentException e) {
            response.setMessage("Error deleting request : " + e.getMessage());
        }

        return response;
    }

    @PreAuthorize("hasRole('ROLE_JEFE')")
    @GetMapping("/allRequestPerDependence")
    public List<SolicitudDTO> getAllRequestPerDependence(@RequestParam Integer idDependence ){
        return requestService.getAllRequest(idDependence);
    }

}
