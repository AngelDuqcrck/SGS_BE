package com.sistema.solicitudes.sgs.entities;

import javax.persistence.*;

import lombok.*;

@Getter
@Setter@Entity
@Table(name = "dependencias")
public class Dependencia {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", length = 100)
    private String nombre;
}
