package com.sg.gestion.services;


import com.sg.gestion.dto.LoginDTO;
import com.sg.gestion.seguridad.JwtToken;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sg.gestion.dto.UsuarioDTO;
import com.sg.gestion.entities.Usuario;
import com.sg.gestion.repositories.UsuarioRepositorio;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Log4j2
public class UsuarioService implements UserDetailsService {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private JwtToken jwtToken;


    public UsuarioService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public Usuario crearUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setContrasena(usuarioDTO.getContrasena());
        usuario.setRol(usuarioDTO.getRol());
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> optionalUser = usuarioRepositorio.findByEmail(username);
        Usuario usuario = optionalUser.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return new User(usuario.getEmail(), usuario.getContrasena(),
                Collections.emptyList());
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepositorio.findByEmail(email) ;
    }

    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        return usuarios.stream()
                .map(usuario -> {
                    UsuarioDTO usuarioDTO = convertirUsuarioADTO(usuario);
                    usuarioDTO.setId(usuario.getId()); // Asignar el ID del usuario al DTO
                    return usuarioDTO;
                })
                .collect(Collectors.toList());
    }

    public void eliminarUsuario(Long id) {
        usuarioRepositorio.deleteById(id);
    }

    private UsuarioDTO convertirUsuarioADTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNombre(usuario.getNombre());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setContrasena(usuario.getContrasena()); // Asignar la contraseña
        usuarioDTO.setRol(usuario.getRol());
        return usuarioDTO;
    }

    public Usuario actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            // Verificar si al menos uno de los campos ha cambiado
            if (campoHaCambiado(usuario.getNombre(), usuarioDTO.getNombre()) ||
                    campoHaCambiado(usuario.getEmail(), usuarioDTO.getEmail()) ||
                    campoHaCambiado(usuario.getContrasena(), usuarioDTO.getContrasena()) ||
                    campoHaCambiado(usuario.getRol(), usuarioDTO.getRol())) {

                // Actualizar los campos del usuario con los valores del DTO
                usuario.setNombre(usuarioDTO.getNombre());
                usuario.setEmail(usuarioDTO.getEmail());
                usuario.setContrasena(usuarioDTO.getContrasena());
                usuario.setRol(usuarioDTO.getRol());

                // Guardar los cambios en la base de datos
                return usuarioRepositorio.save(usuario);
            } else {
                // No se han realizado cambios, devolver el usuario existente sin guardar nada
                return usuario;
            }
        } else {
            // El usuario no existe, se puede lanzar una excepción o manejarlo de alguna otra manera según los requisitos
            // Aquí simplemente devolvemos null para indicar que no se pudo actualizar el usuario
            return null;
        }
    }

    private boolean campoHaCambiado(String valorAnterior, String valorNuevo) {
        return !Objects.equals(valorAnterior, valorNuevo);
    }
    public boolean existeUsuarioPorEmail(String email) {
        return usuarioRepositorio.existsByEmail(email);
    }


}