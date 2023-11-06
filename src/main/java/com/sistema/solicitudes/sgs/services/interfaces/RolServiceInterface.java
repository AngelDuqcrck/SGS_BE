package com.sistema.solicitudes.sgs.services.interfaces;

import java.util.List;

import com.sistema.solicitudes.sgs.shared.dto.RolDTO;

public interface RolServiceInterface {
    
    public List<RolDTO> listRoles();
    public RolDTO updateRol(Integer rolId, RolDTO rolDTO);
    


}
