package com.bibliotecalibertadores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bibliotecalibertadores.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {

	List<Prestamo> findByCodUsuarioOrderByCodPrestamoDesc(Integer codUsuario);

	List<Prestamo> findAllByOrderByCodPrestamoDesc();
}