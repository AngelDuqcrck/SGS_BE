package com.sistema.solicitudes.sgs.services.implementations;

import com.sistema.solicitudes.sgs.entities.Request;
import com.sistema.solicitudes.sgs.entities.StatusRequest;
import com.sistema.solicitudes.sgs.entities.User;
import com.sistema.solicitudes.sgs.repositories.RequestRepository;
import com.sistema.solicitudes.sgs.repositories.StatusRequestRepository;
import com.sistema.solicitudes.sgs.repositories.UserRepository;
import com.sistema.solicitudes.sgs.shared.dto.RequestDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRequestRepository statusRequestRepository;

    /**
     * This method is responsible for creating a new request in the database.
     * It copies the properties from the provided request DTO to a new request
     * entity,
     * associates the request with the specified user, sets its status to
     * "STAND_BY,"
     * and saves it in the database.
     *
     * @param requestDTO The request data to be created.
     * @param userId     The ID of the user creating the request.
     * @return The created request as a DTO.
     * @throws IllegalArgumentException if the user does not exist or if the
     *                                  "STAND_BY" status is not found.
     */
    public RequestDTO createRequest(RequestDTO requestDTO, Integer userId) {
        Request request = new Request();

        BeanUtils.copyProperties(requestDTO, request);

        User user = userRepository.findById(userId).orElse(null);
        if (user == null)
            throw new IllegalArgumentException("User not found");

        request.setUser(user);

        StatusRequest status = statusRequestRepository.findByDescription("STAND_BY").orElse(null);

        if (status == null) {
            throw new IllegalArgumentException("Status STAND_BY not found");
        }

        request.setStatusRequest(status);
        request.setRequestDate(new Date());

        Request savedRequest = requestRepository.save(request);

        RequestDTO createdRequest = new RequestDTO();
        BeanUtils.copyProperties(savedRequest, createdRequest);

        return createdRequest;
    }

    /**
     * This method retrieves a list of requests associated with a specific user.
     * It fetches requests based on the user's ID
     *
     * @param userId The ID of the user to fetch requests for.
     * @return A list of request DTOs.
     */
    public List<RequestDTO> listRequestPerEmployee(Integer userId) {
        List<Request> requests = requestRepository.findUserById(userId);

        List<RequestDTO> requestDTOs = new ArrayList<>();

        for (Request request : requests) {
            RequestDTO requestDTO = new RequestDTO();
            requestDTO.setUserId(request.getUser().getId());
            requestDTO.setStatusId(request.getStatusRequest().getId());
            BeanUtils.copyProperties(request, requestDTO);
            requestDTOs.add(requestDTO);
        }

        return requestDTOs;
    }

    /**
     * This method fetches the details of a specific request by its ID
     *
     * @param requestId The ID of the request to retrieve.
     * @return The details of the request as a DTO.
     */
    public RequestDTO lookRequestDetails(Integer requestId) {
        RequestDTO requestDTO = new RequestDTO();
        Request request = new Request();
        request = requestRepository.findById(requestId).get();
        BeanUtils.copyProperties(request, requestDTO);
        requestDTO.setStatusId(request.getStatusRequest().getId());
        requestDTO.setUserId(request.getUser().getId());

        return requestDTO;

    }

    /**
     * This method changes the state of a request to a new specified state.
     *
     * @param requestId         The ID of the request to update.
     * @param newRequestStateId The ID of the new state for the request.
     * @throws IllegalArgumentException if the request is not found.
     */
    public void changeRequestState(Integer requestId, Integer newRequestStateId) {

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        StatusRequest newStatus = statusRequestRepository.findById(newRequestStateId)
                .orElseThrow(() -> new IllegalArgumentException("Status Request not found"));

        if (newRequestStateId == 5) {
            if (!request.getStatusRequest().getDescription().equals("STAND_BY")) {
                throw new IllegalArgumentException("You can't send the request because it isn't in STAND_BY status.");
            }
            request.setStatusRequest(newStatus);
        }

        if (newRequestStateId == 6) {
            if (!request.getStatusRequest().getDescription().equals("SENT")) {
                throw new IllegalArgumentException(
                        "You can't cancel the request because it isn't in SENT status.");
            }
            request.setStatusRequest(newStatus);
            request.setStatusRequest(statusRequestRepository.findById(1).get());
        }

        requestRepository.save(request);
    }

    /**
     * This method updates the title and description of a request if the request is
     * in "STAND_BY" status (the request can't be in "SENT" status).
     *
     * @param requestDTO The updated request data.
     * @param requestId  The ID of the request to update.
     * @return The updated request as a DTO.
     * @throws IllegalArgumentException if the request is not found or if it's not
     *                                  in "STAND_BY" status.
     */
    public RequestDTO updateRequest(RequestDTO requestDTO, Integer requestId) {

        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (!request.getStatusRequest().getDescription().equals("STAND_BY")) {
            throw new IllegalArgumentException("You can't edit the request because it isn't in STAND_BY status.");
        }

        request.setTitle(requestDTO.getTitle());
        request.setDescription(requestDTO.getDescription());

        Request updatedRequest = requestRepository.save(request);

        RequestDTO UpdatedRequestDTO = new RequestDTO();
        BeanUtils.copyProperties(updatedRequest, UpdatedRequestDTO);

        return UpdatedRequestDTO;
    }

    /**
     * Deletes a request based on its ID if it's in "STAND_BY" status.
     *
     * @param requestId The ID of the request to be deleted.
     * @throws IllegalArgumentException if the request is not found or not in
     *                                  "STAND_BY" status.
     */
    public void deleteRequest(Integer requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (!request.getStatusRequest().getDescription().equals("STAND_BY")) {
            throw new IllegalArgumentException("You can't delete this request because it isn't in STAND_BY status.");
        }

        requestRepository.delete(request);

    }

    /**
     * Retrieves all requests for a given dependence based on its ID.
     *
     * @param idDependence The ID of the dependence.
     * @return A list of request DTOs belonging to the specified dependence.
     */
    public List<RequestDTO> getAllRequest(Integer idDependence) {
        return requestRepository.findAll().stream().filter(
                request -> request.getUser().getDependence().getId() == idDependence).map(request -> {
                    return new RequestDTO().builder()
                            .id(request.getId())
                            .title(request.getTitle())
                            .description(request.getDescription())
                            .requestDate(request.getRequestDate())
                            .statusId(request.getStatusRequest().getId())
                            .userId(request.getUser().getId())
                            .build();
                }).collect(Collectors.toList());
    }

}
