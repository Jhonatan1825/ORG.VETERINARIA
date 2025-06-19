package org.proyectoFinal.repositorio;

import java.util.List;

import org.proyectoFinal.entidad.Cliente;
import org.proyectoFinal.entidad.Mascotas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepositorio extends JpaRepository<Mascotas, Integer > {

	 List<Mascotas> findByCliente(Cliente cliente);
}
