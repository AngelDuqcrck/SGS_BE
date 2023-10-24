package com.sistema.solicitudes.sgs.controllers;

import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.solicitudes.sgs.models.responses.Response;
import com.sistema.solicitudes.sgs.services.implementations.RolService;
import com.sistema.solicitudes.sgs.shared.dto.RolDTO;

@RestController
@RequestMapping("/rol")
public class RolController {
    
      @Autowired
    private RolService rolService;

    @GetMapping
    public List<RolDTO> listarRoles() {
        return rolService.listarRoles();
    }

    @PutMapping
    public Response actualizarRol(@RequestParam Integer rolId, @RequestBody RolDTO rolDTO) {
        Response mensaje = new Response();
        
       RolDTO rolResponse = new RolDTO();
       BeanUtils.copyProperties(rolDTO, rolResponse);
       
       RolDTO rolActualizado = rolService.actualizarRol(rolId, rolDTO);

       if(rolActualizado!= null){
         
         mensaje.setMessage("Rol actualizado con exido");
       }else mensaje.setMessage("Error al actualizar el rol");

       return mensaje;
    }
    
}
