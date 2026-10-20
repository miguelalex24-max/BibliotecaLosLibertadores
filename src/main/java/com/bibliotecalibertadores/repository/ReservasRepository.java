package com.bibliotecalibertadores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotecalibertadores.model.Reserva;

public interface ReservasRepository extends JpaRepository<Reserva, Integer> {
	List<Reserva> findByCodUsuarioOrderByCodReservaDesc(Integer codUsuario);
	List<Reserva> findAllByOrderByCodReservaDesc();
}
