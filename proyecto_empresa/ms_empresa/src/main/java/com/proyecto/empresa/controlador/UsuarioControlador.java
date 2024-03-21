package com.proyecto.empresa.controlador;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.empresa.dto.UsuarioDTO;
import com.proyecto.empresa.entidades.Usuario;
import com.proyecto.empresa.services.UsuarioService;



@RestController
public class UsuarioControlador {
    
	private UsuarioService usuarioService;

    @PostMapping("/usuarios")
    public Usuario crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.crearUsuario(usuarioDTO);
    }


}
