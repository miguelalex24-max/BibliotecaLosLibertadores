package com.bibliotecalibertadores.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bibliotecalibertadores.model.Libro;
import com.bibliotecalibertadores.model.Reserva;
import com.bibliotecalibertadores.model.Usuario;
import com.bibliotecalibertadores.repository.LibroRepository;
import com.bibliotecalibertadores.repository.ReservasRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

	@Autowired
	private ReservasRepository repReservas;

	@Autowired
	private LibroRepository repLibros;

	@GetMapping
	public String listar(HttpSession session, Model model) {
		Usuario usuario = (Usuario) session.getAttribute("logueo");
		if (usuario == null) return "redirect:/login";

		model.addAttribute("lstReservas", repReservas.findByCodUsuarioOrderByCodReservaDesc(usuario.getCodUsuario()));
		return "usuario/reservas";
	}

	@GetMapping("/nueva")
	public String nueva(@RequestParam Integer codLibro, HttpSession session,
			Model model) {
		if (session.getAttribute("logueo") == null) return "redirect:/login";

		model.addAttribute("lstLibros", repLibros.findByEstadoLibro(1));
		model.addAttribute("codLibroSel", codLibro);
		model.addAttribute("hoy", LocalDate.now());
		return "usuario/crearreservas";
	}

	@PostMapping("/guardar")
	@Transactional
	public String guardar(@RequestParam("codLibro") Integer codLibro,
			@RequestParam("fechaPrestamo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaPrestamo,
			@RequestParam("fechaDevolucion") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDevolucion,
			HttpSession session, RedirectAttributes redirectAttrs) {

		Usuario usuario = (Usuario) session.getAttribute("logueo");
		if (usuario == null) return "redirect:/login";

		String error = validarFechas(fechaPrestamo, fechaDevolucion);
		if (error != null) {
			return volverConError(redirectAttrs, error, "redirect:/reservas/nueva?codLibro=" + codLibro);
		}

		Libro libro = repLibros.findById(codLibro).orElse(null);
		if (libro == null || !Integer.valueOf(1).equals(libro.getEstadoLibro())) {
			return volverConError(redirectAttrs, "EL LIBRO YA NO ESTÁ DISPONIBLE", "redirect:/reservas/nueva");
		}

		libro.setEstadoLibro(0); // el libro reservado pasa a "No disponible"
		repLibros.save(libro);

		Reserva reserva = new Reserva();
		reserva.setCodUsuario(usuario.getCodUsuario());
		reserva.setCodLibro(codLibro);
		reserva.setFechaReserva(LocalDate.now());
		reserva.setFechaPrestamo(fechaPrestamo);
		reserva.setFechaDevolucion(fechaDevolucion);
		reserva.setEstadoReserva(1);
		repReservas.save(reserva);

		redirectAttrs.addFlashAttribute("mensaje", "RESERVA REGISTRADA CORRECTAMENTE");
		redirectAttrs.addFlashAttribute("tipoMensaje", "exito");
		return "redirect:/reservas";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable("id") Integer id, HttpSession session, Model model,
			RedirectAttributes redirectAttrs) {

		Usuario usuario = (Usuario) session.getAttribute("logueo");
		if (usuario == null) return "redirect:/login";

		Reserva reserva = repReservas.findById(id).orElse(null);
		if (!esEditable(reserva, usuario)) {
			return volverConError(redirectAttrs, "LA RESERVA NO SE PUEDE EDITAR", "redirect:/reservas");
		}

		model.addAttribute("reserva", reserva);
		model.addAttribute("hoy", LocalDate.now());
		return "usuario/crudreserva";
	}

	@PostMapping("/actualizar")
	public String actualizar(@RequestParam("codReserva") Integer codReserva,
			@RequestParam("fechaPrestamo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaPrestamo,
			@RequestParam("fechaDevolucion") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaDevolucion,
			HttpSession session, RedirectAttributes redirectAttrs) {

		Usuario usuario = (Usuario) session.getAttribute("logueo");
		if (usuario == null) return "redirect:/login";

		Reserva reserva = repReservas.findById(codReserva).orElse(null);
		if (!esEditable(reserva, usuario)) {
			return volverConError(redirectAttrs, "LA RESERVA NO SE PUEDE EDITAR", "redirect:/reservas");
		}

		String error = validarFechas(fechaPrestamo, fechaDevolucion);
		if (error != null) {
			return volverConError(redirectAttrs, error, "redirect:/reservas/editar/" + codReserva);
		}

		reserva.setFechaPrestamo(fechaPrestamo);
		reserva.setFechaDevolucion(fechaDevolucion);
		repReservas.save(reserva);

		redirectAttrs.addFlashAttribute("mensaje", "RESERVA ACTUALIZADA CORRECTAMENTE");
		redirectAttrs.addFlashAttribute("tipoMensaje", "exito");
		return "redirect:/reservas";
	}

	@PostMapping("/cancelar/{id}")
	@Transactional
	public String cancelar(@PathVariable("id") Integer id, HttpSession session, RedirectAttributes redirectAttrs) {

		Usuario usuario = (Usuario) session.getAttribute("logueo");
		if (usuario == null) return "redirect:/login";

		Reserva reserva = repReservas.findById(id).orElse(null);
		if (!esEditable(reserva, usuario)) {
			return volverConError(redirectAttrs, "LA RESERVA NO SE PUEDE CANCELAR", "redirect:/reservas");
		}

		reserva.setEstadoReserva(0);
		repReservas.save(reserva);

		repLibros.findById(reserva.getCodLibro()).ifPresent(libro -> {
			libro.setEstadoLibro(1);
			repLibros.save(libro);
		});

		redirectAttrs.addFlashAttribute("mensaje", "RESERVA CANCELADA");
		redirectAttrs.addFlashAttribute("tipoMensaje", "exito");
		return "redirect:/reservas";
	}
	
	private boolean esEditable(Reserva reserva, Usuario usuario) {
		return reserva != null && reserva.getCodUsuario().equals(usuario.getCodUsuario())
				&& Integer.valueOf(1).equals(reserva.getEstadoReserva());
	}

	private String validarFechas(LocalDate prestamo, LocalDate devolucion) {
		if (prestamo.isBefore(LocalDate.now())) return "LA FECHA DE PRÉSTAMO NO PUEDE SER ANTERIOR A HOY";
		if (devolucion.isBefore(prestamo)) return "LA FECHA DE DEVOLUCIÓN NO PUEDE SER ANTERIOR A LA DE PRÉSTAMO";
		return null;
	}

	private String volverConError(RedirectAttributes redirectAttrs, String mensaje, String destino) {
		redirectAttrs.addFlashAttribute("mensaje", mensaje);
		redirectAttrs.addFlashAttribute("tipoMensaje", "error");
		return destino;
	}
}
