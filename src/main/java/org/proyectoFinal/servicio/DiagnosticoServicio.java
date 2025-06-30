package org.proyectoFinal.servicio;

import java.util.List;
import java.util.Optional;

import org.proyectoFinal.entidad.Diagnostico;
import org.proyectoFinal.repositorio.DiagnosticoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiagnosticoServicio {

    @Autowired
    private DiagnosticoRepositorio diagnosticoRepositorio;

    public Diagnostico guardar(Diagnostico diagnostico) {
        return diagnosticoRepositorio.save(diagnostico);
    }

    public List<Diagnostico> listarTodos() {
        return diagnosticoRepositorio.findAll();
    }

    public Optional<Diagnostico> buscarPorId(Integer id) {
        return diagnosticoRepositorio.findById(id);
    }

    public List<Diagnostico> listarPorMascota(Integer idMascota) {
        return diagnosticoRepositorio.findByMascotaIdMascota(idMascota);
    }
}
