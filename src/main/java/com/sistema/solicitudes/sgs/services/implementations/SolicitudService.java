package com.sistema.solicitudes.sgs.services.implementations;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import com.sistema.solicitudes.sgs.entities.EstadoSolicitud;
import com.sistema.solicitudes.sgs.entities.Solicitud;
import com.sistema.solicitudes.sgs.entities.Usuario;
import com.sistema.solicitudes.sgs.repositories.EstadoSolicitudRepository;
import com.sistema.solicitudes.sgs.repositories.SolicitudRepository;
import com.sistema.solicitudes.sgs.repositories.UsuarioRepository;
import com.sistema.solicitudes.sgs.shared.dto.SolicitudDTO;

@Service("solicitudService")
public class SolicitudService {

    @Autowired
    private SolicitudRepository solicitudRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EstadoSolicitudRepository estadoSolicitudRepository;

    public SolicitudDTO crearSolicitud(SolicitudDTO solicitudDTO, Integer usuarioId) {
        Solicitud solicitud = new Solicitud();
        
        BeanUtils.copyProperties(solicitudDTO, solicitud);
        
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) {

            throw new IllegalArgumentException("Usuario no encontrado") ;
        }
        
        solicitud.setUsuario(usuario);
        
        EstadoSolicitud estado = estadoSolicitudRepository.findByNombre("PENDIENTE").orElse(null);
        
        if (estado == null) {
            throw new IllegalArgumentException("Estado pendiente no encontrado");
        }
        
        solicitud.setEstado(estado);
        solicitud.setFechaSolicitud(new Date());
        
        Solicitud solicitudGuardada = solicitudRepository.save(solicitud);
        
        SolicitudDTO solicitudCreada = new SolicitudDTO();
        BeanUtils.copyProperties(solicitudGuardada, solicitudCreada);
        
        return solicitudCreada;
    }
}
