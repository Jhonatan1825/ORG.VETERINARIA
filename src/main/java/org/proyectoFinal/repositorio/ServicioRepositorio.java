package org.proyectoFinal.repositorio;

import org.proyectoFinal.entidad.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepositorio extends JpaRepository<Servicio, Integer> {
    boolean existsByNombreServicio(String nombreServicio);
}
