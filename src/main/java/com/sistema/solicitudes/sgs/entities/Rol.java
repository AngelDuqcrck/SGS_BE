package com.sistema.solicitudes.sgs.entities;

import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Rol {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 40)
    @NotEmpty
    private String descripcion;
}
