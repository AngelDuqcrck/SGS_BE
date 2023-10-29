package com.sistema.solicitudes.sgs.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name= "requests")
public class Request {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "request_date")
    @Temporal(TemporalType.DATE)
    private Date requestDate;

    @ManyToOne (fetch=FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private StatusRequest statusRequest;

    @ManyToOne (fetch=FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
