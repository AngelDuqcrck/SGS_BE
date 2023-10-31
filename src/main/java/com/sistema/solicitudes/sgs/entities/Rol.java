package com.sistema.solicitudes.sgs.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "roles")
public class Rol {
    //This is the code identifier of a role
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //This is saved the name of each role
    @Column(nullable = false, length = 40)
    @NotEmpty
    private String description;
}
