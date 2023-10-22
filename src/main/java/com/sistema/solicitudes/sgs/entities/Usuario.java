package com.sistema.solicitudes.sgs.entities;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.UniqueElements;

import lombok.*;



@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @UniqueElements
    private String email;

    private String encryptedPassword;

    @ManyToOne
    @JoinColumn(name = "rol_id") 
    private Rol rol;

}
