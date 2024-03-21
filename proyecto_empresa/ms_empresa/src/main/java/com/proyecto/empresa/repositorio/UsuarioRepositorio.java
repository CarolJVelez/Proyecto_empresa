package com.proyecto.empresa.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.empresa.entidades.Usuario;



@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
}
