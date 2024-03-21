package com.proyecto.empresa.services;


import org.springframework.stereotype.Service;

import com.proyecto.empresa.dto.UsuarioDTO;
import com.proyecto.empresa.entidades.Usuario;
import com.proyecto.empresa.repositorio.UsuarioRepositorio;




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

