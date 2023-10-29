package com.sistema.solicitudes.sgs.controllers;

import com.sistema.solicitudes.sgs.models.responses.RequestResponse;
import com.sistema.solicitudes.sgs.models.responses.Response;
import com.sistema.solicitudes.sgs.services.implementations.RequestService;
import com.sistema.solicitudes.sgs.shared.dto.RequestDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/requests")
public class RequestController {

    @Autowired
    RequestService requestService;

    @PostMapping("/create")
    public Response createRequest(@RequestBody RequestDTO solicitudDTO, @RequestParam Integer usuarioId) {
        Response response = new Response();

        RequestDTO newRequest = requestService.createRequest(solicitudDTO, usuarioId);

        if (newRequest != null) response.setMessage("Request created successful");
        else response.setMessage("Unexpected error while request was created");
        return response;
    }

    @GetMapping("/user")
    public List<RequestResponse> listRequestsPerUser(@RequestParam Integer userId) {
        List<RequestResponse> requestsReturn = new ArrayList<>();
        List<RequestDTO> requests = requestService.listRequestPerEmployee(userId);

        for (RequestDTO request : requests) {
            RequestResponse requestResponse = new RequestResponse();
            BeanUtils.copyProperties(request, requestResponse);
            requestsReturn.add(requestResponse);
        }
        return requestsReturn;
    }

    @GetMapping
    public RequestDTO lookRequestDetails(@RequestParam Integer requestId) {
        return requestService.lookRequestDetails(requestId);
    }

    @PostMapping("/update")
    public Response updateRequest(@RequestParam Integer requestId, @RequestBody RequestDTO requestDTO) {
        Response response = new Response();
        RequestDTO updatedRequest = requestService.updateRequest(requestDTO, requestId);
        if (updatedRequest != null)
            response.setMessage("Request updated successfully");
        else response.setMessage("Error updating the request");

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
    public Response deleteRequest(@RequestParam Integer requestId) {
        Response response = new Response();
        try {
            requestService.deleteRequest(requestId);
            response.setMessage("Request was deleted succesfully");
        } catch (IllegalArgumentException e) {
            response.setMessage("Error deleting request : " + e.getMessage());
        }

        return response;
    }

    @PreAuthorize("hasRole('ROLE_JEFE')")
    @GetMapping("/allRequestPerDependence")
    public List<RequestDTO> getAllRequestPerDependence(@RequestParam Integer idDependence) {
        return requestService.getAllRequest(idDependence);
    }

}
