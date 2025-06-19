package org.proyectoFinal.repositorio;

import java.util.Optional;

import org.proyectoFinal.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByUsername(String username);
}
