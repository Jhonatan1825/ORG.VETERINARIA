package org.proyectoFinal.repositorio;

import org.proyectoFinal.entidad.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Integer>{
	boolean existsByDni(String dni);
    Cliente findByDni(String dni);
}
