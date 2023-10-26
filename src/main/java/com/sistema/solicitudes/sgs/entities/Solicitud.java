package com.sistema.solicitudes.sgs.entities;
import lombok.*;
import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name= "solicitudes")
public class Solicitud {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "titulo", length = 50)
    private String titulo;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "fecha_solicitud")
    @Temporal(TemporalType.DATE)
    private Date fechaSolicitud;

    @ManyToOne (fetch=FetchType.EAGER)
    @JoinColumn(name = "estado_id")
    private EstadoSolicitud estado;

    @ManyToOne (fetch=FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
