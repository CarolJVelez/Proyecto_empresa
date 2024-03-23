package com.sg.gestion.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sg.gestion.dto.UsuarioDTO;
import com.sg.gestion.entities.Usuario;
import com.sg.gestion.services.UsuarioService;

import java.util.List;

@RestController
public class UsuarioControlador {

    private final UsuarioService usuarioService;

    public UsuarioControlador(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.7:5500", "http://127.0.0.1:3000", "http://172.18.144.1:3000"}) // Especifica la URL de frontend
    @PostMapping("/usuarios")
    public Usuario crearUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.crearUsuario(usuarioDTO);
    }
    @CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.7:5500", "http://127.0.0.1:3000", "http://172.18.144.1:3000"}) // Especifica la URL de frontend
    @GetMapping("/usuarios")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.7:5500", "http://127.0.0.1:3000", "http://172.18.144.1:3000"}) // Especifica la URL de frontend
    @DeleteMapping("/usuarios/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }

    @CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.7:5500", "http://127.0.0.1:3000", "http://172.18.144.1:3000"}) // Especifica la URL de frontend
    @PutMapping("/usuarios/{id}")
    public Usuario actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.actualizarUsuario(id, usuarioDTO);
    }
    @CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.7:5500", "http://127.0.0.1:3000", "http://172.18.144.1:3000"}) // Especifica la URL de frontend    @GetMapping("/usuarios")
    @GetMapping("/usuarios/verificar")
    public ResponseEntity<?> verificarExistenciaUsuarioPorEmail(@RequestParam String email) {
        boolean existeUsuario = usuarioService.existeUsuarioPorEmail(email);
        return ResponseEntity.ok().body(existeUsuario);
    }
}
