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

    /**
     * Retrieves a list of all roles in the system.
     *
     * @return A list of role DTOs.
     */
    public List<RolDTO> listRoles() {
        List<Rol> roles = rolRepository.findAll();
        return roles.stream()
                .map(this::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Updates the description of a role based on its ID.
     *
     * @param rolId  The ID of the role to update.
     * @param rolDTO The role data with the updated description.
     * @return The updated role as a DTO, or null if the role is not found.
     */
    public RolDTO updateRol(Integer rolId, RolDTO rolDTO) {
        Rol rol = rolRepository.findById(rolId).orElse(null);
        if (rol == null) {
            return null;
        }
        rol.setDescription(rolDTO.getDescription());

        Rol updatedRol = rolRepository.save(rol);

        return convertEntityToDTO(updatedRol);
    }

    /**
     * Converts a Rol entity to a RolDTO.
     *
     * @param rol The role entity to convert.
     * @return The role DTO.
     */
    private RolDTO convertEntityToDTO(Rol rol) {
        RolDTO rolDTO = new RolDTO();
        BeanUtils.copyProperties(rol, rolDTO);
        return rolDTO;
    }

}
