package com.bibliotecalibertadores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bibliotecalibertadores.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Integer> {
}