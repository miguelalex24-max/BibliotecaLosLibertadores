package com.bibliotecalibertadores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotecalibertadores.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer> {
	List<Libro> findByEstadoLibro(Integer estadoLibro);

}
