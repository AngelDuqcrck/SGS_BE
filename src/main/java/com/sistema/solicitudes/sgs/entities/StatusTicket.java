package com.sistema.solicitudes.sgs.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "status_tickets")
public class StatusTicket {
    //Here we save code identifier of each status that could have a ticket
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //This is the name of each status that could be a ticket
    @Column(name = "description", length = 50)
    private String description;
}
