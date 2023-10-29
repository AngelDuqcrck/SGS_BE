package com.sistema.solicitudes.sgs.services.implementations;

import com.sistema.solicitudes.sgs.entities.Dependence;
import com.sistema.solicitudes.sgs.entities.Rol;
import com.sistema.solicitudes.sgs.entities.User;
import com.sistema.solicitudes.sgs.repositories.DependenceRepository;
import com.sistema.solicitudes.sgs.repositories.RolRepository;
import com.sistema.solicitudes.sgs.repositories.UserRepository;
import com.sistema.solicitudes.sgs.shared.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private DependenceRepository dependenceRepository;

    public UserDTO registerUsers(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        Rol rol = rolRepository.findById(userDTO.getRolId()).orElse(null);
        Dependence dependence = dependenceRepository.findById(userDTO.getDependenceId()).orElse(null);


        if (rol == null) throw new IllegalArgumentException("Rol not found");
        if (dependence == null) throw new IllegalArgumentException("Dependence not found");

        user.setRol(rol);
        user.setDependence(dependence);
        User savedUser = userRepository.save(user);

        UserDTO createdUserDTO = new UserDTO();
        BeanUtils.copyProperties(savedUser, createdUserDTO);

        return createdUserDTO;
    }
}
