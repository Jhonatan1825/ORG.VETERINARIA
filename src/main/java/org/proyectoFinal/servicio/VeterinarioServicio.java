package org.proyectoFinal.servicio;

import java.util.List;
import java.util.Optional;


import org.proyectoFinal.entidad.Veterinario;
import org.proyectoFinal.repositorio.VeterinarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VeterinarioServicio {

	@Autowired
	private VeterinarioRepositorio veterinarioRepositorio;
	
	public List<Veterinario>listartodos(){
		return veterinarioRepositorio.findAll()	;	
	}
	   public List<Veterinario> listarTodos() {
	        return veterinarioRepositorio.findAll();
	    }
	public Optional<Veterinario> buscarPorId(int id) {
		return veterinarioRepositorio.findById(id);
	}
	
	public Veterinario guardar(Veterinario veterinario) {
		return veterinarioRepositorio.save(veterinario);
	}
	
	public void eliminar(int id) {
		veterinarioRepositorio.deleteById(id);
	}

	public boolean existePorNombre(String dni) {
		return veterinarioRepositorio.existsByNombre(dni);
	}
	
	//public Veterinario buscarPorDni(String dni) {
	//}

	/*public boolean validarDniUnico(String dni, Integer id) {
		Veterinario veterinarioExistente = buscarPorDni(dni);
		if (veterinarioExistente == null) {
			return true; // DNI no existe, es válido
		}
		return veterinarioExistente.getId_veterinario() == id;
	}*/

}
