package com.sistema.solicitudes.sgs.entities;
import lombok.*;
import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "tickets")
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(length = 255)
    private String observacion;

    @Column(name = "fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @Column(name = "fecha_final")
    @Temporal(TemporalType.DATE)
    private Date fechaFinal;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario empleado;

    @Column(name = "fecha_asig")
    @Temporal(TemporalType.DATE)
    private Date fechaAsignacion;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoTicket estado;
}
