package com.sg.gestion.controller;

import com.sg.gestion.dto.LoginDTO;
import com.sg.gestion.entities.Usuario;
import com.sg.gestion.exceptions.PrecondicionFallidaException;
import com.sg.gestion.seguridad.JwtToken;
import com.sg.gestion.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class LoginControlador {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtToken jwtTokenProvider;
   // @Autowired
    //private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioService usuarioService;
    @CrossOrigin(origins = {"http://localhost:3000","http://127.0.0.7:5500", "http://127.0.0.1:3000", "http://172.18.144.1:3000"}) // Especifica la URL de frontend
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {

        Optional<Usuario> usuarioOptional = usuarioService.findByEmail(login.getEmail());
        System.out.println(usuarioOptional);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            System.out.println("usuarioOptional32");
            System.out.println(usuario.getContrasena()+" usuarioOptional32");
            System.out.println(login.getContrasena()+" getContrasena");
            System.out.println(login.getEmail()+" getEmail");
            if (usuario.getContrasena().equals(login.getContrasena())) {

                String jwt = jwtTokenProvider.createTK(login.getEmail(), Collections.emptyList());
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("jwt", jwt);
                responseBody.put("usuario", usuario);

                return ResponseEntity.ok(responseBody);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Crredenciales incorrectas");

            }
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No se encontró ningún usuario con el correo electrónico proporcionado");
        }
    }
}




