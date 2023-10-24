package com.sistema.solicitudes.sgs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.solicitudes.sgs.entities.EstadoSolicitud;

@Repository
public interface EstadoSolicitudRepository extends JpaRepository<EstadoSolicitud, Integer> {
    EstadoSolicitud findByNombre(String nombre);
}