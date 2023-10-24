package com.sistema.solicitudes.sgs.shared.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UsuarioDTO {
    private Integer id;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String apellido;

    private String email;

    private String password;

    private String encryptedPassword;

    // Utilizaremos el ID del rol en lugar de una referencia al objeto Rol
    private Integer rolId;

    // Utilizaremos el ID del la dependencia en lugar de una referencia al objeto Dependencia
    private Integer dependenciaId;

}
