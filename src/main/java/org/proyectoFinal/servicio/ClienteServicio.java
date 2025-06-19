package org.proyectoFinal.servicio;

import java.util.List;
import java.util.Optional;

import org.proyectoFinal.entidad.Cliente;
import org.proyectoFinal.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServicio {
	@Autowired
	private ClienteRepositorio clienteRepositorio;

	public List<Cliente> listarTodos() {
		return clienteRepositorio.findAll();
	}

	public Optional<Cliente> buscarPorId(int id) {
		return clienteRepositorio.findById(id);
	}

	public Cliente guardar(Cliente cliente) {
		return clienteRepositorio.save(cliente);
	}

	public void eliminar(int id) {
		clienteRepositorio.deleteById(id);
	}

	public boolean existePorDni(String dni) {
		return clienteRepositorio.existsByDni(dni);
	}

	public Cliente buscarPorDni(String dni) {
		return clienteRepositorio.findByDni(dni);
	}

	public boolean validarDniUnico(String dni, Integer id) {
		Cliente clienteExistente = buscarPorDni(dni);
		if (clienteExistente == null) {
			return true; // DNI no existe, es válido
		}
		return clienteExistente.getIdCliente() == id;
	}

}
