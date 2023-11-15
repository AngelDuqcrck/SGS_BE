package com.sistema.solicitudes.sgs.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    // This is the code identifier that is asociated with a user
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // Here, we save the user's first name
    @NotEmpty
    private String firstName;
    @NotEmpty
    // Here, we save the user's last name
    private String lastName;
    // Here, we save the user's email, this is used in log in
    @NotEmpty
    private String email;
    // This is the password, we use the password to log in, obiously we need a email
    private String password;
    // Here, we save the id of the rol to identify the charge that each user has
    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;
    //Here, we save the id of the dependence to identify the dependence where user works
    @ManyToOne
    @JoinColumn(name = "dependence_id")
    private Dependence dependence;

    @OneToMany(mappedBy = "employee")
    private List<Ticket> tickets;

}
