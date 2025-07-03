package org.proyectoFinal.controlador;

import java.security.Principal;

import org.proyectoFinal.entidad.Usuario;
import org.proyectoFinal.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class LoginController {

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/inicio") // Este será tu inicio tras el login
    public String mostrarBienvenido(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName(); // nombre del usuario autenticado
            Usuario usuario = usuarioRepo.findByUsername(username).orElse(null);
            model.addAttribute("usuario", usuario); // lo mandamos a la vista
        }
        return "bienvenido";
    }
}
