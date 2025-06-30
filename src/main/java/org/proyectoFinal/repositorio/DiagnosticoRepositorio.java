package org.proyectoFinal.repositorio;

import java.util.List;

import org.proyectoFinal.entidad.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosticoRepositorio extends JpaRepository<Diagnostico, Integer> {
	List<Diagnostico> findByMascotaIdMascota(Integer idMascota);
}
