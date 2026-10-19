package com.bibliotecalibertadores.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotecalibertadores.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
Usuario findByCorreoUsuarioAndPasswordUsuario(String correo, String password);
}
