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
public class UserServiceEmployeeDTO {

    private Integer id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

}
