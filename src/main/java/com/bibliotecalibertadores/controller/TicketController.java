package com.bibliotecalibertadores.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bibliotecalibertadores.model.Ticket;
import com.bibliotecalibertadores.model.Usuario;
import com.bibliotecalibertadores.repository.TicketsRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	private TicketsRepository repTickets;

	@GetMapping
	public String listar(HttpSession session, Model model) {
		Usuario usuario = (Usuario) session.getAttribute("logueo");
		if (usuario == null) return "redirect:/login";

		model.addAttribute("lstTickets", repTickets.findByCodUsuarioOrderByCodTicketDesc(usuario.getCodUsuario()));
		return "usuario/tickets";
	}

	@GetMapping("/nuevo")
	public String nuevo(HttpSession session) {
		if (session.getAttribute("logueo") == null) return "redirect:/login";
		return "usuario/crearticket";
	}

	@PostMapping("/guardar")
	public String guardar(@RequestParam("asunto") String asunto, @RequestParam("descripcion") String descripcion,
			HttpSession session, RedirectAttributes redirectAttrs) {

		Usuario usuario = (Usuario) session.getAttribute("logueo");
		if (usuario == null) return "redirect:/login";

		try {
			Ticket ticket = new Ticket();
			repTickets.save(ticket);

			redirectAttrs.addFlashAttribute("mensaje", "TICKET REGISTRADO CORRECTAMENTE");
			redirectAttrs.addFlashAttribute("tipoMensaje", "exito");
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("mensaje", "NO SE PUDO REGISTRAR EL TICKET");
			redirectAttrs.addFlashAttribute("tipoMensaje", "error");
		}
		return "redirect:/tickets";
	}
}
