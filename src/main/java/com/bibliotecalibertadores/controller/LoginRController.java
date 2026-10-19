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
import jakarta.servlet.http.HttpSession;

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
            @RequestParam String password, HttpSession session, Model model) {
        
    	Usuario usuario = repUsuarios.findByCorreoUsuarioAndPasswordUsuario(correo, password);
        
        if (usuario != null) {
            session.setAttribute("logueo", usuario); 
            return "redirect:/inicio";
        } else {
            model.addAttribute("error", "Correo o contraseña incorrectos");
            return "loginregistro/login";
        }
    }

    // Registro 
    @GetMapping("/registro")
    public String formularioRegistro(Model model) {
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuario());
        }
        return "loginregistro/registro";
    }
    
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario, 
    		RedirectAttributes redirectAttrs, Model model) {
        try {
            repUsuarios.save(usuario);
            model.addAttribute("mensaje", "REGISTRADO CON ÉXITO");
            model.addAttribute("cssmensaje","alert alert-success");
        } catch (Exception e) {
            model.addAttribute("mensaje", "ERROR AL REGISTRAR");
            model.addAttribute("cssmensaje", "alert alert-danger"); 
        }
        return "redirect:/registro";
    }
}
