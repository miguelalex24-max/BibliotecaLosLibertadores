package com.bibliotecalibertadores.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    // 1. Menú o Inicio del usuario
    @GetMapping({"/", "/inicio"})
    public String inicioUsuario() {
        return "inicio"; // Esto buscará un archivo inicio.html dentro de templates/usuario/
    }

    // 2. Repertorio de libros
    @GetMapping("/libros/repertorio")
    public String repertorioLibros() {
        return "repertorio_libros"; 
    }

    // 3. Generar ticket (Crear / Listar)
    @GetMapping("/tickets")
    public String listarTickets() {
        return "listar_tickets"; 
    }

    @GetMapping("/tickets/nuevo")
    public String crearTicket() {
        return "form_ticket"; 
    }

    // 4. Reservar libros (Crear / Listar / Editar)
    @GetMapping("/reservas")
    public String listarReservas() {
        return "listar_reservas"; 
    }

    @GetMapping("/reservas/nueva")
    public String crearReserva() {
        return "form_reserva"; 
    }

    @GetMapping("/reservas/editar")
    public String editarReserva() {
        return "editar_reserva"; 
    }
}