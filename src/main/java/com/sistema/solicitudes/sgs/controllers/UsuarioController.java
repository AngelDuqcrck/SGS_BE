package com.sistema.solicitudes.sgs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.solicitudes.sgs.models.responses.Response;
import com.sistema.solicitudes.sgs.services.implementations.UsuarioService;
import com.sistema.solicitudes.sgs.shared.dto.UsuarioDTO;

@RestController
@RequestMapping("/users")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

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
