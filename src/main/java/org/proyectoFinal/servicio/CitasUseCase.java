package org.proyectoFinal.servicio;

import java.util.List;

import org.proyectoFinal.entidad.Citas;

public interface CitasUseCase {
    Citas registrarCita(Citas cita);
    List<Citas> listarCitasPorCliente(int idCliente);
    Citas buscarPorId(int idCita);
    void eliminarPorId(int idCita);

   
    List<Citas> listarTodas();
}