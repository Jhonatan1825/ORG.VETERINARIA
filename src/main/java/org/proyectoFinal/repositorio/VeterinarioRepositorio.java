package org.proyectoFinal.repositorio;


import org.proyectoFinal.entidad.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinarioRepositorio extends JpaRepository<Veterinario, Integer>{
	boolean existsByNombre(String nombre); 
}
