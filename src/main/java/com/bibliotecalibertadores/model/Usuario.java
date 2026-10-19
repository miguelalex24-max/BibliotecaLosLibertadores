package com.bibliotecalibertadores.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Usuario")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codUsuario;
    private String nomUsuario;
    private String apeUsuario;
    private String dniUsuario;
    private String tlfUsuario;
    private String correoUsuario;
}
