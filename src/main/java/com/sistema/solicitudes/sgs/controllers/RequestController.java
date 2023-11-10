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

    /**
     * Endpoint that creates a new request based on the provided request data for a
     * specific user.
     *
     * @param requestDTO The request data.
     * @param userId     The ID of the user associated with the request.
     * @return A response indicating whether the request was created successfully.
     */
    @PostMapping("/create")
    public Response createRequest(@RequestBody RequestDTO requestDTO, @RequestParam Integer userId) {
        Response response = new Response();

        try {
            RequestDTO newRequest = requestService.createRequest(requestDTO, userId);

            if (newRequest != null) {
                response.setMessage("Request created successfully");
            } else {
                response.setMessage("Unexpected error while request was created");
            }
        } catch (IllegalArgumentException e) {
            response.setMessage("Error: " + e.getMessage());
        }

        return response;
    }

    /**
     * Endpoint that retrieves a list of requests associated with a specific user.
     *
     * @param userId The ID of the user to retrieve requests for.
     * @return A list of request responses.
     */
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

    /**
     * Endpoint that gets details of a specific request by its ID.
     *
     * @param requestId The ID of the request to retrieve details for.
     * @return The request details.
     */
    @GetMapping
    public RequestDTO lookRequestDetails(@RequestParam Integer requestId) {
        return requestService.lookRequestDetails(requestId);
    }

    /**
     * Endpoint that updates the details of a specific request.
     *
     * @param requestId  The ID of the request to update.
     * @param requestDTO The updated request data.
     * @return A response indicating whether the request was updated successfully.
     */
    @PostMapping("/update")
    public Response updateRequest(@RequestParam Integer requestId, @RequestBody RequestDTO requestDTO) {
        Response response = new Response();
        RequestDTO updatedRequest = requestService.updateRequest(requestDTO, requestId);
        if (updatedRequest != null)
            response.setMessage("Request updated successfully");
        else
            response.setMessage("Error updating the request");

        return response;
    }

    /**
     * Send a request, changing its state to "SENT".
     *
     * @param requestId The ID of the request to send.
     * @return A response indicating whether the request was sent successfully.
     */
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

    /**
     * Endpoint to verify a request, changing its state to "APPROVE".
     *
     * @param requestId The ID of the request to verify.
     * @return A response indicating whether the request was verified successfully.
     */
    @PostMapping("/verify")
    public Response verifyRequest(@RequestParam Integer requestId) {
        Response response = new Response();
        try {
            requestService.changeRequestState(requestId, 2);
            response.setMessage("Request verified succesfully");
        } catch (IllegalArgumentException e) {
            response.setMessage("Error verifying request : " + e.getMessage());
        }

        return response;
    }

    /**
     * Endpoint to reject a request, changing its state to "REJECT".
     *
     * @param requestId The ID of the request to reject.
     * @return A response indicating whether the request was rejected successfully.
     */
    
    @PostMapping("/reject")
    public Response rejectRequest(@RequestParam Integer requestId) {
        Response response = new Response();
        try {
            requestService.changeRequestState(requestId, 4);
            response.setMessage("Request rejected succesfully");
        } catch (IllegalArgumentException e) {
            response.setMessage("Error rejecting request : " + e.getMessage());
        }

        return response;
    }

    /**
     * Endpoint that cancels a request, changing its state to "CANCELLED".
     *
     * @param requestId The ID of the request to cancel.
     * @return A response indicating whether the request was canceled successfully.
     */
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

    /**
     * Endpoint that deletes a request if it is in "STAND_BY" status.
     *
     * @param requestId The ID of the request to delete.
     * @return A response indicating whether the request was deleted successfully.
     */
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

    /**
     * Endpoint that retrieves all requests associated with a specific dependence.
     * Accessible by
     * users with the role "ROLE_DEPENDENCE_BOSS"
     *
     * @param idDependence The ID of the dependence to retrieve requests for.
     * @return A list of requests associated with the specified dependence.
     */
    @PreAuthorize("hasRole('ROLE_DEPENDENCE_BOSS')")
    @GetMapping("/allRequestPerDependence")
    public List<RequestDTO> getAllRequestPerDependence(@RequestParam Integer idDependence) {
        return requestService.getAllRequest(idDependence);
    }

    /*
     * Endpoint that retrieves all request that is currently in VERIFIED status
     * Accessible by
     * users with the role "ROLE_SERVICE_BOSS."
     */
    @GetMapping("/verifiedRequest")
    public List<RequestDTO> getAllVerifiedRequests() {
        return requestService.getAllVerifiedRequests();
    }


     @PostMapping("/approve")
    public Response approveRequest(@RequestParam Integer requestId) {
        Response response = new Response();
        try {
            requestService.changeRequestState(requestId, 3);
            response.setMessage("Request was approved successfully");
        } catch (IllegalArgumentException e) {
            response.setMessage("Error approving request : " + e.getMessage());
        }

        return response;
    }
}
