package org.proyectoFinal.modelo;

import java.util.List;

import org.proyectoFinal.entidad.Citas;
import org.proyectoFinal.entidad.Cliente;
import org.proyectoFinal.repositorio.CitasRepositorio;
import org.proyectoFinal.repositorio.ClienteRepositorio;
import org.proyectoFinal.servicio.CitasUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CitasModel implements CitasUseCase {

    @Autowired
    private CitasRepositorio citasRepo;

    @Override
    public Citas registrarCita(Citas cita) {
        return citasRepo.save(cita);
    }

    @Override
    public List<Citas> listarCitasPorCliente(int idCliente) {
        return citasRepo.findByClienteIdCliente(idCliente);
    }
    @Override
    public Citas buscarPorId(int idCita) {
        return citasRepo.findById(idCita).orElse(null);
    }
    @Override
    public List<Citas> listarTodas() {
        return citasRepo.findAll();
    }
}







