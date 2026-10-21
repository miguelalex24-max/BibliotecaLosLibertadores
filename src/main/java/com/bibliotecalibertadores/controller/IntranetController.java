package com.bibliotecalibertadores.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bibliotecalibertadores.repository.LibroRepository;
import com.bibliotecalibertadores.repository.PrestamoRepository;
import com.bibliotecalibertadores.repository.TicketsRepository;
import com.bibliotecalibertadores.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/intranet")
public class IntranetController {

	private static final String LOGIN = "redirect:/login";

	@Autowired
	private LibroRepository repLibros;

	@Autowired
	private PrestamoRepository repPrestamos;

	@Autowired
	private TicketsRepository repTickets;

	@Autowired
	private UsuarioRepository repUsuarios;

	@GetMapping({ "", "/" })
	public String inicio(HttpSession session) {
		return "bibliotecario/inicioadmin";
	}

	@GetMapping("/libros")
	public String libros(HttpSession session, Model model) {
		model.addAttribute("lstLibros", repLibros.findAll(Sort.by("codigo")));
		return "bibliotecario/librosadmin";
	}

	@GetMapping("/prestamos")
	public String prestamos(HttpSession session, Model model) {
		model.addAttribute("lstPrestamos", repPrestamos.findAllByOrderByCodPrestamoDesc());
		model.addAttribute("hoy", LocalDate.now());
		return "bibliotecario/prestamosadmin";
	}

	@GetMapping("/tickets")
	public String tickets(HttpSession session, Model model) {
		model.addAttribute("lstTickets", repTickets.findAllByOrderByCodTicketDesc());
		return "bibliotecario/ticketsadmin";
	}

	@GetMapping("/usuarios")
	public String usuarios(HttpSession session, Model model) {
		model.addAttribute("lstUsuarios", repUsuarios.findAll(Sort.by("codUsuario")));
		return "bibliotecario/usuariosadmin";
	}

	@GetMapping("/reportes")
	public String reportes(HttpSession session) {
		return "bibliotecario/reportes"; // en blanco: aqui ira JasperReports
	}
}