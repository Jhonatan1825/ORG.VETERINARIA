package org.proyectoFinal.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.proyectoFinal.entidad.Citas;
import java.util.List;


	@Repository
	public interface CitasRepositorio extends JpaRepository<Citas, Integer> {
		List<Citas> findByClienteIdCliente(int idCliente);
	}
