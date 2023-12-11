package com.sistema.solicitudes.sgs.services.implementations;

import com.sistema.solicitudes.sgs.entities.Dependence;
import com.sistema.solicitudes.sgs.entities.Rol;
import com.sistema.solicitudes.sgs.entities.User;
import com.sistema.solicitudes.sgs.repositories.DependenceRepository;
import com.sistema.solicitudes.sgs.repositories.RolRepository;
import com.sistema.solicitudes.sgs.repositories.UserRepository;
import com.sistema.solicitudes.sgs.services.interfaces.UserServiceInterface;
import com.sistema.solicitudes.sgs.shared.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceInterface{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private DependenceRepository dependenceRepository;

    /**
     * Registers a new user based on the provided user data. It associates the user
     * with a specific role
     * and dependence, and saves the user in the system.
     *
     * @param userDTO The user data for registration.
     * @return The newly registered user as a DTO.
     * @throws IllegalArgumentException if the associated role or dependence is not
     *                                  found.
     */
    @Override
    public UserDTO registerUsers(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);

        Rol rol = rolRepository.findById(userDTO.getRolId()).orElse(null);
        Dependence defaultDependence = dependenceRepository.findByDescription("SERVICES").orElse(null);

        if (rol == null)
            throw new IllegalArgumentException("Rol not found");

    
        if (rol.getDescription().equals("ROLE_SERVICE_BOSS") || rol.getDescription().equals("ROLE_SERVICE_EMPLOYEE")) {
            if (defaultDependence == null) {
                throw new IllegalArgumentException("Default dependence 'SERVICES' not found");
            }
            user.setDependence(defaultDependence);
        } else {
            
            Dependence dependence = dependenceRepository.findById(userDTO.getDependenceId()).orElse(null);
            if (dependence == null) {
                throw new IllegalArgumentException("Dependence not found");
            }
            user.setDependence(dependence);
        }

        user.setRol(rol);
        User savedUser = userRepository.save(user);

        UserDTO createdUserDTO = new UserDTO();
        BeanUtils.copyProperties(savedUser, createdUserDTO);

        return createdUserDTO;
    }

    @Override
    public UserDTO findUser(String email) {
        User userRes = userRepository.findByEmail(email).get();
        UserDTO user = new UserDTO().builder()
                .id(userRes.getId())
                .email(userRes.getEmail())
                .firstName(userRes.getFirstName())
                .lastName(userRes.getLastName())
                .dependence(userRes.getDependence().getDescription())
                .password(null)
                .rol(userRes.getRol().getDescription())
                .dependenceId(userRes.getDependence().getId())
                .build();
        return user;
    }
}
