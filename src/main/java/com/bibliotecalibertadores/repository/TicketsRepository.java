package com.bibliotecalibertadores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotecalibertadores.model.Ticket;

public interface TicketsRepository extends JpaRepository<Ticket, Integer> {
	List<Ticket> findByCodUsuarioOrderByCodTicketDesc(Integer codUsuario);
	List<Ticket> findAllByOrderByCodTicketDesc();
}
