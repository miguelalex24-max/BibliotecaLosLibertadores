package com.bibliotecalibertadores.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Reserva")
@Data
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codReserva")
	private Integer codReserva;

	private Integer codUsuario;
	private Integer codLibro;

	@Column(name = "fechaReserva")
	private LocalDate fechaReserva;

	@Column(name = "fechaPrestamo")
	private LocalDate fechaPrestamo;

	@Column(name = "fechaDevolucion")
	private LocalDate fechaDevolucion;

	@Column(name = "estadoReserva")
	private Integer estadoReserva = 1; // 1: Pendiente, 2: Atendida, 3: Devuelta, 0: Cancelada

	@ManyToOne
	@JoinColumn(name = "codUsuario", insertable = false, updatable = false)
	private Usuario obj_usuario;

	@ManyToOne
	@JoinColumn(name = "codLibro", insertable = false, updatable = false)
	private Libro obj_libro;
}