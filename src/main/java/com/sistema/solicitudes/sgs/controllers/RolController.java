package com.sistema.solicitudes.sgs.controllers;

import com.sistema.solicitudes.sgs.models.responses.Response;
import com.sistema.solicitudes.sgs.services.implementations.RolService;
import com.sistema.solicitudes.sgs.shared.dto.RolDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rol")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public List<RolDTO> listRoles() {
        return rolService.listRoles();
    }

    @PutMapping
    public Response updateRol(@RequestParam Integer rolId, @RequestBody RolDTO rolDTO) {
        Response message = new Response();

        RolDTO rolResponse = new RolDTO();
        BeanUtils.copyProperties(rolDTO, rolResponse);

        RolDTO updatedRol = rolService.updateRol(rolId, rolDTO);

        if (updatedRol != null)
            message.setMessage("Rol updated successfully");
        else
            message.setMessage("Unexpected error while rol was updated");

        return message;
    }

}
