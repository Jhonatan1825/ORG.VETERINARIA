package org.proyectoFinal.controlador;

import org.proyectoFinal.entidad.Usuario;
import org.proyectoFinal.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	@Autowired
	private UsuarioServicio usuarioServicio;
	
	@GetMapping("/")
	public String inicio() {
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String mostrarLogin(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "login";
	}
	
	@PostMapping("/login")
	public String procesarLogin(@ModelAttribute Usuario usuario, Model model, HttpSession session) {
		if(usuarioServicio.validarUsuario(usuario.getUsername(), usuario.getPassword())) {
			Usuario usuarioLogueado = usuarioServicio.obtnerUsuario(usuario.getUsername());
			session.setAttribute("usuario", usuarioLogueado);
			return "redirect:/bienvenido";
		}else {
			model.addAttribute("error", "Usuario o contraseña incorrecta");
			return "login";
		}
		
	}
	
	@GetMapping("/bienvenido")
	public String bienvenido(HttpSession session, Model model) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if(usuario == null) {
			return "redirect:/login";
		}
		model.addAttribute("usuario", usuario);
		return "bienvenido";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
	
}





