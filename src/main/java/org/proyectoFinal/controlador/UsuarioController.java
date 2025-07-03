
package org.proyectoFinal.controlador;

import org.proyectoFinal.entidad.Rol;
import org.proyectoFinal.entidad.Usuario;
import org.proyectoFinal.repositorio.RolRepositorio;
import org.proyectoFinal.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepositorio usuarioRepo;

    @Autowired
    private RolRepositorio rolRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String rol) {

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(password));
        usuario.setActivo(true);

        Rol rolUsuario = rolRepo.findByNombre(rol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        usuario.setRoles(new HashSet<>(Collections.singletonList(rolUsuario)));
        usuarioRepo.save(usuario);
        System.out.println("Usuario registrado: " + username);


        return "redirect:/login";
    }
    @GetMapping("/")
    public String mostrarBienvenido(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            Usuario usuario = usuarioRepo.findByUsername(username).orElse(null);
            model.addAttribute("usuario", usuario);
        }
        return "bienvenido";
    }    
}
