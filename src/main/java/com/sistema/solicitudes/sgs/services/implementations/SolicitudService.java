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
    private EstadoSolicitudRepository estadoSolicitudRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public SolicitudDTO crearSolicitud(SolicitudDTO solicitudDTO) {
        Solicitud solicitud = new Solicitud();
        BeanUtils.copyProperties(solicitudDTO, solicitud);

        // Obtén el usuario que está creando la solicitud
        Usuario usuario = usuarioRepository.findById(solicitudDTO.getUsuarioId()).orElse(null);
        
        if (usuario == null) {
            throw new IllegalArgumentException("No se encontró el usuario");
        }
        
        // Establece la fecha de solicitud como la fecha y hora actuales
        solicitud.setFechaSolicitud(new Date());

        // Obtén el estado "PENDIENTE" para la solicitud
        EstadoSolicitud estado = estadoSolicitudRepository.findByNombre("PENDIENTE");

        if (estado == null) {
            throw new IllegalArgumentException("No se encontró el estado de solicitud PENDIENTE ");
        }

        solicitud.setEstado(estado);
        solicitud.setUsuario(usuario);

        Solicitud solicitudGuardada = solicitudRepository.save(solicitud);
        SolicitudDTO solicitudCreadaDTO = new SolicitudDTO();
        BeanUtils.copyProperties(solicitudGuardada, solicitudCreadaDTO);

        return solicitudCreadaDTO;
    }
}
