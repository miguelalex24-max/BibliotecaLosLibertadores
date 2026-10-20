package com.bibliotecalibertadores.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.bibliotecalibertadores.model.Usuario;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.bibliotecalibertadores.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository repUsuarios;

	@GetMapping("/inicio")
	public String inicioUsuario(HttpSession session, Model model) {
		Usuario usuario = (Usuario) session.getAttribute("logueo");

		if (usuario == null) {
			return "redirect:/login";
		}

		model.addAttribute("usuario", usuario);
		return "usuario/iniciousuario";
	}

	// Tickets
	@GetMapping("/tickets")
	public String listarTickets() {
		return "usuario/tickets";
	}

	@GetMapping("/tickets/nuevo")
	public String crearTicket() {
		return "usuario/crearticket";
	}

	// Reservas
	@GetMapping("/reservas")
	public String listarReservas() {
		return "usuario/reservas";
	}

	@GetMapping("/reservas/nueva")
	public String crearReserva() {
		return "usuario/crearreservas";
	}

	@GetMapping("/reservas/editar")
	public String editarReserva() {
		return "usuario/crudreserva";
	}
}
