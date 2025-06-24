package org.proyectoFinal.servicio;

import java.util.List;
import java.util.Optional;

import org.proyectoFinal.entidad.Servicio;
import org.proyectoFinal.repositorio.ServicioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioServicio {

    @Autowired
    private ServicioRepositorio servicioRepositorio;

    /** Lista todos los servicios */
    public List<Servicio> listarTodos() {
        return servicioRepositorio.findAll();
    }

    /** Guarda o actualiza un servicio */
    public Servicio guardar(Servicio servicio) {
        return servicioRepositorio.save(servicio);
    }

    /** Busca un servicio por su ID */
    public Optional<Servicio> buscarPorId(int id) {
        return servicioRepositorio.findById(id);
    }

    /** Elimina un servicio por su ID */
    public void eliminar(int id) {
        servicioRepositorio.deleteById(id);
    }

    /** Verifica si existe un servicio por nombre */
    public boolean existePorNombre(String nombreServicio) {
        return servicioRepositorio.existsByNombreServicio(nombreServicio);
    }
}
