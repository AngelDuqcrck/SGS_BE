package com.sistema.solicitudes.sgs.entities;

import lombok.*;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    // This is the code identifier of each request.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // This is the title or main name of a request
    @Column(name = "title", length = 50)
    private String title;

    // This is all content of a request, the request details
    @Column(name = "description", length = 255)
    private String description;

    // This is the date when a request was created, here is exact time (hours,
    // minutes and seconds)
    @Column(name = "request_date")
    @Temporal(TemporalType.DATE)
    private Date requestDate;

    // This is the current state of a request
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    private StatusRequest statusRequest;

    // This is the id of status request, through we can identify the current status
    // request
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StatusChangeRequest> statusChanges = new ArrayList<>();

    // This is the id of user, through we can identify the user who wrote the
    // request
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
