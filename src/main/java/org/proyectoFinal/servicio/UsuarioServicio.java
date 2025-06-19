package org.proyectoFinal.servicio;

import java.util.Optional;

import org.proyectoFinal.entidad.Usuario;
import org.proyectoFinal.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio {
	@Autowired
	private  UsuarioRepositorio usuarioRepositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public boolean validarUsuario(String  username, String password) {
		Optional<Usuario> usuario = usuarioRepositorio.findByUsername(username);
		if(usuario.isPresent()) {
			return passwordEncoder.matches(password, usuario.get().getPassword());
		}
		return false;
	}
	
	public Usuario obtnerUsuario(String username) {
		return usuarioRepositorio.findByUsername(username).orElse(null);
	}
	

	public Usuario guardarUsuario(Usuario usuario) {
	    String password = usuario.getPassword();

	    if (password == null || password.isBlank()) {
	        throw new IllegalArgumentException("La contraseña no puede ser nula o vacía.");
	    }
	    if (!(password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$"))) {
	        usuario.setPassword(passwordEncoder.encode(password));
	    }

	    return usuarioRepositorio.save(usuario);
	}


}
