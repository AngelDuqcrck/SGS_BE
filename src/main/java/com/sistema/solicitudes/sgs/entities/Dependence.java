package com.sistema.solicitudes.sgs.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dependencies")
public class Dependence {
    //This is the code identifier of each dependence
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //This atribute is the name of each dependence, that has a lenght of 100 chars
    @Column(name = "description", length = 100)
    private String description;
}
