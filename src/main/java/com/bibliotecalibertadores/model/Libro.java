package com.bibliotecalibertadores.model;

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
@Table(name = "Libro")
@Data
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codLibro")
    private Integer codigo;

    @Column(name = "tituloLibro")
    private String nombre;

    @Column(name = "autorLibro")
    private String autor;

    private Integer codGenero; 

    @Column(name = "anioPub")
    private Integer anio;
    
    @Column(name = "editorial")
    private String editorial;

    @Column(name = "sinopsis")
    private String sinopsis;

    @Column(name = "imagenLibro")
    private String imagen;

    @Column(name = "estadoLibro")
    private Integer estadoLibro = 1; // 1: Disponible, 0: No disponible;
    
    
    @ManyToOne
    @JoinColumn(name = "codGenero", insertable = false, updatable = false)
    private Genero obj_genero;
}