package com.sistema.solicitudes.sgs.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Integer id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private String email;

    private String password;

    // We'll use the rol id
    private String rol;

    private Integer rolId;

    // We'll use the dependence id
    private String dependence;
    private Integer dependenceId;

}
