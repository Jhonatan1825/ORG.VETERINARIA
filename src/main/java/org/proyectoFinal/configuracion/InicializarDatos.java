package org.proyectoFinal.configuracion;

import org.proyectoFinal.entidad.Usuario;
import org.proyectoFinal.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InicializarDatos implements CommandLineRunner {
	
	@Autowired
	private UsuarioServicio usuarioServicio;

	@Override
	public void run(String... args) throws Exception {
		if (usuarioServicio.obtnerUsuario("admin") == null) {
			Usuario usuario = new Usuario(null, "admin", "1234", "Administrador");
			usuarioServicio.guardarUsuario(usuario);
			System.out.println("Usuario admin creado.");
		} else {
			System.out.println("Usuario admin ya existe.");
		}

		if (usuarioServicio.obtnerUsuario("user") == null) {
			Usuario usuario2 = new Usuario(null, "user", "password", "Usuario Normal");
			usuarioServicio.guardarUsuario(usuario2);
			System.out.println("Usuario user creado.");
		} else {
			System.out.println("Usuario user ya existe.");
		}
	}
}
