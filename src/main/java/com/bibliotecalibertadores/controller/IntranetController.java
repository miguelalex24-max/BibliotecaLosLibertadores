package com.bibliotecalibertadores.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bibliotecalibertadores.repository.LibroRepository;

@Controller
@RequestMapping("/intranet")
public class IntranetController {

	@Autowired
	private LibroRepository repLibros;

	@GetMapping({ "", "/" })
	public String inicio() {
		return "bibliotecario/inicioadmin";
	}

	@GetMapping("/libros")
	public String libros(Model model) {
		model.addAttribute("lstLibros", repLibros.findAll());
		return "bibliotecario/librosadmin";
	}

	@GetMapping("/tickets")
	public String tickets() {
		return "bibliotecario/ticketsadmin";
	}

	@GetMapping("/reservas")
	public String reservas() {
		return "bibliotecario/reservasadmin";
	}
}