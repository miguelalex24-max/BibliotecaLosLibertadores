package com.bibliotecalibertadores.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bibliotecalibertadores.model.Usuario;
import com.bibliotecalibertadores.repository.UsuarioRepository;

@Controller
public class LoginRController {
	
    @Autowired
    private UsuarioRepository repUsuarios;

    @GetMapping("/")
    public String principal() {
        return "redirect:/login";
    }
    
    //Inicio de sesion
    @GetMapping("/login")
    public String mostrarLogin() {
        return "loginregistro/login"; 
    }
    
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String correo, 
            @RequestParam String password, Model model) {
        
    	Usuario usuario = repUsuarios.findByCorreoUsuarioAndPasswordUsuario(correo, password);
        
        if (usuario != null) {
            return "redirect:/inicio";
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "loginregistro/login";
        }
    }

    // Registro 
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuario());
        }
        return "loginregistro/registro";
    }
    
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, 
    		RedirectAttributes redirectAttrs) {
        try {
            repUsuarios.save(usuario);
            redirectAttrs.addFlashAttribute("mensajeExito", "REGISTRADO CON ÉXITO");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "ERROR AL REGISTRAR");
            redirectAttrs.addFlashAttribute("usuario", usuario); 
        }
        return "redirect:/registro";
    }
}
