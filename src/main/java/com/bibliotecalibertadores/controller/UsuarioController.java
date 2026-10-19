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

@Controller
public class UsuarioController {

	// 1. Menú o Inicio del usuario
	@GetMapping("/inicio")
	public String inicioUsuario() {
	    return "inicio"; 
	}

	@Autowired
	private UsuarioRepository repUsuarios;
	
	@GetMapping("/listadousuarios")
	public String listarUsuarios(Model model) {
		model.addAttribute("lstUsuarios", repUsuarios.findAll());
		return "listadousuarios";
	}
	
	// Mostrar formulario de registro
	@GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        // Inicializa un usuario vacío para que el formulario aparezca despejado
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuario());
        }
        return "registro";
    }
    
 // Procesar el registro
	@PostMapping("/registro")
	public String registrarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttrs) {
	    try {
	        // Intenta guardar en la base de datos MySQL
	        repUsuarios.save(usuario);
	        redirectAttrs.addFlashAttribute("mensajeExito", "REGISTRADO CON ÉXITO");
	    } catch (Exception e) {
	        // Si ocurre un error (ej. DNI, teléfono o correo duplicado)
	        redirectAttrs.addFlashAttribute("mensajeError", "ERROR AL REGISTRAR");
	        redirectAttrs.addFlashAttribute("usuario", usuario); // Mantiene los datos que escribió el usuario
	    }
	    
	    return "redirect:/registro";
	}

    
 // Mostrar formulario de login
    @PostMapping("/login")
    public String procesarLogin(@RequestParam("correo") String correo, 
                                @RequestParam("password") String password, 
                                Model model) {
        
        Usuario usuario = repUsuarios.validarLogin(correo, password);
        
        if (usuario != null) {
            return "redirect:/listadousuarios";
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "login";
        }
    
    }
    
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // Busca login.html en templates
    }
	
	
	
    // 1. Menú o Inicio del usuario
    //@GetMapping({"/", "/inicio"})
   // public String inicioUsuario() {
        //return "inicio"; // Esto buscará un archivo inicio.html dentro de templates/usuario/
    //}

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