package com.sistema.solicitudes.sgs.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "tickets")
public class Ticket {
    //This is the code identifier for each ticket 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //This is the ticket tittle
    @Column(nullable = false, length = 100)
    private String tittle;

    //This is all the information about a ticket
    @Column(nullable = false, length = 200)
    private String description;

    //Here, the service employee could write comments about the ticket
    @Column(length = 255)
    private String observation;

    //Here, we save the date when a ticket was created/opened
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    //Here, we save the date when a ticket was closed/finished
    @Column(name = "end_date")
    @Temporal(TemporalType.TIME)
    private Date endDate;

    //Here we save the id to identify the employee who have to solve the problem whereby the request was made
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User employee;

    //Here we save the request for which this ticket was generated
    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    //Here we saved the date, when the ticket was assigned to a service employee
    @Column(name = "assignment_date")
    @Temporal(TemporalType.DATE)
    private Date assignmentDate;

    //Here we saved the status, that ticket is currently
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusTicket statusTicket;
}
