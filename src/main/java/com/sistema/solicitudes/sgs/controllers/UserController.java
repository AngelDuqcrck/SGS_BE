package com.sistema.solicitudes.sgs.controllers;

import com.sistema.solicitudes.sgs.models.responses.Response;
import com.sistema.solicitudes.sgs.services.implementations.UserService;
import com.sistema.solicitudes.sgs.shared.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Endpoint that permits to register a new user based on the provided user data.
     *
     * @param userDTO The user data to create a new user.
     * @return A response indicating whether the user was created successfully.
     */
    @PostMapping
    public Response registerUser(@RequestBody UserDTO userDTO) {
        Response message = new Response();

        UserDTO createdUser = userService.registerUsers(userDTO);

        if (createdUser != null)
            message.setMessage("User created successfully");
        else
            message.setMessage("Unexpected error while user was created");

        return message;
    }
}
