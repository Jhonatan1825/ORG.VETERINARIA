package org.proyectoFinal.servicio;

import java.util.List;

import org.proyectoFinal.entidad.Cliente;
import org.proyectoFinal.entidad.Mascotas;
import org.proyectoFinal.repositorio.MascotaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MascotaServicio {
	
	@Autowired
	private MascotaRepositorio mascotaRepositorio;

	@Autowired
	private ClienteServicio clienteServicio;

	public List<Mascotas> listarTodos() {
		return mascotaRepositorio.findAll();
	}
	
	public Mascotas guardar(Mascotas mascotas) {
		return mascotaRepositorio.save(mascotas);
	}

	public void eliminar(int id) {
		mascotaRepositorio.deleteById(id);
	}
	

	public List<Mascotas> listarPorCliente(int idCliente) {
		Cliente cliente = clienteServicio.buscarPorId(idCliente).orElse(null);
		if (cliente == null) {
			return List.of(); // lista vacía si no se encuentra cliente
		}
		return mascotaRepositorio.findByCliente(cliente);
	}
}
