package com.sistema.solicitudes.sgs.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.solicitudes.sgs.entities.Rol;
import com.sistema.solicitudes.sgs.repositories.RolRepository;
import com.sistema.solicitudes.sgs.shared.dto.RolDTO;

@Service("rolService")
public class RolService {
    
     @Autowired
    private RolRepository rolRepository;

    public List<RolDTO> listarRoles() {
        List<Rol> roles = rolRepository.findAll();
        return roles.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public RolDTO actualizarRol(Integer rolId, RolDTO rolDTO) {
        Rol rol = rolRepository.findById(rolId).orElse(null);
        if (rol == null) {
            return null;
        }
        
        BeanUtils.copyProperties(rolDTO, rol);

        Rol rolActualizado = rolRepository.save(rol);

        return convertEntityToDTO(rolActualizado);
    }


    private RolDTO convertEntityToDTO(Rol rol) {
        RolDTO rolDTO = new RolDTO();
        BeanUtils.copyProperties(rol, rolDTO);
        return rolDTO;
    }

}
