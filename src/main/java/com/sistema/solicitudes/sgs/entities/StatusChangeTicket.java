package com.sistema.solicitudes.sgs.entities;

import java.util.Date;

import javax.persistence.*;

import lombok.*;

 @Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "statusChangeTickets")
public class StatusChangeTicket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private StatusTicket statusTicket;

    @Column(name = "change_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeDate;

}
