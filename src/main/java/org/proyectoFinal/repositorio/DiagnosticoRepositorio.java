package org.proyectoFinal.repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.proyectoFinal.entidad.Diagnostico;

@Repository
public interface DiagnosticoRepositorio extends JpaRepository<Diagnostico, Integer> {
    List<Diagnostico> findByMascotaIdMascota(Integer idMascota);
}
