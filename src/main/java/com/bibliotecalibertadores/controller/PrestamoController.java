package com.bibliotecalibertadores.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bibliotecalibertadores.model.Usuario;
import com.bibliotecalibertadores.repository.PrestamoRepository;

import jakarta.servlet.http.HttpSession;

// Estados del prestamo: 1 = Prestado (activo), 0 = Devuelto.

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

	@Autowired
	private PrestamoRepository repPrestamos;

	@GetMapping
	public String misPrestamos(HttpSession session, Model model) {
		Usuario usuario = (Usuario) session.getAttribute("logueo");
		if (usuario == null) return "redirect:/login";

		// Solo los prestamos del usuario logueado
		model.addAttribute("lstPrestamos", repPrestamos.findByCodUsuarioOrderByCodPrestamoDesc(usuario.getCodUsuario()));
		model.addAttribute("hoy", LocalDate.now());
		return "usuario/misprestamos";
	}
}