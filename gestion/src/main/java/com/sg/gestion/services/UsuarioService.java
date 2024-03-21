package com.sg.gestion.services;


import org.springframework.stereotype.Service;

import com.sg.gestion.dto.UsuarioDTO;
import com.sg.gestion.entities.Usuario;
import com.sg.gestion.repositories.UsuarioRepositorio;


@Service
public class UsuarioService {

    private UsuarioRepositorio usuarioRepositorio;

    public Usuario crearUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setContraseña(usuarioDTO.getContraseña());
        usuario.setRol(usuarioDTO.getRol());
        return usuarioRepositorio.save(usuario);
    }
   
}

