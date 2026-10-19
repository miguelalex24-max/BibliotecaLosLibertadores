package com.bibliotecalibertadores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bibliotecalibertadores.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	@Query("SELECT u FROM Usuario u WHERE u.correoUsuario = :correo AND u.passwordUsuario = :password")
    Usuario validarLogin(@Param("correo") String correo, @Param("password") String password);
}
