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
@Table(name = "Ticket")
@Data
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codTicket")
	private Integer codTicket;

	private Integer codUsuario;

	@Column(name = "asuntoTicket", length = 100)
	private String asunto;

	@Column(name = "descripcionTicket", length = 500)
	private String descripcion;

	@Column(name = "fechaTicket")
	private LocalDate fechaTicket;

	@Column(name = "estadoTicket")
	private Integer estadoTicket = 1; // Activo por defecto

	@ManyToOne
	@JoinColumn(name = "codUsuario", insertable = false, updatable = false)
	private Usuario obj_usuario;
}
