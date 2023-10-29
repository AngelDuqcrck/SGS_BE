package com.sistema.solicitudes.sgs.services.implementations;

import com.sistema.solicitudes.sgs.entities.Rol;
import com.sistema.solicitudes.sgs.repositories.RolRepository;
import com.sistema.solicitudes.sgs.shared.dto.RolDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolService {
    
     @Autowired
    private RolRepository rolRepository;

    public List<RolDTO> listRoles() {
        List<Rol> roles = rolRepository.findAll();
        return roles.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    public RolDTO updateRol(Integer rolId, RolDTO rolDTO) {
        Rol rol = rolRepository.findById(rolId).orElse(null);
        if (rol == null) {
            return null;
        }
        rol.setDescription(rolDTO.getDescription());

        Rol updatedRol = rolRepository.save(rol);

        return convertEntityToDTO(updatedRol);
    }

    private RolDTO convertEntityToDTO(Rol rol) {
        RolDTO rolDTO = new RolDTO();
        BeanUtils.copyProperties(rol, rolDTO);
        return rolDTO;
    }

}
