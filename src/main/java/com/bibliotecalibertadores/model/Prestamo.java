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
@Table(name = "Prestamo")
@Data
public class Prestamo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codPrestamo")
	private Integer codPrestamo;

	private Integer codUsuario;

	private Integer codLibro;

	@Column(name = "fechaPrestamo")
	private LocalDate fechaPrestamo;

	@Column(name = "fechaDevolucion")
	private LocalDate fechaDevolucion;

	@Column(name = "estadoPrestamo")
	private Integer estadoPrestamo = 1; // Activo por defecto

	@ManyToOne
	@JoinColumn(name = "codUsuario", insertable = false, updatable = false)
	private Usuario obj_usuario;

	@ManyToOne
	@JoinColumn(name = "codLibro", insertable = false, updatable = false)
	private Libro obj_libro;
}
