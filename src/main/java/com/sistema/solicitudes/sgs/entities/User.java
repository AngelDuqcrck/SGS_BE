package com.sistema.solicitudes.sgs.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    @NotEmpty
    private String email;

    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_id") 
    private Rol rol;


    @ManyToOne
    @JoinColumn(name = "dependence_id")
    private Dependence dependence;

}
