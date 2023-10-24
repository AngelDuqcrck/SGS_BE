package com.sistema.solicitudes.sgs.services.implementations;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.solicitudes.sgs.entities.Dependencia;
import com.sistema.solicitudes.sgs.entities.Rol;
import com.sistema.solicitudes.sgs.entities.Usuario;
import com.sistema.solicitudes.sgs.repositories.DependenciaRepository;
import com.sistema.solicitudes.sgs.repositories.RolRepository;
import com.sistema.solicitudes.sgs.repositories.UsuarioRepository;
import com.sistema.solicitudes.sgs.shared.dto.UsuarioDTO;

@Service("usuarioService")
public class UsuarioService {
    
     @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private DependenciaRepository dependenciaRepository;

    public UsuarioDTO registrarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(usuarioDTO, usuario);

        Rol rol = rolRepository.findById(usuarioDTO.getRolId()).orElse(null);
        Dependencia dependencia = dependenciaRepository.findById(usuarioDTO.getDependenciaId()).orElse(null);


        if (rol == null) throw new IllegalArgumentException("No se encontro el rol");
        if (dependencia == null) throw new IllegalArgumentException("No se encontro la dependencia");

        usuario.setRol(rol);
        usuario.setDependencia(dependencia);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        UsuarioDTO usuarioCreadoDTO = new UsuarioDTO();
        BeanUtils.copyProperties(usuarioGuardado, usuarioCreadoDTO);

        return usuarioCreadoDTO;
    }
}
