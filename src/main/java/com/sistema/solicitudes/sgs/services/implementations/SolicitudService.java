package com.sistema.solicitudes.sgs.services.implementations;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sistema.solicitudes.sgs.entities.EstadoSolicitud;
import com.sistema.solicitudes.sgs.entities.Solicitud;
import com.sistema.solicitudes.sgs.entities.Usuario;
import com.sistema.solicitudes.sgs.repositories.EstadoSolicitudRepository;
import com.sistema.solicitudes.sgs.repositories.SolicitudRepository;
import com.sistema.solicitudes.sgs.repositories.UsuarioRepository;
import com.sistema.solicitudes.sgs.shared.dto.SolicitudDTO;

@Service("solicitudService")
public class SolicitudService {

    @Autowired
    private SolicitudRepository requestRepository;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private EstadoSolicitudRepository statusRequestRepository;

    /**
     * This method is responsible for creating a new request in the database.
     * It copies the properties from the provided request DTO to a new request
     * entity,
     * associates the request with the specified user, sets its status to
     * "PENDIENTE,"
     * and saves it in the database.
     *
     * @param solicitudDTO The request data to be created.
     * @param usuarioId    The ID of the user creating the request.
     * @return The created request as a DTO.
     * @throws IllegalArgumentException if the user does not exist or if the
     *                                  "PENDIENTE" status is not found.
     */
    public SolicitudDTO createRequest(SolicitudDTO solicitudDTO, Integer usuarioId) {
        Solicitud request = new Solicitud();

        BeanUtils.copyProperties(solicitudDTO, request);

        Usuario user = userRepository.findById(usuarioId).orElse(null);
        if (user == null) {

            throw new IllegalArgumentException("User not found");
        }

        request.setUsuario(user);

        EstadoSolicitud status = statusRequestRepository.findByNombre("PENDIENTE").orElse(null);

        if (status == null) {
            throw new IllegalArgumentException("Status PENDIENTE not found");
        }

        request.setEstado(status);
        request.setFechaSolicitud(new Date());

        Solicitud savedRequest = requestRepository.save(request);

        SolicitudDTO createdRequest = new SolicitudDTO();
        BeanUtils.copyProperties(savedRequest, createdRequest);

        return createdRequest;
    }

    /**
     * This method retrieves a list of requests associated with a specific user.
     * It fetches requests based on the user's ID
     *
     * @param usuarioId The ID of the user to fetch requests for.
     * @return A list of request DTOs.
     */
    public List<SolicitudDTO> listRequestPerEmployee(Integer usuarioId) {
        List<Solicitud> requests = requestRepository.findByUsuarioId(usuarioId);

        List<SolicitudDTO> requestDTOs = new ArrayList<>();
        for (Solicitud request : requests) {
            SolicitudDTO requestDTO = new SolicitudDTO();
            requestDTO.setUsuarioId(request.getUsuario().getId());
            requestDTO.setEstadoId(request.getEstado().getId());
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
    public SolicitudDTO lookRequestDetails(Integer requestId) {
        SolicitudDTO requestDTO = new SolicitudDTO();
        Solicitud request = new Solicitud();
        request = requestRepository.findById(requestId).get();
        BeanUtils.copyProperties(request, requestDTO);
        requestDTO.setEstadoId(request.getEstado().getId());
        requestDTO.setUsuarioId(request.getUsuario().getId());

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
        Solicitud request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        EstadoSolicitud nuevoEstado = statusRequestRepository.findById(newRequestStateId)
                .orElseThrow(() -> new IllegalArgumentException("Status Request not found"));
        if (newRequestStateId == 5) {
            if (!request.getEstado().getNombre().equals("PENDIENTE")) {
                throw new IllegalArgumentException("You can't send the request because it isn't in PENDIENTE status.");
            }
            request.setEstado(nuevoEstado);
        }

        if (newRequestStateId == 6) {
            if (!request.getEstado().getNombre().equals("ENVIADA")) {
                throw new IllegalArgumentException(
                        "You can't cancel the request because it isn't in PENDIENTE status.");
            }
            request.setEstado(nuevoEstado);
            request.setEstado(statusRequestRepository.findById(1).get());
        }

        requestRepository.save(request);
    }

    /**
     * This method updates the title and description of a request if the request is
     * in "PENDIENTE" status (the request can't be in "ENVIADO" status).
     *
     * @param requestDTO The updated request data.
     * @param requestId  The ID of the request to update.
     * @return The updated request as a DTO.
     * @throws IllegalArgumentException if the request is not found or if it's not
     *                                  in "PENDIENTE" status.
     */
    public SolicitudDTO updateRequest(SolicitudDTO requestDTO, Integer requestId) {

        Solicitud request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (!request.getEstado().getNombre().equals("PENDIENTE")) {
            throw new IllegalArgumentException("You can't edit the request because it isn't in PENDIENTE status.");
        }

        request.setTitulo(requestDTO.getTitulo());
        request.setDescripcion(requestDTO.getDescripcion());

        Solicitud updatedRequest = requestRepository.save(request);

        SolicitudDTO UpdatedRequestDTO = new SolicitudDTO();
        BeanUtils.copyProperties(updatedRequest, UpdatedRequestDTO);

        return UpdatedRequestDTO;
    }

    public void deleteRequest(Integer requestId) {
        Solicitud request = requestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        if (!request.getEstado().getNombre().equals("PENDIENTE")) {
            throw new IllegalArgumentException("You can't delete this request because it isn't in PENDIENTE status.");
        }

            requestRepository.delete(request);
        
    }

}
