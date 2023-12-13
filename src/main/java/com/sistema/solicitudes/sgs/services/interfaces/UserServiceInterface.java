package com.sistema.solicitudes.sgs.services.interfaces;

import com.sistema.solicitudes.sgs.shared.dto.UserDTO;

import java.util.List;

public interface UserServiceInterface {

    public List<UserDTO> getUsers();
    
    public UserDTO registerUsers(UserDTO userDTO);

    public UserDTO findUser(String email);

}
