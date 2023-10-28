package com.sistema.solicitudes.sgs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.solicitudes.sgs.entities.EstadoSolicitud;
import java.util.Optional;


@Repository
public interface EstadoSolicitudRepository extends JpaRepository<EstadoSolicitud, Integer> {

  
    Optional<EstadoSolicitud> findByNombre(String nombre);
}