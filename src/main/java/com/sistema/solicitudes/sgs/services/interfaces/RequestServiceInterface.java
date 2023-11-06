package com.sistema.solicitudes.sgs.services.interfaces;

import com.sistema.solicitudes.sgs.shared.dto.RequestDTO;
import java.util.List;

public interface RequestServiceInterface {
    
    public RequestDTO createRequest(RequestDTO requestDTO, Integer userId);
    
    public void changeRequestState(Integer requestId, Integer newRequestStateId);
    
    public RequestDTO updateRequest(RequestDTO requestDTO, Integer requestId);
    
    public void deleteRequest(Integer requestId);
    
    public List<RequestDTO> getAllRequest(Integer idDependence);
    
    public List<RequestDTO> getAllVerifiedRequests();
    
    public List<RequestDTO> listRequestPerEmployee(Integer userId);
    
    public RequestDTO lookRequestDetails(Integer requestId);

}
