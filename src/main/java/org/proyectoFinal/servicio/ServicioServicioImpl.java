package org.proyectoFinal.servicio;

import java.util.List;

import org.proyectoFinal.entidad.Servicio;
import org.proyectoFinal.repositorio.ServicioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioServicioImpl implements ServicioServicio {

    @Autowired
    private ServicioRepositorio servicioRepositorio;

    @Override
    public List<Servicio> listarTodos() {
        return servicioRepositorio.findAll();
    }
}
