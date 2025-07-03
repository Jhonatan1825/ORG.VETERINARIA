package org.proyectoFinal.repositorio;

import java.util.Optional;

import org.proyectoFinal.entidad.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombre(String nombre);
}
