package com.bibliotecalibertadores.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotecalibertadores.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Integer> {

}
