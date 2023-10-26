package com.sistema.solicitudes.sgs.entities;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.UniqueElements;

import lombok.*;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name = "usuarios")
public class Usuario  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    private String nombre;
    @NotEmpty
    private String apellido;

    @NotEmpty
    private String email;

    private String encryptedPassword;

    @ManyToOne
    @JoinColumn(name = "rol_id") 
    private Rol rol;


    @ManyToOne
    @JoinColumn(name = "dependencia_id") 
    private Dependencia dependencia;

}
