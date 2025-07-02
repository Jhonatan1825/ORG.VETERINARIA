package org.proyectoFinal.repositorio;

import org.proyectoFinal.entidad.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepositorio extends JpaRepository<Factura, Integer> {
    // Si en el futuro quieres buscar facturas por cliente, cita, etc., puedes agregar métodos personalizados aquí
}
