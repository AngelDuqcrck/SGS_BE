package com.sistema.solicitudes.sgs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.sistema.solicitudes.sgs.models.responses.Response;
import com.sistema.solicitudes.sgs.services.implementations.UsuarioService;
import com.sistema.solicitudes.sgs.shared.dto.UsuarioDTO;

@RestController
@RequestMapping("/users")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;


    @PreAuthorize("hasRole('ROLE_JEFE_SERVICIO')")
    @GetMapping("/funca")
    public String prueba(){
        return "Funca!!!!";
    }

    @PostMapping
    public Response registrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Response mensaje = new Response();

        UsuarioDTO usuarioCreado = usuarioService.registrarUsuario(usuarioDTO);

        if (usuarioCreado != null) {
            mensaje.setMessage("Usuario creado con éxito");
        } else {
            mensaje.setMessage("Error al crear el usuario");
        }

        return mensaje;
    }
}
