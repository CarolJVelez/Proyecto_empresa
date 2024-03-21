package com.sg.gestion.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sg.gestion.dto.UsuarioDTO;
import com.sg.gestion.entities.Usuario;
import com.sg.gestion.services.UsuarioService;

@RestController
public class UsuarioControlador {
    
	private UsuarioService usuarioService;

    @PostMapping("/usuarios")
    public Usuario crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.crearUsuario(usuarioDTO);
    }


}
